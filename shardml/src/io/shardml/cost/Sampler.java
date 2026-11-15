package io.shardml.cost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongUnaryOperator;
import java.util.random.RandomGenerator;

import io.shardml.cost.Workload.Dist;
import io.shardml.cost.Workload.Draw;
import io.shardml.cost.Workload.ElseWith;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Let;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.Transaction;
import io.shardml.cost.Workload.VarRef;

/* Draws a transaction's variable bindings from the model's own distributions, for the Phase 4
 * driver (Plan-phase4 2.2). Built on the code the model evaluates, not a second implementation:
 *  - the pmf of a draw is Marginals.draw (RankMapping.weights -> LargestRemainder.zipfWeights,
 *    StrictMath.pow), placed on values by RankMapping.value, so the rank offset applies;
 *  - a value is drawn by inverse CDF over ranks: one uniform u in [0, 1), the smallest rank r with
 *    u < cdf[r] (binary search, O(log |D|)), then RankMapping.value(D, r). cdf[r] - cdf[r - 1] is the
 *    pmf of rank r up to floating-point rounding of the prefix sums; the last entry is exactly 1;
 *  - a projection `$a.customer` is RankMapping.projector, as in the model and in route;
 *  - `distinct from $y`: draws until the value differs from y's (projected) value: the pmf
 *    renormalised over D minus that value, which is what Marginals assumes;
 *  - `$y, else with p:`: one Bernoulli(p) (u < p), then y's value or the excluding draw above;
 *  - `repeat k`: k iterations; the lets inside are drawn again in each.
 * The draws depend only on the workload, the rank offset and the random generator: never on a
 * layout. One uniform per plain draw, one Bernoulli plus the draws of the excluding loop for `else
 * with`. Every table is built in `of` and read-only after, so one Sampler serves many threads, each
 * with its own generator. */
public final class Sampler {

	private final RankMapping mapping;
	private final Map<List<Object>, Table> tables = new java.util.concurrent.ConcurrentHashMap<>();
	private final Map<String, Program> programs = new HashMap<>();

	/* A draw table of one (domain, distribution): cdf over ranks 1..n, and rank -> value. */
	static final class Table {
		final double[] cdf;
		final long[] values;

		Table(double[] cdf, long[] values) {
			this.cdf = cdf;
			this.values = values;
		}

		long draw(RandomGenerator random) {
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
			return values[lo];
		}
	}

	// a let compiled: its table, and for distinct-from / else-with its base (let id and projection)
	private record Compiled(int id, Table table, int base, LongUnaryOperator projector, double p, boolean elseWith) {
	}

	private record Program(Transaction transaction, List<Object> steps, int lets) {
	}

	// one iteration block: count times its steps
	private record Loop(int count, List<Object> steps) {
	}

	// a statement visited, by its index (StatementSql's order)
	private record Visit(int index) {
	}

	/* Receives the draws of one transaction run, in order. values: every let's current value, indexed
	 * by let id (a let not drawn yet holds 0); the array is reused, copy what is kept. */
	public interface Visitor {
		default void let(int id, long value) {
		}

		void statement(int index, long[] values);
	}

	private Sampler(Workload workload, RankMapping mapping) {
		this.mapping = mapping;
		for (Transaction t : workload.transactions()) {
			int[] statements = { 0 };
			int[] lets = { 0 };
			Map<Integer, String> domains = new HashMap<>();
			List<Object> steps = compile(t.steps(), statements, lets, domains);
			programs.put(t.name(), new Program(t, steps, lets[0]));
		}
	}

	public static Sampler of(Workload workload, RankMapping mapping) {
		return new Sampler(workload, mapping);
	}

	public RankMapping mapping() {
		return mapping;
	}

	// the number of lets of a transaction (ids 0..n-1): the length of Visitor's values array
	public int lets(String transaction) {
		return program(transaction).lets();
	}

	/* One run of the transaction: every let drawn in order (again in each repeat iteration), every
	 * statement visited with the values at that point. */
	public void run(String transaction, RandomGenerator random, Visitor visitor) {
		Program program = program(transaction);
		long[] values = new long[program.lets()];
		run(program.steps(), values, random, visitor);
	}

