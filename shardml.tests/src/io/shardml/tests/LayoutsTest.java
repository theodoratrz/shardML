package io.shardml.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.HashTable;
import io.shardml.cost.KeyType;
import io.shardml.cost.Layout;
import io.shardml.cost.ReferenceTable;
import io.shardml.placement.Node;
import io.shardml.placement.ShardSlot;

// Layouts.of: the tables of a model as the cost model reads them, and Layout.withPlacement.
public class LayoutsTest {

	private final CostFixtures f = new CostFixtures();

	@Test
	public void testBankingExample() throws Exception {
		f.load("banking.msql", Files.readString(Path.of("..", "examples", "banking.msql")));
		Layout layout = f.layout("banking.shardml", Files.readString(Path.of("..", "examples", "banking.shardml")));
		Assertions.assertEquals("banking", layout.name());
		Assertions.assertEquals(List.of("w2", "w3", "w1"), layout.nodes().stream().map(Node::name).toList());
		Assertions.assertEquals(List.of(
				new HashTable("customers", "customers", "id", KeyType.INT8, "customers", 32),
				new HashTable("accounts", "accounts", "customer_id", KeyType.INT8, "customers", 32),
				new HashTable("transactions", "transactions", "customer_id", KeyType.INT8, "customers", 32),
				new HashTable("cards", "cards", "customer_id", KeyType.INT8, "customers", 32)), layout.tables());
		Assertions.assertEquals(List.of("customers"), layout.groups());
		// the layout's node of a shard is the generated placement's
		HashTable accounts = (HashTable) layout.table("accounts").orElseThrow();
		for (int b = 0; b < 32; b++)
			Assertions.assertEquals(layout.placement().nodeOf("accounts", b), layout.nodeOf(accounts, b));
		Assertions.assertTrue(layout.schema().tables().containsKey("customers"));
	}

	@Test
	public void testReferenceTablesKeyTypesAndQualifiedNames() {
		f.load("s.msql", """
				CREATE TABLE shop.orders (customer_id SMALLINT, id INT, PRIMARY KEY (customer_id, id))
				CREATE TABLE shop.items (id SERIAL PRIMARY KEY)
				CREATE TABLE shop."Countries" (code TEXT PRIMARY KEY)
				""");
		Layout layout = f.layout("l.shardml", "import \"s.msql\"\ndatabase l {\n    type: postgres\n" + CostFixtures.NODES + """
				    shard orders { strategy: hash key: customer_id buckets: 4 }
				    shard items { strategy: hash key: id buckets: 6 }
				    shard Countries { strategy: replicated }
				}
				""");
		Assertions.assertEquals(List.of(
				new HashTable("orders", "shop.orders", "customer_id", KeyType.INT2, "orders", 4),
				new HashTable("items", "shop.items", "id", KeyType.INT4, "items", 6),
				new ReferenceTable("Countries", "shop.\"Countries\"")), layout.tables());
		Assertions.assertEquals(List.of("orders", "items"), layout.groups());
	}

	@Test
	public void testWithPlacement() {
		f.load("s.msql", CostFixtures.SCHEMA);
		Layout layout = f.layout("c.shardml", CostFixtures.LAYOUT_C);
		Node n1 = layout.nodes().get(0);
		Node n2 = layout.nodes().get(1);
		HashTable customers = (HashTable) layout.table("customers").orElseThrow();
		// everything on n2
		Map<ShardSlot, Node> all = new LinkedHashMap<>();
		layout.placement().assignments().keySet().forEach(slot -> all.put(slot, n2));
		Layout moved = layout.withPlacement(all);
		Assertions.assertEquals(n1, layout.nodeOf(customers, 0));
		Assertions.assertEquals(n2, moved.nodeOf(customers, 0));
		Assertions.assertEquals(layout.tables(), moved.tables());
		Assertions.assertEquals(layout.nodes(), moved.nodes());

		Map<ShardSlot, Node> missing = new LinkedHashMap<>(all);
		missing.remove(new ShardSlot("accounts", 3));
		Assertions.assertEquals("The placement does not place bucket 3 of accounts.",
				Assertions.assertThrows(IllegalArgumentException.class, () -> layout.withPlacement(missing)).getMessage());
		Map<ShardSlot, Node> extra = new LinkedHashMap<>(all);
		extra.put(new ShardSlot("accounts", 4), n1);
		Assertions.assertEquals("The placement places bucket 4 of accounts, which the layout does not have.",
				Assertions.assertThrows(IllegalArgumentException.class, () -> layout.withPlacement(extra)).getMessage());
		Map<ShardSlot, Node> stranger = new LinkedHashMap<>(all);
		stranger.put(new ShardSlot("customers", 0), new Node("n3", "h3", 5432));
		Assertions.assertEquals("The placement puts bucket 0 of customers on n3 (h3:5432), which the layout does not declare.",
				Assertions.assertThrows(IllegalArgumentException.class, () -> layout.withPlacement(stranger)).getMessage());
	}
}
