package io.shardml.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.CostModel;
import io.shardml.cost.Layout;
import io.shardml.cost.Marginals;
import io.shardml.cost.RankMapping;
import io.shardml.cost.SkewAndLoad;
import io.shardml.cost.SkewAndLoad.NodeRows;
import io.shardml.cost.SkewAndLoad.NodeValue;
import io.shardml.cost.SkewAndLoad.TableRows;
import io.shardml.cost.Workload;

// Step 6 (Plan-phase3 4.3 M3, M4; 4.4c): the hand-computable fixtures of 6.1 with
// bucket(v) = (v - 1) mod 4, buckets 0 and 2 on n1, 1 and 3 on n2; zipf(1) over 4 is 0.48, 0.24, 0.16, 0.12.
public class CostModelSkewLoadTest {

	private static final double EPS = 1e-9;
	private static final CostModel MODEL = new CostModel(CostFixtures.MOD);

	private static String workload(String transactions) {
		return CostFixtures.WORKLOAD.substring(0, CostFixtures.WORKLOAD.indexOf("    transaction")) + transactions + "}\n";
	}

	private static final String ZIPF_READ = """
			    transaction read weight 1 {
			        let c = customer zipf(1)
			        read accounts where customer_id = $c
			    }
			""";

	private static final String UNIFORM_READ = ZIPF_READ.replace("zipf(1)", "uniform");

	private static SkewAndLoad run(String layout, String workload, long offset) {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		Layout l = f.layout("l.shardml", layout);
		Workload w = f.workload("w.workload", workload);
		return MODEL.skewAndLoad(l, w, offset);
	}

	private static void assertLoad(SkewAndLoad r, double n1, double n2) {
		Assertions.assertEquals(n1, r.load().perNode().get(0).value(), EPS, "n1");
		Assertions.assertEquals(n2, r.load().perNode().get(1).value(), EPS, "n2");
	}

	@Test
	public void testT2T3DataSkew() {
		SkewAndLoad c = run(CostFixtures.LAYOUT_C, workload(ZIPF_READ), 0);
		Assertions.assertEquals(List.of(new NodeRows("n1", 7), new NodeRows("n2", 5)), c.data().rowsPerNode());
		Assertions.assertEquals("1.166667", fmt(c.data().maxOverMean()));
		Assertions.assertEquals("n1", c.data().maxNode());
		Assertions.assertEquals(List.of(new TableRows("customers", false, List.of(new NodeRows("n1", 2), new NodeRows("n2", 2))),
				new TableRows("accounts", false, List.of(new NodeRows("n1", 5), new NodeRows("n2", 3)))), c.data().tables());
		SkewAndLoad a = run(CostFixtures.LAYOUT_A, workload(ZIPF_READ), 0);
		Assertions.assertEquals(List.of(new NodeRows("n1", 6), new NodeRows("n2", 6)), a.data().rowsPerNode());
		Assertions.assertEquals("1.000000", fmt(a.data().maxOverMean()));
	}

	@Test
	public void testT4RoutedZipfRead() {
		SkewAndLoad r = run(CostFixtures.LAYOUT_C, workload(ZIPF_READ), 0);
		// customers 1, 3 on n1: 0.48 + 0.16; 2, 4 on n2: 0.24 + 0.12
		assertLoad(r, 0.64, 0.36);
		Assertions.assertEquals("1.280000", fmt(r.load().maxOverMean()));
		Assertions.assertEquals("0.640000", fmt(r.load().bottleneck()));
		Assertions.assertEquals("n1", r.load().maxNode());
		Assertions.assertEquals(1.0, r.load().total(), EPS);
		Assertions.assertEquals(List.of(new NodeValue("n1", 0), new NodeValue("n2", 0)), r.load().writePerNode());
	}

