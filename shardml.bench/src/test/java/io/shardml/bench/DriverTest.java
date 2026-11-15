package io.shardml.bench;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.Inputs;
import io.shardml.cost.RankMapping;

/* The driver's options, run directory, output, CPU flag and replay rules (no cluster). */
public class DriverTest {

	private static Driver.Options options(String... args) throws Driver.UsageError {
		return Driver.Options.parse(List.of(args), "run");
	}

	@Test
	public void testRunDirectoryIsDeterministic() throws Exception {
		String w = Examples.COST + "banking.workload";
		String m = Examples.COST + "banking-by-customer.shardml";
		Driver.Options closed = options("--mode", "closed", "--clients", "16", "--targets", "w1:5432,w2:5432", "--seed", "3", "--rep",
				"2", w, m);
		Assertions.assertEquals(Path.of("bench-runs/banking/banking_by_customer/workers/closed-c16/defaults-k0/seed-3-rep-2"),
				Driver.runDirectory(closed, "banking", "banking_by_customer"));
		Driver.Options open = options("--mode", "open", "--rate", "250.5", "--clients", "8", "--targets", "c:5432", "--entry",
				"coordinator", "--set", "s=1.2", "--set", "history_w=40", "--rank-offset", "3", "--out", "x", w, m);
		Assertions.assertEquals(Path.of("x/banking/banking_by_customer/coordinator/open-c8-r250.5/history_w=40,s=1.2-k3/seed-1-rep-1"),
				Driver.runDirectory(open, "banking", "banking_by_customer"));
	}

	@Test
	public void testUsageErrors() {
		for (String[] args : List.of(new String[] { "run", "--mode", "open", "--targets", "a:1" }, new String[] { "run", "--mode", "x" },
				new String[] { "run", "--targets", "nohost" }, new String[] { "nope" })) {
			ByteArrayOutputStream err = new ByteArrayOutputStream();
			int exit = Driver.run(List.of(args), new PrintStream(OutputStream.nullOutputStream()), new PrintStream(err, true,
					StandardCharsets.UTF_8));
			Assertions.assertEquals(Driver.USAGE, exit, err.toString(StandardCharsets.UTF_8));
		}
		Assertions.assertThrows(Driver.UsageError.class, () -> options("--mode", "open", "--targets", "a:1",
				Examples.COST + "banking.workload", Examples.COST + "banking-by-customer.shardml"));
		Assertions.assertThrows(Driver.UsageError.class, () -> options("--mode", "closed", "--rate", "5", "--targets", "a:1",
				Examples.COST + "banking.workload", Examples.COST + "banking-by-customer.shardml"));
	}

	@Test
	public void testCpuFlag() {
		long s = 1_000_000_000L;
		List<long[]> busy = new ArrayList<>();
		List<long[]> bursty = new ArrayList<>();
		for (int i = 0; i <= 10; i++) {
			// 4 cores: 2.4 core-seconds per second is 60%; 1.2 is 30%, plus one 2.4 s burst in second 5
			busy.add(new long[] { i * s, (long) (i * 2.4 * s) });
			bursty.add(new long[] { i * s, (long) (i * 1.2 * s) + (i >= 5 ? (long) (2.4 * s) : 0) });
		}
		CpuMeter.Summary b = CpuMeter.summarize(busy, 4, CpuMeter.LIMIT);
		Assertions.assertEquals(0.6, b.mean(), 1e-9);
		Assertions.assertTrue(b.overLimit());
		// the flag is on the window's mean; the busiest second is reported beside it
		CpuMeter.Summary u = CpuMeter.summarize(bursty, 4, CpuMeter.LIMIT);
		Assertions.assertEquals(0.36, u.mean(), 1e-9);
		Assertions.assertEquals(0.9, u.max(), 1e-9);
		Assertions.assertFalse(u.overLimit());
		CpuMeter.Summary half = CpuMeter.summarize(busy, 5, 0.48);
		Assertions.assertEquals(0.48, half.mean(), 1e-9);
		Assertions.assertFalse(half.overLimit(), "exactly at the limit is not over it");
		Assertions.assertTrue(Double.isNaN(CpuMeter.summarize(busy.subList(0, 1), 4, 0.5).mean()), "one sample: no rate");
	}

	@Test
	public void testDeletesAreRefused() {
		Inputs.Loaded shapes = Examples.load(Examples.COST + "../../bench/integration/cost-routing/shapes.workload",
				Examples.COST + "tpcc-by-warehouse.shardml");
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Replay(shapes.workload(), shapes.layouts().get(0), RankMapping.of(shapes.workload(), 0)));
		Assertions.assertTrue(e.getMessage().contains("cannot replay a delete"), e.getMessage());
	}

	@Test
	public void testResultJson() {
		Inputs.Loaded b = Examples.banking();
		Replay replay = new Replay(b.workload(), b.layouts().get(0), RankMapping.of(b.workload(), 0));
		Stats s = new Stats(replay.transactions().size());
		s.started = 5;
		s.committed = 3;
		s.failed[ErrorClass.DEADLOCK.ordinal()] = 2;
		s.failedByTemplate[1][ErrorClass.DEADLOCK.ordinal()] = 2;
		s.overall.recordValue(1500);
		s.byTemplate[1].recordValue(1500);
		Map<String, Object> r = Driver.result(optionsUnchecked(), replay, s,
				CpuMeter.summarize(List.of(new long[] { 0, 0 }, new long[] { 1_000_000_000L, 100_000_000L }), 1, 0.5), List.of());
		String json = Json.write(r);
		Assertions.assertTrue(json.contains("\"deadlock\": 2"), json);
		Assertions.assertTrue(json.contains("\"balanced\": true"), json);
		Assertions.assertTrue(json.contains("\"history\""), json);
		Assertions.assertTrue(json.contains("\"overLimit\": false"), json);
		Assertions.assertTrue(Driver.summary(r).startsWith("committed 3 ("), Driver.summary(r));
		Map<String, Object> small = new java.util.LinkedHashMap<>();
		small.put("a", List.of(1, "x\"y", 0.25, Double.NaN));
		small.put("b", null);
		Assertions.assertEquals("{\n  \"a\": [\n    1,\n    \"x\\\"y\",\n    0.25,\n    null\n  ],\n  \"b\": null\n}\n", Json.write(small));
	}

	private static Driver.Options optionsUnchecked() {
		try {
			return options("--mode", "closed", "--targets", "a:1", "--duration", "10", Examples.COST + "banking.workload",
					Examples.COST + "banking-by-customer.shardml");
		} catch (Driver.UsageError e) {
			throw new IllegalStateException(e);
		}
	}
}
