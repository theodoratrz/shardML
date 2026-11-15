package io.shardml.cost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.LongUnaryOperator;

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
import io.shardml.schema.SqlNames;

/* Every workload statement as SQL with positional `?` placeholders, one per bound condition, and what
 * each placeholder holds: a variable (the let's id) and its projection path. Shared by `shardml route`
 * (which inlines sampled values) and the Phase 4 driver (which prepares the SQL and binds them), so both
 * send the same statements:
 *  - read: SELECT * FROM t [JOIN j ON ..] [WHERE conditions];
 *  - write: UPDATE t SET c = c [FROM j] WHERE [join conditions AND] (conditions): c is the payload
 *    column (payloadColumn), set to its own value: a real row write (a new row version, its lock and
 *    WAL) that changes no data and no row count, so the snapshot the model describes stays valid;
 *  - delete: DELETE FROM t [USING j] WHERE .., as written.
 * A condition bound to `?` (no variable) is the literal 0. Statements are numbered in the order
 * Sampler visits them: depth-first through repeats, each statement once (not once per iteration). */
public final class StatementSql {

	private StatementSql() {
	}

	// the let a placeholder binds and the path its value is projected along (path.domain() is the
	// let's domain; no parents: the value itself)
	public record Placeholder(int let, String name, KeyPath path) {
	}

	/* index: the statement's number in its transaction (0-based, Sampler's order); parts has one more
	 * element than placeholders: the SQL is parts[0] ?1 parts[1] ?2 ... */
	public static final class Template {
		private final int index;
		private final Statement statement;
		private final List<String> parts;
		private final List<Placeholder> placeholders;
		private final LongUnaryOperator[] projectors;

		private Template(int index, Statement statement, List<String> parts, List<Placeholder> placeholders, RankMapping mapping) {
			this.index = index;
			this.statement = statement;
			this.parts = List.copyOf(parts);
			this.placeholders = List.copyOf(placeholders);
			this.projectors = new LongUnaryOperator[placeholders.size()];
			for (int i = 0; i < projectors.length; i++) {
				KeyPath path = placeholders.get(i).path();
				projectors[i] = path.parents().isEmpty() ? LongUnaryOperator.identity() : mapping.projector(path);
			}
		}

		public int index() {
			return index;
		}

		public Statement statement() {
			return statement;
		}

		public List<Placeholder> placeholders() {
			return placeholders;
		}

		// the SQL with `?` for each placeholder, for a prepared statement
		public String sql() {
			return String.join("?", parts);
		}

		// the placeholders' values from the variables' values (indexed by let id), projected
		public long[] parameters(long[] lets) {
			long[] out = new long[projectors.length];
			for (int i = 0; i < out.length; i++)
				out[i] = projectors[i].applyAsLong(lets[placeholders.get(i).let()]);
			return out;
		}

		// the SQL with the parameters written in as literals (what `shardml route` prints)
		public String inline(long[] parameters) {
			if (parameters.length != placeholders.size())
				throw new IllegalArgumentException(parameters.length + " parameters for " + placeholders.size() + " placeholders.");
			StringBuilder sql = new StringBuilder(parts.get(0));
			for (int i = 0; i < parameters.length; i++)
				sql.append(parameters[i]).append(parts.get(i + 1));
			return sql.toString();
		}
	}

	// a transaction's statements, in Sampler's order. Expects CostInputs.check to have passed
	public static List<Template> templates(Transaction transaction, Layout layout, RankMapping mapping) {
		Map<Integer, Let> lets = new java.util.LinkedHashMap<>();
		List<Statement> statements = new ArrayList<>();
		collect(transaction.steps(), lets, statements);
		List<Template> out = new ArrayList<>();
		for (Statement statement : statements)
			out.add(template(out.size(), statement, lets, layout, mapping));
		return List.copyOf(out);
	}

