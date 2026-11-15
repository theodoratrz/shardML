package io.shardml.bench;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.IntFunction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.Inputs;
import io.shardml.cost.RankMapping;

/* The client loop on virtual time with a fake session: error classification and counting (nothing
 * dropped), bounded retries reported apart, and the open loop's latency from the intended start. */
public class ClientTest {

	// virtual nanoseconds: sleeping jumps, a transaction takes `service` nanoseconds
	static final class VirtualClock implements Client.Clock {
		long now;

		@Override
		public long now() {
			return now;
		}

		@Override
		public void sleepUntil(long t) {
			now = Math.max(now, t);
		}
	}

	// fails the n-th attempt (1-based) with the exception the function gives, or not when it gives null
	static final class FakeSession implements Session {
		final VirtualClock clock;
		final long service;
		final IntFunction<SQLException> failure;
		int attempts;
		int resets;
		boolean closed;

		FakeSession(VirtualClock clock, long service, IntFunction<SQLException> failure) {
			this.clock = clock;
			this.service = service;
			this.failure = failure;
		}

		@Override
		public void execute(Replay.Run run) throws SQLException {
			attempts++;
			clock.now += service;
			SQLException e = failure.apply(attempts);
			if (e != null)
				throw e;
		}

		@Override
		public void reset() {
			resets++;
		}

		@Override
		public void close() {
			closed = true;
		}
	}

	private static Replay banking() {
		Inputs.Loaded b = Examples.banking();
		return new Replay(b.workload(), b.layouts().get(0), RankMapping.of(b.workload(), 0));
	}

	@Test
	public void testErrorClasses() {
		Assertions.assertEquals(ErrorClass.SERIALIZATION, ErrorClass.of(new SQLException("x", "40001")));
		Assertions.assertEquals(ErrorClass.DEADLOCK, ErrorClass.of(new SQLException("x", "40P01")));
		Assertions.assertEquals(ErrorClass.TIMEOUT, ErrorClass.of(new SQLException("canceling statement due to statement timeout", "57014")));
		Assertions.assertEquals(ErrorClass.TIMEOUT, ErrorClass.of(new SQLException("x", "55P03")));
		Assertions.assertEquals(ErrorClass.CONNECTION, ErrorClass.of(new SQLException("x", "08006")));
		Assertions.assertEquals(ErrorClass.CONNECTION, ErrorClass.of(new SQLException("x", "57P01")));
		Assertions.assertEquals(ErrorClass.CONNECTION, ErrorClass.of(new SQLException("x", null, new IOException("reset"))));
		Assertions.assertEquals(ErrorClass.OTHER, ErrorClass.of(new SQLException("x", "23505")));
		Assertions.assertEquals(ErrorClass.OTHER, ErrorClass.of(new SQLException("x")));
		// the state may sit on a chained exception
		SQLException outer = new SQLException("batch");
		outer.setNextException(new SQLException("x", "40P01"));
		Assertions.assertEquals(ErrorClass.DEADLOCK, ErrorClass.of(outer));
		Assertions.assertEquals(ErrorClass.SERIALIZATION, ErrorClass.of(new SQLException("wrap", null, new SQLException("x", "40001"))));
		Assertions.assertTrue(ErrorClass.SERIALIZATION.retryable() && ErrorClass.DEADLOCK.retryable());
		Assertions.assertFalse(ErrorClass.TIMEOUT.retryable() || ErrorClass.CONNECTION.retryable() || ErrorClass.OTHER.retryable());
	}

	private static final String[] STATES = { "40001", "40P01", "57014", "08006", "XX000" };

	// attempt k fails with STATES[k / 2 % 5] when k is even (every second attempt fails, all classes in turn)
	private static SQLException pattern(int k) {
		return k % 2 == 0 ? new SQLException("fail", STATES[(k / 2) % STATES.length]) : null;
	}

	@Test
	public void testClosedLoopCountsEveryFailure() {
		VirtualClock clock = new VirtualClock();
		FakeSession session = new FakeSession(clock, 1_000_000L, ClientTest::pattern);
		long sec = 1_000_000_000L;
		Client c = new Client(banking(), session, new Streams(1, 1).draws(0), null, 0, 0, sec, 3 * sec, 3 * sec, clock);
		c.run();
		Assertions.assertNull(c.crash());
		Assertions.assertTrue(session.closed);
		Stats s = c.stats;
		Assertions.assertTrue(s.started > 100, String.valueOf(s.started)); // about 18 cycles of 10 attempts and one 100 ms pause in 2 s
		Assertions.assertEquals(s.started, s.committed + s.committedAfterRetry + s.failedTotal(), "nothing dropped");
		Assertions.assertEquals(0, s.committedAfterRetry);
		// every second attempt fails, the five classes in turn
		Assertions.assertEquals(s.committed, s.failedTotal(), 1);
		for (ErrorClass e : ErrorClass.values())
			Assertions.assertEquals(s.failedTotal() / 5.0, s.failed[e.ordinal()], 1, e.name());
		Assertions.assertEquals(s.committed, s.overall.getTotalCount());
		Assertions.assertEquals(1000, s.overall.getValueAtPercentile(50), 1, "1 ms from its start");
		// a connection error resets the session (and pauses); the next transaction reconnects
		Assertions.assertTrue(session.resets >= s.failed[ErrorClass.CONNECTION.ordinal()]);
		long perTemplate = 0;
		long failedPerTemplate = 0;
		for (int t = 0; t < s.committedByTemplate.length; t++) {
			perTemplate += s.committedByTemplate[t];
			for (long f : s.failedByTemplate[t])
				failedPerTemplate += f;
		}
		Assertions.assertEquals(s.committed, perTemplate);
		Assertions.assertEquals(s.failedTotal(), failedPerTemplate);
	}

