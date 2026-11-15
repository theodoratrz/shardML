package io.shardml.tests;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.CostModel;
import io.shardml.cost.CostReport;
import io.shardml.cost.DistributedTransactions;
import io.shardml.cost.Layout;
import io.shardml.cost.RankMapping;
import io.shardml.cost.Workload;
import io.shardml.placement.Node;
import io.shardml.placement.ShardSlot;

// Step 7 (Plan-phase3 4.3 M1, M2; 4.3a): the hand-computable fixtures of 6.1 with bucket(v) = (v - 1) mod 4,
// buckets 0 and 2 on n1, 1 and 3 on n2, so customers 1, 3 live on n1 and 2, 4 on n2.
public class CostModelDistributedTest {

	private static final CostModel MODEL = new CostModel(CostFixtures.MOD);

	static final String HEAD = """
			import "s.msql"
			workload t {
			    domain customer { size: 4 }
			    domain account { size: 8 within customer zipf(1) }
			    domain country { size: 3 }
			    table customers { rows: per customer id: customer }
			    table accounts { rows: per account id: account customer_id: account.customer }
			    table countries { rows: per country code: country }
			""";

	static final String SCHEMA = CostFixtures.SCHEMA + "CREATE TABLE countries (code INT PRIMARY KEY)\n";

	static final String LAYOUT_C = CostFixtures.LAYOUT_C.replace("    accounts belongs_to",
			"    shard countries { strategy: replicated }\n    accounts belongs_to");
	static final String LAYOUT_A = CostFixtures.LAYOUT_A.replace("    shard accounts",
			"    shard countries { strategy: replicated }\n    shard accounts");

	private static final String T6 = """
			    transaction transfer weight 1 {
			        let a = customer uniform
			        let b = customer uniform distinct from $a
			        write customers where id = $a
			        write customers where id = $b
			    }
			""";

	private static final String T7 = T6.replace("transfer", "lookup").replace("write customers where id = $b",
			"read customers where id = $b");

	private static CostReport run(String layout, String transactions) {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", SCHEMA);
		Layout l = f.layout("l.shardml", layout);
		Workload w = f.workload("w.workload", HEAD + transactions + "}\n");
		return MODEL.evaluate(l, w, 0);
	}

	private static String fmt(double v) {
		return CostModelSkewLoadTest.fmt(v);
	}

	private static void assertM1(String twoPhase, String multiNode, CostReport r) {
		Assertions.assertEquals(twoPhase, fmt(r.distributed().twoPhase()), "2PC");
		Assertions.assertEquals(multiNode, fmt(r.distributed().multiNode()), "multi-node");
	}

	@Test
	public void testT6Transfer() {
		// 12 ordered pairs, 4 on one node: 8/12
		assertM1("0.666667", "0.666667", run(LAYOUT_C, T6));
	}

	@Test
	public void testT7SecondStatementARead() {
		assertM1("0.000000", "0.666667", run(LAYOUT_C, T7));
	}

	@Test
	public void testT8T18ElseWithExcludesTheBase() {
		CostReport r = run(LAYOUT_C, T6.replace("let b = customer uniform distinct from $a", "let b = $a, else with 0.25: customer uniform"));
		// 0.25 * 8/12; a draw that could return the base would give 0.25 * 8/16 = 0.125
		Assertions.assertEquals("0.166667", fmt(r.distributed().twoPhase()), "else with must exclude the base: 0.166667, not 0.125");
		Assertions.assertEquals("0.166667", fmt(r.distributed().multiNode()));
	}

	@Test
	public void testT9Repeat() {
		// each i on a's node with probability 1/2, twice: local 1/4
		assertM1("0.750000", "0.750000", run(LAYOUT_C, """
				    transaction r weight 1 {
				        let a = customer uniform
				        write customers where id = $a
				        repeat 2 {
				            let i = customer uniform
				            write customers where id = $i
				        }
				    }
				"""));
	}

