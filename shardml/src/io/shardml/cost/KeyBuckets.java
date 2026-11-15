package io.shardml.cost;

/* The bucket of every value 1..size of a key path's domain, for one hasher and bucket count
 * (RankMapping.buckets): an immutable array, so enumeration is array loops. */
public final class KeyBuckets {

	private final int[] buckets;
	private final int bucketCount;

	KeyBuckets(int[] buckets, int bucketCount) {
		this.buckets = buckets;
		this.bucketCount = bucketCount;
	}

	public int size() {
		return buckets.length;
	}

	public int bucketCount() {
		return bucketCount;
	}

	// the bucket of value v, 1 <= v <= size
	public int bucket(long value) {
		return buckets[(int) value - 1];
	}
}
