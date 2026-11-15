package io.shardml.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.RouteCommand;
import io.shardml.cost.RankMapping;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.Draw;

// `shardml route` (Plan-phase3 4.5b): deterministic, every statement shape, the sampling.
public class RouteCommandTest {

	private static final Path COST = Path.of("..", "examples", "cost");
	private static final String SHAPES = Path.of("..", "bench", "integration", "cost-routing", "shapes.workload").toString();

	private record Run(int exit, String out, String err) {
	}

	private static Run run(String... args) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		int exit = RouteCommand.run(List.of(args), new PrintStream(out, true, StandardCharsets.UTF_8),
				new PrintStream(err, true, StandardCharsets.UTF_8));
		return new Run(exit, out.toString(StandardCharsets.UTF_8), err.toString(StandardCharsets.UTF_8));
	}

	private static String file(String name) {
		return COST.resolve(name).toString();
	}

	@Test
	public void testDeterministicAndEveryShape() {
		Set<String> shapes = new TreeSet<>();
		for (String[] args : List.of(new String[] { file("tpcc-stock-by-item.shardml"), file("tpcc.workload") },
				new String[] { file("tpcc-by-warehouse.shardml"), SHAPES },
				new String[] { file("tpcc-stock-by-item.shardml"), SHAPES },
				new String[] { file("banking-by-account.shardml"), file("banking.workload") })) {
			Run a = run(args);
			Assertions.assertEquals(0, a.exit(), a.err());
			Assertions.assertEquals(a.out(), run(args).out(), "deterministic");
			for (String line : a.out().split("\n")) {
				String[] f = line.split("\t");
				Assertions.assertEquals(7, f.length, line);
				shapes.add(f[3]);
			}
		}
		Assertions.assertEquals(Set.of("routed", "unrouted", "reference-read", "reference-write", "repartition"), shapes);
	}

	@Test
	public void testPredictionsAndSql() {
		String w = run(file("tpcc-by-warehouse.shardml"), SHAPES).out();
		// a reference write commits on every node; a reference join with a routed table goes to that table's node;
		// a colocated join without a key condition touches every bucket; a delete is written as a delete
		Assertions.assertTrue(w.contains("reference_write\t1\t1\treference-write\t1\tworker1:5432,worker2:5432,worker3:5432\t"
				+ "UPDATE item SET i_price = i_price WHERE item.i_id = 1\n"), w);
		Assertions.assertTrue(w.contains("reference_join\t1\t1\trouted\t1\tworker3:5432\tSELECT * FROM order_line JOIN item ON "
				+ "order_line.ol_i_id = item.i_id WHERE order_line.ol_w_id = 1\n"), w);
		Assertions.assertTrue(w.contains("colocated_unrouted\t1\t1\tunrouted\t32\tworker1:5432,worker2:5432,worker3:5432\tSELECT * "
				+ "FROM order_line JOIN stock ON order_line.ol_w_id = stock.s_w_id AND order_line.ol_i_id = stock.s_i_id WHERE "
				+ "order_line.ol_i_id > 0\n"), w);
		Assertions.assertTrue(w.contains("delete_routed\t1\t1\trouted\t1\tworker3:5432\tDELETE FROM new_order WHERE "
				+ "new_order.no_w_id = 1\n"), w);
		String s = run(file("tpcc-stock-by-item.shardml"), SHAPES).out();
		Assertions.assertTrue(s.contains("colocated_unrouted\t1\t1\trepartition\t-\t-\t"), s);
		// a projection: the account's customer, consistent with the account
		String b = run("--samples", "1", file("banking-by-customer.shardml"), file("banking.workload")).out();
		Assertions.assertTrue(b.contains("history\t1\t1\trouted\t1\t"), b);
		Assertions.assertTrue(b.contains("SELECT * FROM transactions WHERE transactions.customer_id = 1 AND transactions.account_id = 1\n"), b);
		// --keys: N keys per key type with the model's bucket and node
		String keys = run("--keys", "4", file("tpcc-by-warehouse.shardml"), file("tpcc.workload")).out();
		Assertions.assertEquals(4, keys.split("\n").length, keys);
		Assertions.assertTrue(keys.startsWith("warehouse\t1\t1\tworker3:5432\nwarehouse\t2\t24\tworker2:5432\n"), keys);
	}

	@Test
	public void testSamplingIncludesHottestAndExtremes() {
		CostFixtures f = new CostFixtures();
		f.load("s.msql", CostFixtures.SCHEMA);
		Workload w = f.workload("w.workload", CostFixtures.WORKLOAD);
		Draw c = (Draw) w.transactions().get(0).steps().get(0);
		// customer zipf(1) over 4: hottest ranks 1, 2, 3 are values 1, 2, 3 at offset 0, then 1 (already) and 4
		Assertions.assertEquals(List.of(1L, 2L, 3L, 4L), RouteCommand.samples(c, RankMapping.of(w, 0), 4, 7).subList(0, 4));
		// offset 1: ranks 1, 2, 3 are values 2, 3, 4; then the smallest, 1
		Assertions.assertEquals(List.of(2L, 3L, 4L, 1L), RouteCommand.samples(c, RankMapping.of(w, 1), 4, 7));
		// longer lists continue with seeded draws within the domain, the same for the same seed
		List<Long> ten = RouteCommand.samples(c, RankMapping.of(w, 0), 10, 7);
		Assertions.assertEquals(10, ten.size());
		Assertions.assertTrue(ten.stream().allMatch(v -> v >= 1 && v <= 4));
		Assertions.assertEquals(ten, RouteCommand.samples(c, RankMapping.of(w, 0), 10, 7));
	}

	@Test
	public void testUsage() {
		Assertions.assertEquals(2, run(file("banking.workload")).exit());
		Assertions.assertEquals(2, run("--samples", "0", file("banking-by-customer.shardml"), file("banking.workload")).exit());
		Assertions.assertEquals(2, run("--frob", file("banking-by-customer.shardml"), file("banking.workload")).exit());
		Run unknown = run("--set", "nope=1", file("banking-by-customer.shardml"), file("banking.workload"));
		Assertions.assertEquals(1, unknown.exit());
		Assertions.assertTrue(unknown.err().contains("shardml route: error: --set nope:"), unknown.err());
	}
}
