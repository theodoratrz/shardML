package io.shardml.bench;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.LongUnaryOperator;

import org.postgresql.PGConnection;
import org.postgresql.copy.CopyIn;

import io.shardml.cost.CostModel;
import io.shardml.cost.Layout;
import io.shardml.cost.RankMapping;
import io.shardml.cost.RowCounts;
import io.shardml.cost.SkewAndLoad;
import io.shardml.cost.TableLayout;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.ColumnMapping;
import io.shardml.cost.Workload.Mapped;
import io.shardml.cost.Workload.TableData;
import io.shardml.cost.Workload.Unique;
import io.shardml.placement.Node;
import io.shardml.schema.SqlNames;
import io.shardml.schema.SqlTypes.SqlType;

/* `driver load` (Plan-phase4 2.5, P4.2): fills a deployed layout with the rows the model counts, then checks
 * that the rows on every node equal the model's data-skew rows exactly.
 *
 * Rows: RowCounts.forEachRowGroup, the model's own enumeration (per-domain combinations, or an `over`
 * table's largest-remainder counts per rank, RankMapping applying the rank offset). Column values:
 *  - a mapped column: its key path projected from the row domain's value (RankMapping.projector);
 *  - a `unique` column, and any other column of the table's primary or unique keys: the row's number,
 *    1..total in RowCounts' order (so keys the workload does not map stay unique);
 *  - any other column: a constant of its type (0, 'x', false, 2000-01-01, '{}', ...); NULL for a type it
 *    cannot write.
 * Every table block of the workload that the layout distributes is loaded with COPY through the
 * coordinator, in chunks of at most CHUNK_ROWS rows on `jobs` connections in parallel; then VACUUM ANALYZE.
 * The tables must be empty (or --truncate). Foreign keys are deployed after loading (chunks of different
 * tables run in parallel).
 *
 * Check: per table, count(*) of every shard placement (run_command_on_placements) summed per node, against
 * SkewAndLoad.DataSkew's rows per node for that table (CostModel with CitusHash); hash tables must have rows
 * only on the layout's nodes and equal the model there exactly; a reference table's copies must each hold
 * the model's total (a copy on a node the layout does not declare, the coordinator's, is listed, not compared).
 * Any difference fails. This checks the loader, not the model. */
final class Loader {

	static final int CHUNK_ROWS = 200_000;

	record Chunk(TableData table, String qualified, List<String> columns, long firstGroup, long endGroup, long firstRow,
			long rows) {
	}

	record NodeCheck(String table, String node, long expected, long actual, boolean compared) {
		boolean ok() {
			return !compared || expected == actual;
		}
	}

	private Loader() {
	}

	// the COPY text value of each column for a row: index into the row's domain values, or a source
	private interface Column {
		String value(long[] domainValues, long row);
	}

	static List<Chunk> chunks(Workload workload, Layout layout, RankMapping mapping) {
		List<Chunk> out = new ArrayList<>();
		for (TableData data : workload.tables()) {
			TableLayout table = layout.table(data.table()).orElse(null);
			if (table == null)
				continue;
			List<String> columns = new ArrayList<>(layout.schema().tables().get(data.table()).keySet());
			RowCounts counts = RowCounts.of(workload, mapping, data.table());
			long[] group = { 0 };
			long[] rowsInChunk = { 0 };
			long[] firstGroup = { 0 };
			long[] firstRow = { 1 };
			long[] row = { 1 };
			counts.forEachRowGroup((values, rows) -> {
				if (rowsInChunk[0] > 0 && rowsInChunk[0] + rows > CHUNK_ROWS) {
					out.add(new Chunk(data, table.qualified(), columns, firstGroup[0], group[0], firstRow[0], rowsInChunk[0]));
					firstGroup[0] = group[0];
					firstRow[0] = row[0];
					rowsInChunk[0] = 0;
				}
				rowsInChunk[0] += rows;
				row[0] += rows;
				group[0]++;
			});
			if (rowsInChunk[0] > 0)
				out.add(new Chunk(data, table.qualified(), columns, firstGroup[0], group[0], firstRow[0], rowsInChunk[0]));
		}
		return out;
	}

