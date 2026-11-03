package io.shardml.cost;

/* Maps a distribution key value to its bucket, the i-th shard of the table by shardminvalue
 * (see io.shardml.placement.ShardSlot). The cost model uses CitusHash; tests inject a
 * hand-computable one so expected values can be checked by hand. */
public interface KeyHasher {

	// the bucket in [0, buckets) of a key value; buckets >= 1
	int bucket(long key, int buckets);
}
