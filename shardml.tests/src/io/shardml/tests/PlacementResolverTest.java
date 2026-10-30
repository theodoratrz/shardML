package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.SQLStandaloneSetup;
import io.shardml.placement.ColocationGroup;
import io.shardml.placement.Node;
import io.shardml.placement.Placement;
import io.shardml.placement.PlacementResolver;
import io.shardml.placement.ShardSlot;
import io.shardml.shardML.Model;

@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class PlacementResolverTest {

	private static final String MSQL = """
			CREATE TABLE customers (
			    id BIGINT PRIMARY KEY
			)
			CREATE TABLE accounts (
			    id BIGINT NOT NULL,
			    customer_id BIGINT NOT NULL,
			    PRIMARY KEY (customer_id, id)
			)
			CREATE TABLE transactions (
			    id BIGINT NOT NULL,
			    customer_id BIGINT NOT NULL,
			    PRIMARY KEY (customer_id, id)
			)
			CREATE TABLE countries (
			    code INT PRIMARY KEY
			)
			CREATE TABLE events (
			    id BIGINT PRIMARY KEY
			)
			""";

	// declared out of name order on purpose: spread follows declaration order
	private static final String NODES = """
			    node w2 { host: "worker2" port: 5432 }
			    node w3 { host: "worker3" port: 5432 }
			    node w1 { host: "worker1" port: 5432 }
			""";

	private static final Node W1 = new Node("w1", "worker1", 5432);
	private static final Node W2 = new Node("w2", "worker2", 5432);
	private static final Node W3 = new Node("w3", "worker3", 5432);

	// transactions is declared before its parents, so group order cannot come from declaration order alone
	private static final String SHARDS = """
			    shard transactions {
			        strategy: hash
			        key: customer_id
			        buckets: 8
			        colocate_with: accounts
			    }
			    shard customers {
			        strategy: hash
			        key: id
			        buckets: 8
			    }
			    shard accounts {
			        strategy: hash
			        key: customer_id
			        buckets: 8
			        colocate_with: customers
			    }
			    shard events {
			        strategy: hash
			        key: id
			        buckets: 4
			    }
			    shard countries {
			        strategy: replicated
			    }
			""";

	private static final String LINKS = """
			    accounts belongs_to customers via customer_id
			    transactions belongs_to accounts via customer_id
			""";

	@Inject
	private ParseHelper<Model> parseHelper;

	@Inject
	private Provider<XtextResourceSet> xtextResourceSetProvider;

	@Inject
	private ValidationTestHelper validationTestHelper;

	private Model parse(String msql, String shardml) throws Exception {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = xtextResourceSetProvider.get();
		Resource sqlResource = rs.createResource(URI.createURI("dummy:/test.msql"));
		sqlResource.load(new ByteArrayInputStream(msql.getBytes()), null);
		Model model = parseHelper.parse(shardml, URI.createURI("dummy:/test.shardml"), rs);
		validationTestHelper.assertNoErrors(model);
		return model;
	}

	private Placement resolve(String body) throws Exception {
		Model model = parse(MSQL, "import \"test.msql\"\ndatabase test {\n    type: postgres\n" + body + LINKS + "}\n");
		return PlacementResolver.resolve(model.getDb());
	}

	private static List<Node> column(Placement placement, String table, int buckets) {
		List<Node> nodes = new ArrayList<>();
		for (int bucket = 0; bucket < buckets; bucket++)
			nodes.add(placement.nodeOf(table, bucket));
		return nodes;
	}

	// no place block: bucket i -> nodes[i mod 3], in declared order
	@Test
	public void testSpreadFollowsDeclarationOrder() throws Exception {
		Placement placement = resolve(NODES + SHARDS);
		Assertions.assertEquals(List.of(W2, W3, W1), placement.nodes());
		Assertions.assertEquals(List.of(W2, W3, W1, W2, W3, W1, W2, W3), column(placement, "customers", 8));
		Assertions.assertEquals(List.of(W2, W3, W1, W2), column(placement, "events", 4));
	}

	@Test
	public void testExplicitSpreadEqualsDefault() throws Exception {
		Placement explicit = resolve(NODES + SHARDS + """
				    place customers {
				        strategy: spread
				    }
				""");
		Assertions.assertEquals(resolve(NODES + SHARDS).assignments(), explicit.assignments());
	}

	@Test
	public void testPinOneNode() throws Exception {
		Placement placement = resolve(NODES + SHARDS + """
				    place events {
				        strategy: pin
				        nodes: w3
				    }
				""");
		Assertions.assertEquals(List.of(W3, W3, W3, W3), column(placement, "events", 4));
		// the other group keeps the default spread
		Assertions.assertEquals(List.of(W2, W3, W1, W2, W3, W1, W2, W3), column(placement, "customers", 8));
	}

	// pinned to a subset: round-robin over the listed nodes, in listed order
	@Test
	public void testPinSubset() throws Exception {
		Placement placement = resolve(NODES + SHARDS + """
				    place customers {
				        strategy: pin
				        nodes: w1, w3
				    }
				""");
		Assertions.assertEquals(List.of(W1, W3, W1, W3, W1, W3, W1, W3), column(placement, "customers", 8));
		Assertions.assertEquals(List.of(W1, W3), placement.groups().get(0).targets());
	}

	// transactions -> accounts -> customers: every table follows the root bucket by bucket
	@Test
	public void testColocationChainInheritsRootPlacement() throws Exception {
		Placement placement = resolve(NODES + SHARDS + """
				    place customers {
				        strategy: pin
				        nodes: w1, w3
				    }
				""");
		ColocationGroup group = placement.groups().get(0);
		Assertions.assertEquals("customers", group.root());
		Assertions.assertEquals(List.of("customers", "transactions", "accounts"), group.tables());
		for (int bucket = 0; bucket < 8; bucket++) {
			Node root = placement.nodeOf("customers", bucket);
			Assertions.assertEquals(root, placement.nodeOf("accounts", bucket), "accounts bucket " + bucket);
			Assertions.assertEquals(root, placement.nodeOf("transactions", bucket), "transactions bucket " + bucket);
		}
	}

	// two groups, reference table left out: 3 tables x 8 + 1 table x 4
	@Test
	public void testGroupsAndReferenceTables() throws Exception {
		Placement placement = resolve(NODES + SHARDS);
		Assertions.assertEquals(List.of("customers", "events"), placement.groups().stream().map(ColocationGroup::root).toList());
		Assertions.assertEquals(28, placement.assignments().size());
		Assertions.assertNull(placement.nodeOf("countries", 0));
		Assertions.assertNull(placement.nodeOf("customers", 8));
	}

	// the same model, parsed twice, gives the same map in the same iteration order
	@Test
	public void testDeterministic() throws Exception {
		String body = NODES + SHARDS + """
				    place customers {
				        strategy: pin
				        nodes: w3, w2
				    }
				""";
		Placement first = resolve(body);
		Placement second = resolve(body);
		Assertions.assertEquals(new ArrayList<>(first.assignments().entrySet()), new ArrayList<>(second.assignments().entrySet()));
		Assertions.assertEquals(first.groups(), second.groups());
	}

	@Test
	public void testNoNodesIsEmpty() throws Exception {
		Placement placement = resolve(SHARDS);
		Assertions.assertTrue(placement.isEmpty());
		Assertions.assertTrue(placement.nodes().isEmpty());
		Assertions.assertTrue(placement.groups().isEmpty());
		Assertions.assertTrue(placement.assignments().isEmpty());
	}

	private Model banking(boolean withNodes) throws Exception {
		Path examples = Path.of("..", "examples");
		String msql = Files.readString(examples.resolve("banking.msql"));
		String shardml = Files.readString(examples.resolve("banking.shardml")).replace("\"banking.msql\"", "\"test.msql\"");
		if (withNodes)
			shardml = shardml.replace("type: postgres\n", "type: postgres\n\n" + NODES);
		return parse(msql, shardml);
	}

	// the committed banking example has no nodes, so Citus decides placement
	@Test
	public void testBankingExampleWithoutNodes() throws Exception {
		Assertions.assertTrue(PlacementResolver.resolve(banking(false).getDb()).isEmpty());
	}

	// banking with three nodes: one group of four tables, 32 buckets spread 11/11/10
	@Test
	public void testBankingExampleWithNodes() throws Exception {
		Placement placement = PlacementResolver.resolve(banking(true).getDb());
		Assertions.assertEquals(1, placement.groups().size());
		ColocationGroup group = placement.groups().get(0);
		Assertions.assertEquals(List.of("customers", "accounts", "transactions", "cards"), group.tables());
		Assertions.assertEquals(32, group.buckets());
		Assertions.assertEquals(128, placement.assignments().size());
		Map<String, Integer> perNode = new TreeMap<>();
		for (Map.Entry<ShardSlot, Node> e : placement.assignments().entrySet()) {
			Assertions.assertEquals(List.of(W2, W3, W1).get(e.getKey().bucket() % 3), e.getValue(), e.getKey().toString());
			if (e.getKey().table().equals("customers"))
				perNode.merge(e.getValue().name(), 1, Integer::sum);
		}
		Assertions.assertEquals(Map.of("w1", 10, "w2", 11, "w3", 11), perNode);
	}
}
