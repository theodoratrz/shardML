package io.shardml.cost;

import java.util.Arrays;
import java.util.Comparator;

/* Splits a whole number of items over ranks 1..n in proportion to a distribution, by largest
 * remainder: each rank gets floor(total * pmf(r)), and the items left over go one each to the
 * ranks with the largest fractional parts, ties to the lower rank. This is how a `within`
 * domain's children and an `over` table's rows are counted per parent rank. */
public final class LargestRemainder {

	private LargestRemainder() {
	}

	// the zipf(s) weights r^-s of ranks 1..n (s = 0 gives uniform)
	public static double[] zipfWeights(int n, double s) {
		double[] weights = new double[n];
		for (int r = 1; r <= n; r++)
			weights[r - 1] = s == 0 ? 1.0 : Math.pow(r, -s);
		return weights;
	}

	// counts[r - 1] for rank r; the counts add up to total
	public static long[] apportion(long total, double[] weights) {
		int n = weights.length;
		double sum = 0;
		for (double w : weights)
			sum += w;
		long[] counts = new long[n];
		double[] remainders = new double[n];
		long assigned = 0;
		for (int i = 0; i < n; i++) {
			double exact = total * weights[i] / sum;
			counts[i] = (long) Math.floor(exact);
			remainders[i] = exact - counts[i];
			assigned += counts[i];
		}
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++)
			order[i] = i;
		Arrays.sort(order, Comparator.<Integer>comparingDouble(i -> -remainders[i]).thenComparingInt(i -> i));
		for (long k = 0; k < total - assigned && k < n; k++)
			counts[order[(int) k]]++;
		return counts;
	}
}
