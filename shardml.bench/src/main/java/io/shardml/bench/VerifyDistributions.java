package io.shardml.bench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.function.LongUnaryOperator;

import io.shardml.cost.CitusHash;
import io.shardml.cost.KeyBuckets;
import io.shardml.cost.Layout;
import io.shardml.cost.Marginals;
import io.shardml.cost.RankMapping;
import io.shardml.cost.Routing;
import io.shardml.cost.Sampler;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.Draw;
import io.shardml.cost.Workload.ElseWith;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Let;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.Transaction;
import io.shardml.cost.Workload.VarRef;

/* `driver verify-distributions` (Plan-phase4 2.3): the driver's draws against the model's exact
 * distributions, no cluster. For each transaction, `draws` runs of the Sampler with a fixed seed; per
 * let, its value in the first iteration of each run (independent across runs; later iterations share
 * any base drawn outside the repeat, so they are tested separately):
 *  - value: chi-square against Marginals (the model's exact marginal): the values with expected
 *    count >= 5, at most the top 100 by probability, one bin each, plus one bin for the rest; when no
 *    single value reaches 5 (a large uniform domain), 100 bins of equal mass over the values in
 *    probability order;
 *  - buckets: for every routed statement of every given layout, chi-square of the routed bucket
 *    (CitusHash over the projected value, KeyBuckets) against the bucket mass of the marginal;
 *  - else with: P[x != base] = p (chi-square with 1 df, the binomial test); p = 0 or 1 exactly;
 *  - distinct from: no draw ever equals its (projected) base, in any iteration: an exact count;
 *  - repeat: iterations 1 and 2 of a let in a repeat of 2 or more are independent: a plain draw (or a
 *    distinct-from whose base is drawn in the same iteration), 10 x 10 equal-mass bins of the pair
 *    against the product of the exact marginals; an else-with, the pair of "differs" indicators
 *    against Bernoulli(p) x Bernoulli(p) (exact whatever the base);
 *  - the template mix: chi-square of `draws` template draws against weight / total.
 * Multiple tests: Bonferroni. With m chi-square tests, each fails below alpha / m, alpha = 0.001
 * (the plan's threshold, for the family), so the chance that a correct sampler fails any test is at
 * most 0.001. The exact checks fail on any violation. */
final class VerifyDistributions {

	static final double ALPHA = 0.001;

	record Check(String transaction, String subject, String test, int bins, int df, double statistic, double p, long violations,
			boolean exact) {
	}

	record Report(List<Check> checks, int tests, double threshold, boolean passed) {
	}

	private VerifyDistributions() {
	}

	static Report run(Workload workload, List<Layout> layouts, RankMapping mapping, long draws, long seed) {
		Sampler sampler = Sampler.of(workload, mapping);
		List<Check> checks = new ArrayList<>();
		SplittableRandom root = new SplittableRandom(seed);
		for (Transaction t : workload.transactions())
			transaction(workload, t, layouts, mapping, sampler, draws, root.split(), checks);
		Mix mix = new Mix(workload.transactions().stream().mapToDouble(Transaction::weight).toArray());
		double total = workload.transactions().stream().mapToDouble(Transaction::weight).sum();
		long[] counts = new long[mix.size()];
		SplittableRandom random = root.split();
		for (long i = 0; i < draws; i++)
			counts[mix.draw(random)]++;
		double[] p = workload.transactions().stream().mapToDouble(x -> x.weight() / total).toArray();
		checks.add(check("*", "template", "mix", ChiSquare.test(p, counts, draws)));
		int m = (int) checks.stream().filter(c -> !c.exact()).count();
		double threshold = ALPHA / Math.max(1, m);
		boolean passed = checks.stream().allMatch(c -> c.exact() ? c.violations() == 0 : c.p() >= threshold);
		return new Report(checks, m, threshold, passed);
	}

	private static Check check(String t, String subject, String test, ChiSquare.Result r) {
		return new Check(t, subject, test, r.bins(), r.df(), r.statistic(), r.p(), 0, false);
	}

	// a let as the verifier needs it
	private record Info(Let let, boolean inRepeat, Set<Integer> sameIteration, LongUnaryOperator baseProjector, int base, double p,
			boolean elseWith) {
	}

