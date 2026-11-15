package io.shardml.bench;

import java.util.random.RandomGenerator;

/* The open-loop arrival schedule of one client: a Poisson process of rate (target rate / clients),
 * so the clients together form a Poisson process at the target rate (a superposition of independent
 * Poisson processes). Intended start times in nanoseconds from the run start, fixed in advance by the
 * client's arrival stream: t_1 = E_1, t_{k+1} = t_k + E_{k+1}, E ~ Exponential(rate), drawn by
 * inversion -ln(1 - u) / rate. They never depend on how fast the database answers. */
final class Schedule {

	private final RandomGenerator random;
	private final double meanNanos;
	private double next;

	Schedule(RandomGenerator random, double ratePerSecond) {
		if (!(ratePerSecond > 0))
			throw new IllegalArgumentException("The arrival rate is " + ratePerSecond + "; it must be positive.");
		this.random = random;
		this.meanNanos = 1e9 / ratePerSecond;
	}

	// the next intended start time, nanoseconds after the run start
	long next() {
		next += -Math.log(1 - random.nextDouble()) * meanNanos;
		return (long) next;
	}
}
