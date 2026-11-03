package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.SQLStandaloneSetup;
import io.shardml.generator.ShardMLGenerator;
import io.shardml.shardML.Model;

@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class ShardMLGeneratorTest {

	private static final String TEST_MSQL = """
			CREATE TABLE customers (
			    id BIGINT PRIMARY KEY,
			    name VARCHAR(255) NOT NULL
			)
			CREATE TABLE orders (
			    id BIGINT PRIMARY KEY,
			    customer_id BIGINT NOT NULL,
			    total DECIMAL
			)
			""";

	@Inject
	private Provider<XtextResourceSet> rsProvider;
	@Inject
	private ShardMLGenerator generator;

	private Model parseShardML(String msql, String shardml) throws IOException {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = rsProvider.get();
		Resource sqlResource = rs.createResource(URI.createURI("dummy:/test.msql"));
		sqlResource.load(new ByteArrayInputStream(msql.getBytes()), rs.getLoadOptions());
		Resource shardResource = rs.createResource(URI.createURI("dummy:/test.shardml"));
		shardResource.load(new ByteArrayInputStream(shardml.getBytes()), rs.getLoadOptions());
		return (Model) shardResource.getContents().get(0);
	}

	// Runs the generator and returns the generated files, keyed by file name
	private Map<String, CharSequence> generate(String shardml) throws IOException {
		Model model = parseShardML(TEST_MSQL, shardml);
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
		generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
		return fsa.getTextFiles();
	}

	private static String file(Map<String, CharSequence> files, String name) {
		CharSequence content = files.get("DEFAULT_OUTPUT" + name);
		return content == null ? null : content.toString();
	}

	// --- Citus (postgres) output ---

	@Test
	public void testPostgresJsonContainsDatabaseName() throws IOException {
		String json = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				"""), "mybank-sharding.json");
		Assertions.assertNotNull(json, "Citus JSON file should be generated");
		Assertions.assertTrue(json.contains("\"database\": \"mybank\""), "JSON should contain database name");
		Assertions.assertTrue(json.contains("\"target\": \"citus\""), "JSON should target citus");
	}

	@Test
	public void testPostgresJsonHashTable() throws IOException {
		String json = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 64
				    }
				}
				"""), "mybank-sharding.json");
		Assertions.assertTrue(json.contains("\"distribution_column\": \"id\""), "Should include distribution column");
		Assertions.assertTrue(json.contains("\"shard_count\": 64"), "Should include shard count");
		Assertions.assertTrue(json.contains("\"distribution_type\": \"hash\""), "Should be hash distribution");
	}

	@Test
	public void testPostgresJsonReplicatedTable() throws IOException {
		String json = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: replicated
				    }
				}
				"""), "mybank-sharding.json");
		Assertions.assertTrue(json.contains("\"distribution_type\": \"reference\""), "Replicated table should use reference distribution");
	}

	@Test
	public void testPostgresJsonColocation() throws IOException {
		String json = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    shard orders {
				        strategy: hash
				        key: customer_id
				        buckets: 32
				        colocate_with: customers
				    }
				}
				"""), "mybank-sharding.json");
		Assertions.assertTrue(json.contains("\"colocate_with\": \"customers\""), "Should include colocate_with field");
	}

	@Test
	public void testPostgresSqlDistributionScript() throws IOException {
		String sql = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    shard orders {
				        strategy: replicated
				    }
				}
				"""), "mybank-distribution.sql");
		Assertions.assertNotNull(sql, "SQL distribution file should be generated");
		Assertions.assertTrue(sql.contains("create_distributed_table"), "Hash table should use create_distributed_table");
		Assertions.assertTrue(sql.contains("create_reference_table"), "Replicated table should use create_reference_table");
	}

	@Test
	public void testPostgresSqlShardCount() throws IOException {
		String sql = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 64
				    }
				    shard orders {
				        strategy: hash
				        key: customer_id
				        buckets: 64
				        colocate_with: customers
				    }
				}
				"""), "mybank-distribution.sql");
		// Citus rejects shard_count together with colocate_with; orders takes the count from customers
		Assertions.assertEquals("""
				SELECT create_distributed_table('customers', 'id'  ,shard_count => 64 ,colocate_with => 'none' );
				SELECT create_distributed_table('orders', 'customer_id'  ,colocate_with => 'customers' );
				""", sql);
	}

	@Test
	public void testDistributionWithoutNodesSeparatesRoots() throws IOException {
		// two roots of the same shape and no nodes: each still starts its own colocation group
		String sql = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				    shard orders {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				"""), "mybank-distribution.sql");
		Assertions.assertEquals("""
				SELECT create_distributed_table('customers', 'id'  ,shard_count => 32 ,colocate_with => 'none' );
				SELECT create_distributed_table('orders', 'id'  ,shard_count => 32 ,colocate_with => 'none' );
				""", sql);
	}

	@Test
	public void testPostgresSchemaScript() throws IOException {
		String sql = file(generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				"""), "mybank-schema.sql");
		Assertions.assertEquals("""
				CREATE TABLE customers (
				    id BIGINT PRIMARY KEY,
				    name VARCHAR(255) NOT NULL
				);

				CREATE TABLE orders (
				    id BIGINT PRIMARY KEY,
				    customer_id BIGINT NOT NULL,
				    total DECIMAL
				);
				""", sql, "Schema script should create every imported table, including unsharded ones");
	}

	@Test
	public void testPostgresSchemaScriptTableConstraints() throws IOException {
		Model model = parseShardML("""
				CREATE TABLE accounts (
				    id BIGINT NOT NULL,
				    customer_id BIGINT NOT NULL,
				    iban VARCHAR(34),
				    PRIMARY KEY (customer_id, id),
				    UNIQUE (customer_id, iban)
				)
				""", """
				import "test.msql"
				database mybank {
				    type: postgres
				    shard accounts {
				        strategy: hash
				        key: customer_id
				        buckets: 32
				    }
				}
				""");
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
		generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
		Assertions.assertEquals("""
				CREATE TABLE accounts (
				    id BIGINT NOT NULL,
				    customer_id BIGINT NOT NULL,
				    iban VARCHAR(34),
				    PRIMARY KEY (customer_id, id),
				    UNIQUE (customer_id, iban)
				);
				""", file(fsa.getTextFiles(), "mybank-schema.sql"));
	}

	// --- placement ---

	private static final String PLACED = """
			import "test.msql"
			database mybank {
			    type: postgres
			    node w2 { host: "worker2" port: 5432 }
			    node w1 { host: "worker1" port: 5433 }
			    shard customers {
			        strategy: hash
			        key: id
			        buckets: 3
			    }
			    shard orders {
			        strategy: hash
			        key: customer_id
			        buckets: 3
			        colocate_with: customers
			    }
			    orders belongs_to customers via customer_id
			}
			""";

	@Test
	public void testNoPlacementScriptWithoutNodes() throws IOException {
		Map<String, CharSequence> files = generate("""
				import "test.msql"
				database mybank {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				""");
		Assertions.assertNull(file(files, "mybank-placement.sql"), "Without nodes Citus decides placement");
		Assertions.assertEquals(3, files.size());
	}

	@Test
	public void testDistributionWithNodesSeparatesRoots() throws IOException {
		// a root gets its own Citus colocation group; colocated tables keep theirs
		Assertions.assertEquals("""
				SELECT create_distributed_table('customers', 'id'  ,shard_count => 3 ,colocate_with => 'none' );
				SELECT create_distributed_table('orders', 'customer_id'  ,colocate_with => 'customers' );
				""", file(generate(PLACED), "mybank-distribution.sql"));
	}

	@Test
	public void testPlacementScriptMovesEachBucketOfTheRoot() throws IOException {
		String sql = file(generate(PLACED), "mybank-placement.sql");
		Assertions.assertNotNull(sql, "Nodes declared: a placement script is generated");
		Assertions.assertTrue(sql.startsWith("\\set ON_ERROR_STOP on\n"), sql);
		Assertions.assertTrue(sql.contains("-- customers group (customers, orders): 3 buckets on w2, w1\n"), sql);
		// bucket i -> node i mod 2, in declaration order
		Assertions.assertTrue(sql.contains("""
				-- bucket 2 -> w2 (worker2:5432)
				SELECT citus_move_shard_placement(s.shardid, s.src_host, s.src_port, 'worker2', 5432, shard_transfer_mode => 'force_logical')
				FROM (
				    SELECT b.shardid, n.nodename AS src_host, n.nodeport AS src_port, n.groupid AS src_groupid
				    FROM (SELECT shardid, row_number() OVER (ORDER BY shardminvalue::bigint) - 1 AS bucket
				          FROM pg_dist_shard WHERE logicalrelid = 'customers'::regclass) b
				    JOIN pg_dist_placement p ON p.shardid = b.shardid
				    JOIN pg_dist_node n ON n.groupid = p.groupid AND n.noderole = 'primary'
				    WHERE b.bucket = 2
				) s
				WHERE (s.src_host, s.src_port) <> ('worker2', 5432) AND s.src_groupid <> 0;
				"""), sql);
		Assertions.assertTrue(sql.contains("-- bucket 1 -> w1 (worker1:5433)\n"), sql);
		// colocated tables move with their root, so only the root's shards are moved
		Assertions.assertEquals(3, sql.split("citus_move_shard_placement\\(", -1).length - 1);
		Assertions.assertFalse(sql.contains("'orders'::regclass"), sql);
	}

	@Test
	public void testPlacementPreflightListsNodesAndGroups() throws IOException {
		String sql = file(generate(PLACED), "mybank-placement.sql");
		Assertions.assertTrue(sql.contains("""
				    FROM (VALUES
				        ('w2', 'worker2', 5432),
				        ('w1', 'worker1', 5433)
				    ) AS d(name, host, port)
				"""), sql);
		Assertions.assertTrue(sql.contains("AND n.isactive AND n.noderole = 'primary' AND n.groupid <> 0"), sql);
		Assertions.assertTrue(sql.contains("        ('customers', ARRAY['customers', 'orders'], 3)\n"), sql);
		// the preflight runs no moves: it ends before the first one
		int preflightEnd = sql.indexOf("END $preflight$;");
		Assertions.assertTrue(preflightEnd > 0 && preflightEnd < sql.indexOf("citus_move_shard_placement"), sql);
	}

	@Test
	public void testPlacementScriptQuotesHosts() throws IOException {
		String sql = file(generate(PLACED.replace("\"worker2\"", "\"it's\"")), "mybank-placement.sql");
		Assertions.assertTrue(sql.contains("'it''s', 5432"), sql);
		Assertions.assertFalse(sql.contains("'it's'"), sql);
	}

	// --- mysql (unsupported) ---

	@Test
	public void testMysqlGeneratesNothing() throws IOException {
		Map<String, CharSequence> files = generate("""
				import "test.msql"
				database social {
				    type: mysql
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				""");
		Assertions.assertTrue(files.isEmpty(), "mysql is rejected by the validator and has no output");
	}
}