	private static void transaction(Workload workload, Transaction t, List<Layout> layouts, RankMapping mapping, Sampler sampler,
			long draws, SplittableRandom random, List<Check> checks) {
		Map<Integer, Info> lets = new HashMap<>();
		List<Statement> statements = new ArrayList<>();
		walk(t.steps(), false, null, lets, statements, mapping, new HashMap<>());
		Marginals marginals = Marginals.of(workload, mapping, t);
		int n = sampler.lets(t.name());
		long[][] counts = new long[n][];
		for (Info info : lets.values())
			counts[info.let().id()] = new long[(int) mapping.size(info.let().domain())];
		// per let: the bin of each value for the repeat test (10 equal-mass bins), the pair counts
		Map<Integer, int[]> pairBins = new HashMap<>();
		Map<Integer, long[][]> pairs = new HashMap<>();
		Map<Integer, double[]> pairMass = new HashMap<>();
		for (Info info : lets.values()) {
			if (!info.inRepeat())
				continue;
			if (info.elseWith()) {
				pairs.put(info.let().id(), new long[2][2]);
			} else if (info.base() < 0 || info.sameIteration().contains(info.base())) {
				double[] p = marginal(marginals, info.let().id(), mapping.size(info.let().domain()));
				int[] bins = equalMassBins(p, 10);
				double[] mass = new double[10];
				for (int v = 0; v < p.length; v++)
					mass[bins[v]] += p[v];
				pairBins.put(info.let().id(), bins);
				pairMass.put(info.let().id(), mass);
				pairs.put(info.let().id(), new long[10][10]);
			}
		}
		long[] violations = new long[n];
		long[] differs = new long[n];
		for (long run = 0; run < draws; run++) {
			int[] seen = new int[n];
			long[] current = new long[n];
			long[] first = new long[n];
			sampler.run(t.name(), random, new Sampler.Visitor() {
				@Override
				public void let(int id, long value) {
					Info info = lets.get(id);
					long base = info.base() < 0 ? 0 : info.baseProjector().applyAsLong(current[info.base()]);
					current[id] = value;
					int k = ++seen[id];
					if (info.base() >= 0 && !info.elseWith() && value == base)
						violations[id]++;
					if (k == 1) {
						counts[id][(int) value - 1]++;
						first[id] = value;
						if (info.elseWith() && value != base)
							differs[id]++;
					}
					long[][] pair = pairs.get(id);
					if (pair != null) {
						if (k == 1 && info.elseWith())
							first[id] = value != base ? 1 : 0;
						if (k == 2) {
							if (info.elseWith())
								pair[(int) first[id]][value != base ? 1 : 0]++;
							else
								pair[pairBins.get(id)[(int) first[id] - 1]][pairBins.get(id)[(int) value - 1]]++;
						}
					}
				}

				@Override
				public void statement(int index, long[] values) {
				}
			});
		}
		List<Info> ordered = lets.values().stream().sorted((a, b) -> Integer.compare(a.let().id(), b.let().id())).toList();
		for (Info info : ordered) {
			int id = info.let().id();
			String subject = "$" + info.let().name();
			double[] p = marginal(marginals, id, mapping.size(info.let().domain()));
			checks.add(check(t.name(), subject, "value", valueTest(p, counts[id], draws)));
			if (info.base() >= 0 && !info.elseWith())
				checks.add(new Check(t.name(), subject, "distinct-from (draws equal to the base)", 0, 0, 0, 0, violations[id], true));
			if (info.elseWith()) {
				if (info.p() == 0 || info.p() == 1)
					checks.add(new Check(t.name(), subject, "else-with p=" + info.p() + " (exact)", 0, 0, 0, 0,
							info.p() == 0 ? differs[id] : draws - differs[id], true));
				else
					checks.add(check(t.name(), subject, "else-with P[x != base] = " + info.p(),
							ChiSquare.test(new double[] { info.p() }, new long[] { differs[id] }, draws)));
			}
			long[][] pair = pairs.get(id);
			if (pair != null) {
				double[] mass = info.elseWith() ? new double[] { 1 - info.p(), info.p() } : pairMass.get(id);
				double[] joint = new double[mass.length * mass.length];
				long[] observed = new long[joint.length];
				for (int i = 0; i < mass.length; i++) {
					for (int j = 0; j < mass.length; j++) {
						joint[i * mass.length + j] = mass[i] * mass[j];
						observed[i * mass.length + j] = pair[i][j];
					}
				}
				checks.add(check(t.name(), subject, "repeat: iterations 1 and 2 independent", ChiSquare.test(joint, observed, draws)));
			}
		}
		// buckets of routed statements, each (let, path, bucket count) once
		Set<List<Object>> done = new LinkedHashSet<>();
		for (Layout layout : layouts) {
			for (Statement s : statements) {
				if (!(Routing.route(s, layout) instanceof Routing.Routed routed))
					continue;
				VarRef ref = routed.value();
				Info info = lets.get(ref.let());
				int buckets = routed.keyTable().buckets();
				if (!done.add(List.of(ref.let(), ref.path(), buckets)))
					continue;
				KeyBuckets kb = mapping.buckets(new KeyPath(info.let().domain(), ref.path()), CitusHash.INSTANCE, buckets);
				double[] p = marginal(marginals, ref.let(), mapping.size(info.let().domain()));
				double[] mass = new double[buckets];
				long[] observed = new long[buckets];
				long[] c = counts[ref.let()];
				for (int v = 1; v <= p.length; v++) {
					int b = kb.bucket(v);
					mass[b] += p[v - 1];
					observed[b] += c[v - 1];
				}
				String path = ref.path().isEmpty() ? "" : "." + String.join(".", ref.path());
				checks.add(check(t.name(), "$" + ref.name() + path, "buckets (" + buckets + ", " + routed.keyTable().table() + ")",
						ChiSquare.test(mass, observed, draws)));
			}
		}
	}

