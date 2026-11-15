package io.shardml.bench;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.Inputs;
import io.shardml.cost.CitusHash;
import io.shardml.cost.CostModel;
import io.shardml.cost.HashTable;
import io.shardml.cost.Layout;
import io.shardml.cost.RankMapping;
import io.shardml.cost.SkewAndLoad;
import io.shardml.cost.Workload;
import io.shardml.schema.SqlTypes;

/* The loader's rows, without a cluster: the generated COPY rows, hashed with CitusHash and placed by the
 * layout, give exactly the model's data-skew rows per node (what the cluster check compares); unique and
 * unmapped key columns keep every key unique; chunks cover every row once. */
public class LoaderTest {

	// every row of every chunk, split into fields
	private static void forEachRow(Workload w, Layout layout, RankMapping mapping, Loader.Chunk chunk, java.util.function.Consumer<String[]> row) {
		Loader.rows(w, layout, mapping, chunk, block -> {
			for (String line : block.toString().split("\n"))
				row.accept(line.split("\t", -1));
		});
	}

	private static void check(Inputs.Loaded loaded, long offset) {
		Workload w = loaded.workload();
		RankMapping mapping = RankMapping.of(w, offset);
		for (Layout layout : loaded.layouts()) {
			SkewAndLoad model = new CostModel().skewAndLoad(layout, w, offset);
			List<Loader.Chunk> chunks = Loader.chunks(w, layout, mapping);
			Map<String, Map<String, Long>> perNode = new HashMap<>();
			Map<String, Set<String>> keys = new HashMap<>();
			Map<String, Long> rows = new HashMap<>();
			for (Loader.Chunk chunk : chunks) {
				Assertions.assertTrue(chunk.rows() <= Loader.CHUNK_ROWS || chunk.endGroup() - chunk.firstGroup() == 1);
				String table = chunk.table().table();
				List<String> pk = layout.schema().keyColumns().get(table);
				int[] keyIndex = pk.stream().mapToInt(chunk.columns()::indexOf).toArray();
				HashTable hash = layout.table(table).orElseThrow() instanceof HashTable h ? h : null;
				int keyColumn = hash == null ? -1 : chunk.columns().indexOf(hash.keyColumn());
				long[] n = { 0 };
				forEachRow(w, layout, mapping, chunk, f -> {
					n[0]++;
					StringBuilder key = new StringBuilder();
					for (int i : keyIndex)
						key.append(f[i]).append('|');
					Assertions.assertTrue(keys.computeIfAbsent(table, k -> new HashSet<>()).add(key.toString()), table + " key " + key);
					if (hash != null) {
						int bucket = CitusHash.INSTANCE.bucket(Long.parseLong(f[keyColumn]), hash.buckets());
						perNode.computeIfAbsent(table, k -> new HashMap<>()).merge(layout.nodeOf(hash, bucket).name(), 1L, Long::sum);
					}
				});
				Assertions.assertEquals(chunk.rows(), n[0], table);
				rows.merge(table, n[0], Long::sum);
			}
			for (SkewAndLoad.TableRows t : model.data().tables()) {
				for (SkewAndLoad.NodeRows r : t.rowsPerNode()) {
					long expected = r.rows();
					long actual = t.reference() ? rows.getOrDefault(t.table(), 0L) : perNode.getOrDefault(t.table(), Map.of()).getOrDefault(r.node(), 0L);
					Assertions.assertEquals(expected, actual, w.name() + " " + layout.name() + " " + t.table() + " on " + r.node());
				}
			}
		}
	}

	@Test
	public void testRowsGiveTheModelsDataSkew() {
		check(Examples.load(Examples.COST + "../../bench/driver-smoke/banking-smoke.workload", Examples.COST + "banking-by-customer.shardml",
				Examples.COST + "banking-by-account.shardml"), 0);
		// zipf children, an `over` table and a rank offset, scaled down
		check(Examples.load(Examples.COST + "../../bench/integration/loader/banking-small.workload",
				Examples.COST + "banking-by-customer.shardml", Examples.COST + "banking-by-account.shardml"), 7);
		// several row domains (stock per warehouse, item), unmapped key columns (district, customer, ...), a reference table
		check(Examples.load(Examples.COST + "../../bench/integration/loader/tpcc-small.workload",
				Examples.COST + "tpcc-by-warehouse.shardml", Examples.COST + "tpcc-stock-by-item.shardml"), 0);
	}

	@Test
	public void testColumnValues() {
		Inputs.Loaded b = Examples.load(Examples.COST + "../../bench/driver-smoke/banking-smoke.workload", Examples.COST + "banking-by-customer.shardml");
		Workload w = b.workload();
		Layout layout = b.layouts().get(0);
		RankMapping mapping = RankMapping.of(w, 0);
		Loader.Chunk accounts = Loader.chunks(w, layout, mapping).stream().filter(c -> c.table().table().equals("accounts")).findFirst()
				.orElseThrow();
		Assertions.assertEquals(List.of("id", "customer_id", "balance"), accounts.columns());
		List<String> first = new java.util.ArrayList<>();
		forEachRow(w, layout, mapping, accounts, f -> {
			if (first.size() < 11)
				first.add(String.join(",", f));
		});
		// 10 accounts per customer, contiguous; balance (no mapping, no key) a constant
		Assertions.assertEquals("1,1,0", first.get(0));
		Assertions.assertEquals("10,1,0", first.get(9));
		Assertions.assertEquals("11,2,0", first.get(10));
		Assertions.assertEquals("x", Loader.constant(new SqlTypes.SqlType(SqlTypes.Base.TEXT, null, null, "TEXT")));
		Assertions.assertEquals("\\N", Loader.constant(null));
	}
}
