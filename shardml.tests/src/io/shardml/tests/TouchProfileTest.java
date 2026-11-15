package io.shardml.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.CostModel;
import io.shardml.cost.Layout;
import io.shardml.cost.SkewAndLoad;
import io.shardml.cost.TouchKind;
import io.shardml.cost.TouchProfile;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.Transaction;

/* Phase 4's model extension (Plan-phase4 1a, P4.0b): load by touch kind and statements by kind, with entry-node
 * counts. A hand-computable fixture (bucket(v) = (v - 1) mod 4, buckets 0, 2 on n1 and 1, 3 on n2) with every
 * kind, a repeat and a repartition; and on the cost examples, the kinds sum to SkewAndLoad's read and write load
 * per node, which touchProfile leaves unchanged. */
public class TouchProfileTest {

	private static final double EPS = 1e-12;

	private static final String SCHEMA = CostFixtures.SCHEMA + "CREATE TABLE codes (id BIGINT PRIMARY KEY)\n";

	private static final String LAYOUT = CostFixtures.LAYOUT_C.replace("    accounts belongs_to", "    shard codes { strategy: replicated }\n    accounts belongs_to");

	private static final String WORKLOAD = """
			import "s.msql"
			workload t {
			    domain customer { size: 4 }
			    domain account { size: 8 within customer uniform }
			    domain code { size: 2 }
			    table customers { rows: per customer id: customer }
			    table accounts { rows: per account id: account customer_id: account.customer }
			    table codes { rows: per code id: code }
			    transaction all weight 3 {
			        let c = customer uniform
			        let a = account uniform
			        let k = code uniform
			        read accounts where customer_id = $c
			        write customers where id = $c
			        read accounts where id = $a
			        write accounts where id = $a
			        repeat 2 {
			            read codes where id = $k
			        }
			        write codes where id = $k
			        read accounts join customers on accounts.id = customers.id where customers.id = $c
			    }
			    transaction one weight 1 {
			        let c = customer uniform
			        read customers where id = $c
			    }
			}
			""";

	@Test
	public void testFixture() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", SCHEMA);
		Layout layout = f.layout("l.shardml", LAYOUT);
		Workload w = f.workload("w.workload", WORKLOAD);
		TouchProfile p = new CostModel(CostFixtures.MOD).touchProfile(layout, w, 0);
		Assertions.assertEquals(List.of("n1", "n2"), p.nodes());
		// all: pi 3/4; one: pi 1/4. Routed reads: all's accounts by customer (uniform: half per node) and one's
		// customers by id, both 1 relation
		check(p, TouchKind.ROUTED_READ, 0.5, 0.5, 1.0);
		check(p, TouchKind.ROUTED_WRITE, 0.375, 0.375, 0.75);
		// unrouted: every bucket of the group, 2 per node
		check(p, TouchKind.UNROUTED_READ, 1.5, 1.5, 0.75);
		check(p, TouchKind.UNROUTED_WRITE, 1.5, 1.5, 0.75);
		// a reference read adds relations / n to each node (x2, the repeat); a write the relations to each
		check(p, TouchKind.REFERENCE_READ, 0.75, 0.75, 1.5);
		check(p, TouchKind.REFERENCE_WRITE, 0.75, 0.75, 0.75);
		Assertions.assertEquals(0.75, p.repartitionStatements(), EPS);
		Assertions.assertEquals(5.5, p.totalStatements(), EPS);
		Assertions.assertEquals(0.5 + 0.375 + 1.5 + 1.5 + 0.75 + 0.75, p.touchesOn("n1"), EPS);
		Assertions.assertTrue(p.format().contains("repartition      0.750000"), p.format());
		Assertions.assertTrue(p.json().contains("\"routed_write\": 0.750000"), p.json());
	}

	private static void check(TouchProfile p, TouchKind kind, double n1, double n2, double statements) {
		Assertions.assertEquals(n1, p.touches(kind).perNode().get(0).value(), EPS, kind + " n1");
		Assertions.assertEquals(n2, p.touches(kind).perNode().get(1).value(), EPS, kind + " n2");
		Assertions.assertEquals(statements, p.statements(kind), EPS, kind + " statements");
		// round-robin entry: statements / n on every node
		for (int n = 0; n < 2; n++)
			Assertions.assertEquals(statements / 2, p.entryStatements().get(kind.ordinal()).perNode().get(n).value(), EPS, kind + " entry");
	}

	// the read kinds sum to Load.readPerNode, the write kinds to writePerNode; statements to an independent count
	@Test
	public void testExamplesSumToTheLoad() throws Exception {
		Path dir = Path.of("..", "examples", "cost");
		record Case(String schema, String workload, Map<String, String> sets, List<String> layouts) {
		}
		List<Case> cases = List.of(
				new Case("banking.msql", "banking.workload", Map.of("s", "1.2"), List.of("banking-by-customer.shardml", "banking-by-account.shardml")),
				new Case("tpcc.msql", "tpcc.workload", Map.of("remote", "0.3"), List.of("tpcc-by-warehouse.shardml", "tpcc-stock-by-item.shardml")));
		for (Case c : cases) {
			for (long offset : new long[] { 0, 7 }) {
				CostFixtures f = new CostFixtures();
				f.load("s.msql", Files.readString(dir.resolve(c.schema())));
				Workload w = f.workload("w.workload",
						Files.readString(dir.resolve(c.workload())).replaceFirst("import \"[^\"]+\"", "import \"s.msql\""), c.sets());
				for (String file : c.layouts()) {
					Layout layout = f.layout(file, Files.readString(dir.resolve(file)).replaceFirst("import \"[^\"]+\"", "import \"s.msql\""));
					CostModel model = new CostModel();
					SkewAndLoad before = model.skewAndLoad(layout, w, offset);
					TouchProfile p = model.touchProfile(layout, w, offset);
					SkewAndLoad after = model.skewAndLoad(layout, w, offset);
					Assertions.assertEquals(before.format(), after.format(), file);
					String where = file + " offset " + offset;
					for (int n = 0; n < p.nodes().size(); n++) {
						double read = 0, write = 0;
						for (TouchKind k : TouchKind.values()) {
							double v = p.touches(k).perNode().get(n).value();
							if (k.write())
								write += v;
							else
								read += v;
						}
						double expectedRead = before.load().readPerNode().get(n).value();
						double expectedWrite = before.load().writePerNode().get(n).value();
						Assertions.assertEquals(expectedRead, read, 1e-9 * Math.max(1, expectedRead), where + " read");
						Assertions.assertEquals(expectedWrite, write, 1e-9 * Math.max(1, expectedWrite), where + " write");
						Assertions.assertEquals(before.load().perNode().get(n).value(), p.touchesOn(p.nodes().get(n)),
								1e-9 * Math.max(1, before.load().perNode().get(n).value()), where);
					}
					double weights = w.transactions().stream().mapToDouble(Transaction::weight).sum();
					double statements = 0;
					for (Transaction t : w.transactions())
						statements += t.weight() / weights * count(t.steps());
					Assertions.assertEquals(statements, p.totalStatements() + p.repartitionStatements(), 1e-9, where);
					Assertions.assertEquals(before.repartitions().stream().mapToDouble(r -> r.probability() * r.times()).sum(),
							p.repartitionStatements(), 1e-9, where);
				}
			}
		}
	}

	private static long count(List<Step> steps) {
		long n = 0;
		for (Step s : steps) {
			if (s instanceof Statement)
				n++;
			else if (s instanceof Repeat r)
				n += r.count() * count(r.steps());
		}
		return n;
	}
}
