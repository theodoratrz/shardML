package io.shardml.tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.Layout;
import io.shardml.cost.Workload;

// Equal inputs give equal records with byte-identical toString (Plan-phase3 4.3: no randomness,
// fixed iteration order): two independent parses, and overrides given in either order.
public class CostDeterminismTest {

	private static Layout layout() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		return f.layout("c.shardml", CostFixtures.LAYOUT_C);
	}

	private static Workload workload(Map<String, String> overrides) {
		return new CostFixtures().workloadWith(CostFixtures.SCHEMA, WorkloadsTest.WORKLOAD, overrides);
	}

	@Test
	public void testTwoParsesGiveTheSameRecords() {
		Layout a = layout();
		Layout b = layout();
		Assertions.assertNotSame(a, b);
		Assertions.assertEquals(a, b);
		Assertions.assertEquals(a.toString(), b.toString());
		Workload w1 = workload(Map.of());
		Workload w2 = workload(Map.of());
		Assertions.assertEquals(w1, w2);
		Assertions.assertEquals(w1.toString(), w2.toString());
	}

	@Test
	public void testOverridesInAnyOrder() {
		Map<String, String> forward = new LinkedHashMap<>();
		forward.put("s", "1.25");
		forward.put("tw", "5");
		forward.put("remote", "0.5");
		Map<String, String> backward = new LinkedHashMap<>();
		backward.put("remote", "0.5");
		backward.put("tw", "5");
		backward.put("s", "1.25");
		Workload a = workload(forward);
		Workload b = workload(backward);
		Assertions.assertEquals(a, b);
		Assertions.assertEquals(a.toString(), b.toString());
		Assertions.assertNotEquals(workload(Map.of()), a);
	}
}
