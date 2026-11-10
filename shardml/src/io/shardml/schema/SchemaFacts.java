package io.shardml.schema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.shardml.schema.SqlTypes.SqlType;
import io.shardml.sql.Column;
import io.shardml.sql.Constraint;
import io.shardml.sql.NotNull;
import io.shardml.sql.PrimaryAnnotation;
import io.shardml.sql.PrimaryKey;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.sql.TableConstraint;

/* An EMF-free view of a .msql schema: what the cost model and the validators need to know about
 * tables and columns, read in one place. Column types come from SqlTypes, so a type that did not
 * parse is UNKNOWN here, never INT.
 *
 * This first version has columns, their types and nullability, and the primary key. Unique keys
 * and foreign keys join it with the grammar that declares them (Plan-sql, step 4). */
public final class SchemaFacts {

	private SchemaFacts() {
	}

	// nullable: no NOT NULL and not part of the primary key
	public record ColumnFacts(String name, SqlType type, boolean nullable) {
	}

	public record TableFacts(String name, List<ColumnFacts> columns, List<String> primaryKey) {

		public ColumnFacts column(String name) {
			return columns.stream().filter(c -> c.name().equals(name)).findFirst().orElse(null);
		}
	}

	// tables by name, in schema order; tables or columns without a name (a syntax error) are left out
	public static Map<String, TableFacts> of(Schema schema) {
		Map<String, TableFacts> tables = new LinkedHashMap<>();
		if (schema == null)
			return tables;
		for (Table table : schema.getTables()) {
			if (table.getName() == null)
				continue;
			List<String> primaryKey = primaryKey(table);
			List<ColumnFacts> columns = new ArrayList<>();
			for (Column column : table.getColumns()) {
				if (column.getName() == null)
					continue;
				boolean notNull = primaryKey.contains(column.getName())
						|| column.getConstraint().stream().anyMatch(NotNull.class::isInstance);
				columns.add(new ColumnFacts(column.getName(), SqlTypes.of(column), !notNull));
			}
			tables.putIfAbsent(table.getName(), new TableFacts(table.getName(), List.copyOf(columns), primaryKey));
		}
		return tables;
	}

	// a column-level PRIMARY KEY, or the columns of a table-level one
	private static List<String> primaryKey(Table table) {
		for (Column column : table.getColumns()) {
			for (Constraint constraint : column.getConstraint()) {
				if (constraint instanceof PrimaryAnnotation && column.getName() != null)
					return List.of(column.getName());
			}
		}
		for (TableConstraint constraint : table.getConstraints()) {
			if (constraint instanceof PrimaryKey key)
				return key.getColumns().stream().filter(c -> !c.eIsProxy()).map(Column::getName).toList();
		}
		return List.of();
	}
}
