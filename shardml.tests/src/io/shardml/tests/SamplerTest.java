package io.shardml.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.Layout;
import io.shardml.cost.Marginals;
import io.shardml.cost.RankMapping;
import io.shardml.cost.Sampler;
import io.shardml.cost.StatementSql;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Transaction;

/* The workload sampler (Phase 4 P4.1) and the statement templates it feeds: the sampler draws from
 * the model's own pmf (Marginals), applies the rank offset and projections through RankMapping, and
 * implements distinct from / else with / repeat as the model defines them; the templates are the SQL
 * `shardml route` prints, with `?` where route writes a value. */
public class SamplerTest {

	static final String WORKLOAD = """
			import "s.msql"
			workload t {
			    domain customer { size: 6 }
			    domain account { size: 30 within customer zipf(1) }
			    table customers { rows: per customer id: customer }
			    table accounts { rows: per account id: account customer_id: account.customer }
			    transaction mix weight 1 {
			        let c = customer zipf(1.2)
			        let a = account uniform
			        let d = customer zipf(1) distinct from $a.customer
			        let e = $c, else with 0.3: customer uniform
			        read customers where id = $d
			        repeat 3 {
			            let b = account zipf(0.5)
			            write accounts where customer_id = $b.customer AND id = $b
			        }
			        read customers where id = $e OR id = ?
			    }
			}
			""";

	private static Workload workload(CostFixtures f) {
		return f.workloadWith(CostFixtures.SCHEMA, WORKLOAD, java.util.Map.of());
	}

	@Test
	public void testCdfIsThePmf() {
		Workload w = workload(new CostFixtures());
		Transaction t = w.transactions().get(0);
		for (long offset : new long[] { 0, 2, 5 }) {
			RankMapping mapping = RankMapping.of(w, offset);
			Sampler sampler = Sampler.of(w, mapping);
			Marginals marginals = Marginals.of(w, mapping, t);
			// lets c (0) and a (1) are plain draws: their marginal is the draw's pmf
			for (Object[] c : new Object[][] { { 0, "customer", new Workload.Zipf(1.2) }, { 1, "account", new Workload.Uniform() } }) {
				String domain = (String) c[1];
				double[] cdf = sampler.cdf(domain, (Workload.Dist) c[2]);
				Assertions.assertEquals(mapping.size(domain), cdf.length);
				Assertions.assertEquals(1.0, cdf[cdf.length - 1]);
				for (int r = 1; r <= cdf.length; r++) {
					double mass = cdf[r - 1] - (r == 1 ? 0 : cdf[r - 2]);
					double p = marginals.probability((Integer) c[0], mapping.value(domain, r));
					Assertions.assertEquals(p, mass, 1e-15, domain + " rank " + r + " offset " + offset);
				}
			}
		}
	}

	@Test
	public void testDrawsMatchTheMarginals() {
		Workload w = workload(new CostFixtures());
		Transaction t = w.transactions().get(0);
		RankMapping mapping = RankMapping.of(w, 2);
		Sampler sampler = Sampler.of(w, mapping);
		Marginals marginals = Marginals.of(w, mapping, t);
		int n = 200_000;
		long[][] counts = new long[5][31];
		int[] differ = { 0 };
		KeyPath up = new KeyPath("account", List.of("customer"));
		SplittableRandom random = new SplittableRandom(42);
		for (int i = 0; i < n; i++) {
			sampler.run("mix", random, new Sampler.Visitor() {
				long[] last = new long[5];

				@Override
				public void let(int id, long value) {
					if (id < 4 || last[4] == 0)
						counts[id][(int) value]++;
					last[id] = value;
				}

				@Override
				public void statement(int index, long[] values) {
					if (index == 0) {
						// distinct from never returns its base; else with differs exactly when it draws
						Assertions.assertNotEquals(mapping.project(up, values[1]), values[2]);
						if (values[3] != values[0])
							differ[0]++;
					}
				}
			});
		}
		for (int let = 0; let < 5; let++) {
			String domain = let == 1 || let == 4 ? "account" : "customer";
			for (long v = 1; v <= mapping.size(domain); v++) {
				double p = marginals.probability(let, v);
				double sigma = Math.sqrt(n * p * (1 - p));
				Assertions.assertEquals(n * p, counts[let][(int) v], 5 * sigma + 1, "let " + let + " value " + v);
			}
		}
		Assertions.assertEquals(0.3 * n, differ[0], 5 * Math.sqrt(n * 0.3 * 0.7));
	}

