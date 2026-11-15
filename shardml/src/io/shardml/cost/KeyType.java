package io.shardml.cost;

import java.util.Optional;

import io.shardml.schema.SqlTypes.Base;

/* The distribution key types whose Citus hash CitusHash reproduces, with their value ranges.
 * Measured on Citus 14.2 (Plan-phase3 4.4a): worker_hash and the shard of
 * get_shard_id_for_distribution_column for smallint, int and bigint at negatives, 0 and each type's
 * extremes, at 32 and 6 shards: 106 checks, 0 mismatches with CitusHash, and the three types never
 * disagree on an in-range value (hashint2 and hashint4 hash the int32, hashint8 folds an in-range
 * value to the same int32). SERIAL and BIGSERIAL are INT4 and INT8. */
public enum KeyType {
	INT2(Short.MIN_VALUE, Short.MAX_VALUE),
	INT4(Integer.MIN_VALUE, Integer.MAX_VALUE),
	INT8(Long.MIN_VALUE, Long.MAX_VALUE);

	private final long min;
	private final long max;

	KeyType(long min, long max) {
		this.min = min;
		this.max = max;
	}

	public long min() {
		return min;
	}

	public long max() {
		return max;
	}

	// empty for every other base, UNKNOWN (a type that did not parse) included
	public static Optional<KeyType> of(Base base) {
		return switch (base) {
			case INT2 -> Optional.of(INT2);
			case INT4 -> Optional.of(INT4);
			case INT8 -> Optional.of(INT8);
			default -> Optional.empty();
		};
	}
}
