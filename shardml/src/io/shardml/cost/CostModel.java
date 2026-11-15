package io.shardml.cost;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.shardml.cost.Routing.AllShards;
import io.shardml.cost.Routing.ReferenceOnly;
import io.shardml.cost.Routing.Repartition;
import io.shardml.cost.Routing.Route;
import io.shardml.cost.Routing.Routed;
import io.shardml.cost.SkewAndLoad.DataSkew;
import io.shardml.cost.SkewAndLoad.Load;
import io.shardml.cost.SkewAndLoad.NodeRows;
import io.shardml.cost.SkewAndLoad.NodeValue;
import io.shardml.cost.SkewAndLoad.RepartitionJoin;
import io.shardml.cost.SkewAndLoad.TableRows;
import io.shardml.cost.Workload.Draw;
import io.shardml.cost.Workload.ElseWith;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Kind;
import io.shardml.cost.Workload.Let;
import io.shardml.cost.Workload.Mapped;
import io.shardml.cost.Workload.OverRows;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.TableData;
import io.shardml.cost.Workload.Transaction;
import io.shardml.cost.Workload.VarRef;
import io.shardml.placement.Node;

/* The static cost model (Plan-phase3 4.3, 4.3a, 4.4c). Step 6: data skew (M3), load skew and
 * bottleneck load (M4), computed exactly from per-value distributions, never by sampling.
 *  - rows(n): the rows of every hash table whose key value's bucket lives on n (RowCounts).
 *  - L(n) = sum_j pi_j * sum over statements of E[shard relations on n the statement touches], a repeat
 *    multiplying its body. A routed statement lands on node(bucket(hash(key value))), the value's
 *    distribution being its variable's marginal (Marginals, rank offset applied, projections through
 *    the domain parents); an unrouted one touches every bucket of its group; a reference-only read adds
 *    relations / n to every node, a write relations to every node; a repartition join is excluded and
 *    listed (Routing). */
public final class CostModel {

	private final KeyHasher hasher;
	private final long perTemplate;
	private final long perEvaluation;

	public CostModel() {
		this(CitusHash.INSTANCE);
	}

	public CostModel(KeyHasher hasher) {
		this(hasher, Budget.STEPS_PER_TEMPLATE, Budget.STEPS_PER_EVALUATION);
	}

	// tests lower the limits to exercise the budget error
	public CostModel(KeyHasher hasher, long perTemplate, long perEvaluation) {
		this.hasher = hasher;
		this.perTemplate = perTemplate;
		this.perEvaluation = perEvaluation;
	}

	public SkewAndLoad skewAndLoad(Layout layout, Workload workload, long rankOffset) {
		CostInputs.check(layout, workload);
		budget(layout, workload, false);
		return loads(layout, workload, RankMapping.of(workload, rankOffset), rankOffset, new java.util.HashMap<>());
	}

	// The full report (Plan-phase3 4.4): Step 6's data and load, plus M1 distributed transactions,
	// M2 fan-out, per template. The budget pre-pass covers every loop before any runs
	public CostReport evaluate(Layout layout, Workload workload, long rankOffset) {
		CostInputs.check(layout, workload);
		budget(layout, workload, true);
		RankMapping mapping = RankMapping.of(workload, rankOffset);
		java.util.Map<String, Double> touches = new java.util.HashMap<>();
		SkewAndLoad loads = loads(layout, workload, mapping, rankOffset, touches);
		DistributedTransactions m1 = new DistributedTransactions(layout, mapping, hasher);
		double totalWeight = totalWeight(workload);
		double twoPhase = 0;
		double multiNode = 0;
		double tasks = 0;
		double statements = 0;
		double multiShard = 0;
		int max = 0;
		List<CostReport.TransactionCost> costs = new ArrayList<>();
		for (Transaction t : workload.transactions()) {
			double pi = t.weight() / totalWeight;
			DistributedTransactions.Result r = m1.of(t);
			FanOutSum f = new FanOutSum(layout);
			f.steps(t.steps(), 1);
			twoPhase += pi * r.twoPhase();
			multiNode += pi * r.multiNode();
			tasks += pi * f.tasks;
			statements += pi * f.statements;
			multiShard += pi * f.multiShard;
			max = Math.max(max, f.max);
			costs.add(new CostReport.TransactionCost(t.name(), pi, r.twoPhase(), r.multiNode(),
					f.statements == 0 ? 0 : f.tasks / f.statements, f.max, touches.get(t.name())));
		}
		return new CostReport(layout.name(), workload.name(), loads.nodes(), rankOffset, workload.params(),
				new CostReport.Distributed(twoPhase, multiNode),
				new CostReport.FanOut(statements == 0 ? 0 : tasks / statements, statements == 0 ? 0 : multiShard / statements, max),
				loads.data(), loads.load(), loads.repartitions(), costs);
	}

