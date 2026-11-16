package io.shardml.bench;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.Inputs;

/* The parts of the run's counters that need no cluster: the shard-name pattern of the per-node load
 * diagnostic, the architecture names behind the `emulated` flag, and the entry-share rule (amendment 3, 2). */
public class CountersTest {

	@Test
	public void testShardPattern() {
		Inputs.Loaded b = Examples.banking();
		String p = Counters.shardPattern(b.layouts().get(0));
		Assertions.assertTrue(p.startsWith("\\m(") && p.endsWith(")_[0-9]+\\M"), p);
		for (String t : List.of("customers", "accounts", "transactions", "cards"))
			Assertions.assertTrue(p.contains(t), p);
		// the same pattern in Java's syntax: a shard name matches, the distributed table's own name does not
		java.util.regex.Pattern re = java.util.regex.Pattern.compile(p.replace("\\m", "\\b").replace("\\M", "\\b"));
		Assertions.assertTrue(re.matcher("SELECT * FROM public.accounts_102008 accounts WHERE").find());
		Assertions.assertFalse(re.matcher("SELECT * FROM public.accounts WHERE id = $1").find());
	}

	@Test
	public void testArch() {
		Assertions.assertEquals("amd64", Counters.arch("x86_64"));
		Assertions.assertEquals("amd64", Counters.arch("amd64"));
		Assertions.assertEquals("arm64", Counters.arch("aarch64"));
		Assertions.assertEquals("arm64", Counters.arch("arm64"));
		Assertions.assertEquals("riscv64", Counters.arch("riscv64"));
	}

	@Test
	public void testEntryShares() {
		List<String> targets = List.of("w1", "w2", "w3");
		// 6 clients, two per target, equal work: uniform
		Map<String, Object> e = Counters.entry(targets, 6, new long[] { 10, 10, 10, 10, 10, 10 }, new long[] { 20, 20, 20, 20, 20, 20 });
		Assertions.assertEquals(Boolean.TRUE, e.get("uniform"));
		Assertions.assertEquals(0.0, (Double) e.get("maxShareDeviation"), 1e-12);
		// 4 clients on 3 targets: w1 gets two of them, never uniform
		e = Counters.entry(targets, 4, new long[] { 10, 10, 10, 10 }, new long[] { 20, 20, 20, 20 });
		Assertions.assertEquals(Boolean.FALSE, e.get("uniform"));
		Assertions.assertEquals(0.5 - 1.0 / 3, (Double) e.get("maxShareDeviation"), 1e-12);
		Assertions.assertEquals(2, ((Map<?, ?>) ((List<?>) e.get("targets")).get(0)).get("clients"));
		// a multiple of the targets, but one target's clients ran slower: 0.30 vs 1/3 is off by more than 0.02
		e = Counters.entry(targets, 3, new long[] { 30, 35, 35 }, new long[] { 30, 35, 35 });
		Assertions.assertEquals(Boolean.FALSE, e.get("uniform"));
		// within 0.02: uniform
		e = Counters.entry(targets, 3, new long[] { 33, 33, 34 }, new long[] { 33, 33, 34 });
		Assertions.assertEquals(Boolean.TRUE, e.get("uniform"));
		// one target (mode a, the coordinator): share 1, uniform
		e = Counters.entry(List.of("coordinator"), 4, new long[] { 1, 2, 3, 4 }, new long[] { 1, 2, 3, 4 });
		Assertions.assertEquals(Boolean.TRUE, e.get("uniform"));
	}
}
