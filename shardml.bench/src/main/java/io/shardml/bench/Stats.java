package io.shardml.bench;

import org.HdrHistogram.Histogram;

/* One client's counts and latency histograms over the measured window (merged after the run;
 * nothing is shared between clients while they run). Latencies in microseconds, 3 significant
 * digits, up to one hour. Every measured transaction ends in exactly one of: committed (first
 * attempt), committed after a retry, failed (by class), unstarted (open loop: its intended start was
 * in the window but it had not started when the drain deadline passed). */
final class Stats {

	static final long MAX_MICROS = 3_600_000_000L;

	final Histogram overall = histogram();
	final Histogram[] byTemplate;
	final Histogram afterRetry = histogram();
	final Histogram lag = histogram();
	long started;
	long committed;
	long committedAfterRetry;
	long unstarted;
	long offered;
	long retries;
	final long[] failed = new long[ErrorClass.values().length];
	final long[][] failedByTemplate;
	final long[] committedByTemplate;

	Stats(int templates) {
		byTemplate = new Histogram[templates];
		for (int i = 0; i < templates; i++)
			byTemplate[i] = histogram();
		failedByTemplate = new long[templates][ErrorClass.values().length];
		committedByTemplate = new long[templates];
	}

	static Histogram histogram() {
		return new Histogram(MAX_MICROS, 3);
	}

	long failedTotal() {
		long n = 0;
		for (long f : failed)
			n += f;
		return n;
	}

	void add(Stats other) {
		overall.add(other.overall);
		for (int i = 0; i < byTemplate.length; i++) {
			byTemplate[i].add(other.byTemplate[i]);
			committedByTemplate[i] += other.committedByTemplate[i];
			for (int c = 0; c < failed.length; c++)
				failedByTemplate[i][c] += other.failedByTemplate[i][c];
		}
		afterRetry.add(other.afterRetry);
		lag.add(other.lag);
		started += other.started;
		committed += other.committed;
		committedAfterRetry += other.committedAfterRetry;
		unstarted += other.unstarted;
		offered += other.offered;
		retries += other.retries;
		for (int c = 0; c < failed.length; c++)
			failed[c] += other.failed[c];
	}

	static long micros(long nanos) {
		return Math.min(MAX_MICROS, Math.max(0, nanos / 1000));
	}
}