	@Test
	public void testBoundedRetriesAreReportedApart() {
		VirtualClock clock = new VirtualClock();
		// a serialization failure on every attempt but every third: a run needs up to 3 attempts
		FakeSession session = new FakeSession(clock, 1_000_000L, k -> k % 3 == 0 ? null : new SQLException("s", "40001"));
		long sec = 1_000_000_000L;
		Client one = new Client(banking(), session, new Streams(1, 1).draws(0), null, 1, 0, 0, sec, sec, clock);
		one.run();
		// with 1 retry, attempts go fail, fail (give up), ok, fail, fail, ok ...: no transaction commits after a retry
		Assertions.assertEquals(one.stats.started, one.stats.committed + one.stats.committedAfterRetry + one.stats.failedTotal());
		Assertions.assertTrue(one.stats.retries > 0);
		VirtualClock clock2 = new VirtualClock();
		FakeSession session2 = new FakeSession(clock2, 1_000_000L, k -> k % 3 == 0 ? null : new SQLException("s", "40001"));
		Client two = new Client(banking(), session2, new Streams(1, 1).draws(0), null, 2, 0, 0, sec, sec, clock2);
		two.run();
		Stats s = two.stats;
		// with 2 retries every transaction commits on its third attempt, after a retry
		Assertions.assertEquals(0, s.committed);
		Assertions.assertEquals(0, s.failedTotal());
		Assertions.assertEquals(s.started, s.committedAfterRetry);
		Assertions.assertEquals(2 * s.started, s.retries);
		Assertions.assertEquals(0, s.overall.getTotalCount(), "the main histograms hold first-attempt commits only");
		Assertions.assertEquals(3000, s.afterRetry.getValueAtPercentile(50), 3, "timed from the first attempt");
	}

	@Test
	public void testOpenLoopTimesFromTheIntendedStart() {
		long sec = 1_000_000_000L;
		// 2 ms per transaction at 400 arrivals/s: the client keeps up (utilization 0.8), with queueing
		VirtualClock clock = new VirtualClock();
		FakeSession session = new FakeSession(clock, 2_000_000L, k -> null);
		Client c = new Client(banking(), session, new Streams(9, 1).draws(0), new Schedule(new Streams(9, 1).arrivals(0), 400), 0, 0,
				sec, 11 * sec, 11 * sec + sec, clock);
		c.run();
		Stats s = c.stats;
		Assertions.assertEquals(s.offered, s.started + s.unstarted);
		Assertions.assertEquals(s.started, s.committed);
		Assertions.assertEquals(4000, s.offered, 5 * Math.sqrt(4000));
		Assertions.assertEquals(0, s.unstarted);
		// latency = lag + 2 ms service for every transaction; some arrivals waited (lag > 0)
		Assertions.assertEquals(s.lag.getTotalCount(), s.overall.getTotalCount());
		Assertions.assertEquals(2000, s.overall.getMinValue(), 2);
		Assertions.assertTrue(s.lag.getMaxValue() > 0);
		Assertions.assertEquals(s.lag.getMean() + 2000, s.overall.getMean(), 0.002 * s.overall.getMean() + 2);
		// the schedule does not depend on the database: the same arrivals with a slower session
		VirtualClock slowClock = new VirtualClock();
		FakeSession slow = new FakeSession(slowClock, 5_000_000L, k -> null);
		Client overloaded = new Client(banking(), slow, new Streams(9, 1).draws(0), new Schedule(new Streams(9, 1).arrivals(0), 400), 0, 0,
				sec, 11 * sec, 11 * sec + sec, slowClock);
		overloaded.run();
		Stats o = overloaded.stats;
		Assertions.assertEquals(s.offered, o.offered, "the same offered load");
		// 5 ms per transaction caps it at 200/s: most measured arrivals start late, the rest are counted unstarted
		Assertions.assertTrue(o.unstarted > 0);
		Assertions.assertEquals(o.offered, o.started + o.unstarted, "nothing dropped");
		Assertions.assertTrue(o.overall.getValueAtPercentile(99) > 1_000_000, "queueing shows in the latency");
	}
}
