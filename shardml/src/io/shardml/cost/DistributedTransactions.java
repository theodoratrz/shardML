package io.shardml.cost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.shardml.cost.Routing.AllShards;
import io.shardml.cost.Routing.ReferenceOnly;
import io.shardml.cost.Routing.Route;
import io.shardml.cost.Routing.Routed;
import io.shardml.cost.Workload.Draw;
import io.shardml.cost.Workload.ElseWith;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Kind;
import io.shardml.cost.Workload.Let;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.Transaction;
import io.shardml.cost.Workload.VarRef;
import io.shardml.placement.Node;

/* M1 for one transaction template (Plan-phase3 4.3, 4.3a and its Step 7 refinements): the
 * probability that its write statements, and that all its statements, touch more than one node.
 * Exact, by factorization over node sets, never over key tuples:
 *   z(T) = P[every statement's node set lies within T], for T = empty and T = {n};
 *   P[at most one node] = sum_n z({n}) - (n - 1) z(empty).
 * z is a product over independent parts: constant node sets (unrouted statements, reference writes),
 * independent draws inside a repeat (raised to its multiplicity), and each top-level root with its
 * dependents:
 *   z_x(T) = sum_v p_x(v) 1[S_x(v) within T] prod_y q_y(T | v)^k_y,
 *   q_y(T | v) = (m_y(T) - p_y(u) 1[S_y(u) within T]) / (1 - p_y(u))       distinct from (u = v in y's domain)
 *              = (1 - p) 1[S_y(u) within T] + p (the same exclusion term)   else with p.
 * S_x(v) is per value AND per statement (trap 1): single(v) is the one node all of x's statements
 * map v to, EMPTY without statements, MULTI when two disagree. */
public final class DistributedTransactions {

	static final int EMPTY = -1;
	static final int MULTI = -2;

	public record Result(double twoPhase, double multiNode) {
	}

	private final Layout layout;
	private final RankMapping mapping;
	private final KeyHasher hasher;
	private final List<Node> nodes;

	public DistributedTransactions(Layout layout, RankMapping mapping, KeyHasher hasher) {
		this.layout = layout;
		this.mapping = mapping;
		this.hasher = hasher;
		this.nodes = layout.nodes();
	}

	public Result of(Transaction transaction) {
		Structure structure = Structure.of(transaction, layout);
		double all = 1 - atMostOneNode(structure, false);
		double writes = 1 - atMostOneNode(structure, true);
		return new Result(clean(writes), clean(all));
	}

	// a probability, with the rounding of long sums (about 1e-11 over 10^6 values) kept inside [0, 1]: an
	// exact 0 can come out as -1e-12, which would print as -0.000000
	private static double clean(double p) {
		return Math.abs(p) < 1e-15 ? 0 : Math.min(1, Math.max(0, p));
	}

	// z(T) of the transaction's write statements (writesOnly) or all of them: z[0] = P[every node set is
	// empty], z[i + 1] = P[every node set lies within {node i}]. The factorized route, exposed for tests
	public double[] within(Transaction transaction, boolean writesOnly) {
		return z(Structure.of(transaction, layout), writesOnly);
	}

	/* Phase 4 (Plan-phase4 amendment 1, 4): per node n, P[the transaction commits with 2PC AND n is one of its
	 * participants], a participant being a node one of its writes touches, and 2PC that the writes touch more
	 * than one node (as twoPhase). With W the writes' node set and z(T) = P[W within T]:
	 *   P[|W| >= 2 and n in W] = P[n in W] - P[W = {n}] = (1 - z(N \ {n})) - (z({n}) - z(empty)).
	 * Additive: z over arbitrary node sets (zWithin) beside z, which of() keeps using unchanged. Summed over the
	 * nodes it is E[|W|; 2PC], at least 2 * twoPhase. */
	public double[] participation(Transaction transaction) {
		int n = nodes.size();
		long all = n == 64 ? -1L : (1L << n) - 1;
		long[] masks = new long[2 * n + 1]; // 0: empty; 1 + i: {i}; 1 + n + i: N \ {i}
		for (int i = 0; i < n; i++) {
			masks[1 + i] = 1L << i;
			masks[1 + n + i] = all & ~(1L << i);
		}
		double[] z = zWithin(Structure.of(transaction, layout), true, masks);
		double[] out = new double[n];
		for (int i = 0; i < n; i++)
			out[i] = clean((1 - z[1 + n + i]) - (z[1 + i] - z[0]));
		return out;
	}

	// z(T) for each node set T given as a bit mask over layout.nodes(): the factorization of z, with fits()
	// generalized from {} and {node i} to any set. Exposed for tests (at {} and the singletons it equals within())
	public double[] within(Transaction transaction, boolean writesOnly, long[] masks) {
		return zWithin(Structure.of(transaction, layout), writesOnly, masks);
	}

