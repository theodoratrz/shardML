package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Diagnostic;
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
import io.shardml.shardML.BelongsTo;
import io.shardml.shardML.Model;
import io.shardml.shardML.ShardMLPackage;
import io.shardml.sql.Table;

@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class ShardMLParsingTest {

	private static final String TEST_MSQL = """
			CREATE TABLE test_table (
			    id INT PRIMARY KEY,
			    sec_id INT
			)
			CREATE TABLE second_table (
			     id INT PRIMARY KEY
			)
			CREATE TABLE third_table (
			     id INT PRIMARY KEY,
			     body TEXT
			    )
			CREATE TABLE fourth_table (
			     id INT PRIMARY KEY
			    )
			CREATE TABLE fifth_table (
			         id INT PRIMARY KEY
			        )
			""";

	@Inject
	private ParseHelper<Model> parseHelper;

	@Inject
	private Provider<XtextResourceSet> xtextResourceSetProvider;

	@Inject
	private ValidationTestHelper validationTestHelper;

	private Model parseShardML(String msql, String shardml) throws Exception {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = xtextResourceSetProvider.get();
		Resource sqlResource = rs.createResource(URI.createURI("dummy:/test.msql"));
		sqlResource.load(new ByteArrayInputStream(msql.getBytes()), null);
		return parseHelper.parse(shardml, URI.createURI("dummy:/test.shardml"), rs);
	}

	@Test
	public void testCheckBuckets_zeroBuckets() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 0
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_004", "Buckets must be greater than 0 in hash sharding.");
	}

	@Test
	public void testCheckBuckets_replicatedWithBuckets() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: replicated
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_004", "In replicated sharding we don't define buckets.");
	}

	@Test
	public void testNumOfBuckets_notPowerOfTwo() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 12
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_017", "Bucket count should be a power of 2 for optimal performance.");
	}

	@Test
	public void testMaxNumOfBuckets_exceedsLimit() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 3000
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_018", "Bucket count should not exceed 2048 for optimal performance.");
	}

	@Test
	public void testCheckSelfColocation() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: test_table
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_005", "Cannot colocate a table with itself");
	}

	@Test
	public void testCheckStrategyKeyExists() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_002", "Key is required for hash sharding");
	}

	@Test
	public void testCheckReplicatedKeyNotExists() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: replicated
				        key: id
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_003","No key is required for replicated sharding");
	}

	@Test
	public void testDuplicateShardDecl() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_019", "The same table cannot appear in 2 shard declarations.");
	}

	@Test
	public void testCheckCircularColocation() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: second_table
				    }
				    shard second_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: test_table
				    }

				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_015", "Circular colocation detected: two tables cannot colocate with each other.");
	}

	@Test
	public void shardKeyInShardTable() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: wrong_id
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_006", "Shard column must belong to sharded table");
	}

	@Test
	public void testQueryPatternNoWhere_warn() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				        }
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_007", "Read query without where clause is a scatter-gather risk.");
	}

	@Test
	public void testQueryPatternNoWhere_deny() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: deny
				        query QueryExample {
				            type: read
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_007", "Read query without where clause is a scatter-gather risk.");
	}

	@Test
	public void testQueryPatternNoWhere_delete() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: allow
				        query QueryExample {
				            type: delete
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_007", "Delete query without where clause will affect all shards.");
	}

	@Test
	public void testQueryPatternNoWhere_write_warn() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: write
				        }
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_007", "Write query without where clause will affect all shards.");
	}

	@Test
	public void testQueryPatternNoWhere_write_deny() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: deny
				        query QueryExample {
				            type: write
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_007", "Write query without where clause will affect all shards.");
	}

	@Test
	public void testShardingKeyNotInQuery_warn() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				            where: sec_id = ?
				        }
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_008", "Where clause should include shard key");
	}

	@Test
	public void testOrinWhereClause() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				            where: sec_id = ? OR sec_id = ?
				        }
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_012", "Use of OR in a Where clause can lead to scatter-gather.");
	}

	@Test
	public void testShardingKeyNotInQuery_deny() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: deny
				        query QueryExample {
				            type: read
				            where: sec_id = ?
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_008", "Where clause should include shard key");
	}

	@Test
	public void testShardingKeyNotInGroupBy_warn() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				            group_by: sec_id
				        }
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_010", "GROUP BY on a non shard-key column requires cross shard aggregation and may cause performance issues.");
	}

	@Test
	public void testShardingKeyNotInGroupBy_deny() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: deny
				        query QueryExample {
				            type: read
				            group_by: sec_id 
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_010", "GROUP BY on a non shard-key column requires cross shard aggregation and may cause performance issues.");
	}

	@Test
	public void testShardingKeyNotInOrderBy_warn() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				            sort: sec_id ASC
				        }
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_011", "ORDER BY on a non shard-key column requires cross shard aggregation and may cause performance issues.");
	}

	@Test
	public void testShardingKeyNotInOrderBy_deny() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: deny
				        query QueryExample {
				            type: read
				            sort: sec_id ASC
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_011", "ORDER BY on a non shard-key column requires cross shard aggregation and may cause performance issues.");
	}

	@Test
	public void testDestinctInQuery_deny() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: deny
				        query QueryExample {
				            type: read
				            distinct: true
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_009", "DISTINCT requires cross-shard validation and may cause performance issues");
	}

	@Test
	public void testDestinctInQuery_warn() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				            distinct: true
				        }
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.QUERY_PATTERN, "SHML_009", "DISTINCT requires cross-shard validation and may cause performance issues");
	}

	@Test
	public void testTablesColocation() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: second_table
				    }
				    shard second_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    shard third_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    test_table belongs_to third_table via id
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.DATABASE, "SHML_013", "Tables related to each other should be colocated");
	}

	@Test
	public void testEmptyDatabase() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.DATABASE, "SHML_001", "Database has no shard declarations.");
	}

	@Test
	public void testUnsupportedTarget() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: mysql
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.DATABASE, "SHML_025", "Target 'mysql' (Vitess) is not supported; only 'postgres' (Citus) is.");
	}

	@Test
	public void testColocationTypeMismatch() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: second_table
				    }
				    shard second_table {
				        strategy: replicated
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_014", "Cannot colocate with replicated table.");
	}

	@Test
	public void testDuplicateRouteRule() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				            where: sec_id = ?
				        }
				    }
				    route test_table {
				        policy: warn
				        query QueryExample {
				            type: read
				            where: sec_id = ?
				        }
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.ROUTE_RULE, "SHML_020", "The same table cannot have 2 route rules defined.");
	}

	@Test
	public void testKeyTypeWarning() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard third_table {
				        strategy: hash
				        key: body
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_021", "Shard key should be of type INT, BIGINT or UUID for optimal performance.");
	}

	@Test
	public void testMissingRouteRule() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard third_table {
				        strategy: hash
				        key: body
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_022", "No route rule defined for table");
	}

	@Test
	public void testRelationshipWithoutColocation() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: second_table
				    }
				    shard second_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    shard third_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    test_table belongs_to third_table via id
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.DATABASE, "SHML_023", "have a relationship, but are not colocated");
	}

	@Test
	public void testCheckLongColocationChain() throws Exception {
		Model model = parseShardML(TEST_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard test_table {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    shard second_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: test_table
				    }
				    shard third_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: second_table
				    }
				    shard fourth_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: third_table
				    }
				     shard fifth_table {
				        strategy: hash
				        key: id
				        buckets: 32
				        colocate_with: fourth_table
				    }
				}
				""");
		validationTestHelper.assertWarning(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_016", "Long colocation chain found.");
	}

	private static final String KEYS_MSQL = """
			CREATE TABLE customers (
			    id BIGINT PRIMARY KEY,
			    email VARCHAR(255) UNIQUE
			)
			CREATE TABLE accounts (
			    id BIGINT NOT NULL,
			    customer_id BIGINT NOT NULL,
			    iban VARCHAR(34),
			    PRIMARY KEY (customer_id, id),
			    UNIQUE (iban)
			)
			""";

	@Test
	public void testUniqueWithoutShardKey_columnConstraint() throws Exception {
		Model model = parseShardML(KEYS_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_024",
				"UNIQUE (email) on 'customers' does not include the shard key 'id'. "
				+ "Citus enforces uniqueness only within one shard, so it cannot enforce this constraint. Options: "
				+ "enforce it in the application; "
				+ "a lookup table with PRIMARY KEY (email), distributed by email and written in the same transaction; "
				+ "or, if 'customers' is small, make it a reference table (strategy: replicated).");
	}

	@Test
	public void testUniqueWithoutShardKey_primaryKey() throws Exception {
		Model model = parseShardML(KEYS_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: email
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_024",
				"PRIMARY KEY (id) on 'customers' does not include the shard key 'email'.");
	}

	@Test
	public void testUniqueWithoutShardKey_tableConstraint() throws Exception {
		Model model = parseShardML(KEYS_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard accounts {
				        strategy: hash
				        key: customer_id
				        buckets: 32
				    }
				}
				""");
		// the composite primary key includes customer_id; only UNIQUE (iban) is reported
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_024",
				"UNIQUE (iban) on 'accounts' does not include the shard key 'customer_id'.");
		Assertions.assertEquals(1, validationTestHelper.validate(model).stream()
				.filter(i -> "SHML_024".equals(i.getCode())).count());
	}

	@Test
	public void testUniqueWithoutShardKey_replicated() throws Exception {
		Model model = parseShardML(KEYS_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard customers {
				        strategy: replicated
				    }
				}
				""");
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_024");
	}

	@Test
	public void testBelongsToVia() throws Exception {
		Model model = parseShardML(KEYS_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard customers {
				        strategy: replicated
				    }
				    accounts belongs_to customers via customer_id
				}
				""");
		validationTestHelper.assertNoErrors(model);
		BelongsTo rel = (BelongsTo) model.getDb().getRelationships().get(0);
		Assertions.assertEquals("customer_id", rel.getVia().getName());
		Assertions.assertSame(rel.getSubject(), rel.getVia().eContainer());
	}

	// `id` exists on both tables; via must resolve on the subject (accounts), not the parent
	@Test
	public void testBelongsToVia_scopedToSubject() throws Exception {
		Model model = parseShardML(KEYS_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard customers {
				        strategy: replicated
				    }
				    accounts belongs_to customers via id
				    customers belongs_to accounts via customer_id
				}
				""");
		BelongsTo rel = (BelongsTo) model.getDb().getRelationships().get(0);
		Assertions.assertEquals("accounts", ((Table) rel.getVia().eContainer()).getName());
		validationTestHelper.assertError(model, ShardMLPackage.Literals.BELONGS_TO, Diagnostic.LINKING_DIAGNOSTIC, "customer_id");
	}

	@Test
	public void testBelongsToVia_required() throws Exception {
		Model model = parseShardML(KEYS_MSQL, """
				import "test.msql"
				database test{
				    type: postgres
				    shard customers {
				        strategy: replicated
				    }
				    accounts belongs_to customers
				}
				""");
		Assertions.assertFalse(model.eResource().getErrors().isEmpty(), "belongs_to without via should not parse");
	}

	private static final String COLOCATION_MSQL = """
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
			    account_id BIGINT NOT NULL,
			    PRIMARY KEY (customer_id, id)
			)
			CREATE TABLE cards (
			    id BIGINT NOT NULL,
			    customer_id INT NOT NULL,
			    PRIMARY KEY (customer_id, id)
			)
			""";

	private static final String CUSTOMER_TREE = """
			    shard customers {
			        strategy: hash
			        key: id
			        buckets: 32
			    }
			    shard accounts {
			        strategy: hash
			        key: customer_id
			        buckets: 32
			        colocate_with: customers
			    }
			""";

	// customers <- accounts <- transactions all resolve to customers.id
	@Test
	public void testColocationRootKey_chain() throws Exception {
		Model model = parseShardML(COLOCATION_MSQL, "import \"test.msql\"\ndatabase test{\n    type: postgres\n" + CUSTOMER_TREE + """
				    shard transactions {
				        strategy: hash
				        key: customer_id
				        buckets: 32
				        colocate_with: accounts
				    }
				    accounts belongs_to customers via customer_id
				    transactions belongs_to accounts via customer_id
				}
				""");
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_026");
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.BELONGS_TO, "SHML_027");
	}

	// the original banking mistake: transactions placed by account_id, accounts by customer_id
	@Test
	public void testColocationRootKey_differentKey() throws Exception {
		Model model = parseShardML(COLOCATION_MSQL, "import \"test.msql\"\ndatabase test{\n    type: postgres\n" + CUSTOMER_TREE + """
				    shard transactions {
				        strategy: hash
				        key: account_id
				        buckets: 32
				        colocate_with: accounts
				    }
				    accounts belongs_to customers via customer_id
				    transactions belongs_to accounts via customer_id
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_026",
				"'transactions' is colocated with 'accounts', but their rows are placed by different keys: "
				+ "transactions.account_id and customers.id. Shard 'transactions' by a foreign key that leads to customers.id "
				+ "and declare it with `transactions belongs_to <parent> via <that column>`.");
	}

	// same shard key column, but no belongs_to says it references the parent's shard key
	@Test
	public void testColocationRootKey_missingLink() throws Exception {
		Model model = parseShardML(COLOCATION_MSQL, "import \"test.msql\"\ndatabase test{\n    type: postgres\n" + CUSTOMER_TREE + """
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_026",
				"placed by different keys: accounts.customer_id and customers.id");
	}

	// belongs_to through a column that is not the shard key does not tie the placement
	@Test
	public void testColocationRootKey_linkNotThroughShardKey() throws Exception {
		Model model = parseShardML(COLOCATION_MSQL, "import \"test.msql\"\ndatabase test{\n    type: postgres\n" + CUSTOMER_TREE + """
				    shard transactions {
				        strategy: hash
				        key: customer_id
				        buckets: 32
				        colocate_with: accounts
				    }
				    accounts belongs_to customers via customer_id
				    transactions belongs_to accounts via account_id
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_026",
				"placed by different keys: transactions.customer_id and customers.id");
	}

	@Test
	public void testForeignKeyTypeMismatch() throws Exception {
		Model model = parseShardML(COLOCATION_MSQL, "import \"test.msql\"\ndatabase test{\n    type: postgres\n" + CUSTOMER_TREE + """
				    shard cards {
				        strategy: hash
				        key: customer_id
				        buckets: 32
				        colocate_with: customers
				    }
				    accounts belongs_to customers via customer_id
				    cards belongs_to customers via customer_id
				}
				""");
		validationTestHelper.assertError(model, ShardMLPackage.Literals.BELONGS_TO, "SHML_027",
				"Foreign key cards.customer_id is INT, but the shard key it references, customers.id, is BIGINT. "
				+ "Colocated tables need the same type at every link.");
		// the link is still followed, so the placement itself is not reported again
		validationTestHelper.assertNoIssue(model, ShardMLPackage.Literals.SHARD_DECL, "SHML_026");
	}
}