	// the convenience form: a parsed model, with its own placement unless another is given (rank offset 0)
	public static CostReport evaluate(io.shardml.shardML.Database db, Workload workload, io.shardml.placement.Placement placement) {
		Layout layout = Layouts.of(db);
		java.util.Objects.requireNonNull(placement, "placement");
		if (!placement.assignments().equals(layout.placement().assignments()))
			layout = layout.withPlacement(placement.assignments());
		return new CostModel().evaluate(layout, workload, 0);
	}

	private static double totalWeight(Workload workload) {
		double total = 0;
		for (Transaction t : workload.transactions())
			total += t.weight();
		return total;
	}

	// M2 per template: tasks per executed statement, repeat multiplicities included; repartitions excluded
	private static final class FanOutSum {
		private final Layout layout;
		double tasks;
		double statements;
		double multiShard;
		int max;

		FanOutSum(Layout layout) {
			this.layout = layout;
		}

		void steps(List<Step> steps, long times) {
			for (Step step : steps) {
				if (step instanceof Repeat repeat)
					steps(repeat.steps(), times * repeat.count());
				else if (step instanceof Statement s) {
					Route route = Routing.route(s, layout);
					if (route instanceof Repartition)
						continue;
					int f = route instanceof AllShards all ? all.anyTable().buckets() : 1;
					tasks += (double) times * f;
					statements += times;
					if (f > 1)
						multiShard += times;
					max = Math.max(max, f);
				}
			}
		}
	}

	private SkewAndLoad loads(Layout layout, Workload workload, RankMapping mapping, long rankOffset,
			java.util.Map<String, Double> touches) {
		List<Node> nodes = layout.nodes();
		DataSkew data = data(layout, workload, mapping, nodes);

		double totalWeight = 0;
		for (Transaction t : workload.transactions())
			totalWeight += t.weight();
		double[] read = new double[nodes.size()];
		double[] write = new double[nodes.size()];
		List<RepartitionJoin> repartitions = new ArrayList<>();
		for (Transaction t : workload.transactions()) {
			double pi = t.weight() / totalWeight;
			Marginals marginals = Marginals.of(workload, mapping, t);
			LoadWalk walk = new LoadWalk(layout, mapping, marginals, t, pi, read, write, repartitions);
			walk.steps(t.steps(), 1);
			touches.put(t.name(), walk.touches);
		}
		double[] total = new double[nodes.size()];
		for (int n = 0; n < total.length; n++)
			total[n] = read[n] + write[n];
		int max = argmax(total);
		double sum = 0;
		for (double l : total)
			sum += l;
		double mean = sum / nodes.size();
		Load load = new Load(sum, total[max], mean == 0 ? 0 : total[max] / mean, nodes.get(max).name(),
				values(nodes, total), values(nodes, read), values(nodes, write));
		return new SkewAndLoad(layout.name(), workload.name(), rankOffset, nodes.stream().map(Node::name).toList(), data, load,
				repartitions);
	}

	// --- M3: data skew ---