	@Test
	public void testT10ReferenceWrite() {
		CostReport r = run(LAYOUT_C, """
				    transaction r weight 1 {
				        let k = country uniform
				        write countries where code = $k
				    }
				""");
		assertM1("1.000000", "1.000000", r);
		Assertions.assertEquals(1, r.fanOut().max());
	}

	@Test
	public void testT11Weights() {
		CostReport r = run(LAYOUT_C, T6.replace("weight 1", "weight 3") + T7);
		// 0.75 * 0.666667 + 0.25 * 0
		assertM1("0.500000", "0.666667", r);
		Assertions.assertEquals(List.of("transfer", "lookup"), r.transactions().stream().map(t -> t.name()).toList());
		Assertions.assertEquals("0.750000", fmt(r.transactions().get(0).probability()));
		Assertions.assertEquals("0.000000", fmt(r.transactions().get(1).twoPhase()));
		Assertions.assertEquals("0.666667", fmt(r.transactions().get(1).multiNode()));
	}

	@Test
	public void testT12RepartitionFlaggedAndExcluded() {
		String join = """
				    transaction j weight 1 {
				        let c = customer uniform
				        write customers where id = $c
				        read accounts join customers on accounts.customer_id = customers.id where customers.id = $c
				    }
				""";
		CostReport a = run(LAYOUT_A, join);
		Assertions.assertEquals(1, a.repartitions().size());
		Assertions.assertEquals(2, a.repartitions().get(0).statement());
		// only the write remains: one node, fan-out 1 over one statement
		assertM1("0.000000", "0.000000", a);
		Assertions.assertEquals("1.000000", fmt(a.fanOut().mean()));
		Assertions.assertEquals(1, a.fanOut().max());
		// in C the join is colocated and routed by the same customer: still one node
		CostReport c = run(LAYOUT_C, join);
		Assertions.assertEquals(List.of(), c.repartitions());
		assertM1("0.000000", "0.000000", c);
	}

