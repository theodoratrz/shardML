package io.shardml.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.shardml.command.CostCommand;

// `shardml cost` end to end (Plan-phase3 4.5, 4.5a): goldens for the banking example (examples/cost), exit
// codes 0 / 1 / 2 with each kind of error, --set, determinism, and validation output.
public class CostCommandTest {

	private static final Path COST = Path.of("..", "examples", "cost");
	private static final Path EXPECTED = COST.resolve("expected");

	private record Run(int exit, String out, String err) {
	}

	private static Run run(String... args) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		int exit = CostCommand.run(List.of(args), new PrintStream(out, true, StandardCharsets.UTF_8),
				new PrintStream(err, true, StandardCharsets.UTF_8));
		return new Run(exit, out.toString(StandardCharsets.UTF_8), err.toString(StandardCharsets.UTF_8));
	}

	private static String file(String name) {
		return COST.resolve(name).toString();
	}

	private static String expected(String name) throws Exception {
		return Files.readString(EXPECTED.resolve(name), StandardCharsets.UTF_8);
	}

	private static final String WORKLOAD = file("banking.workload");
	private static final String BY_CUSTOMER = file("banking-by-customer.shardml");
	private static final String BY_ACCOUNT = file("banking-by-account.shardml");

	@Test
	public void testSingleReportAndAliases() throws Exception {
		// every example output is compared in CostExamplesTest; here, one, and the command's own behaviour
		Run c = run(WORKLOAD, BY_CUSTOMER);
		Assertions.assertEquals(0, c.exit(), c.err());
		Assertions.assertEquals(expected("banking-by-customer.txt"), c.out());
		// the warnings of a valid model go to stderr: the layouts declare no route rules (SHML_022)
		Assertions.assertTrue(c.err().contains("banking-by-customer.shardml:11: warning: No route rule defined for table "
				+ "'customers'. Query patterns will not get validated [SHML_022]"), c.err());
		// file order does not matter; --compare is an alias of two or more models
		Assertions.assertEquals(expected("banking-by-account.json"), run("--json", BY_ACCOUNT, WORKLOAD).out());
		Assertions.assertEquals(expected("banking-comparison.txt"), run("--compare", WORKLOAD, BY_CUSTOMER, BY_ACCOUNT).out());
	}

	@Test
	public void testRankOffsetSweep() {
		// alone: one row per (offset, layout); with a param: a rank_offset column; with --rank-offset: usage
		Run alone = run("--csv", "--sweep", "rank-offset=0,1", WORKLOAD, BY_CUSTOMER);
		Assertions.assertEquals(0, alone.exit(), alone.err());
		Assertions.assertTrue(alone.out().startsWith("param,param_value,layout,metric,value\nrank-offset,0,banking_by_customer,"),
				alone.out());
		Run both = run("--csv", "--sweep", "s=1.0", "--sweep", "rank-offset=0,1", WORKLOAD, BY_CUSTOMER);
		Assertions.assertTrue(both.out().startsWith("rank_offset,param,param_value,layout,metric,value\n0,s,1.0,banking_by_customer,"),
				both.out());
		Assertions.assertTrue(both.out().contains("\n1,s,1.0,banking_by_customer,distributed_2pc,"), both.out());
		Assertions.assertEquals(2, run("--rank-offset", "1", "--sweep", "rank-offset=0,1", WORKLOAD, BY_CUSTOMER).exit());
		Assertions.assertEquals(2, run("--sweep", "rank-offset=0,-1", WORKLOAD, BY_CUSTOMER).exit());
		Assertions.assertEquals(2, run("--sweep", "rank-offset=a", WORKLOAD, BY_CUSTOMER).exit());
		Assertions.assertEquals(2, run("--sweep", "s=1", "--sweep", "overview_w=2", WORKLOAD, BY_CUSTOMER).exit());
	}

	@Test
	public void testDeterministic() {
		String[] args = { "--json", "--sweep", "transfer_w=10,50", WORKLOAD, BY_CUSTOMER, BY_ACCOUNT };
		Run a = run(args);
		Run b = run(args);
		Assertions.assertEquals(0, a.exit(), a.err());
		Assertions.assertEquals(a.out(), b.out());
		Assertions.assertTrue(a.out().startsWith("[\n  {\n    \"layout\": \"banking_by_customer\""), a.out());
	}

	@Test
	public void testSetOverrides() {
		// --set changes its param, marked (set); a sweep of the same values gives the same metrics
		Run set = run("--set", "s=1.28", WORKLOAD, BY_CUSTOMER);
		Assertions.assertEquals(0, set.exit(), set.err());
		Assertions.assertTrue(set.out().contains("params: s=1.28 (set), overview_w=2, history_w=48, transfer_w=50; rank offset 0"),
				set.out());
		Assertions.assertTrue(set.out().contains("bottleneck load (touches/txn)   1.089131  on w3"), set.out());
		// an override that breaks a rule is an error, exit 1, with the message of Workloads.of
		Run negative = run("--set", "s=-1", WORKLOAD, BY_CUSTOMER);
		Assertions.assertEquals(1, negative.exit());
		Assertions.assertTrue(negative.err().endsWith("shardml cost: error: --set s=-1 makes the zipf exponent of domain 'account' "
				+ "-1; it must be at least 0.\n"), negative.err());
		Run unknown = run("--set", "nope=1", WORKLOAD, BY_CUSTOMER);
		Assertions.assertEquals(1, unknown.exit());
		Assertions.assertTrue(unknown.err().endsWith("shardml cost: error: --set nope: workload 'banking' has no param 'nope' "
				+ "(params: s, overview_w, history_w, transfer_w).\n"), unknown.err());
		Run notNumber = run("--set", "s=abc", WORKLOAD, BY_CUSTOMER);
		Assertions.assertEquals(1, notNumber.exit());
		Assertions.assertTrue(notNumber.err().endsWith("shardml cost: error: --set s=abc: not a number.\n"), notNumber.err());
		// in a sweep, the message names --sweep
		Run sweep = run("--sweep", "s=1,-2", WORKLOAD, BY_CUSTOMER);
		Assertions.assertEquals(1, sweep.exit());
		Assertions.assertTrue(sweep.err().endsWith("shardml cost: error: --sweep s=-2 makes the zipf exponent of domain 'account' "
				+ "-2; it must be at least 0.\n"), sweep.err());
		Run sweepUnknown = run("--sweep", "nope=1,2", WORKLOAD, BY_CUSTOMER);
		Assertions.assertTrue(sweepUnknown.err().endsWith("shardml cost: error: --sweep nope: workload 'banking' has no param "
				+ "'nope' (params: s, overview_w, history_w, transfer_w).\n"), sweepUnknown.err());
	}

	@Test
	public void testUsageErrorsExit2() {
		List<String[]> usage = new ArrayList<>();
		usage.add(new String[] { BY_CUSTOMER });
		usage.add(new String[] { WORKLOAD });
		usage.add(new String[] { WORKLOAD, WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--frobnicate", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--csv", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--json", "--csv", WORKLOAD, BY_CUSTOMER, BY_ACCOUNT });
		usage.add(new String[] { "--compare", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--rank-offset", "-1", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--rank-offset", "x", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--sweep", "s", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--sweep", "s=1,,2", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--sweep", "s=1", "--set", "s=2", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--set", "s", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { "--set", "s=1", "--set", "s=2", WORKLOAD, BY_CUSTOMER });
		usage.add(new String[] { WORKLOAD, "missing.shardml" });
		usage.add(new String[] { WORKLOAD, file("banking.msql") });
		usage.add(new String[] { "--set" });
		for (String[] args : usage) {
			Run r = run(args);
			Assertions.assertEquals(2, r.exit(), String.join(" ", args) + "\n" + r.err());
			Assertions.assertTrue(r.err().contains("Usage: shardml cost"), r.err());
			Assertions.assertEquals("", r.out());
		}
		Assertions.assertTrue(run("--compare", WORKLOAD, BY_CUSTOMER).err()
				.startsWith("shardml cost: --compare needs two or more models, got 1\n"));
	}

	@Test
	public void testErrorsExit1(@TempDir Path dir) throws Exception {
		Files.copy(COST.resolve("banking.msql"), dir.resolve("banking.msql"));
		// a layout without nodes says what to add
		Path noNodes = dir.resolve("no-nodes.shardml");
		Files.writeString(noNodes, Files.readString(COST.resolve("banking-by-customer.shardml"))
				.replaceAll("    node w[0-9] \\{[^}]*\\}\n", ""));
		Path workload = dir.resolve("banking.workload");
		Files.copy(COST.resolve("banking.workload"), workload);
		Run r = run(workload.toString(), noNodes.toString());
		Assertions.assertEquals(1, r.exit(), r.err());
		Assertions.assertTrue(r.err().endsWith("shardml cost: error: Layout 'banking_by_customer' declares no nodes; the cost model "
				+ "needs a placement. Add node blocks to the model.\n"), r.err());
		Assertions.assertEquals("", r.out());

		// a schema mismatch between the layout's .msql and the workload's
		Path other = dir.resolve("other.msql");
		Files.writeString(other, Files.readString(COST.resolve("banking.msql")).replace("customer_id BIGINT,\n    balance",
				"customer_id INT,\n    balance"));
		Path otherWorkload = dir.resolve("other.workload");
		Files.writeString(otherWorkload, Files.readString(COST.resolve("banking.workload")).replace("banking.msql", "other.msql"));
		Run mismatch = run(otherWorkload.toString(), file("banking-by-customer.shardml"));
		Assertions.assertEquals(1, mismatch.exit(), mismatch.err());
		Assertions.assertTrue(mismatch.err().endsWith("shardml cost: error: Column accounts.customer_id is BIGINT in the layout's "
				+ "schema but INT in the workload's.\n"), mismatch.err());
	}

	@Test
	public void testValidationErrorsOfABrokenWorkload(@TempDir Path dir) throws Exception {
		Files.copy(COST.resolve("banking.msql"), dir.resolve("banking.msql"));
		Path broken = dir.resolve("broken.workload");
		Files.writeString(broken, Files.readString(COST.resolve("banking.workload"))
				.replace("transaction overview weight $overview_w", "transaction overview weight 0")
				.replace("read cards     where customer_id = $c", "read cards     where customer_id = $nope"));
		Run r = run(broken.toString(), BY_CUSTOMER);
		Assertions.assertEquals(1, r.exit(), r.err());
		Assertions.assertEquals("", r.out());
		Assertions.assertTrue(r.err().contains("broken.workload:20: error: The weight of 'overview' is 0; a transaction's weight must "
				+ "be greater than 0. [SHML_043]\n"), r.err());
		Assertions.assertTrue(r.err().contains("broken.workload:24: error: Unknown variable 'nope'"), r.err());
		Assertions.assertTrue(r.err().contains("[SHML_042]"), r.err());
		Assertions.assertTrue(r.err().endsWith("shardml cost: 2 errors\n"), r.err());
		// each file is validated as `shardml validate` would: the layout gets only its own warnings
		Assertions.assertFalse(r.err().contains("SHML_030"), r.err());
	}
}