	private DataSkew data(Layout layout, Workload workload, RankMapping mapping, List<Node> nodes) {
		long[] rows = new long[nodes.size()];
		List<TableRows> tables = new ArrayList<>();
		for (TableLayout table : layout.tables()) {
			if (table instanceof HashTable hash) {
				long[] perNode = new long[nodes.size()];
				RowCounts.of(workload, mapping, hash.table()).forEachKeyValue(hash.keyColumn(),
						(value, count) -> perNode[nodes.indexOf(layout.nodeOf(hash, hasher.bucket(value, hash.buckets())))] += count);
				for (int n = 0; n < rows.length; n++)
					rows[n] += perNode[n];
				tables.add(new TableRows(hash.table(), false, rows(nodes, perNode)));
			} else if (workload.table(table.table()).isPresent()) {
				long all = RowCounts.of(workload, mapping, table.table()).total();
				long[] perNode = new long[nodes.size()];
				java.util.Arrays.fill(perNode, all);
				tables.add(new TableRows(table.table(), true, rows(nodes, perNode)));
			}
		}
		double sum = 0;
		int max = 0;
		for (int n = 0; n < rows.length; n++) {
			sum += rows[n];
			if (rows[n] > rows[max])
				max = n;
		}
		double mean = sum / nodes.size();
		return new DataSkew(mean == 0 ? 0 : rows[max] / mean, nodes.get(max).name(), rows(nodes, rows), tables);
	}

	// --- M4: load ---

	private final class LoadWalk {
		private final Layout layout;
		private final RankMapping mapping;
		private final Marginals marginals;
		private final Transaction transaction;
		private final double pi;
		private final double[] read;
		private final double[] write;
		private final List<RepartitionJoin> repartitions;
		private final List<Node> nodes;
		private final java.util.Map<Integer, String> letDomains = new java.util.HashMap<>();
		// per (let, path, bucket count): what the budget pre-pass counts once
		private final java.util.Map<List<Object>, double[]> masses = new java.util.HashMap<>();
		private int statement;
		// the template's expected shard relations per execution (unweighted)
		double touches;

		LoadWalk(Layout layout, RankMapping mapping, Marginals marginals, Transaction transaction, double pi, double[] read,
				double[] write, List<RepartitionJoin> repartitions) {
			this.layout = layout;
			this.mapping = mapping;
			this.marginals = marginals;
			this.transaction = transaction;
			this.pi = pi;
			this.read = read;
			this.write = write;
			this.repartitions = repartitions;
			this.nodes = layout.nodes();
			List<Let> lets = new ArrayList<>();
			collect(transaction.steps(), lets, new ArrayList<>());
			for (Let let : lets)
				letDomains.put(let.id(), let.domain());
		}

		void steps(List<Step> steps, long times) {
			for (Step step : steps) {
				if (step instanceof Repeat repeat)
					steps(repeat.steps(), times * repeat.count());
				else if (step instanceof Statement s)
					statement(s, times);
			}
		}

		private void statement(Statement s, long times) {
			statement++;
			double[] into = s.kind() == Kind.READ ? read : write;
			double w = pi * times;
			Route route = Routing.route(s, layout);
			if (route instanceof Repartition r) {
				repartitions.add(new RepartitionJoin(transaction.name(), statement, s.table(), r.reason(), pi, times));
			} else if (route instanceof ReferenceOnly ref) {
				double each = s.kind() == Kind.READ ? (double) ref.relations().size() / nodes.size() : ref.relations().size();
				for (int n = 0; n < into.length; n++)
					into[n] += w * each;
				touches += times * each * nodes.size();
			} else if (route instanceof AllShards all) {
				int relations = all.relations().size();
				for (int b = 0; b < all.anyTable().buckets(); b++)
					into[nodes.indexOf(layout.nodeOf(all.anyTable(), b))] += w * relations;
				touches += (double) times * relations * all.anyTable().buckets();
			} else {
				Routed routed = (Routed) route;
				int relations = routed.relations().size();
				double[] mass = bucketMass(routed.value(), routed.keyTable().buckets());
				for (int b = 0; b < mass.length; b++)
					into[nodes.indexOf(layout.nodeOf(routed.keyTable(), b))] += w * relations * mass[b];
				touches += (double) times * relations;
			}
		}

