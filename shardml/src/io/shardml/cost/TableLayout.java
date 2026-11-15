package io.shardml.cost;

/* A table as a layout distributes it. table: the stored name, as SchemaFacts and the workload name
 * it; qualified: SqlNames.qualified, as Placement keys its shards. */
public sealed interface TableLayout permits HashTable, ReferenceTable {

	String table();

	String qualified();
}
