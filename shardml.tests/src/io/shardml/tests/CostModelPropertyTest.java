package io.shardml.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.CitusHash;
import io.shardml.cost.CostModel;
import io.shardml.cost.CostReport;
import io.shardml.cost.HashTable;
import io.shardml.cost.KeyHasher;
import io.shardml.cost.Layout;
import io.shardml.cost.RankMapping;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.placement.Node;
import io.shardml.placement.ShardSlot;

// M1 and M2 by factorization (CostModel) against full joint enumeration (here, written independently:
// its own distributions, routing and node sets), on small random workloads with a fixed seed: domains of
// 3-6 values, up to 3 variables in all three let forms, a repeat of 1-3, routed, unrouted and reference
// statements, random placements over 2-3 nodes, projections through a child domain. Plan-phase3 4.3a.
public class CostModelPropertyTest {

	private static final long SEED = 20261115L;
	private static final int CASES = 240;
	private static final KeyHasher HASH = CitusHash.INSTANCE;

	private static final String SCHEMA = """
			CREATE TABLE t1 (k BIGINT PRIMARY KEY)
			CREATE TABLE t2 (k BIGINT PRIMARY KEY)
			CREATE TABLE t3 (pa BIGINT, k BIGINT, PRIMARY KEY (pa, k))
			CREATE TABLE r1 (k BIGINT PRIMARY KEY)
			""";

	// a generated let: name, domain, form (draw / distinct / else), base let index, base path, p, repeat
	private record Var(String name, String domain, String dist, String form, int base, String basePath, double p,
			int repeat) {
	}

	// a generated statement: kind, table, column, the let it binds (-1: unbound `?`), projection to a
	private record Stmt(boolean write, String table, String column, int var, boolean toA) {
	}

	private record Case(String layout, String workload, List<Var> vars, List<Stmt> statements, Map<String, Integer> sizes,
			int nodes, String t3key, int[] buckets) {
	}

	@Test
	public void testFactorizedEqualsEnumeration() {
		Random random = new Random(SEED);
		double maxDiff = 0;
		int checked = 0;
		Set<String> forms = new TreeSet<>();
		Set<Integer> nodeCounts = new TreeSet<>();
		int distinctWithReferenceWrite = 0;
		int strictTwoPhase = 0;
		int strictMulti = 0;
		for (int i = 0; i < CASES; i++) {
			Case c = generate(random);
			CostFixtures f = new CostFixtures();
			f.load("s.msql", SCHEMA);
			Layout layout = f.layout("l.shardml", c.layout());
			layout = layout.withPlacement(randomPlacement(layout, random));
			Workload workload = f.workload("w.workload", c.workload());
			CostReport report = new CostModel(HASH).evaluate(layout, workload, 0);
			double[] brute = enumerate(c, layout, RankMapping.of(workload, 0));
			double d = Math.max(Math.abs(brute[0] - report.distributed().twoPhase()),
					Math.abs(brute[1] - report.distributed().multiNode()));
			d = Math.max(d, Math.max(Math.abs(brute[2] - report.fanOut().mean()), Math.abs(brute[3] - report.fanOut().multiShardShare())));
			Assertions.assertEquals(brute[4], report.fanOut().max(), "case " + i + "\n" + c.workload());
			Assertions.assertTrue(d < 1e-9, "case " + i + ": difference " + d + "\n" + c.layout() + "\n" + c.workload()
					+ "\nbrute " + java.util.Arrays.toString(brute) + "\nreport " + report.distributed() + " " + report.fanOut());
			maxDiff = Math.max(maxDiff, d);
			checked++;
			if (brute[0] > 1e-9 && brute[0] < 1 - 1e-9)
				strictTwoPhase++;
			if (brute[1] > 1e-9 && brute[1] < 1 - 1e-9)
				strictMulti++;

			for (Var v : c.vars())
				forms.add(v.form() + (v.repeat() > 1 ? " in repeat" : ""));
			nodeCounts.add(c.nodes());
			for (Stmt st : c.statements()) {
				if (st.write() && st.table().equals("r1") && st.var() >= 0 && c.vars().get(st.var()).form().equals("distinct")) {
					distinctWithReferenceWrite++;
					break;
				}
			}
		}
		Assertions.assertEquals(CASES, checked);
		// not vacuous: most cases have a probability strictly between 0 and 1
		Assertions.assertTrue(strictMulti >= CASES / 3, "only " + strictMulti + " cases with 0 < multi-node < 1");
		Assertions.assertTrue(strictTwoPhase >= CASES / 5, "only " + strictTwoPhase + " cases with 0 < 2PC < 1");
		Assertions.assertTrue(forms.containsAll(List.of("draw", "distinct", "else", "draw in repeat")), forms.toString());
		Assertions.assertEquals(Set.of(1, 2, 3, 4), nodeCounts);
		Assertions.assertTrue(distinctWithReferenceWrite >= 10, "only " + distinctWithReferenceWrite
				+ " cases with a distinct-from draw and a reference write");
		System.out.println("CostModelPropertyTest: " + checked + " cases, max difference " + maxDiff + ", strictly between 0 and 1: "
				+ strictTwoPhase + " (2PC), " + strictMulti + " (multi-node); node counts " + nodeCounts
				+ "; distinct from with a reference write: " + distinctWithReferenceWrite + "; forms " + forms);
	}

