package io.shardml.bench;

import java.util.SplittableRandom;

/* The driver's random streams: java.util.SplittableRandom (JDK 8+, the SplitMix64 generator of
 * Steele, Lea and Flood, "Fast splittable pseudorandom number generators", OOPSLA 2014), seeded.
 *
 * Derivation, for a run seed S and client (thread) index i:
 *   root = new SplittableRandom(S)
 *   client_i = the (i + 1)-th root.split()          (splits taken in index order 0, 1, ..., i)
 *   draws_i = client_i.split()                       (template choice, then the transaction's lets)
 *   arrivals_i = client_i.split()                    (open loop only: the arrival schedule)
 * So client i's draws depend only on S and i: not on the number of clients, the mode, the
 * layout or the timing. Two runs of the same workload, seed and client index on two layouts
 * replay the same templates with the same values (paired streams). The arrival stream is separate,
 * so the schedule never shifts the draws. Plan-phase4 2.2 says seed = run seed + client id; split()
 * is used instead, which is what the generator is designed for (adjacent seeds are not independent
 * streams by construction). */
final class Streams {

	static final String DESCRIPTION = "java.util.SplittableRandom (SplitMix64); root = new SplittableRandom(seed); "
			+ "client i = the (i+1)-th root.split(); draws_i = client_i.split(), arrivals_i = client_i.split()";

	private final SplittableRandom[] draws;
	private final SplittableRandom[] arrivals;

	Streams(long seed, int clients) {
		SplittableRandom root = new SplittableRandom(seed);
		draws = new SplittableRandom[clients];
		arrivals = new SplittableRandom[clients];
		for (int i = 0; i < clients; i++) {
			SplittableRandom client = root.split();
			draws[i] = client.split();
			arrivals[i] = client.split();
		}
	}

	SplittableRandom draws(int client) {
		return draws[client];
	}

	SplittableRandom arrivals(int client) {
		return arrivals[client];
	}
}
