package io.shardml.tests;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.cost.Workload;
import io.shardml.cost.Workload.ColumnRef;
import io.shardml.cost.Workload.Condition;
import io.shardml.cost.Workload.Connective;
import io.shardml.cost.Workload.Domain;
import io.shardml.cost.Workload.Draw;
import io.shardml.cost.Workload.ElseWith;
import io.shardml.cost.Workload.Join;
import io.shardml.cost.Workload.JoinOn;
import io.shardml.cost.Workload.KeyPath;
import io.shardml.cost.Workload.Kind;
import io.shardml.cost.Workload.Mapped;
import io.shardml.cost.Workload.Op;
import io.shardml.cost.Workload.Param;
import io.shardml.cost.Workload.PerRows;
import io.shardml.cost.Workload.Repeat;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.TableData;
import io.shardml.cost.Workload.Transaction;
import io.shardml.cost.Workload.Uniform;
import io.shardml.cost.Workload.VarRef;
import io.shardml.cost.Workload.Within;
import io.shardml.cost.Workload.Zipf;

// Workloads.of: the records of a parsed workload, every number resolved, and --set overrides.
public class WorkloadsTest {

	static final String WORKLOAD = """
			import "s.msql"
			workload w {
			    param s = 1.0
			    param tw = 3
			    param remote = 0.25
			    domain customer { size: 4 }
			    domain account { size: 8 within customer zipf($s) }
			    table customers { rows: per customer id: customer }
			    table accounts { rows: 2 per account id: account customer_id: account.customer }
			    transaction transfer weight $tw {
			        let a = account uniform
			        let b = account zipf(0.5) distinct from $a
			        let c = $a.customer, else with $remote: customer uniform
			        write accounts where customer_id = $a.customer AND id = $a
			        write accounts where customer_id = $b.customer AND id = $b
			        repeat 2 {
			            let a = customer uniform
			            read customers where id = $a OR id > ?
			        }
			        read accounts join customers on accounts.customer_id = customers.id where customers.id = $c
			    }
			}
			""";

	// a fresh resource set per load
	private static Workload workload(Map<String, String> overrides) {
		return new CostFixtures().workloadWith(CostFixtures.SCHEMA, WORKLOAD, overrides);
	}

	private static Transaction transfer(double weight, double remote) {
		VarRef a = new VarRef(0, "a", List.of());
		VarRef b = new VarRef(1, "b", List.of());
		return new Transaction("transfer", weight, List.of(
				new Draw(0, "a", "account", new Uniform(), null),
				new Draw(1, "b", "account", new Zipf(0.5), a),
				new ElseWith(2, "c", new VarRef(0, "a", List.of("customer")), remote, "customer", new Uniform()),
				new Statement(Kind.WRITE, "accounts", List.of(), List.of(
						new Condition(new ColumnRef("accounts", "customer_id"), Op.EQ, new VarRef(0, "a", List.of("customer"))),
						new Condition(new ColumnRef("accounts", "id"), Op.EQ, a)), List.of(Connective.AND)),
				new Statement(Kind.WRITE, "accounts", List.of(), List.of(
						new Condition(new ColumnRef("accounts", "customer_id"), Op.EQ, new VarRef(1, "b", List.of("customer"))),
						new Condition(new ColumnRef("accounts", "id"), Op.EQ, b)), List.of(Connective.AND)),
				// the inner a shadows the outer one: its own id
				new Repeat(2, List.of(
						new Draw(3, "a", "customer", new Uniform(), null),
						new Statement(Kind.READ, "customers", List.of(), List.of(
								new Condition(new ColumnRef("customers", "id"), Op.EQ, new VarRef(3, "a", List.of())),
								new Condition(new ColumnRef("customers", "id"), Op.GT, null)), List.of(Connective.OR)))),
				new Statement(Kind.READ, "accounts",
						List.of(new Join("customers", List.of(new JoinOn(new ColumnRef("accounts", "customer_id"),
								new ColumnRef("customers", "id"))))),
						List.of(new Condition(new ColumnRef("customers", "id"), Op.EQ, new VarRef(2, "c", List.of()))),
						List.of())));
	}

	@Test
	public void testRecords() {
		Workload w = workload(Map.of());
		Assertions.assertEquals("w", w.name());
		Assertions.assertEquals(List.of(new Param("s", 1.0, false), new Param("tw", 3, false), new Param("remote", 0.25, false)),
				w.params());
		Assertions.assertEquals(List.of(new Domain("customer", 4, null),
				new Domain("account", 8, new Within("customer", new Zipf(1.0)))), w.domains());
		Assertions.assertEquals(List.of(
				new TableData("customers", new PerRows(1, List.of("customer")),
						List.of(new Mapped("id", new KeyPath("customer", List.of())))),
				new TableData("accounts", new PerRows(2, List.of("account")),
						List.of(new Mapped("id", new KeyPath("account", List.of())),
								new Mapped("customer_id", new KeyPath("account", List.of("customer")))))), w.tables());
		Assertions.assertEquals(List.of(transfer(3, 0.25)), w.transactions());
		Assertions.assertEquals("customer", w.tables().get(1).columns().get(1) instanceof Mapped m ? m.path().target() : null);
	}

	@Test
	public void testOverridesChangeExactlyTheirUses() {
		Workload base = workload(Map.of());
		// the weight
		Workload weight = workload(Map.of("tw", "5"));
		Assertions.assertEquals(List.of(transfer(5, 0.25)), weight.transactions());
		Assertions.assertEquals(base.domains(), weight.domains());
		Assertions.assertEquals(new Param("tw", 5, true), weight.params().get(1));
		// the zipf exponent of the child domain
		Workload zipf = workload(Map.of("s", "1.25"));
		Assertions.assertEquals(new Domain("account", 8, new Within("customer", new Zipf(1.25))), zipf.domains().get(1));
		Assertions.assertEquals(base.transactions(), zipf.transactions());
		// the else-with probability, and 0 / 1 at the edges
		for (String p : List.of("0", "0.5", "1")) {
			Workload remote = workload(Map.of("remote", p));
			Assertions.assertEquals(List.of(transfer(3, Double.parseDouble(p))), remote.transactions(), p);
			Assertions.assertEquals(base.domains(), remote.domains());
		}
		// zipf 0 is allowed (uniform)
		Assertions.assertEquals(new Zipf(0),
				workload(Map.of("s", "0")).domains().get(1).within().children());
	}
}
