package io.shardml.cost;

/* A hash-distributed table: its shard key, the key's type, its colocation group (the root
 * table's stored name) and the group's bucket count. */
public record HashTable(String table, String qualified, String keyColumn, KeyType keyType, String group, int buckets)
		implements TableLayout {
}