		// P(the bound value's key lands in bucket b): the variable's marginal through its projection
		// and the cached value -> bucket array
		private double[] bucketMass(VarRef value, int buckets) {
			return masses.computeIfAbsent(List.of(value.let(), value.path(), buckets), k -> computeMass(value, buckets));
		}

		private double[] computeMass(VarRef value, int buckets) {
			String domain = letDomains.get(value.let());
			KeyBuckets kb = mapping.buckets(new KeyPath(domain, value.path()), hasher, buckets);
			double[] marginal = marginals.of(value.let());
			double[] mass = new double[buckets];
			for (int v = 1; v <= marginal.length; v++)
				mass[kb.bucket(v)] += marginal[v - 1];
			return mass;
		}
	}

	// --- the budget pre-pass (4.4c, 5) ---

	private void budget(Layout layout, Workload workload, boolean distributed) {
		Budget budget = new Budget(perTemplate, perEvaluation);
		for (TableLayout table : layout.tables()) {
			if (!(table instanceof HashTable hash))
				continue;
			TableData data = workload.table(hash.table()).orElseThrow();
			long steps;
			if (data.column(hash.keyColumn()).orElseThrow() instanceof Mapped mapped) {
				long rowValues = data.rows() instanceof OverRows over ? workload.domain(over.domain()).size()
						: workload.domain(mapped.path().domain()).size();
				steps = rowValues + workload.domain(mapped.path().target()).size();
			} else {
				steps = RowCounts.total(workload, data);
			}
			budget.charge("table '" + hash.table() + "' (data skew)", steps);
		}
		for (Transaction t : workload.transactions())
			budget.charge("transaction '" + t.name() + "'",
					steps(layout, workload, t) + (distributed ? DistributedTransactions.steps(t, layout, workload) : 0));
	}

	private static long steps(Layout layout, Workload workload, Transaction t) {
		List<Let> lets = new ArrayList<>();
		List<Statement> statements = new ArrayList<>();
		collect(t.steps(), lets, statements);
		long steps = 0;
		for (Let let : lets) {
			long size = workload.domain(let.domain()).size();
			VarRef base = let instanceof ElseWith w ? w.base() : ((Draw) let).distinctFrom();
			if (base == null) {
				steps += size;
				continue;
			}
			steps += 2 * size;
			if (!base.path().isEmpty())
				steps += workload.domain(domainOf(lets, base.let())).size();
		}
		Set<List<Object>> bindings = new LinkedHashSet<>();
		for (Statement s : statements) {
			if (Routing.route(s, layout) instanceof Routed routed && bindings.add(List.of(routed.value().let(),
					routed.value().path(), routed.keyTable().buckets())))
				steps += workload.domain(domainOf(lets, routed.value().let())).size();
		}
		return steps;
	}

	private static String domainOf(List<Let> lets, int id) {
		return lets.stream().filter(l -> l.id() == id).findFirst().orElseThrow().domain();
	}

	private static void collect(List<Step> steps, List<Let> lets, List<Statement> statements) {
		for (Step step : steps) {
			if (step instanceof Let let)
				lets.add(let);
			else if (step instanceof Statement s)
				statements.add(s);
			else
				collect(((Repeat) step).steps(), lets, statements);
		}
	}

	// --- helpers ---

	private static int argmax(double[] values) {
		int max = 0;
		for (int i = 1; i < values.length; i++) {
			if (values[i] > values[max])
				max = i;
		}
		return max;
	}

	private static List<NodeValue> values(List<Node> nodes, double[] values) {
		List<NodeValue> out = new ArrayList<>();
		for (int i = 0; i < values.length; i++)
			out.add(new NodeValue(nodes.get(i).name(), values[i]));
		return out;
	}

	private static List<NodeRows> rows(List<Node> nodes, long[] rows) {
		List<NodeRows> out = new ArrayList<>();
		for (int i = 0; i < rows.length; i++)
			out.add(new NodeRows(nodes.get(i).name(), rows[i]));
		return out;
	}

}