	private static double[] marginal(Marginals marginals, int let, long size) {
		double[] p = new double[(int) size];
		for (int v = 1; v <= size; v++)
			p[v - 1] = marginals.probability(let, v);
		return p;
	}

	// the values in probability order (descending; ties by value)
	private static Integer[] order(double[] p) {
		Integer[] order = new Integer[p.length];
		for (int i = 0; i < p.length; i++)
			order[i] = i;
		Arrays.sort(order, (a, b) -> p[a] != p[b] ? Double.compare(p[b], p[a]) : Integer.compare(a, b));
		return order;
	}

	static ChiSquare.Result valueTest(double[] p, long[] counts, long n) {
		Integer[] order = order(p);
		List<Double> probabilities = new ArrayList<>();
		List<Long> observed = new ArrayList<>();
		for (int i = 0; i < Math.min(100, order.length); i++) {
			if (p[order[i]] * n < ChiSquare.MIN_EXPECTED)
				break;
			probabilities.add(p[order[i]]);
			observed.add(counts[order[i]]);
		}
		if (probabilities.isEmpty()) {
			int[] bins = equalMassBins(p, (int) Math.min(100, p.length));
			int k = Arrays.stream(bins).max().orElse(0) + 1;
			double[] mass = new double[k];
			long[] obs = new long[k];
			for (int v = 0; v < p.length; v++) {
				mass[bins[v]] += p[v];
				obs[bins[v]] += counts[v];
			}
			return ChiSquare.test(mass, obs, n);
		}
		// the rest of the values form ChiSquare's remainder bin
		return ChiSquare.test(probabilities.stream().mapToDouble(Double::doubleValue).toArray(),
				observed.stream().mapToLong(Long::longValue).toArray(), n);
	}

	// each value's bin among k bins of about equal mass, cut along the values in probability order
	static int[] equalMassBins(double[] p, int k) {
		Integer[] order = order(p);
		int[] bins = new int[p.length];
		double total = 0;
		for (double x : p)
			total += x;
		double cumulative = 0;
		for (Integer v : order) {
			int bin = (int) Math.min(k - 1, Math.floor(cumulative / total * k));
			bins[v] = bin;
			cumulative += p[v];
		}
		// renumber the bins actually used 0..k'-1
		int[] map = new int[k];
		Arrays.fill(map, -1);
		int next = 0;
		for (Integer v : order) {
			if (map[bins[v]] < 0)
				map[bins[v]] = next++;
		}
		for (int v = 0; v < bins.length; v++)
			bins[v] = map[bins[v]];
		return bins;
	}

	private static void walk(List<Step> steps, boolean inRepeat, Set<Integer> iteration, Map<Integer, Info> lets,
			List<Statement> statements, RankMapping mapping, Map<Integer, String> domains) {
		for (Step step : steps) {
			if (step instanceof Repeat repeat) {
				Set<Integer> inner = new LinkedHashSet<>();
				walk(repeat.steps(), inRepeat || repeat.count() >= 2, inner, lets, statements, mapping, domains);
			} else if (step instanceof Statement s) {
				statements.add(s);
			} else {
				Let let = (Let) step;
				domains.put(let.id(), let.domain());
				if (iteration != null)
					iteration.add(let.id());
				VarRef base = let instanceof Draw d ? d.distinctFrom() : ((ElseWith) let).base();
				LongUnaryOperator projector = base == null || base.path().isEmpty() ? LongUnaryOperator.identity()
						: mapping.projector(new KeyPath(domains.get(base.let()), base.path()));
				lets.put(let.id(), new Info(let, inRepeat, iteration == null ? Set.of() : iteration, projector,
						base == null ? -1 : base.let(), let instanceof ElseWith e ? e.p() : 0, let instanceof ElseWith));
			}
		}
	}

	static String format(Report report) {
		StringBuilder out = new StringBuilder();
		out.append(String.format("%-14s %-22s %-48s %5s %5s %14s %12s  %s%n", "transaction", "variable", "test", "bins", "df",
				"statistic", "p", "result"));
		for (Check c : report.checks()) {
			if (c.exact())
				out.append(String.format("%-14s %-22s %-48s %5s %5s %14s %12s  %s%n", c.transaction(), c.subject(), c.test(), "-", "-",
						c.violations() + " found", "-", c.violations() == 0 ? "ok" : "FAIL"));
			else
				out.append(String.format("%-14s %-22s %-48s %5d %5d %14.3f %12.4g  %s%n", c.transaction(), c.subject(), c.test(),
						c.bins(), c.df(), c.statistic(), c.p(), c.p() >= report.threshold() ? "ok" : "FAIL"));
		}
		out.append(String.format("%d chi-square tests, Bonferroni: each fails below %.3g (family alpha %.3g); %d exact checks. %s%n",
				report.tests(), report.threshold(), ALPHA, report.checks().size() - report.tests(),
				report.passed() ? "PASSED" : "FAILED"));
		return out.toString();
	}
}
