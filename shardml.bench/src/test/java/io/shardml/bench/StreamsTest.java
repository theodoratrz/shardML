package io.shardml.bench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SplittableRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.Inputs;
import io.shardml.cost.RankMapping;

/* Paired streams: for a workload, seed and client index, the templates and values a client replays
 * depend on nothing else (not the client count, not the layout); and the template mix is the
 * weights. */
public class StreamsTest {

	private static List<String> trace(Replay replay, SplittableRandom random, int runs) {
		List<String> out = new ArrayList<>();
		for (int i = 0; i < runs; i++) {
			Replay.Run run = replay.next(random);
			StringBuilder line = new StringBuilder(replay.transactions().get(run.template()).name());
			for (Replay.Execution e : run.executions())
				line.append(' ').append(e.statement()).append(Arrays.toString(e.parameters()));
			out.add(line.toString());
		}
		return out;
	}

	@Test
	public void testStreamsDependOnSeedAndIndexOnly() {
		long[] a = new Streams(7, 2).draws(1).longs(50).toArray();
		Assertions.assertArrayEquals(a, new Streams(7, 2).draws(1).longs(50).toArray(), "reproducible");
		Assertions.assertArrayEquals(a, new Streams(7, 16).draws(1).longs(50).toArray(), "not the client count");
		Assertions.assertFalse(Arrays.equals(a, new Streams(7, 2).draws(0).longs(50).toArray()), "clients differ");
		Assertions.assertFalse(Arrays.equals(a, new Streams(8, 2).draws(1).longs(50).toArray()), "seeds differ");
		Assertions.assertFalse(Arrays.equals(a, new Streams(7, 2).arrivals(1).longs(50).toArray()), "arrivals are their own stream");
	}

	@Test
	public void testPairedAcrossLayouts() {
		for (Inputs.Loaded loaded : List.of(Examples.banking(), Examples.tpcc())) {
			for (long offset : new long[] { 0, 3 }) {
				RankMapping mapping = RankMapping.of(loaded.workload(), offset);
				Replay first = new Replay(loaded.workload(), loaded.layouts().get(0), mapping);
				Replay second = new Replay(loaded.workload(), loaded.layouts().get(1), mapping);
				for (int client = 0; client < 3; client++) {
					List<String> a = trace(first, new Streams(11, 3).draws(client), 3000);
					Assertions.assertEquals(a, trace(second, new Streams(11, 3).draws(client), 3000),
							loaded.workload().name() + " client " + client);
					Assertions.assertEquals(a, trace(first, new Streams(11, 5).draws(client), 3000));
				}
			}
		}
	}

	@Test
	public void testTemplateMixIsTheWeights() {
		Inputs.Loaded tpcc = Examples.tpcc();
		Replay replay = new Replay(tpcc.workload(), tpcc.layouts().get(0), RankMapping.of(tpcc.workload(), 0));
		double total = tpcc.workload().transactions().stream().mapToDouble(t -> t.weight()).sum();
		double[] p = new double[replay.mix().size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = tpcc.workload().transactions().get(i).weight() / total;
			Assertions.assertEquals(p[i], replay.mix().probability(i), 1e-15);
		}
		int n = 200_000;
		long[] counts = new long[p.length];
		SplittableRandom random = new Streams(3, 1).draws(0);
		for (int i = 0; i < n; i++)
			counts[replay.next(random).template()]++;
		ChiSquare.Result r = ChiSquare.test(p, counts, n);
		Assertions.assertTrue(r.p() > 1e-4, r.toString());
		// a mix with one weight off by 5% is told apart at this n
		long[] skewed = new long[p.length];
		Mix wrong = new Mix(new double[] { 45 * 1.05, 43, 4, 4, 4 });
		for (int i = 0; i < n; i++)
			skewed[wrong.draw(random)]++;
		Assertions.assertTrue(ChiSquare.test(p, skewed, n).p() < 1e-6);
	}

	@Test
	public void testScheduleIsAPoissonProcess() {
		Schedule a = new Schedule(new Streams(5, 4).arrivals(2), 250);
		Schedule b = new Schedule(new Streams(5, 4).arrivals(2), 250);
		int n = 100_000;
		long previous = 0;
		long longGaps = 0;
		long last = 0;
		for (int i = 0; i < n; i++) {
			long t = a.next();
			Assertions.assertEquals(t, b.next(), "fixed in advance by the seed");
			Assertions.assertTrue(t >= previous);
			if (t - previous > 4_000_000L)
				longGaps++;
			previous = t;
			last = t;
		}
		// rate 250/s: mean gap 4 ms, so n arrivals take n / 250 s; P[gap > mean] = 1/e
		Assertions.assertEquals(n / 250.0, last / 1e9, 5 * Math.sqrt(n) / 250.0);
		Assertions.assertEquals(n / Math.E, longGaps, 5 * Math.sqrt(n * (1 / Math.E) * (1 - 1 / Math.E)));
	}
}
