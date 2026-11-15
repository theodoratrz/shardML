package io.shardml.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.CitusHash;
import io.shardml.cost.KeyType;
import io.shardml.schema.SqlTypes.Base;

// The key types the cost model hashes (Plan-phase3 4.4a, decision 1): the 106 measured worker_hash
// values and shard buckets of smallint, int and bigint keys (resources/citus-hash-int-types.txt) as
// goldens for CitusHash, which hashes every key as int8.
public class KeyTypeTest {

	@Test
	public void testCitusHashMatchesTheMeasuredIntTypes() throws Exception {
		List<String> lines = Files.readAllLines(Path.of("resources", "citus-hash-int-types.txt")).stream()
				.filter(l -> !l.startsWith("#") && !l.isBlank()).toList();
		Assertions.assertEquals(106, lines.size());
		for (String line : lines) {
			String[] f = line.split(" ");
			long value = Long.parseLong(f[0]);
			int buckets = Integer.parseInt(f[2]);
			Assertions.assertEquals(Integer.parseInt(f[3]), CitusHash.hashInt8(value), line);
			Assertions.assertEquals(Integer.parseInt(f[4]), CitusHash.INSTANCE.bucket(value, buckets), line);
			// the value fits the column type it was measured with
			KeyType type = switch (f[1]) {
				case "smallint" -> KeyType.INT2;
				case "integer" -> KeyType.INT4;
				default -> KeyType.INT8;
			};
			Assertions.assertTrue(value >= type.min() && value <= type.max(), line);
		}
	}

	@Test
	public void testOnlyTheIntBasesAreKeyTypes() {
		for (Base base : Base.values()) {
			KeyType expected = switch (base) {
				case INT2 -> KeyType.INT2;
				case INT4 -> KeyType.INT4;
				case INT8 -> KeyType.INT8;
				default -> null;
			};
			Assertions.assertEquals(java.util.Optional.ofNullable(expected), KeyType.of(base), base.name());
		}
		Assertions.assertEquals(32767, KeyType.INT2.max());
		Assertions.assertEquals(-2147483648L, KeyType.INT4.min());
	}
}
