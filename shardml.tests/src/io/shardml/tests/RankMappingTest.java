package io.shardml.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.HashTable;
import io.shardml.cost.KeyBuckets;
import io.shardml.cost.Layout;
import io.shardml.cost.RankMapping;
import io.shardml.cost.RowCounts;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.KeyPath;

// The hand-computable fixtures of Plan-phase3 6.1 that Step 5 can already compute: child counts and
// parents (T1, T17), rows per bucket (T2, T3, T17) and an `over` table (T13). With
// bucket(v) = (v - 1) mod 4, buckets 0 and 2 on n1, 1 and 3 on n2; zipf(1) over 4 is 0.48, 0.24, 0.16, 0.12.
public class RankMappingTest {

	private final CostFixtures f = new CostFixtures();

	private Workload workload() {
		f.load("s.msql", CostFixtures.SCHEMA);
		return f.workload("w.workload", CostFixtures.WORKLOAD);
	}

	@Test
	public void testT1ChildCountsAndParents() {
		RankMapping m = RankMapping.of(workload(), 0);
		// 8 * p = 3.84, 1.92, 1.28, 0.96 -> floors 3, 1, 1, 0; the 3 left over to .96, .92, .84 -> 4, 2, 1, 1
		Assertions.assertEquals(List.of(4L, 2L, 1L, 1L), List.of(m.childCount("account", 1), m.childCount("account", 2),
				m.childCount("account", 3), m.childCount("account", 4)));
		Assertions.assertEquals(List.of(1L, 5L, 7L, 8L), List.of(m.firstChild("account", 1), m.firstChild("account", 2),
				m.firstChild("account", 3), m.firstChild("account", 4)));
		Assertions.assertEquals(List.of(1L, 1L, 1L, 1L, 2L, 2L, 3L, 4L), parents(m));
		// children are never offset; rank = value
		Assertions.assertEquals(5, m.value("account", 5));
		Assertions.assertEquals(5, m.rank("account", 5));
	}

	@Test
	public void testT17OffsetMovesTheParentsNotTheChildren() {
		Workload w = workload();
		RankMapping m = RankMapping.of(w, 1);
		// customer ranks 1..4 are values 2, 3, 4, 1; parent rank 1 = customer 2 owns accounts 1-4
		Assertions.assertEquals(List.of(2L, 3L, 4L, 1L), List.of(m.value("customer", 1), m.value("customer", 2),
				m.value("customer", 3), m.value("customer", 4)));
		Assertions.assertEquals(List.of(2L, 2L, 2L, 2L, 3L, 3L, 4L, 1L), parents(m));
		Assertions.assertEquals(4, m.childCount("account", 2));
		Assertions.assertEquals(1, m.firstChild("account", 2));
		// value and rank are inverse for every offset, which is reduced mod size
		for (long k = 0; k <= 9; k++) {
			RankMapping mk = RankMapping.of(w, k);
			for (long r = 1; r <= 4; r++)
				Assertions.assertEquals(r, mk.rank("customer", mk.value("customer", r)), "offset " + k);
			Assertions.assertEquals(RankMapping.of(w, k % 4).value("customer", 1), mk.value("customer", 1));
		}
		Assertions.assertThrows(IllegalArgumentException.class, () -> RankMapping.of(w, -1));
	}

	private static List<Long> parents(RankMapping m) {
		List<Long> parents = new ArrayList<>();
		for (long a = 1; a <= 8; a++)
			parents.add(m.parent("account", a));
		return parents;
	}

	// rows per node of every hash table: RowCounts per key value, bucketed by MOD, placed by the layout
	private static Map<String, Long> rowsPerNode(Layout layout, Workload w, RankMapping m) {
		Map<String, Long> perNode = new TreeMap<>();
		for (var t : layout.tables()) {
			HashTable table = (HashTable) t;
			RowCounts.of(w, m, table.table()).forEachKeyValue(table.keyColumn(), (value, rows) -> perNode
					.merge(layout.nodeOf(table, CostFixtures.MOD.bucket(value, table.buckets())).name(), rows, Long::sum));
		}
		return perNode;
	}