	@Test
	public void testT5UnroutedRead() {
		// accounts by id: customer_id = $c does not route; 4 buckets, 2 per node
		SkewAndLoad r = run(CostFixtures.LAYOUT_A, workload(ZIPF_READ), 0);
		assertLoad(r, 2, 2);
		Assertions.assertEquals("1.000000", fmt(r.load().maxOverMean()));
		Assertions.assertEquals("2.000000", fmt(r.load().bottleneck()));
	}

	@Test
	public void testT14SameSkewDifferentWork() {
		SkewAndLoad c = run(CostFixtures.LAYOUT_C, workload(UNIFORM_READ), 0);
		SkewAndLoad a = run(CostFixtures.LAYOUT_A, workload(UNIFORM_READ), 0);
		Assertions.assertEquals("1.000000", fmt(c.load().maxOverMean()));
		Assertions.assertEquals("1.000000", fmt(a.load().maxOverMean()));
		Assertions.assertEquals("0.500000", fmt(c.load().bottleneck()));
		Assertions.assertEquals("2.000000", fmt(a.load().bottleneck()));
	}

	@Test
	public void testT17LoadUnderRankOffset() {
		// offset 1: ranks 1..4 are values 2, 3, 4, 1: n2 gets 0.48 + 0.16, n1 0.24 + 0.12
		SkewAndLoad r = run(CostFixtures.LAYOUT_C, workload(ZIPF_READ), 1);
		assertLoad(r, 0.36, 0.64);
		Assertions.assertEquals("1.280000", fmt(r.load().maxOverMean()));
		Assertions.assertEquals("n2", r.load().maxNode());
		// and its data skew (T17): the big customer, rank 1, is now customer 2 on n2
		Assertions.assertEquals(List.of(new NodeRows("n1", 5), new NodeRows("n2", 7)), r.data().rowsPerNode());
		Assertions.assertEquals("n2", r.data().maxNode());
	}

