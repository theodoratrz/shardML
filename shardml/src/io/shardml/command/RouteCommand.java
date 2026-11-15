package io.shardml.command;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import org.eclipse.emf.ecore.resource.Resource;

import io.shardml.cost.CitusHash;
import io.shardml.cost.CostInputs;
import io.shardml.cost.HashTable;
import io.shardml.cost.KeyHasher;
import io.shardml.cost.KeyType;
import io.shardml.cost.Layout;
import io.shardml.cost.Layouts;
import io.shardml.cost.RankMapping;
import io.shardml.cost.Routing;
import io.shardml.cost.Routing.AllShards;
import io.shardml.cost.Routing.ReferenceOnly;
import io.shardml.cost.Routing.Repartition;
import io.shardml.cost.Routing.Route;
import io.shardml.cost.Routing.Routed;
import io.shardml.cost.TableLayout;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.ColumnRef;
import io.shardml.cost.Workload.Condition;
import io.shardml.cost.Workload.Join;
import io.shardml.cost.Workload.JoinOn;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Kind;
import io.shardml.cost.Workload.Let;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.Transaction;
import io.shardml.cost.Workload.VarRef;
import io.shardml.cost.Workloads;
import io.shardml.placement.Node;
import io.shardml.schema.SqlNames;
import io.shardml.shardML.Model;
import io.shardml.workload.WorkloadModel;

/* `shardml route <model.shardml> <workload> [options]` (Plan-phase3 4.5b): the SQL of every workload
 * statement for sampled variable bindings, with the cost model's prediction of how Citus runs it (task
 * count, nodes, repartition), so an integration test can EXPLAIN it on a cluster and compare. The
 * predictions come from the cost model's own code: Routing, RankMapping.buckets, Layout.nodeOf.
 * `--keys N` prints keys with their predicted bucket and node instead. Nothing executes. */
public final class RouteCommand {

	public static final String USAGE_TEXT = String.join(System.lineSeparator(),
			"Usage: shardml route [options] <model.shardml> <workload.workload>",
			"  --samples N        bindings per statement (default 10)",
			"  --seed S           seed of the uniform draws (default 1)",
			"  --set name=value   override a workload param (repeatable)",
			"  --rank-offset k    rotate the top-level domains' ranks by k (default 0)",
			"  --keys N           print N keys per key type with their bucket and node instead",
			"Output, tab-separated: template statement sample shape tasks nodes sql",
			"(shape: routed, unrouted, reference-read, reference-write, repartition).");

	private static final KeyHasher HASHER = CitusHash.INSTANCE;

	private RouteCommand() {
	}

	private record Options(File model, File workload, int samples, long seed, Map<String, String> sets, long rankOffset,
			int keys) {
	}

	private static final class UsageError extends Exception {
		UsageError(String message) {
			super(message);
		}
	}

	public static int run(List<String> args, PrintStream out, PrintStream err) {
		Options options;
		try {
			options = parse(args);
		} catch (UsageError e) {
			err.println("shardml route: " + e.getMessage());
			err.println(USAGE_TEXT);
			return CostCommand.USAGE;
		}
		com.google.inject.Injector injector = CommandSupport.setup();
		List<Resource> files = new ArrayList<>();
		try {
			files.add(CommandSupport.load(injector, options.workload()));
			files.add(CommandSupport.load(injector, options.model()));
		} catch (RuntimeException e) {
			err.println("shardml route: error: cannot read " + e.getMessage());
			return CostCommand.ERROR;
		}
		int errors = CommandSupport.validate(files, err);
		if (errors > 0) {
			err.println("shardml route: " + errors + (errors == 1 ? " error" : " errors"));
			return CostCommand.ERROR;
		}
		try {
			Workload workload = Workloads.of((WorkloadModel) files.get(0).getContents().get(0), options.sets());
			Layout layout = Layouts.of(((Model) files.get(1).getContents().get(0)).getDb());
			CostInputs.check(layout, workload);
			RankMapping mapping = RankMapping.of(workload, options.rankOffset());
			out.print(options.keys() > 0 ? keys(layout, options.keys(), options.seed()) : statements(layout, workload, mapping,
					options.samples(), options.seed()));
			return CostCommand.OK;
		} catch (IllegalArgumentException e) {
			err.println("shardml route: error: " + e.getMessage());
			return CostCommand.ERROR;
		}
	}

