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
