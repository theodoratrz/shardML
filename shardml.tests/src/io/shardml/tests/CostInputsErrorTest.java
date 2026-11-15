package io.shardml.tests;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import io.shardml.cost.CostInputs;
import io.shardml.cost.Layout;
import io.shardml.cost.Layouts;
import io.shardml.cost.Workload;
import io.shardml.cost.Workloads;
import io.shardml.shardML.Model;
import io.shardml.workload.WorkloadModel;

// Every construction and cross-input error of Plan-phase3 4.4a, one test each, exact message.
// Plain exceptions, not SHML codes: the cross-input ones involve two files.
public class CostInputsErrorTest {

	private static void assertMessage(String expected, Executable executable) {
		Assertions.assertEquals(expected, Assertions.assertThrows(IllegalArgumentException.class, executable).getMessage());
	}

	private static String layout(String name, String shards) {
		return "import \"l.msql\"\ndatabase " + name + " {\n    type: postgres\n" + CostFixtures.NODES + shards + "}\n";
	}

	// --- L: Layouts.of ---

	@Test
	public void testL1LayoutWithoutNodes() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		Resource r = f.load("c.shardml", CostFixtures.LAYOUT_C.replace(CostFixtures.NODES, ""));
		assertMessage("Layout 'c' declares no nodes; the cost model needs a placement. Add node blocks to the model.",
				() -> Layouts.of(((Model) r.getContents().get(0)).getDb()));
	}

	@Test
	public void testL2KeyTypeDidNotParse() {
		CostFixtures f = new CostFixtures();
		// CHAR(2, 1): a syntax error, so the type reads UNKNOWN (not the enum's default INT)
		f.loadUnchecked("l.msql", "CREATE TABLE t (k CHAR(2, 1) PRIMARY KEY)");
		Resource r = f.loadUnchecked("l.shardml", layout("l", "    shard t { strategy: hash key: k buckets: 4 }\n"));
		assertMessage("Shard key t.k of 'l' has a type that did not parse in l.msql; fix the schema first.",
				() -> Layouts.of(((Model) r.getContents().get(0)).getDb()));
	}

	@Test
	public void testL3KeyTypeNotHashed() {
		CostFixtures f = new CostFixtures();
		f.load("l.msql", "CREATE TABLE t (k TEXT PRIMARY KEY)");
		assertMessage("Shard key t.k of 'l' is TEXT; the cost model hashes only SMALLINT, INTEGER/SERIAL and BIGINT/BIGSERIAL "
				+ "keys (the types whose Citus hash it reproduces).",
				() -> f.layout("l.shardml", layout("l", "    shard t { strategy: hash key: k buckets: 4 }\n")));
	}

	// --- W: Workloads.of ---

	private static Workload workload(Map<String, String> overrides) {
		return new CostFixtures().workloadWith(CostFixtures.SCHEMA, WorkloadsTest.WORKLOAD, overrides);
	}

	@Test
	public void testW1UnknownParam() {
		assertMessage("--set nope: workload 'w' has no param 'nope' (params: s, tw, remote).", () -> workload(Map.of("nope", "1")));
	}

	@Test
	public void testW2NotANumber() {
		assertMessage("--set tw=abc: not a number.", () -> workload(Map.of("tw", "abc")));
	}

	@Test
	public void testW3OverrideBreaksARule() {
		assertMessage("--set s=-1 makes the zipf exponent of domain 'account' -1; it must be at least 0.",
				() -> workload(Map.of("s", "-1")));
		assertMessage("--set tw=0 makes the weight of transaction 'transfer' 0; it must be greater than 0.",
				() -> workload(Map.of("tw", "0")));
		assertMessage("--set tw=-2.5 makes the weight of transaction 'transfer' -2.5; it must be greater than 0.",
				() -> workload(Map.of("tw", "-2.5")));
		assertMessage("--set remote=1.5 makes the probability of 'else with' in 'transfer' (let c) 1.5; it must be between 0 "
				+ "and 1.", () -> workload(Map.of("remote", "1.5")));
		assertMessage("--set remote=-0.1 makes the probability of 'else with' in 'transfer' (let c) -0.1; it must be between 0 "
				+ "and 1.", () -> workload(Map.of("remote", "-0.1")));
	}

	@Test
	public void testW3EveryPlaceAndOrigin() {
		// a zipf exponent of an `over` table and of a draw; and a file value or a literal that the
		// validators would reject (SHML_045), read without validation
		String text = """
				import "s.msql"
				workload w {
				    param s = 1
				    param p = 2
				    domain customer { size: 4 }
				    table customers { rows: per customer id: customer }
				    table accounts { rows: 8 over customer zipf($s) customer_id: customer unique id }
				    transaction t weight 1 {
				        let x = customer zipf($s)
				        let y = $x, else with %s: customer uniform
				        read customers where id = $y
				    }
				}
				""";
		assertMessage("--set s=-2 makes the zipf exponent of table 'accounts' (rows over customer) -2; it must be at least 0.",
				() -> parse(text.formatted("0.5"), Map.of("s", "-2")));
		assertMessage("Param 'p' = 2 makes the probability of 'else with' in 't' (let y) 2; it must be between 0 and 1.",
				() -> parse(text.formatted("$p"), Map.of()));
		assertMessage("The literal 1.5 makes the probability of 'else with' in 't' (let y) 1.5; it must be between 0 and 1.",
				() -> parse(text.formatted("1.5"), Map.of()));
		String draw = text.replace("rows: 8 over customer zipf($s)", "rows: 8 over customer uniform");
		assertMessage("--set s=-2 makes the zipf exponent of let x in 't' -2; it must be at least 0.",
				() -> parse(draw.formatted("0.5"), Map.of("s", "-2")));
	}

	private static Workload parse(String text, Map<String, String> overrides) {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		return Workloads.of((WorkloadModel) f.loadUnchecked("w.workload", text).getContents().get(0), overrides);
	}

	@Test
	public void testW4DomainTooLarge() {
		assertMessage("Domain 'big' has 3000000000 values; the cost model handles at most 2147483647 per domain.",
				() -> parse("""
						import "s.msql"
						workload w {
						    domain big { size: 3000000000 }
						}
						""", Map.of()));
	}

	// --- X: CostInputs.check ---

	// a layout over l.msql and a workload over w.msql, in one resource set
	private static void check(String layoutSchema, String layoutText, String workloadSchema, String workloadText) {
		CostFixtures f = new CostFixtures();
		f.load("l.msql", layoutSchema);
		f.load("w.msql", workloadSchema);
		Layout layout = f.layout("l.shardml", layoutText);
		Workload workload = f.workload("w.workload", workloadText);
		CostInputs.check(layout, workload);
	}

	private static final String LAYOUT_C = CostFixtures.LAYOUT_C.replace("s.msql", "l.msql");
	private static final String WORKLOAD = CostFixtures.WORKLOAD.replace("s.msql", "w.msql");

	@Test
	public void testTheFixturesPass() {
		check(CostFixtures.SCHEMA, LAYOUT_C, CostFixtures.SCHEMA, WORKLOAD);
	}

	@Test
	public void testX1SchemaMismatch() {
		assertMessage("Table 'audit' is in the workload's schema but not in the layout's.",
				() -> check(CostFixtures.SCHEMA, LAYOUT_C, CostFixtures.SCHEMA + "CREATE TABLE audit (id BIGINT PRIMARY KEY)\n",
						WORKLOAD.replace("    transaction", "    table audit { rows: per customer id: customer }\n    transaction")));
		assertMessage("Column accounts.balance is in the workload's schema but not in the layout's.",
				() -> check(CostFixtures.SCHEMA, LAYOUT_C, CostFixtures.SCHEMA.replace("customer_id BIGINT,", "customer_id BIGINT, balance INT,"),
						WORKLOAD.replace("read accounts where customer_id = $c", "read accounts where customer_id = $c AND balance > ?")));
		// INT on both sides of the layout's colocation link (SHML_027), BIGINT in the workload's schema; the
		// first use is the customers table block's id
		assertMessage("Column customers.id is INT in the layout's schema but BIGINT in the workload's.",
				() -> check(CostFixtures.SCHEMA.replace("BIGINT", "INT"), LAYOUT_C, CostFixtures.SCHEMA, WORKLOAD));
	}

	@Test
	public void testX5TableNotDistributed() {
		assertMessage("Workload 't' uses table 'customers', which layout 'c' does not distribute (no shard declaration); the cost "
				+ "model needs every table a statement touches to be hash-distributed or replicated.",
				() -> check(CostFixtures.SCHEMA, LAYOUT_C.replace("    shard customers { strategy: hash key: id buckets: 4 }\n", "")
						.replace(" colocate_with: customers", "").replace("    accounts belongs_to customers via customer_id\n", ""),
						CostFixtures.SCHEMA, WORKLOAD.replace("read accounts where customer_id = $c",
								"read accounts where customer_id = $c\n        read customers where id = $c")));
	}

	@Test
	public void testX2HashTableWithoutTableBlock() {
		assertMessage("Table 'accounts' is hash-distributed in 'c' but has no table block in workload 't'; data skew needs its rows.",
				() -> check(CostFixtures.SCHEMA, LAYOUT_C, CostFixtures.SCHEMA, """
						import "w.msql"
						workload t {
						    domain customer { size: 4 }
						    table customers { rows: per customer id: customer }
						}
						"""));
	}

	@Test
	public void testX3KeyNotMapped() {
		assertMessage("Shard key accounts.customer_id of 'c' has no mapping in the table block of 'accounts' in workload 't'; map "
				+ "it to a domain (customer_id: <domain>) or declare it unique.",
				() -> check(CostFixtures.SCHEMA, LAYOUT_C, CostFixtures.SCHEMA, """
						import "w.msql"
						workload t {
						    domain customer { size: 4 }
						    domain account { size: 8 within customer uniform }
						    table customers { rows: per customer id: customer }
						    table accounts { rows: per account id: account }
						}
						"""));
	}

	@Test
	public void testX4KeyValuesDoNotFit() {
		String schema = """
				CREATE TABLE orders (customer_id SMALLINT, id INT, PRIMARY KEY (customer_id, id))
				CREATE TABLE events (id SMALLINT PRIMARY KEY)
				""";
		String layout = layout("l", """
				    shard orders { strategy: hash key: customer_id buckets: 4 }
				    shard events { strategy: hash key: id buckets: 4 }
				""");
		String workload = """
				import "w.msql"
				workload w {
				    domain customer { size: %d }
				    table orders { rows: per customer customer_id: customer unique id }
				    table events { rows: %d over customer uniform unique id }
				}
				""";
		// 32767 fits a smallint
		check(schema, layout, schema, workload.formatted(32767, 32767));
		assertMessage("Shard key orders.customer_id is SMALLINT, but its values in workload 'w' run up to 40000 (domain 'customer'); "
				+ "they do not fit.", () -> check(schema, layout, schema, workload.formatted(40000, 100)));
		assertMessage("Shard key events.id is SMALLINT, but its values in workload 'w' run up to 40000 (unique row ids); they do "
				+ "not fit.", () -> check(schema, layout, schema, workload.formatted(100, 40000)));
	}
}