	private static Options parse(List<String> args) throws UsageError {
		File model = null;
		File workload = null;
		int samples = 10;
		long seed = 1;
		long rankOffset = 0;
		int keys = 0;
		Map<String, String> sets = new LinkedHashMap<>();
		for (int i = 0; i < args.size(); i++) {
			String arg = args.get(i);
			switch (arg) {
				case "--samples" -> samples = positive(next(args, ++i, arg), arg);
				case "--keys" -> keys = positive(next(args, ++i, arg), arg);
				case "--seed" -> {
					String v = next(args, ++i, arg);
					try {
						seed = Long.parseLong(v);
					} catch (NumberFormatException e) {
						throw new UsageError("--seed needs a whole number, got '" + v + "'");
					}
				}
				case "--rank-offset" -> {
					String v = next(args, ++i, arg);
					try {
						rankOffset = Long.parseLong(v);
					} catch (NumberFormatException e) {
						throw new UsageError("--rank-offset needs a whole number, got '" + v + "'");
					}
					if (rankOffset < 0)
						throw new UsageError("--rank-offset must be at least 0, got " + rankOffset);
				}
				case "--set" -> {
					String v = next(args, ++i, arg);
					int eq = v.indexOf('=');
					if (eq <= 0)
						throw new UsageError("--set needs name=value, got '" + v + "'");
					if (sets.put(v.substring(0, eq), v.substring(eq + 1)) != null)
						throw new UsageError("--set " + v.substring(0, eq) + " given twice");
				}
				default -> {
					if (arg.startsWith("-"))
						throw new UsageError("unknown option '" + arg + "'");
					File file = new File(arg);
					if (arg.endsWith(".shardml") && model == null)
						model = file;
					else if (arg.endsWith(".workload") && workload == null)
						workload = file;
					else
						throw new UsageError("expected one .shardml model and one .workload, got '" + arg + "'");
					if (!file.isFile())
						throw new UsageError("no such file: " + arg);
				}
			}
		}
		if (model == null || workload == null)
			throw new UsageError("needs one .shardml model and one .workload");
		return new Options(model, workload, samples, seed, Map.copyOf(sets), rankOffset, keys);
	}

	private static int positive(String value, String option) throws UsageError {
		try {
			int n = Integer.parseInt(value);
			if (n >= 1)
				return n;
		} catch (NumberFormatException e) {
			// below
		}
		throw new UsageError(option + " needs a whole number of at least 1, got '" + value + "'");
	}

	private static String next(List<String> args, int i, String option) throws UsageError {
		if (i >= args.size())
			throw new UsageError(option + " needs a value");
		return args.get(i);
	}

	// --- statements ---

	public static String statements(Layout layout, Workload workload, RankMapping mapping, int samples, long seed) {
		StringBuilder out = new StringBuilder();
		for (Transaction t : workload.transactions()) {
			Map<Integer, Let> lets = new LinkedHashMap<>();
			List<Statement> statements = new ArrayList<>();
			collect(t.steps(), lets, statements);
			for (int s = 0; s < statements.size(); s++) {
				Statement statement = statements.get(s);
				Map<Integer, List<Long>> values = new LinkedHashMap<>();
				for (Condition c : statement.conditions()) {
					if (c.value() != null)
						values.computeIfAbsent(c.value().let(), id -> samples(lets.get(id), mapping, samples,
								seed * 31 + (t.name() + "/" + id).hashCode()));
				}
				Route route = Routing.route(statement, layout);
				for (int j = 0; j < samples; j++) {
					Map<Integer, Long> binding = new LinkedHashMap<>();
					for (Map.Entry<Integer, List<Long>> e : values.entrySet())
						binding.put(e.getKey(), e.getValue().get(j % e.getValue().size()));
					out.append(t.name()).append('\t').append(s + 1).append('\t').append(j + 1).append('\t')
							.append(prediction(route, statement, binding, lets, layout, mapping)).append('\t')
							.append(sql(statement, binding, lets, layout, mapping)).append('\n');
				}
			}
		}
		return out.toString();
	}

