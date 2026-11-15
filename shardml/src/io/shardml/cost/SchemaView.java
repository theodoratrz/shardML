package io.shardml.cost;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.shardml.schema.SchemaFacts.ColumnFacts;
import io.shardml.schema.SchemaFacts.TableFacts;
import io.shardml.schema.SqlTypes.SqlType;

/* The tables and column types of a .msql schema, read through SchemaFacts: what the cross-input
 * checks compare between a layout's schema and a workload's (by name and type, not file
 * identity). Tables and columns in schema order. keyColumns: per table, the columns that are part
 * of its primary key or of a unique key, in schema order (what a replayed write must not pick as
 * its payload column, StatementSql). */
public record SchemaView(Map<String, Map<String, SqlType>> tables, Map<String, List<String>> keyColumns) {

	public SchemaView {
		Map<String, Map<String, SqlType>> copy = new LinkedHashMap<>();
		tables.forEach((table, columns) -> copy.put(table, java.util.Collections.unmodifiableMap(new LinkedHashMap<>(columns))));
		tables = java.util.Collections.unmodifiableMap(copy);
		Map<String, List<String>> keys = new LinkedHashMap<>();
		keyColumns.forEach((table, columns) -> keys.put(table, List.copyOf(columns)));
		keyColumns = java.util.Collections.unmodifiableMap(keys);
	}

	static SchemaView of(Map<String, TableFacts> facts) {
		Map<String, Map<String, SqlType>> tables = new LinkedHashMap<>();
		Map<String, List<String>> keys = new LinkedHashMap<>();
		for (TableFacts table : facts.values()) {
			Map<String, SqlType> columns = new LinkedHashMap<>();
			for (ColumnFacts column : table.columns())
				columns.put(column.name(), column.type());
			tables.put(table.name(), columns);
			keys.put(table.name(), table.columns().stream().map(ColumnFacts::name)
					.filter(c -> table.primaryKey().contains(c) || table.uniqueKeys().stream().anyMatch(u -> u.contains(c))).toList());
		}
		return new SchemaView(tables, keys);
	}

	// the type of a column, or null if the schema has no such table or column
	public SqlType type(String table, String column) {
		Map<String, SqlType> columns = tables.get(table);
		return columns == null ? null : columns.get(column);
	}
}