	@Test
	public void testT20ThroughTheFactorizedRoute() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", SCHEMA);
		Layout l = f.layout("l.shardml", LAYOUT_C);
		Workload w = f.workload("w.workload", HEAD + T6 + "}\n");
		double[] z = new DistributedTransactions(l, RankMapping.of(w, 0), CostFixtures.MOD).within(w.transactions().get(0), false);
		// z(empty) = 0; z({n1}) = P[a, b both on n1] = (2/4)(1/3) = 1/6; z({n2}) = 1/6; local = 1/3
		Assertions.assertEquals(0, z[0], 1e-12);
		Assertions.assertEquals(1.0 / 6, z[1], 1e-12);
		Assertions.assertEquals(1.0 / 6, z[2], 1e-12);
		Assertions.assertEquals("0.666667", fmt(1 - (z[1] + z[2] - z[0])));
	}

	@Test
	public void testT21SkewedDistinctFrom() {
		String writes = T6.replace("customer uniform\n", "customer zipf(1)\n").replace("customer uniform distinct", "customer zipf(1) distinct");
		// sum_a p(a) p(same-node other) / (1 - p(a)) = 0.309743; distributed 0.690257
		assertM1("0.690257", "0.690257", run(LAYOUT_C, writes));
		assertM1("0.000000", "0.690257", run(LAYOUT_C, writes.replace("write customers where id = $b", "read customers where id = $b")));
	}

	@Test
	public void testTrap1OneVariableTwoGroupsOnDifferentPlacements() {
		String x = """
				    transaction x weight 1 {
				        let x = account uniform
				        write accounts where id = $x
				        write customers where id = $x.customer
				    }
				""";
		// layout A: accounts by id (odd on n1), customers by id; x's customers (T1): 1-4 -> 1, 5-6 -> 2,
		// 7 -> 3, 8 -> 4. Node pairs differ for x = 2, 4, 5: 3/8
		CostFixtures f = new CostFixtures();
		f.load("s.msql", SCHEMA);
		Layout layout = f.layout("l.shardml", LAYOUT_A);
		Workload w = f.workload("w.workload", HEAD + x + "}\n");
		CostReport r = MODEL.evaluate(layout, w, 0);
		assertM1("0.375000", "0.375000", r);
		// the accounts group moved: buckets 0, 2 on n2 and 1, 3 on n1; now odd accounts on n2: x = 1, 3, 6, 7, 8 differ
		Node n1 = layout.nodes().get(0);
		Node n2 = layout.nodes().get(1);
		Map<ShardSlot, Node> moved = new LinkedHashMap<>(layout.placement().assignments());
		for (int b = 0; b < 4; b++)
			moved.put(new ShardSlot("accounts", b), b % 2 == 0 ? n2 : n1);
		assertM1("0.625000", "0.625000", MODEL.evaluate(layout.withPlacement(moved), w, 0));
	}

	@Test
	public void testTrap2ReferenceReadsOnly() {
		String reads = """
				    transaction r weight 1 {
				        let k = country uniform
				        read countries where code = $k
				        read countries where code = $k
				    }
				""";
		// empty node sets: within every node; at most one node with probability 1
		assertM1("0.000000", "0.000000", run(LAYOUT_C, reads));
		assertM1("0.000000", "0.000000", run(oneNode(LAYOUT_C), reads));
		// and a reference write on one node is local
		assertM1("0.000000", "0.000000", run(oneNode(LAYOUT_C), reads.replace("read countries where code = $k\n        read",
				"read countries where code = $k\n        write")));
	}

	private static String oneNode(String layout) {
		return layout.replace("    node n2 { host: \"h2\" port: 5432 }\n", "");
	}

	@Test
	public void testOneNode() {
		assertM1("0.000000", "0.000000", run(oneNode(LAYOUT_C), T6));
	}

	@Test
	public void testUnroutedWrite() {
		String w = """
				    transaction u weight 1 {
				        let c = customer uniform
				        write accounts where customer_id = $c
				    }
				""";
		// layout A: accounts by id, so customer_id does not route; its buckets are on both nodes
		CostReport a = run(LAYOUT_A, w);
		assertM1("1.000000", "1.000000", a);
		Assertions.assertEquals("4.000000", fmt(a.fanOut().mean()));
		Assertions.assertEquals("1.000000", fmt(a.fanOut().multiShardShare()));
		Assertions.assertEquals(4, a.fanOut().max());
		// pinned on one node, the unrouted write is local
		CostFixtures f = new CostFixtures();
		f.load("s.msql", SCHEMA);
		Layout layout = f.layout("l.shardml", LAYOUT_A);
		Workload workload = f.workload("w.workload", HEAD + w + "}\n");
		Map<ShardSlot, Node> pinned = new LinkedHashMap<>(layout.placement().assignments());
		for (int b = 0; b < 4; b++)
			pinned.put(new ShardSlot("accounts", b), layout.nodes().get(0));
		assertM1("0.000000", "0.000000", MODEL.evaluate(layout.withPlacement(pinned), workload, 0));
	}

	@Test
	public void testFanOut() {
		CostReport c = run(LAYOUT_C, """
				    transaction read weight 1 {
				        let c = customer zipf(1)
				        read accounts where customer_id = $c
				    }
				""");
		Assertions.assertEquals("1.000000", fmt(c.fanOut().mean()));
		Assertions.assertEquals("0.000000", fmt(c.fanOut().multiShardShare()));
		Assertions.assertEquals(1, c.fanOut().max());
		// a routed statement and an unrouted one (4 tasks), weights 1 and 3, the second in a repeat 2:
		// statements 1 * 1 + 3 * 2 = 7 executions per 4 weight, tasks 1 + 3 * 2 * 4 = 25 -> 25 / 7; multi-shard 6 / 7
		CostReport mix = run(LAYOUT_A, """
				    transaction one weight 1 {
				        let c = customer uniform
				        read customers where id = $c
				    }
				    transaction two weight 3 {
				        repeat 2 {
				            let c = customer uniform
				            read accounts where customer_id = $c
				        }
				    }
				""");
		Assertions.assertEquals(fmt(25.0 / 7), fmt(mix.fanOut().mean()));
		Assertions.assertEquals(fmt(6.0 / 7), fmt(mix.fanOut().multiShardShare()));
		Assertions.assertEquals("4.000000", fmt(mix.transactions().get(1).fanOutMean()));
		Assertions.assertEquals("8.000000", fmt(mix.transactions().get(1).touches()));
	}

	@Test
	public void testReportFormatsAreDeterministic() {
		CostReport a = run(LAYOUT_C, T6.replace("weight 1", "weight 3") + T7);
		CostReport b = run(LAYOUT_C, T6.replace("weight 1", "weight 3") + T7);
		Assertions.assertEquals(a, b);
		String table = io.shardml.cost.CostReportFormat.table(a);
		String json = io.shardml.cost.CostReportFormat.json(a);
		Assertions.assertEquals(table, io.shardml.cost.CostReportFormat.table(b));
		Assertions.assertEquals(json, io.shardml.cost.CostReportFormat.json(b));
		// a default locale with a decimal comma changes nothing
		java.util.Locale saved = java.util.Locale.getDefault();
		try {
			java.util.Locale.setDefault(java.util.Locale.GERMANY);
			Assertions.assertEquals(table, io.shardml.cost.CostReportFormat.table(a));
			Assertions.assertEquals(json, io.shardml.cost.CostReportFormat.json(a));
		} finally {
			java.util.Locale.setDefault(saved);
		}
		Assertions.assertEquals("""
				shardml cost: c x t   nodes: n1, n2   rank offset: 0
				metric                          value     note
				distributed txns (2PC)          0.500000
				multi-node txns                 0.666667
				fan-out (mean tasks/stmt)       1.000000  multi-shard 0.000000, max 1
				data skew (max/mean rows)       1.166667  max on n1
				bottleneck load (touches/txn)   1.000000  on n1
				load skew (max/mean touches)    1.000000  max on n1
				repartition joins               none

				node                   rows           load           read          write
				n1                        7       1.000000       0.125000       0.875000
				n2                        5       1.000000       0.125000       0.875000

				transaction               p        2PC multi-node    fan-out    max    touches
				transfer           0.750000   0.666667   0.666667   1.000000      1   2.000000
				lookup             0.250000   0.000000   0.666667   1.000000      1   2.000000
				""", table);
		Assertions.assertTrue(json.startsWith("{\n  \"layout\": \"c\",\n  \"workload\": \"t\",\n  \"nodes\": [\n    \"n1\",\n    \"n2\"\n  ],\n"), json);
		Assertions.assertTrue(json.contains("\"distributed\": {\n    \"twoPhase\": 0.500000,\n    \"multiNode\": 0.666667\n  }"), json);
		Assertions.assertTrue(json.contains("\"params\": [],"), json);
		Assertions.assertTrue(json.endsWith("  ]\n}\n"), json);
		Assertions.assertEquals(json.chars().filter(ch -> ch == '{').count(), json.chars().filter(ch -> ch == '}').count());
		Assertions.assertEquals(json.chars().filter(ch -> ch == '[').count(), json.chars().filter(ch -> ch == ']').count());
	}

	@Test
	public void testBudgetCoversTheNewLoops() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		Layout layout = f.layout("c.shardml", CostFixtures.LAYOUT_C);
		Workload w = f.workload("w.workload", CostFixtures.WORKLOAD);
		// transaction 'read' (let c over 4 customers, one read bound to it, 2 nodes): Step 6 counts 8; M1 adds,
		// per scope, 4 * 3 for the root, and 4 for single() where the read is in scope (all statements): 12 + 16.
		// 36 in all; at that limit evaluate passes, one below it fails, though skewAndLoad still passes
		new CostModel(CostFixtures.MOD, 36, 1000).evaluate(layout, w, 0);
		new CostModel(CostFixtures.MOD, 35, 1000).skewAndLoad(layout, w, 0);
		Assertions.assertEquals("The cost of transaction 'read' needs 36 steps; the limit is 35 per template.",
				Assertions.assertThrows(IllegalArgumentException.class,
						() -> new CostModel(CostFixtures.MOD, 35, 1000).evaluate(layout, w, 0)).getMessage());
	}
}
