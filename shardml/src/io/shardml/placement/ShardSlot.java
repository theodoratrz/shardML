package io.shardml.placement;

/* One shard of a table: bucket i is the i-th hash range, ordered by shardminvalue.
 * Colocated tables share their ranges, so bucket i lines up across a colocation group. */
public record ShardSlot(String table, int bucket) {
}
