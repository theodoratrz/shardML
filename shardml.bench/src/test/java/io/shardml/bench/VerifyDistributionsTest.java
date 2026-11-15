package io.shardml.bench;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.Inputs;
import io.shardml.cost.RankMapping;

/* verify-distributions on the example workloads (fewer draws than the command's 10^6, for time), and
 * the statistics under it. */
public class VerifyDistributionsTest {

	@Test
	public void testChiSquarePValues() {
		// 0.95 quantiles of chi-square (standard tables)
		Assertions.assertEquals(0.05, ChiSquare.pValue(3.841458820694124, 1), 1e-9);
		Assertions.assertEquals(0.05, ChiSquare.pValue(18.307038053275146, 10), 1e-9);
		Assertions.assertEquals(0.05, ChiSquare.pValue(124.34211340400407, 100), 1e-9);
		// 0.999 quantile, 31 df (32 buckets)
		Assertions.assertEquals(0.001, ChiSquare.pValue(61.09833, 31), 1e-6);
		Assertions.assertEquals(1.0, ChiSquare.pValue(0, 5));
		Assertions.assertEquals(Math.exp(-5), ChiSquare.pValue(10, 2), 1e-12, "df 2 is exponential");
	}

	@Test
	public void testPoolingAndImpossibleValues() {
		// bins under 5 expected are pooled
		ChiSquare.Result r = ChiSquare.test(new double[] { 0.5, 0.492, 0.004, 0.004 }, new long[] { 500, 492, 4, 4 }, 1000);
		Assertions.assertEquals(3, r.bins());
		// a draw where the pmf has no mass fails outright
		Assertions.assertEquals(0.0, ChiSquare.test(new double[] { 1.0, 0 }, new long[] { 99, 1 }, 100).p());
	}

	@Test
	public void testExamplesPass() {
		for (Inputs.Loaded loaded : List.of(Examples.banking(), Examples.tpcc())) {
			for (long offset : new long[] { 0, 5 }) {
				VerifyDistributions.Report report = VerifyDistributions.run(loaded.workload(), loaded.layouts(),
						RankMapping.of(loaded.workload(), offset), 200_000, 1);
				Assertions.assertTrue(report.passed(), VerifyDistributions.format(report));
				Assertions.assertEquals(VerifyDistributions.ALPHA / report.tests(), report.threshold());
			}
		}
	}

	@Test
	public void testEveryFeatureIsChecked() {
		Inputs.Loaded tpcc = Examples.tpcc();
		VerifyDistributions.Report report = VerifyDistributions.run(tpcc.workload(), tpcc.layouts(), RankMapping.of(tpcc.workload(), 0),
				20_000, 2);
		List<String> tests = report.checks().stream().map(c -> c.transaction() + " " + c.subject() + " " + c.test()).toList();
		Assertions.assertTrue(tests.contains("new_order $sw else-with P[x != base] = 0.01"), tests.toString());
		Assertions.assertTrue(tests.contains("new_order $sw repeat: iterations 1 and 2 independent"), tests.toString());
		Assertions.assertTrue(tests.contains("new_order $i repeat: iterations 1 and 2 independent"), tests.toString());
		Assertions.assertTrue(tests.contains("* template mix"), tests.toString());
		Assertions.assertTrue(tests.stream().anyMatch(t -> t.startsWith("new_order $i buckets (32, stock)")), tests.toString());
		Inputs.Loaded banking = Examples.banking();
		report = VerifyDistributions.run(banking.workload(), banking.layouts(), RankMapping.of(banking.workload(), 0), 20_000, 2);
		tests = report.checks().stream().map(c -> c.transaction() + " " + c.subject() + " " + c.test()).toList();
		Assertions.assertTrue(tests.contains("transfer $dst distinct-from (draws equal to the base)"), tests.toString());
		Assertions.assertTrue(tests.stream().anyMatch(t -> t.startsWith("history $a.customer buckets")), tests.toString());
	}

	@Test
	public void testAWrongDistributionFails() {
		// the sampler of zipf(1.1) checked against the pmf of zipf(1.0): the counts come from one workload, the
		// expected values from another
		Inputs.Loaded right = Examples.load(Map.of("s", "1.0"), Examples.COST + "banking.workload");
		Inputs.Loaded wrong = Examples.load(Map.of("s", "1.1"), Examples.COST + "banking.workload");
		RankMapping rm = RankMapping.of(right.workload(), 0);
		RankMapping wm = RankMapping.of(wrong.workload(), 0);
		// $a.customer of `history`: the customer of a uniform account, so its pmf is the child counts' zipf
		int n = 200_000;
		long[] counts = new long[10_000];
		java.util.SplittableRandom random = new java.util.SplittableRandom(1);
		io.shardml.cost.Sampler sampler = io.shardml.cost.Sampler.of(wrong.workload(), wm);
		for (int i = 0; i < n; i++) {
			long account = sampler.draw("account", new io.shardml.cost.Workload.Uniform(), random);
			counts[(int) wm.parent("account", account) - 1]++;
		}
		double[] p = new double[10_000];
		for (long a = 1; a <= rm.size("account"); a++)
			p[(int) rm.parent("account", a) - 1] += 1.0 / rm.size("account");
		Assertions.assertTrue(VerifyDistributions.valueTest(p, counts, n).p() < 1e-6);
	}

	@Test
	public void testCommand() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int exit = Driver.run(List.of("verify-distributions", "--draws", "20000", Examples.COST + "banking.workload",
				Examples.COST + "banking-by-customer.shardml"), new PrintStream(out, true, StandardCharsets.UTF_8),
				new PrintStream(java.io.OutputStream.nullOutputStream()));
		String text = out.toString(StandardCharsets.UTF_8);
		Assertions.assertEquals(0, exit, text);
		Assertions.assertTrue(text.contains("PASSED"), text);
		Assertions.assertTrue(text.contains("Bonferroni"), text);
	}
}