	// the column sources of a table block
	private static List<Column> sources(Workload workload, Layout layout, RankMapping mapping, TableData data, List<String> columns) {
		RowCounts counts = RowCounts.of(workload, mapping, data.table());
		List<String> domains = counts.rowDomains();
		List<String> keys = layout.schema().keyColumns().getOrDefault(data.table(), List.of());
		List<Column> out = new ArrayList<>();
		for (String name : columns) {
			ColumnMapping m = data.column(name).orElse(null);
			if (m instanceof Mapped mapped) {
				int index = domains.indexOf(mapped.path().domain());
				if (index < 0)
					throw new IllegalArgumentException("Column " + data.table() + "." + name + " is mapped from domain '"
							+ mapped.path().domain() + "', which is not one of the table's row domains " + domains + ".");
				LongUnaryOperator projector = mapped.path().parents().isEmpty() ? LongUnaryOperator.identity()
						: mapping.projector(mapped.path());
				out.add((values, row) -> Long.toString(projector.applyAsLong(values[index])));
			} else if (m instanceof Unique || keys.contains(name)) {
				out.add((values, row) -> Long.toString(row));
			} else {
				String constant = constant(layout.schema().type(data.table(), name));
				out.add((values, row) -> constant);
			}
		}
		return out;
	}

	// a constant of the type, in COPY text format; \N (NULL) when there is none
	static String constant(SqlType type) {
		if (type == null)
			return "\\N";
		return switch (type.base()) {
			case INT2, INT4, INT8, NUMERIC, FLOAT4, FLOAT8 -> "0";
			case BOOL -> "f";
			case TEXT, VARCHAR, BPCHAR -> "x";
			case DATE -> "2000-01-01";
			case TIMESTAMP -> "2000-01-01 00:00:00";
			case TIMESTAMPTZ -> "2000-01-01 00:00:00+00";
			case JSONB -> "{}";
			case UUID -> "00000000-0000-0000-0000-000000000000";
			case BYTEA -> "\\\\x";
			case UNKNOWN -> "\\N";
		};
	}

	// the COPY text of a chunk's rows, written in blocks to the sink
	static void rows(Workload workload, Layout layout, RankMapping mapping, Chunk chunk, java.util.function.Consumer<StringBuilder> sink) {
		List<Column> sources = sources(workload, layout, mapping, chunk.table(), chunk.columns());
		RowCounts counts = RowCounts.of(workload, mapping, chunk.table().table());
		long[] group = { 0 };
		long[] row = { chunk.firstRow() };
		StringBuilder block = new StringBuilder(1 << 20);
		counts.forEachRowGroup((values, rows) -> {
			long g = group[0]++;
			if (g < chunk.firstGroup() || g >= chunk.endGroup())
				return;
			for (long k = 0; k < rows; k++) {
				for (int c = 0; c < sources.size(); c++) {
					if (c > 0)
						block.append('\t');
					block.append(sources.get(c).value(values, row[0]));
				}
				block.append('\n');
				row[0]++;
				if (block.length() > (1 << 20) - 4096) {
					sink.accept(block);
					block.setLength(0);
				}
			}
		});
		if (block.length() > 0)
			sink.accept(block);
	}

	static Connection connect(String target, String database, Map<String, String> settings) throws SQLException {
		Properties p = new Properties();
		p.putAll(settings);
		return DriverManager.getConnection("jdbc:postgresql://" + target + "/" + database, p);
	}

