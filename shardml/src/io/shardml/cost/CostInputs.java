package io.shardml.cost;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.shardml.cost.Workload.ColumnMapping;
import io.shardml.cost.Workload.ColumnRef;
import io.shardml.cost.Workload.Condition;
import io.shardml.cost.Workload.Join;
import io.shardml.cost.Workload.JoinOn;
import io.shardml.cost.Workload.Mapped;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.TableData;
import io.shardml.cost.Workload.Transaction;
import io.shardml.schema.SqlTypes.SqlType;

/* The checks between a layout and a workload, which involve both files and so are no SHML codes:
 * thrown as IllegalArgumentException with a message naming both. In order:
 *  X1 the schemas agree, by name and type, on every table and column the workload names, and on
 *     the layout's key columns (whole-file identity is not required);
 *  X5 every table a statement touches is distributed by the layout (hash or replicated): local,
 *     coordinator-only tables are not modelled;
 *  X2 every hash table of the layout has a table block (data skew needs its rows);
 *  X3 every shard key is mapped in its table block (to a domain path, or unique);
 *  X4 the key's values (1..domain size, or 1..rows for a unique column) fit its type. */
public final class CostInputs {

	private CostInputs() {
	}

	public static void check(Layout layout, Workload workload) {
		// X1
		for (ColumnRef use : uses(workload)) {
			if (layout.schema().tables().get(use.table()) == null)
				throw new IllegalArgumentException("Table '" + use.table() + "' is in the workload's schema but not in the layout's.");
			if (use.column() == null)
				continue;
			SqlType ours = layout.schema().type(use.table(), use.column());
			if (ours == null)
				throw new IllegalArgumentException(
						"Column " + use.table() + "." + use.column() + " is in the workload's schema but not in the layout's.");
			SqlType theirs = workload.schema().type(use.table(), use.column());
			mismatch(use.table(), use.column(), ours, theirs);
		}
		for (TableLayout table : layout.tables()) {
			if (table instanceof HashTable hash) {
				SqlType theirs = workload.schema().type(hash.table(), hash.keyColumn());
				if (theirs != null)
					mismatch(hash.table(), hash.keyColumn(), layout.schema().type(hash.table(), hash.keyColumn()), theirs);
			}
		}
		// X5
		for (String table : statementTables(workload)) {
			if (layout.table(table).isEmpty())
				throw new IllegalArgumentException("Workload '" + workload.name() + "' uses table '" + table + "', which layout '"
						+ layout.name() + "' does not distribute (no shard declaration); the cost model needs every table a "
						+ "statement touches to be hash-distributed or replicated.");
		}
		for (TableLayout table : layout.tables()) {
			if (!(table instanceof HashTable hash))
				continue;
			// X2
			TableData data = workload.table(hash.table()).orElseThrow(() -> new IllegalArgumentException("Table '"
					+ hash.table() + "' is hash-distributed in '" + layout.name() + "' but has no table block in workload '"
					+ workload.name() + "'; data skew needs its rows."));
			// X3
			ColumnMapping mapping = data.column(hash.keyColumn()).orElseThrow(() -> new IllegalArgumentException("Shard key "
					+ hash.table() + "." + hash.keyColumn() + " of '" + layout.name() + "' has no mapping in the table block of '"
					+ hash.table() + "' in workload '" + workload.name() + "'; map it to a domain (" + hash.keyColumn()
					+ ": <domain>) or declare it unique."));
			// X4
			long max = mapping instanceof Mapped mapped ? workload.domain(mapped.path().target()).size()
					: RowCounts.total(workload, data);
			if (max > hash.keyType().max())
				throw new IllegalArgumentException("Shard key " + hash.table() + "." + hash.keyColumn() + " is "
						+ layout.schema().type(hash.table(), hash.keyColumn()).spelling() + ", but its values in workload '"
						+ workload.name() + "' run up to " + max + " ("
						+ (mapping instanceof Mapped mapped ? "domain '" + mapped.path().target() + "'" : "unique row ids")
						+ "); they do not fit.");
		}
	}

	private static void mismatch(String table, String column, SqlType ours, SqlType theirs) {
		if (theirs != null && ours != null && ours.base() != theirs.base())
			throw new IllegalArgumentException("Column " + table + "." + column + " is " + ours.spelling()
					+ " in the layout's schema but " + theirs.spelling() + " in the workload's.");
	}

	// every table (column null) and column the workload names, in file order, once each
	private static Set<ColumnRef> uses(Workload workload) {
		Set<ColumnRef> uses = new LinkedHashSet<>();
		for (TableData data : workload.tables()) {
			uses.add(new ColumnRef(data.table(), null));
			for (ColumnMapping mapping : data.columns())
				uses.add(new ColumnRef(data.table(), mapping.column()));
		}
		for (Statement statement : statements(workload)) {
			uses.add(new ColumnRef(statement.table(), null));
			for (Join join : statement.joins()) {
				uses.add(new ColumnRef(join.table(), null));
				for (JoinOn on : join.on()) {
					uses.add(on.left());
					uses.add(on.right());
				}
			}
			for (Condition condition : statement.conditions())
				uses.add(condition.column());
		}
		return uses;
	}

	private static Set<String> statementTables(Workload workload) {
		Set<String> tables = new LinkedHashSet<>();
		for (Statement statement : statements(workload)) {
			tables.add(statement.table());
			for (Join join : statement.joins())
				tables.add(join.table());
		}
		return tables;
	}

	private static List<Statement> statements(Workload workload) {
		List<Statement> out = new ArrayList<>();
		for (Transaction transaction : workload.transactions())
			collect(transaction.steps(), out);
		return out;
	}

	private static void collect(List<Step> steps, List<Statement> out) {
		for (Step step : steps) {
			if (step instanceof Statement statement)
				out.add(statement);
			else if (step instanceof Repeat repeat)
				collect(repeat.steps(), out);
		}
	}
}