	private double[] zWithin(Structure s, boolean writesOnly, long[] masks) {
		if (nodes.size() > 64)
			throw new IllegalArgumentException("node sets as bit masks: at most 64 nodes, the layout has " + nodes.size());
		double[] z = new double[masks.length];
		java.util.Arrays.fill(z, 1);
		for (Constant c : s.constants) {
			if (writesOnly && !c.write)
				continue;
			for (int t = 0; t < masks.length; t++) {
				for (int node : c.nodes) {
					if ((masks[t] >>> node & 1) == 0) {
						z[t] = 0;
						break;
					}
				}
			}
		}
		for (Cluster r : s.repeatRoots) {
			double[] m = mWithin(sets(r.root, s, writesOnly), Marginals.draw(r.root.domain(), drawDist(r.root), mapping), masks);
			for (int t = 0; t < masks.length; t++)
				z[t] *= power(m[t], r.times);
		}
		for (Cluster c : s.roots) {
			double[] zc = rootWithin(c, s, writesOnly, masks);
			for (int t = 0; t < masks.length; t++)
				z[t] *= zc[t];
		}
		return z;
	}

	private double[] rootWithin(Cluster c, Structure s, boolean writesOnly, long[] masks) {
		Let x = c.root;
		double[] px = Marginals.draw(x.domain(), drawDist(x), mapping);
		long[] sx = sets(x, s, writesOnly);
		List<Dependent> deps = new ArrayList<>();
		List<long[]> depSets = new ArrayList<>();
		List<double[]> depM = new ArrayList<>();
		for (Map.Entry<Let, Long> e : c.dependents.entrySet()) {
			Dependent d = dependent(e.getKey(), e.getValue(), s, writesOnly);
			long[] set = sets(e.getKey(), s, writesOnly);
			deps.add(d);
			depSets.add(set);
			depM.add(mWithin(set, d.p, masks));
		}
		double[] z = new double[masks.length];
		for (int v = 1; v <= px.length; v++) {
			long set = sx[v - 1];
			for (int t = 0; t < masks.length; t++) {
				if (!fitsMask(set, masks[t]))
					continue;
				double term = px[v - 1];
				for (int j = 0; j < deps.size(); j++) {
					Dependent d = deps.get(j);
					int u = d.baseValue[v - 1];
					double pu = d.p[u - 1];
					double ind = fitsMask(depSets.get(j)[u - 1], masks[t]) ? 1 : 0;
					double excluded = (depM.get(j)[t] - pu * ind) / (1 - pu);
					double q = d.elseWith ? (1 - d.remote) * ind + d.remote * excluded : excluded;
					term *= power(q, d.times);
				}
				z[t] += term;
			}
		}
		return z;
	}

	private static double[] mWithin(long[] set, double[] p, long[] masks) {
		double[] m = new double[masks.length];
		for (int u = 1; u <= p.length; u++) {
			for (int t = 0; t < masks.length; t++) {
				if (fitsMask(set[u - 1], masks[t]))
					m[t] += p[u - 1];
			}
		}
		return m;
	}

	// a value's node set lies within the set
	private static boolean fitsMask(long set, long mask) {
		return (set & ~mask) == 0;
	}

	// sets(v): the node set, as a bit mask, of the scope's statements bound to the let, for each value: what
	// singles() keeps only as EMPTY, one node or MULTI, which is enough for {} and the singletons but not for
	// larger sets (two statements on nodes a and b lie within N \ {n} when n is neither)
	private long[] sets(Let let, Structure s, boolean writesOnly) {
		int size = (int) mapping.size(let.domain());
		long[] set = new long[size];
		for (Bound b : s.bound.getOrDefault(let.id(), List.of())) {
			if (writesOnly && !b.write)
				continue;
			KeyBuckets kb = mapping.buckets(new KeyPath(let.domain(), b.routed.value().path()), hasher, b.routed.keyTable().buckets());
			int[] nodeOfBucket = nodeIndexOfBuckets(layout, b.routed.keyTable());
			for (int v = 1; v <= size; v++)
				set[v - 1] |= 1L << nodeOfBucket[kb.bucket(v)];
		}
		return set;
	}

	// P[the node sets of the scope's statements together hold at most one node]
	private double atMostOneNode(Structure s, boolean writesOnly) {
		int n = nodes.size();
		double[] z = z(s, writesOnly);
		double sum = 0;
		for (int i = 0; i < n; i++)
			sum += z[i + 1];
		return sum - (n - 1) * z[0];
	}