	// --- generation ---

	private static Case generate(Random random) {
		Map<String, Integer> sizes = new LinkedHashMap<>();
		sizes.put("a", 3 + random.nextInt(4));
		sizes.put("b", 3 + random.nextInt(4));
		sizes.put("ch", 3 + random.nextInt(4));
		String chDist = random.nextBoolean() ? "uniform" : "zipf(1)";
		// 1 to 4 nodes: 1 in 10 cases one node, 2 and 3 in 35% each, 4 in 20%
		int nodes = new int[] { 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 1 }[random.nextInt(20)];
		String t3key = random.nextBoolean() ? "k" : "pa";
		int[] buckets = { 2 + random.nextInt(3), 2 + random.nextInt(3), 2 + random.nextInt(3) };
		StringBuilder layout = new StringBuilder("import \"s.msql\"\ndatabase p {\n    type: postgres\n");
		for (int n = 1; n <= nodes; n++)
			layout.append("    node n").append(n).append(" { host: \"h").append(n).append("\" port: 5432 }\n");
		layout.append("    shard t1 { strategy: hash key: k buckets: ").append(buckets[0]).append(" }\n");
		layout.append("    shard t2 { strategy: hash key: k buckets: ").append(buckets[1]).append(" }\n");
		layout.append("    shard t3 { strategy: hash key: ").append(t3key).append(" buckets: ").append(buckets[2]).append(" }\n");
		layout.append("    shard r1 { strategy: replicated }\n}\n");

		// mostly 2-3 variables, so node sets can both meet and part
		int count = random.nextInt(5) == 0 ? 1 : 2 + random.nextInt(2);
		List<Var> vars = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			String domain = List.of("a", "b", "ch").get(random.nextInt(3));
			String dist = random.nextBoolean() ? "uniform" : List.of("zipf(1)", "zipf(0.5)", "zipf(1.5)").get(random.nextInt(3));
			int repeat = i == count - 1 && random.nextInt(10) < 4 ? 1 + random.nextInt(3) : 1;
			String form = "draw";
			int base = -1;
			String basePath = "";
			double p = 0;
			if (i > 0 && random.nextInt(3) > 0) {
				// a base: an independent top-level draw of the same domain, or a ch draw projected to a
				List<Integer> candidates = new ArrayList<>();
				for (int j = 0; j < i; j++) {
					Var v = vars.get(j);
					if (v.form().equals("draw") && v.repeat() == 1 && (v.domain().equals(domain) || domain.equals("a") && v.domain().equals("ch")))
						candidates.add(j);
				}
				if (!candidates.isEmpty()) {
					base = candidates.get(random.nextInt(candidates.size()));
					basePath = vars.get(base).domain().equals(domain) ? "" : ".a";
					form = random.nextBoolean() ? "distinct" : "else";
					p = List.of(0.0, 0.3, 0.5, 1.0).get(random.nextInt(4));
				}
			}
			vars.add(new Var("v" + i, domain, dist, form, base, basePath, p, repeat));
		}
		List<Stmt> statements = new ArrayList<>();
		List<List<Stmt>> byVar = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			List<Stmt> mine = new ArrayList<>();
			Var v = vars.get(i);
			int k = 1 + random.nextInt(2);
			for (int s = 0; s < k; s++) {
				boolean write = random.nextBoolean();
				int pick = random.nextInt(10);
				// mostly routed statements (on t3, mostly by its shard key), some unrouted and reference ones
				switch (v.domain()) {
					case "a" -> mine.add(pick < 2 ? new Stmt(write, "r1", "k", i, false)
							: pick < 5 && (t3key.equals("pa") || pick == 2) ? new Stmt(write, "t3", "pa", i, false)
									: new Stmt(write, "t1", "k", i, false));
					case "b" -> mine.add(new Stmt(write, "t2", "k", i, false));
					default -> mine.add(pick < 4 ? new Stmt(write, "t1", "k", i, true)
							: pick < 9 ? (t3key.equals("k") ? new Stmt(write, "t3", "k", i, false) : new Stmt(write, "t3", "pa", i, true))
									: new Stmt(write, "t3", t3key.equals("k") ? "pa" : "k", i, t3key.equals("k")));
				}
			}
			// a distinct-from draw over a, half the time with a reference write too (all nodes, all at once)
			if (v.form().equals("distinct") && v.domain().equals("a") && random.nextBoolean())
				mine.add(new Stmt(true, "r1", "k", i, false));
			byVar.add(mine);
		}
		List<Stmt> unbound = new ArrayList<>();
		if (random.nextInt(6) == 0)
			unbound.add(new Stmt(random.nextBoolean(), List.of("t1", "t2", "r1").get(random.nextInt(3)), "k", -1, false));

		StringBuilder w = new StringBuilder("import \"s.msql\"\nworkload p {\n");
		w.append("    domain a { size: ").append(sizes.get("a")).append(" }\n");
		w.append("    domain b { size: ").append(sizes.get("b")).append(" }\n");
		w.append("    domain ch { size: ").append(sizes.get("ch")).append(" within a ").append(chDist).append(" }\n");
		w.append("    table t1 { rows: per a k: a }\n    table t2 { rows: per b k: b }\n");
		w.append("    table t3 { rows: per ch k: ch pa: ch.a }\n    table r1 { rows: per a k: a }\n");
		w.append("    transaction x weight 1 {\n");
		for (int i = 0; i < count; i++) {
			Var v = vars.get(i);
			String indent = "        ";
			if (v.repeat() > 1) {
				w.append(indent).append("repeat ").append(v.repeat()).append(" {\n");
				indent += "    ";
			}
			w.append(indent).append("let ").append(v.name()).append(" = ");
			if (v.form().equals("else"))
				w.append("$").append(vars.get(v.base()).name()).append(v.basePath()).append(", else with ").append(p(v.p()))
						.append(": ").append(v.domain()).append(' ').append(v.dist()).append('\n');
			else {
				w.append(v.domain()).append(' ').append(v.dist());
				if (v.form().equals("distinct"))
					w.append(" distinct from $").append(vars.get(v.base()).name()).append(v.basePath());
				w.append('\n');
			}
			for (Stmt s : byVar.get(i)) {
				w.append(indent).append(s.write() ? "write " : "read ").append(s.table()).append(" where ").append(s.column())
						.append(" = $").append(v.name()).append(s.toA() ? ".a" : "").append('\n');
				statements.add(s);
			}
			if (v.repeat() > 1)
				w.append("        }\n");
		}
		for (Stmt s : unbound) {
			w.append("        ").append(s.write() ? "write " : "read ").append(s.table()).append(" where ").append(s.column())
					.append(" > ?\n");
			statements.add(s);
		}
		w.append("    }\n}\n");
		// the statements in workload order with the repeat they are in
		return new Case(layout.toString(), w.toString(), vars, statements, sizes, nodes, t3key, buckets);
	}

	private static String p(double p) {
		return p == 0 ? "0" : p == 1 ? "1" : String.valueOf(p);
	}

	private static Map<ShardSlot, Node> randomPlacement(Layout layout, Random random) {
		Map<ShardSlot, Node> map = new LinkedHashMap<>();
		for (ShardSlot slot : layout.placement().assignments().keySet())
			map.put(slot, layout.nodes().get(random.nextInt(layout.nodes().size())));
		return map;
	}

	// --- enumeration ---

	// { 2PC, multi-node, mean fan-out, multi-shard share, max fan-out }
	private static double[] enumerate(Case c, Layout layout, RankMapping mapping) {
		// instances: every let once per iteration of its repeat; statements per iteration of their let
		List<int[]> instances = new ArrayList<>(); // { var index, iteration }
		for (int i = 0; i < c.vars().size(); i++) {
			for (int it = 0; it < c.vars().get(i).repeat(); it++)
				instances.add(new int[] { i, it });
		}
		double[] out = new double[5];
		Map<Integer, Integer> instanceOf = new HashMap<>(); // top-level var -> its instance
		for (int k = 0; k < instances.size(); k++) {
			if (c.vars().get(instances.get(k)[0]).repeat() == 1)
				instanceOf.put(instances.get(k)[0], k);
		}
		int[] values = new int[instances.size()];
		double[] acc = new double[2];
		walk(0, 1.0, values, instances, instanceOf, c, layout, mapping, acc);
		out[0] = acc[0];
		out[1] = acc[1];
		// fan-out: deterministic per statement; executions = its let's repeat
		double tasks = 0;
		double executions = 0;
		double multi = 0;
		int max = 0;
		for (Stmt s : c.statements()) {
			int times = s.var() < 0 ? 1 : c.vars().get(s.var()).repeat();
			int f = isRouted(s, c) || s.table().equals("r1") ? 1 : buckets(s.table(), c);
			tasks += times * f;
			executions += times;
			if (f > 1)
				multi += times;
			max = Math.max(max, f);
		}
		out[2] = tasks / executions;
		out[3] = multi / executions;
		out[4] = max;
		return out;
	}

	private static void walk(int k, double probability, int[] values, List<int[]> instances, Map<Integer, Integer> instanceOf,
			Case c, Layout layout, RankMapping mapping, double[] acc) {
		if (probability == 0)
			return;
		if (k == instances.size()) {
			Set<Integer> writeNodes = new TreeSet<>();
			Set<Integer> allNodes = new TreeSet<>();
			for (Stmt s : c.statements()) {
				List<Integer> iterations = new ArrayList<>();
				if (s.var() < 0)
					iterations.add(-1);
				else {
					for (int j = 0; j < instances.size(); j++) {
						if (instances.get(j)[0] == s.var())
							iterations.add(j);
					}
				}
				for (int j : iterations) {
					Set<Integer> nodes = nodeSet(s, j < 0 ? 0 : values[j], c, layout, mapping);
					allNodes.addAll(nodes);
					if (s.write())
						writeNodes.addAll(nodes);
				}
			}
			if (writeNodes.size() > 1)
				acc[0] += probability;
			if (allNodes.size() > 1)
				acc[1] += probability;
			return;
		}
		Var v = c.vars().get(instances.get(k)[0]);
		int size = c.sizes().get(v.domain());
		double[] pmf = pmf(size, v.dist());
		for (int value = 1; value <= size; value++) {
			double p;
			if (v.form().equals("draw"))
				p = pmf[value - 1];
			else {
				int baseValue = values[instanceOf.get(v.base())];
				int u = v.basePath().isEmpty() ? baseValue : (int) mapping.parent("ch", baseValue);
				double excluded = value == u ? 0 : pmf[value - 1] / (1 - pmf[u - 1]);
				p = v.form().equals("distinct") ? excluded : (1 - v.p()) * (value == u ? 1 : 0) + v.p() * excluded;
			}
			values[k] = value;
			walk(k + 1, probability * p, values, instances, instanceOf, c, layout, mapping, acc);
		}
	}

	private static double[] pmf(int n, String dist) {
		double s = dist.equals("uniform") ? 0 : Double.parseDouble(dist.substring(5, dist.length() - 1));
		double[] w = new double[n];
		double sum = 0;
		for (int r = 1; r <= n; r++) {
			w[r - 1] = Math.pow(r, -s);
			sum += w[r - 1];
		}
		for (int r = 0; r < n; r++)
			w[r] /= sum;
		return w;
	}

	private static boolean isRouted(Stmt s, Case c) {
		if (s.var() < 0 || s.table().equals("r1"))
			return false;
		return !s.table().equals("t3") || s.column().equals(c.t3key());
	}

	private static int buckets(String table, Case c) {
		return switch (table) {
			case "t1" -> c.buckets()[0];
			case "t2" -> c.buckets()[1];
			default -> c.buckets()[2];
		};
	}

	private static Set<Integer> nodeSet(Stmt s, int value, Case c, Layout layout, RankMapping mapping) {
		Set<Integer> nodes = new TreeSet<>();
		if (s.table().equals("r1")) {
			if (s.write()) {
				for (int n = 0; n < layout.nodes().size(); n++)
					nodes.add(n);
			}
			return nodes;
		}
		HashTable table = (HashTable) layout.table(s.table()).orElseThrow();
		if (!isRouted(s, c)) {
			for (int b = 0; b < table.buckets(); b++)
				nodes.add(layout.nodes().indexOf(layout.nodeOf(table, b)));
			return nodes;
		}
		long key = s.toA() ? mapping.project(new KeyPath("ch", List.of("a")), value) : value;
		nodes.add(layout.nodes().indexOf(layout.nodeOf(table, HASH.bucket(key, table.buckets()))));
		return nodes;
	}
}
