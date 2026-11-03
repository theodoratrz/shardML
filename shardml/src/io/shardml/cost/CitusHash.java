package io.shardml.cost;

/* Citus's bucket assignment for INT and BIGINT distribution columns, reproduced exactly.
 * Citus hashes a value with its type's PostgreSQL hash function (worker_hash): hashint8 for
 * BIGINT, which folds the high word into the low one, so an INT and a BIGINT of the same value
 * hash alike. The int32 hash space is split into equal ranges, one per shard, and the last
 * shard takes the remainder. Checked against worker_hash and get_shard_id_for_distribution_column
 * on Citus 14.2 for 10,000 keys at 32 and 6 shards (see CitusHashTest). */
public final class CitusHash implements KeyHasher {

	public static final CitusHash INSTANCE = new CitusHash();

	private static final long TOKEN_COUNT = 1L << 32;

	private CitusHash() {
	}

	@Override
	public int bucket(long key, int buckets) {
		return bucketOfHash(hashInt8(key), buckets);
	}

	// PostgreSQL hashint8: fold the high 32 bits into the low ones, then hash those
	public static int hashInt8(long value) {
		int low = (int) value;
		int high = (int) (value >>> 32);
		low ^= value >= 0 ? high : ~high;
		return hashUint32(low);
	}

	// PostgreSQL hash_bytes_uint32: Bob Jenkins' lookup3 final mix of one 32-bit word
	static int hashUint32(int k) {
		int a = 0x9e3779b9 + 4 + 3923095;
		int b = a;
		int c = a;
		a += k;
		c ^= b;
		c -= Integer.rotateLeft(b, 14);
		a ^= c;
		a -= Integer.rotateLeft(c, 11);
		b ^= a;
		b -= Integer.rotateLeft(a, 25);
		c ^= b;
		c -= Integer.rotateLeft(b, 16);
		a ^= c;
		a -= Integer.rotateLeft(c, 4);
		b ^= a;
		b -= Integer.rotateLeft(a, 14);
		c ^= b;
		c -= Integer.rotateLeft(b, 24);
		return c;
	}

	// the shard whose hash range holds the hash: ranges of floor(2^32 / buckets) tokens each,
	// starting at INT32_MIN, with the last range running on to INT32_MAX
	public static int bucketOfHash(int hash, int buckets) {
		if (buckets < 1)
			throw new IllegalArgumentException("buckets must be at least 1, got " + buckets);
		long increment = TOKEN_COUNT / buckets;
		long index = ((long) hash - Integer.MIN_VALUE) / increment;
		return (int) Math.min(index, buckets - 1);
	}
}