	// a variable's sample values: its hottest ranks 1, 2, 3 (as values, offset applied), its smallest and largest
	// values, then seeded uniform draws; deterministic
	public static List<Long> samples(Let let, RankMapping mapping, int n, long seed) {
		long size = mapping.size(let.domain());
		LinkedHashSet<Long> picks = new LinkedHashSet<>();
		for (long r = 1; r <= Math.min(3, size); r++)
			picks.add(mapping.value(let.domain(), r));
		picks.add(1L);
		picks.add(size);
		List<Long> out = new ArrayList<>(picks);
		Random random = new Random(seed);
		while (out.size() < n)
			out.add(1 + (long) (random.nextDouble() * size));
		return List.copyOf(out.subList(0, n));
	}

	// shape, tasks, nodes
	private static String prediction(Route route, Statement statement, Map<Integer, Long> binding, Map<Integer, Let> lets,
			Layout layout, RankMapping mapping) {
		if (route instanceof Repartition)
			return "repartition\t-\t-";
		if (route instanceof ReferenceOnly) {
			if (statement.kind() == Kind.READ)
				return "reference-read\t1\t-";
			return "reference-write\t1\t" + nodes(layout.nodes());
		}
		if (route instanceof AllShards all) {
			TreeSet<String> held = new TreeSet<>();
			for (int b = 0; b < all.anyTable().buckets(); b++)
				held.add(hostPort(layout.nodeOf(all.anyTable(), b)));
			return "unrouted\t" + all.anyTable().buckets() + "\t" + String.join(",", held);
		}
		Routed routed = (Routed) route;
		VarRef ref = routed.value();
		Let let = lets.get(ref.let());
		int bucket = mapping.buckets(new KeyPath(let.domain(), ref.path()), HASHER, routed.keyTable().buckets())
				.bucket(binding.get(ref.let()));
		return "routed\t1\t" + hostPort(layout.nodeOf(routed.keyTable(), bucket));
	}

	private static String nodes(List<Node> nodes) {
		return String.join(",", new TreeSet<>(nodes.stream().map(RouteCommand::hostPort).toList()));
	}

	private static String hostPort(Node node) {
		return node.host() + ":" + node.port();
	}

	// the statement as SQL with the binding's values: SELECT for a read, UPDATE .. SET c = c for a write (c the
	// first non-key column), DELETE for a delete; `?` is the literal 0
	static String sql(Statement statement, Map<Integer, Long> binding, Map<Integer, Let> lets, Layout layout,
			RankMapping mapping) {
		String table = qualified(layout, statement.table());
		List<String> where = new ArrayList<>();
		for (Join join : statement.joins()) {
			for (JoinOn on : join.on())
				where.add(column(layout, on.left()) + " = " + column(layout, on.right()));
		}
		StringBuilder conditions = new StringBuilder();
		for (int i = 0; i < statement.conditions().size(); i++) {
			Condition c = statement.conditions().get(i);
			if (i > 0)
				conditions.append(' ').append(statement.connectives().get(i - 1).name()).append(' ');
			conditions.append(column(layout, c.column())).append(' ').append(op(c.op())).append(' ')
					.append(c.value() == null ? "0" : String.valueOf(value(c.value(), binding, lets, mapping)));
		}
		StringBuilder sql = new StringBuilder();
		switch (statement.kind()) {
			case READ -> {
				sql.append("SELECT * FROM ").append(table);
				for (Join join : statement.joins()) {
					List<String> on = new ArrayList<>();
					for (JoinOn o : join.on())
						on.add(column(layout, o.left()) + " = " + column(layout, o.right()));
					sql.append(" JOIN ").append(qualified(layout, join.table())).append(" ON ").append(String.join(" AND ", on));
				}
				if (conditions.length() > 0)
					sql.append(" WHERE ").append(conditions);
				return sql.toString();
			}
			case WRITE -> {
				String c = SqlNames.write(nonKeyColumn(layout, statement.table()));
				sql.append("UPDATE ").append(table).append(" SET ").append(c).append(" = ").append(c);
			}
			case DELETE -> sql.append("DELETE FROM ").append(table);
		}
		if (!statement.joins().isEmpty())
			sql.append(statement.kind() == Kind.WRITE ? " FROM " : " USING ")
					.append(String.join(", ", statement.joins().stream().map(j -> qualified(layout, j.table())).toList()));
		List<String> all = new ArrayList<>(where);
		if (conditions.length() > 0)
			all.add(where.isEmpty() ? conditions.toString() : "(" + conditions + ")");
		if (!all.isEmpty())
			sql.append(" WHERE ").append(String.join(" AND ", all));
		return sql.toString();
	}

