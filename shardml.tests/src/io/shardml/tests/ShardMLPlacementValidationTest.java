package io.shardml.tests;

import java.io.ByteArrayInputStream;

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
import io.shardml.shardML.Model;
import io.shardml.shardML.PlacementStrategy;
import io.shardml.shardML.ShardMLPackage;

// node declarations and place blocks (SHML_031 - SHML_039); every issue is checked at its exact location
@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class ShardMLPlacementValidationTest {

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
			CREATE TABLE audit (
			    id BIGINT PRIMARY KEY
			)
			CREATE TABLE node (
			    pin BIGINT PRIMARY KEY
			)
			""";

	private static final String NODES = """
			    node w1 { host: "worker1" port: 5432 }
			    node w2 { host: "worker2" port: 5432 }
			    node w3 { host: "worker3" port: 5432 }
			""";

	@Inject
	private ParseHelper<Model> parseHelper;

	@Inject
	private Provider<XtextResourceSet> xtextResourceSetProvider;

	@Inject
	private ValidationTestHelper validationTestHelper;

	private String text;

	private Model parse(String body) throws Exception {
		text = "import \"test.msql\"\ndatabase test {\n    type: postgres\n" + body + "}\n";
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = xtextResourceSetProvider.get();
		Resource sqlResource = rs.createResource(URI.createURI("dummy:/test.msql"));
		sqlResource.load(new ByteArrayInputStream(MSQL.getBytes()), null);
		return parseHelper.parse(text, URI.createURI("dummy:/test.shardml"), rs);
	}

	// customers <- accounts <- transactions, all with the given bucket count, plus a reference table
	private static String tree(int buckets) {
		return """
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: %1$d
				    }
				    shard accounts {
				        strategy: hash
				        key: customer_id
				        buckets: %1$d
				        colocate_with: customers
				    }
				    shard transactions {
				        strategy: hash
				        key: customer_id
				        buckets: %1$d
				        colocate_with: accounts
				    }
				    shard countries {
				        strategy: replicated
				    }
				""".formatted(buckets);
	}

	private static final String LINKS = """
			    accounts belongs_to customers via customer_id
			    transactions belongs_to accounts via customer_id
			""";

	// offset of the n-th (1-based) occurrence of snippet in the model text, plus a shift into it
	private int at(String snippet, int occurrence, int shift) {
		int offset = -1;
		for (int i = 0; i < occurrence; i++) {
			offset = text.indexOf(snippet, offset + 1);
			Assertions.assertTrue(offset >= 0, "'" + snippet + "' occurs fewer than " + occurrence + " times");
		}
		return offset + shift;
	}

	private int at(String snippet) {
		return at(snippet, 1, 0);
	}

	private void assertNoPlacementIssues(Model model) {
		for (int code = 31; code <= 39; code++) {
			validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_0" + code);
			validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_0" + code);
			validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.NODE, "SHML_0" + code);
		}
	}

	@Test
	public void testValidPlacement() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place customers {
				        strategy: pin
				        nodes: w3, w1
				    }
				""" + LINKS);
		validationTestHelper.assertNoErrors(model);
		assertNoPlacementIssues(model);
		Assertions.assertEquals(3, model.getDb().getNodes().size());
		Assertions.assertEquals("worker3", model.getDb().getPlacements().get(0).getNodes().get(0).getHost());
		Assertions.assertEquals(5432, model.getDb().getNodes().get(0).getPort());
		Assertions.assertEquals(PlacementStrategy.PIN, model.getDb().getPlacements().get(0).getStrategy());
	}

	// the words placement made keywords still work as table and column names
	@Test
	public void testKeywordsAsNames() throws Exception {
		Model model = parse(NODES + """
				    shard node {
				        strategy: hash
				        key: pin
				        buckets: 3
				    }
				    place node {
				        strategy: spread
				    }
				""");
		validationTestHelper.assertNoErrors(model);
		Assertions.assertEquals("pin", model.getDb().getShard_decl().get(0).getId().getName());
	}

	@Test
	public void testUnknownNode() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place customers {
				        strategy: pin
				        nodes: w1, w9
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_031", at("w9"), 2,
				"Unknown node 'w9'. Declared nodes: w1, w2, w3.");
	}

	@Test
	public void testDuplicateNodeName() throws Exception {
		Model model = parse(NODES + """
				    node w1 { host: "worker4" port: 5432 }
				""" + tree(32) + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.NODE, "SHML_032", at("node w1", 2, 5), 2,
				"Node 'w1' is declared twice.");
	}

	@Test
	public void testDuplicateNodeAddress() throws Exception {
		Model model = parse(NODES + """
				    node w4 { host: "worker1" port: 5432 }
				""" + tree(32) + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.NODE, "SHML_032", at("\"worker1\"", 2, 0), 9,
				"Nodes 'w1' and 'w4' are both worker1:5432. Declare each worker once.");
	}

	@Test
	public void testNodeListedTwice() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place customers {
				        strategy: pin
				        nodes: w1, w2, w1
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_032", at("w2, w1", 1, 4), 2,
				"Node 'w1' is listed twice.");
	}

	@Test
	public void testPinWithoutNodes() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place customers {
				        strategy: pin
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_033", at("strategy: pin", 1, 10), 3,
				"Strategy 'pin' needs the nodes to pin to: `nodes: <node>, ...`.");
	}

	@Test
	public void testSpreadWithNodes() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place customers {
				        strategy: spread
				        nodes: w1, w2
				    }
				""" + LINKS);
		// a list feature reported without an index is marked at its first element
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_033", at("w1, w2"), 2,
				"Strategy 'spread' always uses every declared node. To use only some, pin to them: `strategy: pin`.");
	}

	@Test
	public void testPlacementWithoutNodes() throws Exception {
		Model model = parse(tree(32) + """
				    place customers {
				        strategy: spread
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_033", at("place customers", 1, 6), 9,
				"A place block needs declared nodes. Declare the cluster's workers with `node <name> { host: \"<host>\" port: <port> }`.");
	}

	// transactions -> accounts -> customers: the message names the root, not the direct parent's parent
	@Test
	public void testPlacementOnColocatedTable() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place transactions {
				        strategy: spread
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_034", at("place transactions", 1, 6), 12,
				"'transactions' is colocated with 'accounts', so its shards move with its colocation group. "
				+ "Place the group's root, 'customers', instead.");
	}

	@Test
	public void testPlacementOnReferenceTable() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place countries {
				        strategy: pin
				        nodes: w1
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_035", at("place countries", 1, 6), 9,
				"'countries' is a reference table (strategy: replicated). Citus keeps a copy on every node, so it cannot be placed.");
	}

	@Test
	public void testDuplicatePlacement() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place customers {
				        strategy: spread
				    }
				    place customers {
				        strategy: pin
				        nodes: w1
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_038", at("place customers", 2, 6), 9,
				"The same table cannot have 2 place blocks.");
	}

	@Test
	public void testPlacementWithoutShard() throws Exception {
		Model model = parse(NODES + tree(32) + """
				    place audit {
				        strategy: spread
				    }
				""" + LINKS);
		validationTestHelper.assertError(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_039", at("place audit", 1, 6), 5,
				"'audit' has no shard declaration, so it has no shards to place.");
	}

	// no place block: the default spread is reported on the root's bucket count
	@Test
	public void testFewerBucketsThanNodes_default() throws Exception {
		Model model = parse(NODES + tree(2) + LINKS);
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_036", at("buckets: 2", 1, 9), 1,
				"'customers' has 2 buckets but is placed on 3 nodes, so 1 node gets no shards of its colocation group.");
	}

	@Test
	public void testFewerBucketsThanNodes_place() throws Exception {
		Model model = parse(NODES + tree(1) + """
				    place customers {
				        strategy: spread
				    }
				""" + LINKS);
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_036", at("strategy: spread", 1, 10), 6,
				"'customers' has 1 bucket but is placed on 3 nodes, so 2 nodes get no shards of its colocation group.");
	}

	// 32 over 3: 11 and 10, 10% more: no warning
	@Test
	public void testSkew_belowThreshold() throws Exception {
		Model model = parse(NODES + tree(32) + LINKS);
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_037");
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_036");
	}

	// 9 pinned over 2: 5 and 4, exactly 25% more: no warning
	@Test
	public void testSkew_atThreshold() throws Exception {
		Model model = parse(NODES + tree(9) + """
				    place customers {
				        strategy: pin
				        nodes: w1, w2
				    }
				""" + LINKS);
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_037");
	}

	// 7 over 3: 3 and 2, 50% more
	@Test
	public void testSkew_aboveThreshold() throws Exception {
		Model model = parse(NODES + tree(7) + LINKS);
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_037", at("buckets: 7", 1, 9), 1,
				"'customers' has 7 buckets on 3 nodes: the busiest node gets 3 of them and the least busy 2, 50% more. "
				+ "Use a bucket count divisible by 3, or one large enough to keep the difference within 25%.");
	}

	// 11 pinned over 2: 6 and 5, 20% more: no warning; over 3 nodes (4 and 3, 33%) it warns
	@Test
	public void testSkew_pinnedSubset() throws Exception {
		Model model = parse(NODES + tree(11) + """
				    place customers {
				        strategy: pin
				        nodes: w1, w2
				    }
				""" + LINKS);
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.PLACE_DECL, "SHML_037");
		Model spread = parse(NODES + tree(11) + LINKS);
		validationTestHelper.assertWarning(spread, ShardMLPackage.Literals.SHARD_DECL, "SHML_037", at("buckets: 11", 1, 9), 2,
				"'customers' has 11 buckets on 3 nodes: the busiest node gets 4 of them and the least busy 3, 33% more. "
				+ "Use a bucket count divisible by 3, or one large enough to keep the difference within 25%.");
	}

	// without nodes nothing is placed, so bucket counts get no placement warnings
	@Test
	public void testNoNodes_noPlacementWarnings() throws Exception {
		Model model = parse(tree(1) + LINKS);
		assertNoPlacementIssues(model);
	}
}