	static int run(Driver.Options o, Workload workload, Layout layout, PrintStream out, PrintStream err) {
		RankMapping mapping = RankMapping.of(workload, o.rankOffset());
		Map<String, String> settings = new LinkedHashMap<>();
		settings.put("user", o.user());
		settings.put("ApplicationName", "shardml-loader");
		settings.put("options", "-c statement_timeout=0" + (o.schema() == null ? "" : " -c search_path=" + o.schema()));
		String target = o.targets().get(0);
		List<Chunk> chunks = chunks(workload, layout, mapping);
		List<String> tables = chunks.stream().map(Chunk::qualified).distinct().toList();
		try {
			if (!o.verifyOnly()) {
				try (Connection c = connect(target, o.database(), settings); Statement s = c.createStatement()) {
					for (String t : tables) {
						if (o.truncate()) {
							s.execute("TRUNCATE " + t);
							continue;
						}
						try (ResultSet rs = s.executeQuery("SELECT EXISTS (SELECT 1 FROM " + t + ")")) {
							rs.next();
							if (rs.getBoolean(1))
								throw new IllegalArgumentException("table " + t + " is not empty (load into empty tables, or pass --truncate)");
						}
					}
				}
				long started = System.nanoTime();
				long total = chunks.stream().mapToLong(Chunk::rows).sum();
				out.println("load: " + total + " rows in " + tables.size() + " tables, " + chunks.size() + " chunks on " + o.jobs()
						+ " connections to " + target);
				ExecutorService pool = Executors.newFixedThreadPool(o.jobs());
				List<Future<Long>> done = new ArrayList<>();
				for (Chunk chunk : chunks)
					done.add(pool.submit(() -> copy(chunk, workload, layout, mapping, target, o.database(), settings)));
				pool.shutdown();
				try {
					for (Future<Long> f : done)
						f.get();
				} catch (java.util.concurrent.ExecutionException e) {
					pool.shutdownNow();
					throw new IllegalArgumentException("COPY failed: " + e.getCause().getMessage(), e.getCause());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new IllegalStateException("interrupted");
				}
				long copied = System.nanoTime();
				try (Connection c = connect(target, o.database(), settings); Statement s = c.createStatement()) {
					c.setAutoCommit(true);
					for (String t : tables)
						s.execute("VACUUM ANALYZE " + t);
				}
				out.printf("load: COPY %.1f s, VACUUM ANALYZE %.1f s%n", (copied - started) / 1e9, (System.nanoTime() - copied) / 1e9);
			}
			List<NodeCheck> checks = verify(workload, layout, mapping, target, o.database(), settings);
			out.print(format(checks));
			boolean ok = checks.stream().allMatch(NodeCheck::ok);
			out.println(ok ? "load: rows per node equal the model's data-skew rows exactly"
					: "load: FAILED: rows per node differ from the model's data-skew rows");
			return ok ? Driver.OK : Driver.FAIL;
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	private static long copy(Chunk chunk, Workload workload, Layout layout, RankMapping mapping, String target, String database,
			Map<String, String> settings) throws SQLException {
		String columns = String.join(", ", chunk.columns().stream().map(SqlNames::write).toList());
		try (Connection c = connect(target, database, settings)) {
			CopyIn copy = c.unwrap(PGConnection.class).getCopyAPI().copyIn("COPY " + chunk.qualified() + " (" + columns + ") FROM STDIN");
			try {
				rows(workload, layout, mapping, chunk, block -> {
					byte[] bytes = block.toString().getBytes(StandardCharsets.UTF_8);
					try {
						copy.writeToCopy(bytes, 0, bytes.length);
					} catch (SQLException e) {
						throw new IllegalStateException(e.getMessage(), e);
					}
				});
				return copy.endCopy();
			} catch (RuntimeException e) {
				if (copy.isActive())
					copy.cancelCopy();
				throw e;
			}
		}
	}

	static List<NodeCheck> verify(Workload workload, Layout layout, RankMapping mapping, String target, String database,
			Map<String, String> settings) throws SQLException {
		SkewAndLoad model = new CostModel().skewAndLoad(layout, workload, mapping.offset());
		Map<String, String> hostOf = new LinkedHashMap<>();
		for (Node n : layout.nodes())
			hostOf.put(n.name(), n.host() + ":" + n.port());
		List<NodeCheck> out = new ArrayList<>();
		Map<String, long[]> totals = new LinkedHashMap<>();
		try (Connection c = connect(target, database, settings);
				PreparedStatement ps = c.prepareStatement("SELECT nodename || ':' || nodeport, sum(result::bigint) "
						+ "FROM run_command_on_placements(?::regclass, 'select count(*) from %s') GROUP BY 1 ORDER BY 1")) {
			for (SkewAndLoad.TableRows t : model.data().tables()) {
				String qualified = layout.table(t.table()).orElseThrow().qualified();
				Map<String, Long> actual = new LinkedHashMap<>();
				ps.setString(1, qualified);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next())
						actual.put(rs.getString(1), rs.getLong(2));
				}
				for (SkewAndLoad.NodeRows n : t.rowsPerNode()) {
					String host = hostOf.get(n.node());
					long a = actual.getOrDefault(host, 0L);
					actual.remove(host);
					out.add(new NodeCheck(t.table(), n.node() + " (" + host + ")", n.rows(), a, true));
					if (!t.reference())
						totals.computeIfAbsent(n.node() + " (" + host + ")", k -> new long[2])[0] += n.rows();
					if (!t.reference())
						totals.get(n.node() + " (" + host + ")")[1] += a;
				}
				// a node the layout does not declare: a hash table must have no rows there; a reference table's copy
				// on it (the coordinator) is listed only
				for (Map.Entry<String, Long> e : actual.entrySet())
					out.add(new NodeCheck(t.table(), e.getKey() + " (not a layout node)", t.reference() ? -1 : 0, e.getValue(),
							!t.reference()));
			}
		}
		for (Map.Entry<String, long[]> e : totals.entrySet())
			out.add(new NodeCheck("(hash tables)", e.getKey(), e.getValue()[0], e.getValue()[1], true));
		return out;
	}

	static String format(List<NodeCheck> checks) {
		StringBuilder out = new StringBuilder(String.format("%-16s %-34s %12s %12s  %s%n", "table", "node", "model", "loaded", "result"));
		for (NodeCheck c : checks)
			out.append(String.format("%-16s %-34s %12s %12d  %s%n", c.table(), c.node(), c.expected() < 0 ? "-" : String.valueOf(c.expected()),
					c.actual(), !c.compared() ? "(listed)" : c.ok() ? "ok" : "DIFFERS"));
		return out.toString();
	}
}