	private static long value(VarRef ref, Map<Integer, Long> binding, Map<Integer, Let> lets, RankMapping mapping) {
		long v = binding.get(ref.let());
		return ref.path().isEmpty() ? v : mapping.project(new KeyPath(lets.get(ref.let()).domain(), ref.path()), v);
	}

	private static String op(Workload.Op op) {
		return switch (op) {
			case EQ -> "=";
			case NEQ -> "<>";
			case LT -> "<";
			case LE -> "<=";
			case GT -> ">";
			case GE -> ">=";
		};
	}

	private static String qualified(Layout layout, String table) {
		return layout.table(table).map(TableLayout::qualified).orElseThrow();
	}

	private static String column(Layout layout, ColumnRef ref) {
		return qualified(layout, ref.table()) + "." + SqlNames.write(ref.column());
	}

	// the first column of the table that is not its shard key (any column of a reference table)
	private static String nonKeyColumn(Layout layout, String table) {
		TableLayout t = layout.table(table).orElseThrow();
		String key = t instanceof HashTable hash ? hash.keyColumn() : null;
		for (String column : layout.schema().tables().get(table).keySet()) {
			if (!column.equals(key))
				return column;
		}
		throw new IllegalArgumentException("Table '" + table + "' has no column besides its shard key to update.");
	}

	private static void collect(List<Step> steps, Map<Integer, Let> lets, List<Statement> statements) {
		for (Step step : steps) {
			if (step instanceof Let let)
				lets.put(let.id(), let);
			else if (step instanceof Statement s)
				statements.add(s);
			else
				collect(((Repeat) step).steps(), lets, statements);
		}
	}

	// --- keys ---

	// n keys per key type used by the layout's hash tables (the first table of each type): half 1..n/2, half
	// seeded over the type's range; with the model's bucket and node
	static String keys(Layout layout, int n, long seed) {
		StringBuilder out = new StringBuilder();
		Map<KeyType, HashTable> byType = new LinkedHashMap<>();
		for (TableLayout t : layout.tables()) {
			if (t instanceof HashTable hash)
				byType.putIfAbsent(hash.keyType(), hash);
		}
		for (HashTable table : byType.values()) {
			Random random = new Random(seed * 31 + table.keyType().ordinal());
			List<Long> keys = new ArrayList<>();
			for (long k = 1; k <= n / 2; k++)
				keys.add(k);
			KeyType type = table.keyType();
			while (keys.size() < n) {
				long k = type == KeyType.INT8 ? random.nextLong() : type.min() + (long) (random.nextDouble() * (type.max() - type.min() + 1.0));
				keys.add(Math.max(type.min(), Math.min(type.max(), k)));
			}
			for (long key : keys) {
				int bucket = HASHER.bucket(key, table.buckets());
				out.append(table.qualified()).append('\t').append(key).append('\t').append(bucket).append('\t')
						.append(hostPort(layout.nodeOf(table, bucket))).append('\n');
			}
		}
		return out.toString();
	}
}
