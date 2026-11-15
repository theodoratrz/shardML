package io.shardml.cost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.shardml.cost.Workload.Dist;
import io.shardml.cost.Workload.Draw;
import io.shardml.cost.Workload.ElseWith;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Let;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.Transaction;
import io.shardml.cost.Workload.VarRef;

/* The marginal distribution of every variable of a transaction over its domain's values
 * (Plan-phase3 4.4c, 4). Joint distributions are Step 7's; the load metrics need only these.
 *  - a draw: pmf(rank) on value(D, rank), so the rank offset applies (top-level domains);
 *  - `distinct from $y`: x by pmf p excluding y's value u, y's marginal q taken in D (projected when
 *    y is of a descendant domain): P(x = v) = p(v) * (S - q(v) / (1 - p(v))), S = sum_u q(u) / (1 - p(u));
 *  - `$y, else with p:`: (1 - p) * q(v) + p * the excluding draw above.
 * Each costs O(|D|) (plus |E| to project a base of domain E); never pairs of values. */
public final class Marginals {

	private final Map<Integer, double[]> byLet = new HashMap<>();
	private final Map<Integer, String> domains = new HashMap<>();

	private Marginals() {
	}

	public static Marginals of(Workload workload, RankMapping mapping, Transaction transaction) {
		Marginals m = new Marginals();
		m.walk(transaction.steps(), mapping);
		return m;
	}

	// P(the let's variable = value), value in 1..|domain|
	public double probability(int let, long value) {
		return of(let)[(int) value - 1];
	}

	double[] of(int let) {
		double[] marginal = byLet.get(let);
		if (marginal == null)
			throw new IllegalArgumentException("No let with id " + let + ".");
		return marginal;
	}

	private void walk(List<Step> steps, RankMapping mapping) {
		for (Step step : steps) {
			if (step instanceof Repeat repeat)
				walk(repeat.steps(), mapping);
			else if (step instanceof Let let) {
				domains.put(let.id(), let.domain());
				byLet.put(let.id(), marginal(let, mapping));
			}
		}
	}

	private double[] marginal(Let let, RankMapping mapping) {
		if (let instanceof Draw draw) {
			double[] p = draw(draw.domain(), draw.dist(), mapping);
			return draw.distinctFrom() == null ? p : excluding(p, base(draw.distinctFrom(), draw.domain(), mapping));
		}
		ElseWith with = (ElseWith) let;
		double[] p = draw(with.domain(), with.dist(), mapping);
		double[] q = base(with.base(), with.domain(), mapping);
		double[] excluded = excluding(p, q);
		double[] out = new double[p.length];
		for (int i = 0; i < p.length; i++)
			out[i] = (1 - with.p()) * q[i] + with.p() * excluded[i];
		return out;
	}

	// pmf over ranks, placed on values
	static double[] draw(String domain, Dist dist, RankMapping mapping) {
		int n = (int) mapping.size(domain);
		double[] weights = RankMapping.weights(n, dist);
		double sum = 0;
		for (double w : weights)
			sum += w;
		double[] p = new double[n];
		for (int r = 1; r <= n; r++)
			p[(int) mapping.value(domain, r) - 1] = weights[r - 1] / sum;
		return p;
	}

	// x by p excluding y's value, y ~ q
	private static double[] excluding(double[] p, double[] q) {
		double s = 0;
		for (int u = 0; u < p.length; u++)
			s += q[u] / (1 - p[u]);
		double[] out = new double[p.length];
		for (int v = 0; v < p.length; v++)
			out[v] = p[v] * (s - q[v] / (1 - p[v]));
		return out;
	}

	// the marginal of a reference's value taken in the given domain: through its projection path
	private double[] base(VarRef ref, String domain, RankMapping mapping) {
		double[] own = of(ref.let());
		if (ref.path().isEmpty())
			return own;
		KeyPath path = new KeyPath(domains.get(ref.let()), ref.path());
		double[] out = new double[(int) mapping.size(domain)];
		for (int v = 1; v <= own.length; v++)
			out[(int) mapping.project(path, v) - 1] += own[v - 1];
		return out;
	}
}