	/* The column a write sets to its own value: the first column of the table that is in no primary
	 * or unique key and is not the table's distribution column, so the choice does not depend on the
	 * layout; if the table has none, the first column that is not its distribution column. */
	public static String payloadColumn(Layout layout, String table) {
		TableLayout t = layout.table(table).orElseThrow(() -> new IllegalArgumentException(
				"Table '" + table + "' is not distributed by layout '" + layout.name() + "'."));
		String key = t instanceof HashTable hash ? hash.keyColumn() : null;
		Map<String, ?> columns = layout.schema().tables().get(table);
		List<String> keys = layout.schema().keyColumns().getOrDefault(table, List.of());
		for (String column : columns.keySet()) {
			if (!column.equals(key) && !keys.contains(column))
				return column;
		}
		for (String column : columns.keySet()) {
			if (!column.equals(key))
				return column;
		}
		throw new IllegalArgumentException("Table '" + table + "' has no column besides its shard key to update.");
	}

	private static Template template(int index, Statement statement, Map<Integer, Let> lets, Layout layout, RankMapping mapping) {
		List<String> parts = new ArrayList<>();
		List<Placeholder> placeholders = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		String table = qualified(layout, statement.table());
		List<String> joinConditions = new ArrayList<>();
		for (Join join : statement.joins()) {
			for (JoinOn on : join.on())
				joinConditions.add(column(layout, on.left()) + " = " + column(layout, on.right()));
		}
		boolean hasConditions = !statement.conditions().isEmpty();
		switch (statement.kind()) {
			case READ -> {
				sql.append("SELECT * FROM ").append(table);
				for (Join join : statement.joins()) {
					List<String> on = new ArrayList<>();
					for (JoinOn o : join.on())
						on.add(column(layout, o.left()) + " = " + column(layout, o.right()));
					sql.append(" JOIN ").append(qualified(layout, join.table())).append(" ON ").append(String.join(" AND ", on));
				}
				if (hasConditions) {
					sql.append(" WHERE ");
					conditions(statement, lets, layout, sql, parts, placeholders);
				}
				parts.add(sql.toString());
				return new Template(index, statement, parts, placeholders, mapping);
			}
			case WRITE -> {
				String c = SqlNames.write(payloadColumn(layout, statement.table()));
				sql.append("UPDATE ").append(table).append(" SET ").append(c).append(" = ").append(c);
			}
			case DELETE -> sql.append("DELETE FROM ").append(table);
		}
		if (!statement.joins().isEmpty())
			sql.append(statement.kind() == Kind.WRITE ? " FROM " : " USING ")
					.append(String.join(", ", statement.joins().stream().map(j -> qualified(layout, j.table())).toList()));
		if (!joinConditions.isEmpty() || hasConditions)
			sql.append(" WHERE ").append(String.join(" AND ", joinConditions));
		if (hasConditions) {
			boolean wrap = !joinConditions.isEmpty();
			sql.append(wrap ? " AND (" : "");
			conditions(statement, lets, layout, sql, parts, placeholders);
			sql.append(wrap ? ")" : "");
		}
		parts.add(sql.toString());
		return new Template(index, statement, parts, placeholders, mapping);
	}

	// the conditions with their connectives; each bound value ends a part and adds a placeholder
	private static void conditions(Statement statement, Map<Integer, Let> lets, Layout layout, StringBuilder sql, List<String> parts,
			List<Placeholder> placeholders) {
		for (int i = 0; i < statement.conditions().size(); i++) {
			Condition c = statement.conditions().get(i);
			if (i > 0)
				sql.append(' ').append(statement.connectives().get(i - 1).name()).append(' ');
			sql.append(column(layout, c.column())).append(' ').append(op(c.op())).append(' ');
			if (c.value() == null) {
				sql.append('0');
				continue;
			}
			VarRef ref = c.value();
			parts.add(sql.toString());
			sql.setLength(0);
			placeholders.add(new Placeholder(ref.let(), ref.name(), new KeyPath(lets.get(ref.let()).domain(), ref.path())));
		}
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

	static void collect(List<Step> steps, Map<Integer, Let> lets, List<Statement> statements) {
		for (Step step : steps) {
			if (step instanceof Let let)
				lets.put(let.id(), let);
			else if (step instanceof Statement s)
				statements.add(s);
			else
				collect(((Repeat) step).steps(), lets, statements);
		}
	}
}
