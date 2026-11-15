package io.shardml.cost;

import java.util.LinkedHashMap;
import java.util.Map;

import io.shardml.schema.SchemaFacts.ColumnFacts;
import io.shardml.schema.SchemaFacts.TableFacts;
import io.shardml.schema.SqlTypes.SqlType;

/* The tables and column types of a .msql schema, read through SchemaFacts: what the cross-input
 * checks compare between a layout's schema and a workload's (by name and type, not file
 * identity). Tables and columns in schema order. */
public record SchemaView(Map<String, Map<String, SqlType>> tables) {

	public SchemaView {
		Map<String, Map<String, SqlType>> copy = new LinkedHashMap<>();
		tables.forEach((table, columns) -> copy.put(table, java.util.Collections.unmodifiableMap(new LinkedHashMap<>(columns))));
		tables = java.util.Collections.unmodifiableMap(copy);
	}

	static SchemaView of(Map<String, TableFacts> facts) {
		Map<String, Map<String, SqlType>> tables = new LinkedHashMap<>();
		for (TableFacts table : facts.values()) {
			Map<String, SqlType> columns = new LinkedHashMap<>();
			for (ColumnFacts column : table.columns())
				columns.put(column.name(), column.type());
			tables.put(table.name(), columns);
		}
		return new SchemaView(tables);
	}

	// the type of a column, or null if the schema has no such table or column
	public SqlType type(String table, String column) {
		Map<String, SqlType> columns = tables.get(table);
		return columns == null ? null : columns.get(column);
	}
}
