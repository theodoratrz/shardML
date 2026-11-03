package io.shardml.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.CitusHash;
import io.shardml.cost.KeyHasher;

// Golden values read from Citus 14.2.0 / PostgreSQL 17.10 on the bench cluster:
// worker_hash(key::bigint), and the bucket (rank by shardminvalue) of
// get_shard_id_for_distribution_column for a table with that many shards
public class CitusHashTest {

	// key, worker_hash, bucket at 32 shards
	private static final long[][] GOLDEN_32 = {
			{ 0L, -272711505, 13 },
			{ 1L, -1905060026, 1 },
			{ 2L, 1134484726, 24 },
			{ 3L, -28094569, 15 },
			{ 42L, 1509752520, 27 },
			{ -1L, 385747274, 18 },
			{ 10000L, 1377887537, 26 },
			{ 2147483647L, -96758253, 15 },
			{ 123456789L, 524883300, 19 },
			{ 5000000000L, -694934712, 10 },
	};

	// key, bucket at 6 shards: 2^32 is not divisible by 6, so the last range is 4 tokens longer
	private static final long[][] GOLDEN_6 = {
			{ 1L, 0 },
			{ 2L, 4 },
			{ 3L, 2 },
			{ 7L, 1 },
			{ 42L, 5 },
			{ 100L, 3 },
			{ 9999L, 5 },
			{ 10000L, 4 },
	};

	@Test
	public void testHashMatchesWorkerHash() {
		for (long[] g : GOLDEN_32)
			Assertions.assertEquals((int) g[1], CitusHash.hashInt8(g[0]), "worker_hash(" + g[0] + ")");
	}

	@Test
	public void testBucketAt32Shards() {
		for (long[] g : GOLDEN_32)
			Assertions.assertEquals((int) g[2], CitusHash.INSTANCE.bucket(g[0], 32), "bucket of " + g[0] + " at 32 shards");
	}

	@Test
	public void testBucketAt6Shards() {
		for (long[] g : GOLDEN_6)
			Assertions.assertEquals((int) g[1], CitusHash.INSTANCE.bucket(g[0], 6), "bucket of " + g[0] + " at 6 shards");
	}

	@Test
	public void testBucketRangeEnds() {
		Assertions.assertEquals(0, CitusHash.bucketOfHash(Integer.MIN_VALUE, 32));
		Assertions.assertEquals(31, CitusHash.bucketOfHash(Integer.MAX_VALUE, 32));
		// 32 shards split evenly: shard 16 starts at hash 0
		Assertions.assertEquals(15, CitusHash.bucketOfHash(-1, 32));
		Assertions.assertEquals(16, CitusHash.bucketOfHash(0, 32));
		// 6 shards of 715827882 tokens; the hashes past 6 full ranges belong to the last shard
		Assertions.assertEquals(5, CitusHash.bucketOfHash(Integer.MAX_VALUE, 6));
		Assertions.assertEquals(1, CitusHash.bucketOfHash(Integer.MIN_VALUE + 715827882, 6));
		Assertions.assertEquals(0, CitusHash.bucketOfHash(Integer.MIN_VALUE + 715827881, 6));
		Assertions.assertEquals(0, CitusHash.INSTANCE.bucket(42L, 1));
	}

	@Test
	public void testRejectsNoBuckets() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> CitusHash.INSTANCE.bucket(1L, 0));
	}

	@Test
	public void testInjectableHasher() {
		// the hand-computable hasher of the cost model's fixtures: key v goes to bucket (v - 1) mod B
		KeyHasher mod = (key, buckets) -> (int) Math.floorMod(key - 1, (long) buckets);
		Assertions.assertArrayEquals(new int[] { 0, 1, 2, 3, 0 },
				new int[] { mod.bucket(1, 4), mod.bucket(2, 4), mod.bucket(3, 4), mod.bucket(4, 4), mod.bucket(5, 4) });
	}
}
