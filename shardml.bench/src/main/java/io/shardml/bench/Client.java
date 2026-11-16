package io.shardml.bench;

import java.sql.SQLException;
import java.util.SplittableRandom;
import java.util.concurrent.locks.LockSupport;

/* One client thread of a run. Times are System.nanoTime() values.
 *
 * Closed loop: from t0, transactions back to back (think time 0) until the end of the measured window;
 * a transaction is measured when it starts inside [warmEnd, end); latency from its start. Every
 * transaction a client starts is either a warm-up one (before warmEnd) or measured: with no warm-up, the
 * driver counts every transaction it executes.
 *
 * Open loop: the client's Schedule fixes intended start times in advance; the client waits for each,
 * or starts at once when it is behind. Latency is measured from the INTENDED start (so queueing
 * behind a slow transaction counts, no coordinated omission), and the schedule lag (actual minus
 * intended start) is recorded. Arrivals intended inside the window are measured and counted as
 * offered; one still not started at the drain deadline is counted as unstarted, never dropped.
 *
 * Failures: no retry by default. With retries > 0, a serialization failure or deadlock is retried up
 * to that many times with the same bindings; a transaction committed after a retry is counted and
 * timed separately (afterRetry), from its first start. A connection error resets the session (the
 * next transaction reconnects) and the client pauses 100 ms before going on. */
final class Client implements Runnable {

	static final long CONNECTION_PAUSE_NANOS = 100_000_000L;

	// time and waiting: System.nanoTime and parkNanos in runs, virtual time in tests
	interface Clock {
		long now();

		// returns at or after time t
		void sleepUntil(long t);
	}

	static final Clock REAL = new Clock() {
		@Override
		public long now() {
			return System.nanoTime();
		}

		@Override
		public void sleepUntil(long t) {
			for (long now = System.nanoTime(); now < t; now = System.nanoTime())
				LockSupport.parkNanos(t - now);
		}
	};

	private final Replay replay;
	private final Session session;
	private final SplittableRandom draws;
	private final Schedule schedule;
	private final int retries;
	private final long t0;
	private final long warmEnd;
	private final long end;
	private final long drainEnd;
	private final Clock clock;
	final Stats stats;
	private Throwable crash;

	// schedule null: closed loop
	Client(Replay replay, Session session, SplittableRandom draws, Schedule schedule, int retries, long t0, long warmEnd, long end,
			long drainEnd, Clock clock) {
		this.replay = replay;
		this.session = session;
		this.draws = draws;
		this.schedule = schedule;
		this.retries = retries;
		this.t0 = t0;
		this.warmEnd = warmEnd;
		this.end = end;
		this.drainEnd = drainEnd;
		this.clock = clock;
		this.stats = new Stats(replay.transactions().size());
	}

	@Override
	public void run() {
		try {
			if (schedule == null)
				closed();
			else
				open();
		} catch (Throwable t) {
			crash = t;
		} finally {
			session.close();
		}
	}

	Throwable crash() {
		return crash;
	}

	private void closed() {
		// start at t0, as the open loop does: a transaction started before t0 (the threads start up to 200 ms
		// early) would run unmeasured, uncounted even with no warm-up
		clock.sleepUntil(t0);
		while (true) {
			long start = clock.now();
			if (start >= end)
				return;
			Replay.Run run = replay.next(draws);
			transaction(run, start, start, start >= warmEnd);
		}
	}

	private void open() {
		while (true) {
			long intended = t0 + schedule.next();
			if (intended >= end)
				return;
			boolean measured = intended >= warmEnd;
			if (measured)
				stats.offered++;
			Replay.Run run = replay.next(draws);
			long now = clock.now();
			if (now > drainEnd) {
				if (measured)
					stats.unstarted++;
				continue;
			}
			if (now < intended) {
				clock.sleepUntil(intended);
				now = clock.now();
			}
			if (measured)
				stats.lag.recordValue(Stats.micros(now - intended));
			transaction(run, intended, now, measured);
		}
	}

	private void transaction(Replay.Run run, long from, long start, boolean measured) {
		if (measured)
			stats.started++;
		int attempt = 0;
		while (true) {
			try {
				session.execute(run);
				long done = clock.now();
				if (!measured)
					return;
				long micros = Stats.micros(done - from);
				if (attempt == 0) {
					stats.committed++;
					stats.committedByTemplate[run.template()]++;
					stats.overall.recordValue(micros);
					stats.byTemplate[run.template()].recordValue(micros);
				} else {
					stats.committedAfterRetry++;
					stats.afterRetry.recordValue(micros);
				}
				return;
			} catch (SQLException e) {
				ErrorClass c = ErrorClass.of(e);
				if (c == ErrorClass.CONNECTION) {
					session.reset();
					clock.sleepUntil(clock.now() + CONNECTION_PAUSE_NANOS);
				}
				if (c.retryable() && attempt < retries) {
					attempt++;
					if (measured)
						stats.retries++;
					continue;
				}
				if (measured) {
					stats.failed[c.ordinal()]++;
					stats.failedByTemplate[run.template()][c.ordinal()]++;
				}
				return;
			}
		}
	}
}
