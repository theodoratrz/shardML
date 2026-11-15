package io.shardml.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.CostModel;
import io.shardml.cost.Layout;
import io.shardml.cost.SkewAndLoad;
import io.shardml.cost.Workload;

// An independent check of the engine against Plan-phase3 5.A's worked estimate, made by hand and with
// a throwaway script in Step 0 (resources/cost: the plan's banking workload and its two layouts, the
// Step 9 material). Within 1e-3; the expected values are the plan's and are not to be adjusted.
public class BankingCostSmokeTest {

	private static String read(String name) throws Exception {
		return Files.readString(Path.of("resources", "cost", name));
	}

	private static SkewAndLoad run(String layout, Map<String, String> overrides) throws Exception {
		CostFixtures f = new CostFixtures();
		f.load("banking.msql", read("banking.msql"));
		Layout l = f.layout(layout, read(layout));
		Workload w = f.workload("banking.workload", read("banking.workload"), overrides);
		return new CostModel().skewAndLoad(l, w, 0);
	}

	@Test
	public void testTotalTouchesAtTheDefaultWeights() throws Exception {
		// C: 0.02 * 3 + 0.48 * 1 + 0.50 * 4 = 2.54; A: 0.02 * 34 + 0.48 + 2.00 = 3.16
		Assertions.assertEquals(2.54, run("banking-by-customer.shardml", Map.of()).load().total(), 1e-3);
		Assertions.assertEquals(3.16, run("banking-by-account.shardml", Map.of()).load().total(), 1e-3);
	}

	@Test
	public void testAccountLayoutBottleneck() throws Exception {
		// independent of s: account-level touches hash by account id
		Assertions.assertEquals(1.087, run("banking-by-account.shardml", Map.of()).load().bottleneck(), 1e-3);
	}

	@Test
	public void testCustomerLayoutBottleneckAroundTheCrossover() throws Exception {
		Assertions.assertEquals(1.070, run("banking-by-customer.shardml", Map.of("s", "1.25")).load().bottleneck(), 1e-3);
		Assertions.assertEquals(1.089, run("banking-by-customer.shardml", Map.of("s", "1.28")).load().bottleneck(), 1e-3);
	}

	private static io.shardml.cost.CostReport report(String layout, Map<String, String> overrides) throws Exception {
		CostFixtures f = new CostFixtures();
		f.load("banking.msql", read("banking.msql"));
		Layout l = f.layout(layout, read(layout));
		Workload w = f.workload("banking.workload", read("banking.workload"), overrides);
		return new CostModel().evaluate(l, w, 0);
	}

	@Test
	public void testTheFullReportKeepsThePlanNumbers() throws Exception {
		io.shardml.cost.CostReport c = report("banking-by-customer.shardml", Map.of());
		io.shardml.cost.CostReport a = report("banking-by-account.shardml", Map.of());
		Assertions.assertEquals(2.54, c.load().total(), 1e-3);
		Assertions.assertEquals(3.16, a.load().total(), 1e-3);
		Assertions.assertEquals(1.087, a.load().bottleneck(), 1e-3);
		Assertions.assertEquals(1.070, report("banking-by-customer.shardml", Map.of("s", "1.25")).load().bottleneck(), 1e-3);
		io.shardml.cost.CostReport c128 = report("banking-by-customer.shardml", Map.of("s", "1.28"));
		Assertions.assertEquals(1.089, c128.load().bottleneck(), 1e-3);
		io.shardml.cost.CostReport a128 = report("banking-by-account.shardml", Map.of("s", "1.28"));
		// fan-out: C routes every statement; A's overview reads accounts by customer_id on all 32 shards:
		// 0.02 * 1 of 0.02 * 3 + 0.48 + 0.50 * 4 = 2.54 executions -> share 0.02 / 2.54
		Assertions.assertEquals(1.0, c.fanOut().mean(), 1e-12);
		Assertions.assertEquals(0.0, c.fanOut().multiShardShare(), 1e-12);
		Assertions.assertEquals(0.02 / 2.54, a.fanOut().multiShardShare(), 1e-12);
		Assertions.assertEquals((2.54 + 0.02 * 31) / 2.54, a.fanOut().mean(), 1e-12);
		Assertions.assertEquals(32, a.fanOut().max());
		// M1 at s = 1.0 and 1.28 (reported in Plan-phase3 4.3a, Step 7 as built): C's 2PC falls as s grows, A's
		// does not depend on s; A's overview read on 32 shards makes it multi-node, not 2PC
		Assertions.assertTrue(c128.distributed().twoPhase() < c.distributed().twoPhase());
		Assertions.assertEquals(a.distributed().twoPhase(), a128.distributed().twoPhase(), 1e-12);
		// exact in theory; the z sums over 10^6 accounts carry about 1e-11 of rounding
		Assertions.assertEquals(a.distributed().twoPhase() + 0.02, a.distributed().multiNode(), 1e-9);
	}
}
