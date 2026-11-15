package io.shardml.bench;

import java.util.random.RandomGenerator;

/* A categorical draw over weights (the template mix, weight / total): inverse CDF over the
 * cumulative weights, one uniform per draw, binary search (O(log n)). cdf[i] - cdf[i - 1] is
 * weight[i] / total up to the rounding of the prefix sums; the last entry is exactly 1. */
final class Mix {

	private final double[] cdf;

	Mix(double[] weights) {
		if (weights.length == 0)
			throw new IllegalArgumentException("A mix needs at least one weight.");
		double total = 0;
		for (double w : weights) {
			if (!(w > 0))
				throw new IllegalArgumentException("Weight " + w + " is not positive.");
			total += w;
		}
		cdf = new double[weights.length];
		double sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i];
			cdf[i] = sum / total;
		}
		cdf[cdf.length - 1] = 1.0;
	}

	int draw(RandomGenerator random) {
		double u = random.nextDouble();
		int lo = 0;
		int hi = cdf.length - 1;
		while (lo < hi) {
			int mid = (lo + hi) >>> 1;
			if (u < cdf[mid])
				hi = mid;
			else
				lo = mid + 1;
		}
		return lo;
	}

	// the probability of category i under the table (for the check against the weights)
	double probability(int i) {
		return cdf[i] - (i == 0 ? 0 : cdf[i - 1]);
	}

	int size() {
		return cdf.length;
	}
}