	private double[] z(Structure s, boolean writesOnly) {
		int n = nodes.size();
		double[] z = new double[n + 1]; // z[0]: T = empty; z[i + 1]: T = {node i}
		java.util.Arrays.fill(z, 1);
		// constants
		for (Constant c : s.constants) {
			if (writesOnly && !c.write)
				continue;
			z[0] = 0; // a constant set is never empty
			for (int i = 0; i < n; i++) {
				if (!(c.nodes.size() == 1 && c.nodes.get(0) == i))
					z[i + 1] = 0;
			}
		}
		// independent draws inside repeats
		for (Cluster r : s.repeatRoots) {
			double[] m = m(r.root, singles(r.root, s, writesOnly));
			for (int t = 0; t <= n; t++)
				z[t] *= power(m[t], r.times);
		}
		// top-level roots with their dependents
		for (Cluster c : s.roots) {
			double[] zc = root(c, s, writesOnly);
			for (int t = 0; t <= n; t++)
				z[t] *= zc[t];
		}
		return z;
	}

	private double[] root(Cluster c, Structure s, boolean writesOnly) {
		int n = nodes.size();
		Let x = c.root;
		double[] px = Marginals.draw(x.domain(), drawDist(x), mapping);
		int[] sx = singles(x, s, writesOnly);
		List<Dependent> deps = new ArrayList<>();
		for (Map.Entry<Let, Long> e : c.dependents.entrySet())
			deps.add(dependent(e.getKey(), e.getValue(), s, writesOnly));
		double[] z = new double[n + 1];
		for (int v = 1; v <= px.length; v++) {
			int single = sx[v - 1];
			if (single == MULTI)
				continue;
			for (int t = 0; t <= n; t++) {
				if (!fits(single, t))
					continue;
				double term = px[v - 1];
				for (Dependent d : deps) {
					int u = d.baseValue[v - 1];
					double pu = d.p[u - 1];
					double ind = fits(d.single[u - 1], t) ? 1 : 0;
					double excluded = (d.m[t] - pu * ind) / (1 - pu);
					double q = d.elseWith ? (1 - d.remote) * ind + d.remote * excluded : excluded;
					term *= power(q, d.times);
				}
				z[t] += term;
			}
		}
		return z;
	}

	// the index in layout.nodes() of each bucket's node: built once per table, so loops over values do
	// array lookups instead of a placement map lookup per value
	static int[] nodeIndexOfBuckets(Layout layout, HashTable table) {
		int[] index = new int[table.buckets()];
		for (int b = 0; b < index.length; b++)
			index[b] = layout.nodes().indexOf(layout.nodeOf(table, b));
		return index;
	}

	// x^k; fdlibm's pow(x, 1) is x exactly, so the common k = 1 skips the call without changing a bit
	private static double power(double x, long k) {
		return k == 1 ? x : StrictMath.pow(x, k);
	}

	// t = 0: T empty; t = i + 1: T = {node i}
	private static boolean fits(int single, int t) {
		return single == EMPTY || (t > 0 && single == t - 1);
	}

	private record Dependent(double[] p, int[] single, double[] m, int[] baseValue, boolean elseWith, double remote,
			long times) {
	}

	private Dependent dependent(Let y, long times, Structure s, boolean writesOnly) {
		VarRef base = y instanceof ElseWith w ? w.base() : ((Draw) y).distinctFrom();
		double[] p = Marginals.draw(y.domain(), drawDist(y), mapping);
		int[] single = singles(y, s, writesOnly);
		Let root = s.lets.get(base.let());
		int[] baseValue = new int[(int) mapping.size(root.domain())];
		java.util.function.LongUnaryOperator projector = mapping.projector(new KeyPath(root.domain(), base.path()));
		for (int v = 1; v <= baseValue.length; v++)
			baseValue[v - 1] = (int) projector.applyAsLong(v);
		return new Dependent(p, single, m(single, p), baseValue, y instanceof ElseWith,
				y instanceof ElseWith w ? w.p() : 0, times);
	}

	// m(T) = sum_u p(u) 1[S(u) within T], p the let's own draw
	private double[] m(Let let, int[] single) {
		return m(single, Marginals.draw(let.domain(), drawDist(let), mapping));
	}

	private double[] m(int[] single, double[] p) {
		double[] m = new double[nodes.size() + 1];
		for (int u = 1; u <= p.length; u++) {
			for (int t = 0; t < m.length; t++) {
				if (fits(single[u - 1], t))
					m[t] += p[u - 1];
			}
		}
		return m;
	}

	private static Workload.Dist drawDist(Let let) {
		return let instanceof ElseWith w ? w.dist() : ((Draw) let).dist();
	}

