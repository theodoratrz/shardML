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
}