	// a value of the domain drawn from dist (Marginals.draw's pmf), the rank offset applied
	public long draw(String domain, Dist dist, RandomGenerator random) {
		return table(domain, dist).draw(random);
	}

	// the draw table's cumulative probabilities of ranks 1..n (cdf[r - 1] for rank r), for a check
	// against the pmf
	public double[] cdf(String domain, Dist dist) {
		return table(domain, dist).cdf.clone();
	}

	private void run(List<Object> steps, long[] values, RandomGenerator random, Visitor visitor) {
		for (Object step : steps) {
			if (step instanceof Compiled let) {
				long value = draw(let, values, random);
				values[let.id()] = value;
				visitor.let(let.id(), value);
			} else if (step instanceof Loop loop) {
				for (int i = 0; i < loop.count(); i++)
					run(loop.steps(), values, random, visitor);
			} else {
				visitor.statement(((Visit) step).index(), values);
			}
		}
	}

	private static long draw(Compiled let, long[] values, RandomGenerator random) {
		if (let.base() < 0)
			return let.table().draw(random);
		long base = let.projector().applyAsLong(values[let.base()]);
		if (let.elseWith() && !(random.nextDouble() < let.p()))
			return base;
		long value;
		do
			value = let.table().draw(random);
		while (value == base);
		return value;
	}

	private List<Object> compile(List<Step> steps, int[] statements, int[] lets, Map<Integer, String> domains) {
		List<Object> out = new ArrayList<>();
		for (Step step : steps) {
			if (step instanceof Let let) {
				domains.put(let.id(), let.domain());
				lets[0] = Math.max(lets[0], let.id() + 1);
				out.add(compile(let, domains));
			} else if (step instanceof Repeat repeat) {
				out.add(new Loop(repeat.count(), compile(repeat.steps(), statements, lets, domains)));
			} else {
				out.add(new Visit(statements[0]++));
			}
		}
		return List.copyOf(out);
	}

	private Compiled compile(Let let, Map<Integer, String> domains) {
		if (let instanceof Draw draw) {
			Table table = table(draw.domain(), draw.dist());
			if (draw.distinctFrom() == null)
				return new Compiled(draw.id(), table, -1, null, 0, false);
			checkExcludable(table, draw.domain(), draw.name());
			VarRef from = draw.distinctFrom();
			return new Compiled(draw.id(), table, from.let(), projector(from, domains), 0, false);
		}
		ElseWith with = (ElseWith) let;
		Table table = table(with.domain(), with.dist());
		checkExcludable(table, with.domain(), with.name());
		return new Compiled(with.id(), table, with.base().let(), projector(with.base(), domains), with.p(), true);
	}

	private LongUnaryOperator projector(VarRef ref, Map<Integer, String> domains) {
		return ref.path().isEmpty() ? LongUnaryOperator.identity() : mapping.projector(new KeyPath(domains.get(ref.let()), ref.path()));
	}

	// an excluding draw needs at least two values with mass, or it never ends (the validator's SHML_051
	// rejects a one-value domain; this guards records built without it)
	private static void checkExcludable(Table table, String domain, String let) {
		int positive = 0;
		double previous = 0;
		for (double c : table.cdf) {
			if (c > previous)
				positive++;
			previous = c;
		}
		if (positive < 2)
			throw new IllegalArgumentException("let " + let + " excludes a value of domain '" + domain
					+ "', which has fewer than two values with positive probability.");
	}

	private Table table(String domain, Dist dist) {
		return tables.computeIfAbsent(List.of(domain, dist), k -> {
			double[] pmf = Marginals.draw(domain, dist, mapping);
			int n = pmf.length;
			double[] cdf = new double[n];
			long[] values = new long[n];
			double sum = 0;
			for (int r = 1; r <= n; r++) {
				long value = mapping.value(domain, r);
				values[r - 1] = value;
				sum += pmf[(int) value - 1];
				cdf[r - 1] = sum;
			}
			cdf[n - 1] = 1.0;
			return new Table(cdf, values);
		});
	}

	private Program program(String transaction) {
		Program p = programs.get(transaction);
		if (p == null)
			throw new IllegalArgumentException("No transaction '" + transaction + "'.");
		return p;
	}
}