	@Test
	public void testReferenceReadsAndWrites() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA + "CREATE TABLE countries (code INT PRIMARY KEY)\n");
		Layout layout = f.layout("l.shardml",
				CostFixtures.LAYOUT_C.replace("    accounts belongs_to", "    shard countries { strategy: replicated }\n    accounts belongs_to"));
		String body = """
				import "s.msql"
				workload t {
				    domain customer { size: 4 }
				    domain account { size: 8 within customer zipf(1) }
				    domain country { size: 5 }
				    table customers { rows: per customer id: customer }
				    table accounts { rows: per account id: account customer_id: account.customer }
				    table countries { rows: per country code: country }
				""";
		Workload reads = f.workload("r.workload", body + """
				    transaction r weight 1 {
				        let k = country uniform
				        read countries where code = $k
				    }
				}
				""");
		SkewAndLoad r = MODEL.skewAndLoad(layout, reads, 0);
		// a reference read: one task on any placement, 1/n per node (stated assumption)
		assertLoad(r, 0.5, 0.5);
		Assertions.assertEquals(1.0, r.load().total(), EPS);
		// listed per node and per table, excluded from the skew rows (still 7 / 5)
		Assertions.assertEquals(List.of(new NodeRows("n1", 7), new NodeRows("n2", 5)), r.data().rowsPerNode());
		Assertions.assertEquals(new TableRows("countries", true, List.of(new NodeRows("n1", 5), new NodeRows("n2", 5))),
				r.data().tables().get(2));
		Workload writes = f.workload("v.workload", body + """
				    transaction w weight 1 {
				        let k = country uniform
				        write countries where code = $k
				    }
				}
				""");
		SkewAndLoad w = MODEL.skewAndLoad(layout, writes, 0);
		// a reference write commits on every node
		assertLoad(w, 1, 1);
		Assertions.assertEquals(List.of(new NodeValue("n1", 1), new NodeValue("n2", 1)), w.load().writePerNode());
	}

	private static final String JOIN = """
			    transaction j weight 1 {
			        let c = customer uniform
			        read accounts join customers on accounts.customer_id = customers.id where customers.id = $c
			    }
			""";

	@Test
	public void testRepartitionJoinIsExcluded() {
		// layout A: accounts and customers are separate groups; the join is a repartition
		SkewAndLoad a = run(CostFixtures.LAYOUT_A, workload(JOIN), 0);
		assertLoad(a, 0, 0);
		Assertions.assertEquals("0.000000", fmt(a.load().maxOverMean()));
		Assertions.assertEquals(1, a.repartitions().size());
		Assertions.assertEquals("j", a.repartitions().get(0).transaction());
		Assertions.assertEquals(1, a.repartitions().get(0).statement());
		Assertions.assertEquals(1.0, a.repartitions().get(0).probability(), EPS);
		// layout C: colocated on the keys, routed by customers.id: 2 relations on c's node, c uniform
		SkewAndLoad c = run(CostFixtures.LAYOUT_C, workload(JOIN), 0);
		assertLoad(c, 1, 1);
		Assertions.assertEquals(List.of(), c.repartitions());
		// in C, a join not on the keys (accounts.id = customers.id) is a repartition too
		SkewAndLoad off = run(CostFixtures.LAYOUT_C, workload(JOIN.replace("accounts.customer_id = customers.id",
				"accounts.id = customers.id")), 0);
		Assertions.assertEquals(1, off.repartitions().size());
	}

	@Test
	public void testRepeatMultipliesItsBody() {
		SkewAndLoad r = run(CostFixtures.LAYOUT_C, workload("""
				    transaction t weight 1 {
				        repeat 3 {
				            let c = customer uniform
				            read accounts where customer_id = $c
				        }
				    }
				"""), 0);
		assertLoad(r, 1.5, 1.5);
	}

	// --- marginals against brute-force enumeration (4.4c, 4) ---

	private static final String MARGINALS = """
			    transaction m weight 1 {
			        let a = customer zipf(1)
			        let b = customer uniform distinct from $a
			        let c = $a, else with 0.25: customer zipf(1)
			        let x = account uniform
			        let y = customer zipf(1) distinct from $x.customer
			        read accounts where customer_id = $b
			        read accounts where customer_id = $c
			        read accounts where customer_id = $y
			    }
			""";

	@Test
	public void testMarginalsMatchBruteForce() {
		for (long offset : List.of(0L, 1L, 3L)) {
			CostFixtures f = new CostFixtures();
			f.load("s.msql", CostFixtures.SCHEMA);
			Workload w = f.workload("w.workload", workload(MARGINALS));
			RankMapping m = RankMapping.of(w, offset);
			Marginals marginals = Marginals.of(w, m, w.transactions().get(0));
			double[] zipf = new double[5];
			double[] uniform = new double[5];
			double h = 1 + 1 / 2.0 + 1 / 3.0 + 1 / 4.0;
			for (int r = 1; r <= 4; r++) {
				zipf[(int) m.value("customer", r)] = (1.0 / r) / h;
				uniform[(int) m.value("customer", r)] = 0.25;
			}
			for (int v = 1; v <= 4; v++) {
				// b: a ~ zipf, then b uniform over the other three
				double b = 0;
				// c: a with 0.75, else a zipf draw excluding a
				double c = 0.75 * zipf[v];
				for (int a = 1; a <= 4; a++) {
					if (a != v) {
						b += zipf[a] * uniform[v] / (1 - uniform[a]);
						c += 0.25 * zipf[a] * zipf[v] / (1 - zipf[a]);
					}
				}
				// y: x uniform over 8 accounts, y zipf excluding x's customer
				double y = 0;
				for (int x = 1; x <= 8; x++) {
					long parent = m.parent("account", x);
					if (parent != v)
						y += (1 / 8.0) * zipf[v] / (1 - zipf[(int) parent]);
				}
				Assertions.assertEquals(zipf[v], marginals.probability(0, v), EPS, "a " + v + " offset " + offset);
				Assertions.assertEquals(b, marginals.probability(1, v), EPS, "b " + v + " offset " + offset);
				Assertions.assertEquals(c, marginals.probability(2, v), EPS, "c " + v + " offset " + offset);
				Assertions.assertEquals(y, marginals.probability(4, v), EPS, "y " + v + " offset " + offset);
			}
			for (int let : List.of(0, 1, 2, 3, 4)) {
				double sum = 0;
				for (int v = 1; v <= (let == 3 ? 8 : 4); v++)
					sum += marginals.probability(let, v);
				Assertions.assertEquals(1.0, sum, EPS, "let " + let + " sums to 1");
			}
		}
	}

	// --- T19: the budget ---

	@Test
	public void testT19Budget() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		Layout layout = f.layout("c.shardml", CostFixtures.LAYOUT_C);
		Workload w = f.workload("w.workload", CostFixtures.WORKLOAD);
		// exact counts: customers 4 + 4, accounts 8 (account) + 4 (customer) = 12, transaction read: let c 4 +
		// one binding 4 = 8; 28 in all. At the limits it passes
		new CostModel(CostFixtures.MOD, 12, 28).skewAndLoad(layout, w, 0);
		Assertions.assertEquals("The cost of table 'accounts' (data skew) needs 12 steps; the limit is 11 per template.",
				Assertions.assertThrows(IllegalArgumentException.class,
						() -> new CostModel(CostFixtures.MOD, 11, 1000).skewAndLoad(layout, w, 0)).getMessage());
		Assertions.assertEquals("The evaluation needs 28 steps (transaction 'read' brings it over); the limit is 27 per evaluation.",
				Assertions.assertThrows(IllegalArgumentException.class,
						() -> new CostModel(CostFixtures.MOD, 1000, 27).skewAndLoad(layout, w, 0)).getMessage());
	}

	@Test
	public void testT19OversizedWorkloadAtTheDefaultLimits() {
		// 300 million customers: the data skew alone is 6e8 steps; refused before any array is built
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		Layout layout = f.layout("c.shardml", CostFixtures.LAYOUT_C);
		Workload w = f.workload("w.workload", CostFixtures.WORKLOAD.replace("domain customer { size: 4 }",
				"domain customer { size: 300000000 }").replace("domain account { size: 8 within customer zipf(1) }",
						"domain account { size: 300000000 within customer zipf(1) }"));
		long start = System.nanoTime();
		Assertions.assertEquals("The cost of table 'customers' (data skew) needs 600000000 steps; the limit is 200000000 per "
				+ "template.", Assertions.assertThrows(IllegalArgumentException.class, () -> new CostModel().skewAndLoad(layout, w, 0))
						.getMessage());
		Assertions.assertTrue(System.nanoTime() - start < 5_000_000_000L, "refused without enumerating");
	}

	// --- determinism ---

	@Test
	public void testFormatIsDeterministic() {
		String a = run(CostFixtures.LAYOUT_C, workload(ZIPF_READ), 0).format();
		String b = run(CostFixtures.LAYOUT_C, workload(ZIPF_READ), 0).format();
		Assertions.assertEquals(a, b);
		Assertions.assertEquals("""
				layout c, workload t, rank offset 0
				data skew 1.166667 (max on n1)
				  rows n1 7
				  rows n2 5
				  table customers n1=2 n2=2
				  table accounts n1=5 n2=3
				load total 1.000000, bottleneck 0.640000 on n1, skew 1.280000
				  load n1 0.640000 read 0.640000 write 0.000000
				  load n2 0.360000 read 0.360000 write 0.000000
				""", a);
		Assertions.assertEquals(run(CostFixtures.LAYOUT_C, workload(ZIPF_READ), 0), run(CostFixtures.LAYOUT_C, workload(ZIPF_READ), 0));
	}

	static String fmt(double v) {
		return String.format(java.util.Locale.ROOT, "%.6f", v);
	}
}
