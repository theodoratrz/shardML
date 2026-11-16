package io.shardml.bench;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/* The CPU sampler's arithmetic without a cluster: cores from cgroup files, utilization per interval and over
 * the window, and the saturation flag at 85% of a node's cores (Plan-phase4 2.6). */
public class CpuSamplerTest {

	@Test
	public void testCores() {
		Assertions.assertEquals(6, CpuSampler.cores("max 100000", "0-5"), 1e-12);
		Assertions.assertEquals(2, CpuSampler.cores("200000 100000", "0-5"), 1e-12);
		Assertions.assertEquals(3, CpuSampler.cores("max 100000", "0,2,4"), 1e-12);
		Assertions.assertEquals(4, CpuSampler.cores("max 100000", "0-1,6-7\n"), 1e-12);
		Assertions.assertEquals(1.5, CpuSampler.cores("150000 100000", ""), 1e-12);
	}

	@Test
	public void testSaturation() {
		long s = 1_000_000_000L;
		// 2 cores each; node a uses 1.8 CPU-seconds per second (90%), node b 1.0 (50%), over 3 one-second intervals
		List<Long> times = List.of(0L, s, 2 * s, 3 * s);
		List<Map<String, Long>> usages = List.of(Map.of("a", 0L, "b", 0L), Map.of("a", 1_800_000L, "b", 1_000_000L),
				Map.of("a", 3_600_000L, "b", 2_000_000L), Map.of("a", 5_400_000L, "b", 3_000_000L));
		Map<String, Object> out = CpuSampler.summary(Map.of("a", 2.0, "b", 2.0), times, usages, List.of(), true);
		Assertions.assertEquals(List.of("a"), out.get("saturatedNodes"));
		Assertions.assertEquals(Boolean.TRUE, out.get("saturation"));
		Assertions.assertEquals(Boolean.FALSE, out.get("valid"), "emulated: recorded, not a result");
		Map<?, ?> a = (Map<?, ?>) ((Map<?, ?>) out.get("nodes")).get("a");
		Assertions.assertEquals(0.9, (Double) a.get("mean"), 1e-12);
		Assertions.assertEquals(0.9, (Double) a.get("max"), 1e-12);
		Assertions.assertEquals(3L, a.get("intervalsAboveThreshold"));
		Assertions.assertEquals(3, ((List<?>) out.get("series")).size());
		// one spike above 85% in a mean below it: not saturated, but counted
		usages = List.of(Map.of("a", 0L), Map.of("a", 1_900_000L), Map.of("a", 2_400_000L), Map.of("a", 2_900_000L));
		out = CpuSampler.summary(Map.of("a", 2.0), times, usages, List.of(), false);
		Assertions.assertEquals(Boolean.FALSE, out.get("saturation"));
		Assertions.assertEquals(Boolean.TRUE, out.get("valid"));
		Assertions.assertEquals(1L, ((Map<?, ?>) ((Map<?, ?>) out.get("nodes")).get("a")).get("intervalsAboveThreshold"));
	}
}