	@Test
	public void testT2T3RowsPerNode() {
		Workload w = workload();
		Layout c = f.layout("c.shardml", CostFixtures.LAYOUT_C);
		Layout a = f.layout("a.shardml", CostFixtures.LAYOUT_A);
		RankMapping m = RankMapping.of(w, 0);
		// T2: customers 2 / 2; accounts by customer 4 + 1 on n1 (customers 1, 3), 2 + 1 on n2 -> 7 / 5
		Assertions.assertEquals(Map.of("n1", 7L, "n2", 5L), rowsPerNode(c, w, m));
		// T3: accounts by id, 1, 3, 5, 7 on n1 -> 6 / 6
		Assertions.assertEquals(Map.of("n1", 6L, "n2", 6L), rowsPerNode(a, w, m));
		// T17: offset 1 moves the big customer (rank 1 = customer 2) to n2 -> 5 / 7
		Assertions.assertEquals(Map.of("n1", 5L, "n2", 7L), rowsPerNode(c, w, RankMapping.of(w, 1)));
	}

	@Test
	public void testKeyBucketsAreBuiltOnceThroughTheProjection() {
		RankMapping m = RankMapping.of(workload(), 0);
		KeyPath path = new KeyPath("account", List.of("customer"));
		KeyBuckets buckets = m.buckets(path, CostFixtures.MOD, 4);
		// account -> its customer -> (customer - 1) mod 4
		List<Integer> actual = new ArrayList<>();
		for (long a = 1; a <= 8; a++)
			actual.add(buckets.bucket(a));
		Assertions.assertEquals(List.of(0, 0, 0, 0, 1, 1, 2, 3), actual);
		Assertions.assertSame(buckets, m.buckets(new KeyPath("account", List.of("customer")), CostFixtures.MOD, 4), "cached");
		Assertions.assertNotSame(buckets, m.buckets(path, CostFixtures.MOD, 2));
	}

	@Test
	public void testT13RowsOverADomain() {
		f.load("s.msql", CostFixtures.SCHEMA);
		Workload w = f.workload("w.workload", """
				import "s.msql"
				workload t {
				    domain customer { size: 4 }
				    domain account { size: 8 within customer uniform }
				    table customers { rows: per customer id: customer }
				    table accounts { rows: 6 over customer zipf(1) customer_id: customer unique id }
				}
				""");
		Map<Long, Long> rows = new TreeMap<>();
		RowCounts counts = RowCounts.of(w, RankMapping.of(w, 0), "accounts");
		counts.forEachKeyValue("customer_id", rows::put);
		// 6p = 2.88, 1.44, 0.96, 0.72 -> floors 2, 1, 0, 0; +1 to .96, .88, .72 -> 3, 1, 1, 1
		Assertions.assertEquals(Map.of(1L, 3L, 2L, 1L, 3L, 1L, 4L, 1L), rows);
		Assertions.assertEquals(6, counts.total());
		// a unique column: 1..6, one row each
		Map<Long, Long> ids = new TreeMap<>();
		counts.forEachKeyValue("id", ids::put);
		Assertions.assertEquals(Map.of(1L, 1L, 2L, 1L, 3L, 1L, 4L, 1L, 5L, 1L, 6L, 1L), ids);
		// offset 1: rank 1 (3 rows) is customer 2
		Map<Long, Long> offset = new TreeMap<>();
		RowCounts.of(w, RankMapping.of(w, 1), "accounts").forEachKeyValue("customer_id", offset::put);
		Assertions.assertEquals(Map.of(2L, 3L, 3L, 1L, 4L, 1L, 1L, 1L), offset);
	}

	@Test
	public void testRowsPerProductAndPerCount() {
		f.load("s.msql", """
				CREATE TABLE warehouse (w_id INT PRIMARY KEY)
				CREATE TABLE stock (s_w_id INT, s_i_id INT, PRIMARY KEY (s_w_id, s_i_id))
				""");
		Workload w = f.workload("w.workload", """
				import "s.msql"
				workload t {
				    domain warehouse { size: 3 }
				    domain item { size: 5 }
				    table warehouse { rows: 10 per warehouse w_id: warehouse }
				    table stock { rows: per warehouse, item s_w_id: warehouse s_i_id: item }
				}
				""");
		RankMapping m = RankMapping.of(w, 0);
		Map<Long, Long> byWarehouse = new TreeMap<>();
		RowCounts.of(w, m, "stock").forEachKeyValue("s_w_id", byWarehouse::put);
		Assertions.assertEquals(Map.of(1L, 5L, 2L, 5L, 3L, 5L), byWarehouse, "each warehouse carries |item| rows");
		Map<Long, Long> byItem = new TreeMap<>();
		RowCounts.of(w, m, "stock").forEachKeyValue("s_i_id", byItem::put);
		Assertions.assertEquals(Map.of(1L, 3L, 2L, 3L, 3L, 3L, 4L, 3L, 5L, 3L), byItem);
		Assertions.assertEquals(15, RowCounts.of(w, m, "stock").total());
		Assertions.assertEquals(30, RowCounts.of(w, m, "warehouse").total());
	}
}