	// single(v) over the scope's statements bound to the let, value by value and statement by statement
	private int[] singles(Let let, Structure s, boolean writesOnly) {
		int size = (int) mapping.size(let.domain());
		int[] single = new int[size];
		java.util.Arrays.fill(single, EMPTY);
		for (Bound b : s.bound.getOrDefault(let.id(), List.of())) {
			if (writesOnly && !b.write)
				continue;
			KeyBuckets kb = mapping.buckets(new KeyPath(let.domain(), b.routed.value().path()), hasher, b.routed.keyTable().buckets());
			int[] nodeOfBucket = nodeIndexOfBuckets(layout, b.routed.keyTable());
			for (int v = 1; v <= size; v++) {
				int node = nodeOfBucket[kb.bucket(v)];
				int current = single[v - 1];
				single[v - 1] = current == EMPTY ? node : current == node ? node : MULTI;
			}
		}
		return single;
	}

	// the exact steps of(transaction) takes, for the budget pre-pass: per scope (writes, all), |D| per
	// (let, bound statement) for single(), |D_x| * (n + 1) * (1 + dependents) per root, |D_y| * (n + 1)
	// per dependent plus |D_root| for its base values, |D_r| * (n + 1) per repeat root
	static long steps(Transaction transaction, Layout layout, Workload workload) {
		Structure s = Structure.of(transaction, layout);
		long n1 = layout.nodes().size() + 1;
		long steps = 0;
		for (boolean writesOnly : new boolean[] { true, false }) {
			for (Map.Entry<Integer, List<Bound>> e : s.bound.entrySet()) {
				long size = workload.domain(s.lets.get(e.getKey()).domain()).size();
				steps += size * e.getValue().stream().filter(b -> !writesOnly || b.write).count();
			}
			for (Cluster c : s.repeatRoots)
				steps += workload.domain(c.root.domain()).size() * n1;
			for (Cluster c : s.roots) {
				long rootSize = workload.domain(c.root.domain()).size();
				steps += rootSize * n1 * (1 + c.dependents.size());
				for (Let y : c.dependents.keySet())
					steps += workload.domain(y.domain()).size() * n1 + rootSize;
			}
		}
		return steps;
	}

	// --- the transaction's structure ---

	private record Constant(List<Integer> nodes, boolean write) {
	}

	private record Bound(Routed routed, boolean write) {
	}

	// a root and its dependents with their repeat multiplicities; for a repeat root, times is its own
	private static final class Cluster {
		final Let root;
		final long times;
		final Map<Let, Long> dependents = new LinkedHashMap<>();

		Cluster(Let root, long times) {
			this.root = root;
			this.times = times;
		}
	}

	static final class Structure {
		final Map<Integer, Let> lets = new HashMap<>();
		final List<Cluster> roots = new ArrayList<>();
		final List<Cluster> repeatRoots = new ArrayList<>();
		final List<Constant> constants = new ArrayList<>();
		final Map<Integer, List<Bound>> bound = new HashMap<>();
		private final Map<Integer, Cluster> clusterOfRoot = new HashMap<>();

		static Structure of(Transaction transaction, Layout layout) {
			Structure s = new Structure();
			s.walk(transaction.steps(), 1, layout, true);
			return s;
		}

		private void walk(List<Step> steps, long times, Layout layout, boolean topLevel) {
			for (Step step : steps) {
				if (step instanceof Repeat repeat) {
					walk(repeat.steps(), times * repeat.count(), layout, false);
				} else if (step instanceof Let let) {
					lets.put(let.id(), let);
					VarRef base = let instanceof ElseWith w ? w.base() : ((Draw) let).distinctFrom();
					if (base == null) {
						Cluster c = new Cluster(let, times);
						(topLevel ? roots : repeatRoots).add(c);
						if (topLevel)
							clusterOfRoot.put(let.id(), c);
					} else {
						Cluster root = clusterOfRoot.get(base.let());
						if (root == null)
							throw new IllegalStateException("'" + let.name() + "' depends on '" + base.name()
									+ "', which is not an independent draw at the transaction's top level (SHML_052).");
						root.dependents.put(let, times);
					}
				} else {
					Statement statement = (Statement) step;
					boolean write = statement.kind() != Kind.READ;
					Route route = Routing.route(statement, layout);
					if (route instanceof Routed routed)
						bound.computeIfAbsent(routed.value().let(), k -> new ArrayList<>()).add(new Bound(routed, write));
					else if (route instanceof AllShards all)
						constants.add(new Constant(nodesOf(all.anyTable(), layout), write));
					else if (route instanceof ReferenceOnly && write)
						constants.add(new Constant(allNodes(layout), true));
					// a reference read adds no node; a repartition is excluded
				}
			}
		}

		private static List<Integer> nodesOf(HashTable table, Layout layout) {
			java.util.TreeSet<Integer> held = new java.util.TreeSet<>();
			for (int b = 0; b < table.buckets(); b++)
				held.add(layout.nodes().indexOf(layout.nodeOf(table, b)));
			return List.copyOf(held);
		}

		private static List<Integer> allNodes(Layout layout) {
			List<Integer> all = new ArrayList<>();
			for (int i = 0; i < layout.nodes().size(); i++)
				all.add(i);
			return all;
		}
	}
}