	@Test
	public void testRepeatAndOrderAndDeterminism() {
		Workload w = workload(new CostFixtures());
		Sampler sampler = Sampler.of(w, RankMapping.of(w, 0));
		Assertions.assertEquals(5, sampler.lets("mix"));
		List<String> a = trace(sampler, 7);
		Assertions.assertEquals(trace(sampler, 7), a, "same seed, same draws");
		Assertions.assertNotEquals(trace(sampler, 8), a);
		// lets 0..3, statement 0, then three iterations of (let 4, statement 1), then statement 2
		List<String> shape = a.stream().map(s -> s.substring(0, s.indexOf('='))).toList();
		Assertions.assertEquals(List.of("let0", "let1", "let2", "let3", "st0", "let4", "st1", "let4", "st1", "let4", "st1", "st2"), shape);
		// a Sampler of another rank offset draws other values from the same stream, never another sequence shape
		Sampler shifted = Sampler.of(w, RankMapping.of(w, 1));
		Assertions.assertEquals(shape, trace(shifted, 7).stream().map(s -> s.substring(0, s.indexOf('='))).toList());
	}

	private static List<String> trace(Sampler sampler, long seed) {
		List<String> out = new ArrayList<>();
		sampler.run("mix", new SplittableRandom(seed), new Sampler.Visitor() {
			@Override
			public void let(int id, long value) {
				out.add("let" + id + "=" + value);
			}

			@Override
			public void statement(int index, long[] values) {
				out.add("st" + index + "=" + java.util.Arrays.toString(values));
			}
		});
		return out;
	}

	@Test
	public void testTemplates() {
		CostFixtures f = new CostFixtures();
		Workload w = workload(f);
		Layout c = f.layout("c.shardml", CostFixtures.LAYOUT_C);
		Layout a = f.layout("a.shardml", CostFixtures.LAYOUT_A);
		RankMapping mapping = RankMapping.of(w, 0);
		List<StatementSql.Template> tc = StatementSql.templates(w.transactions().get(0), c, mapping);
		List<StatementSql.Template> ta = StatementSql.templates(w.transactions().get(0), a, mapping);
		Assertions.assertEquals(3, tc.size());
		Assertions.assertEquals("SELECT * FROM customers WHERE customers.id = ?", tc.get(0).sql());
		// accounts has no column outside its keys: the first column that is not the distribution column, which
		// depends on the layout
		Assertions.assertEquals("UPDATE accounts SET id = id WHERE accounts.customer_id = ? AND accounts.id = ?", tc.get(1).sql());
		Assertions.assertEquals("UPDATE accounts SET customer_id = customer_id WHERE accounts.customer_id = ? AND accounts.id = ?",
				ta.get(1).sql());
		Assertions.assertEquals("SELECT * FROM customers WHERE customers.id = ? OR customers.id = 0", tc.get(2).sql());
		StatementSql.Template write = tc.get(1);
		Assertions.assertEquals(List.of(new StatementSql.Placeholder(4, "b", new KeyPath("account", List.of("customer"))),
				new StatementSql.Placeholder(4, "b", new KeyPath("account", List.of()))), write.placeholders());
		long[] lets = { 1, 1, 1, 1, 30 };
		long[] parameters = write.parameters(lets);
		Assertions.assertArrayEquals(new long[] { mapping.parent("account", 30), 30 }, parameters);
		Assertions.assertEquals("UPDATE accounts SET id = id WHERE accounts.customer_id = " + parameters[0] + " AND accounts.id = 30",
				write.inline(parameters));
		// a payload column outside every key is chosen whatever the layout
		CostFixtures f2 = new CostFixtures();
		Workload w2 = f2.workloadWith("""
				CREATE TABLE customers (id BIGINT PRIMARY KEY, name TEXT)
				CREATE TABLE accounts (id BIGINT, customer_id BIGINT, balance NUMERIC(12, 2), PRIMARY KEY (customer_id, id))
				""", WORKLOAD, java.util.Map.of());
		Layout c2 = f2.layout("c.shardml", CostFixtures.LAYOUT_C);
		Layout a2 = f2.layout("a.shardml", CostFixtures.LAYOUT_A);
		RankMapping m2 = RankMapping.of(w2, 0);
		for (Layout l : List.of(c2, a2))
			Assertions.assertEquals("UPDATE accounts SET balance = balance WHERE accounts.customer_id = ? AND accounts.id = ?",
					StatementSql.templates(w2.transactions().get(0), l, m2).get(1).sql());
		Assertions.assertEquals("name", StatementSql.payloadColumn(c2, "customers"));
	}
}
