package io.shardml.bench;

/* Pearson's chi-square goodness of fit against exact expected probabilities, and its p-value: the
 * upper tail of the chi-square distribution, Q(df / 2, x / 2), the regularized upper incomplete gamma
 * function (series for x < a + 1, Lentz's continued fraction otherwise; Numerical Recipes 6.2), with
 * ln Gamma by the Lanczos approximation (g = 7, n = 9; relative error below 1e-13 for the half-integer
 * arguments used here). Bins whose expected count is below MIN_EXPECTED are pooled into one bin (the
 * usual validity rule); if the pool is still below it, it is merged into the smallest other bin. */
final class ChiSquare {

	static final double MIN_EXPECTED = 5;

	record Result(int bins, int df, double statistic, double p) {
	}

	private ChiSquare() {
	}

	// probabilities: the exact probability of each bin (they need not add up to 1: the rest is a
	// bin of its own); observed: the count in each bin, n the total draws
	static Result test(double[] probabilities, long[] observed, long n) {
		int k = probabilities.length;
		double sumP = 0;
		long sumO = 0;
		for (int i = 0; i < k; i++) {
			sumP += probabilities[i];
			sumO += observed[i];
		}
		// the rest of the mass (and the draws outside the bins) as one more bin
		double restP = Math.max(0, 1 - sumP);
		long restO = n - sumO;
		java.util.List<double[]> bins = new java.util.ArrayList<>(); // {expected, observed}
		double poolE = 0;
		double poolO = 0;
		for (int i = 0; i <= k; i++) {
			double e = (i < k ? probabilities[i] : restP) * n;
			double o = i < k ? observed[i] : restO;
			if (e == 0 && o == 0)
				continue;
			if (e == 0)
				return new Result(k + 1, 0, Double.POSITIVE_INFINITY, 0); // a draw where the pmf has no mass
			if (e < MIN_EXPECTED) {
				poolE += e;
				poolO += o;
			} else {
				bins.add(new double[] { e, o });
			}
		}
		if (poolE > 0) {
			if (poolE >= MIN_EXPECTED || bins.isEmpty()) {
				bins.add(new double[] { poolE, poolO });
			} else {
				double[] smallest = bins.get(0);
				for (double[] b : bins) {
					if (b[0] < smallest[0])
						smallest = b;
				}
				smallest[0] += poolE;
				smallest[1] += poolO;
			}
		}
		double x = 0;
		for (double[] b : bins)
			x += (b[1] - b[0]) * (b[1] - b[0]) / b[0];
		int df = bins.size() - 1;
		return new Result(bins.size(), df, x, df <= 0 ? 1.0 : pValue(x, df));
	}

	// P[X >= x] for X ~ chi-square(df)
	static double pValue(double x, int df) {
		if (x <= 0)
			return 1.0;
		return upperGamma(df / 2.0, x / 2.0);
	}

	private static double upperGamma(double a, double x) {
		if (x < a + 1)
			return 1 - lowerSeries(a, x);
		return continuedFraction(a, x);
	}

	private static double lowerSeries(double a, double x) {
		double ap = a;
		double sum = 1 / a;
		double del = sum;
		for (int n = 0; n < 10_000; n++) {
			ap++;
			del *= x / ap;
			sum += del;
			if (Math.abs(del) < Math.abs(sum) * 1e-16)
				break;
		}
		return sum * Math.exp(-x + a * Math.log(x) - lnGamma(a));
	}

	private static double continuedFraction(double a, double x) {
		double tiny = 1e-300;
		double b = x + 1 - a;
		double c = 1 / tiny;
		double d = 1 / b;
		double h = d;
		for (int i = 1; i < 10_000; i++) {
			double an = -i * (i - a);
			b += 2;
			d = an * d + b;
			if (Math.abs(d) < tiny)
				d = tiny;
			c = b + an / c;
			if (Math.abs(c) < tiny)
				c = tiny;
			d = 1 / d;
			double del = d * c;
			h *= del;
			if (Math.abs(del - 1) < 1e-16)
				break;
		}
		return Math.exp(-x + a * Math.log(x) - lnGamma(a)) * h;
	}

	private static final double[] LANCZOS = { 0.99999999999980993, 676.5203681218851, -1259.1392167224028, 771.32342877765313,
			-176.61502916214059, 12.507343278686905, -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7 };

	static double lnGamma(double x) {
		if (x < 0.5)
			return Math.log(Math.PI / Math.abs(Math.sin(Math.PI * x))) - lnGamma(1 - x);
		x -= 1;
		double a = LANCZOS[0];
		double t = x + 7.5;
		for (int i = 1; i < 9; i++)
			a += LANCZOS[i] / (x + i);
		return 0.5 * Math.log(2 * Math.PI) + (x + 0.5) * Math.log(t) - t + Math.log(a);
	}
}
