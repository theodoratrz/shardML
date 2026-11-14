package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.SQLStandaloneSetup;
import io.shardml.generator.ShardMLGenerator;
import io.shardml.scoping.ImportedSchema;
import io.shardml.shardML.Model;
import io.shardml.shardML.ShardDecl;
import io.shardml.sql.Column;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;

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

	// --- JSON escaping ---

	// Quoted names keep any character: "a""b" is stored as a"b, "back\slash" keeps its backslash,
	// and a quoted name may span lines. ShardML cannot name such a column yet (its references are
	// ValidIDs), so the shard is parsed against plain columns and then pointed at the quoted ones
	private static final String ODD_MSQL = """
			CREATE TABLE "t\\x" (
			    id BIGINT PRIMARY KEY,
			    "a""b" BIGINT NOT NULL,
			    "back\\slash" BIGINT,
			    "two
			lines" BIGINT
			)
			CREATE TABLE plain (
			    id BIGINT PRIMARY KEY
			)
			""";

	private static final String ODD_SHARDML = """
			import "test.msql"
			database mybank {
			    type: postgres
			    shard plain {
			        strategy: hash
			        key: id
			        buckets: 4
			    }
			    shard plain {
			        strategy: hash
			        key: id
			        buckets: 4
			        colocate_with: plain
			    }
			}
			""";

	// the JSON of ODD_SHARDML after its second shard is moved to table "t\x" and key column `key`
	private String oddJson(String key) throws IOException {
		Model model = parseShardML(ODD_MSQL, ODD_SHARDML);
		Schema schema = ImportedSchema.of(model);
		Table odd = schema.getTables().get(0);
		Assertions.assertEquals("t\\x", odd.getName(), "the .msql should have parsed");
		Column column = odd.getColumns().stream().filter(c -> key.equals(c.getName())).findFirst()
				.orElseThrow(() -> new AssertionError("no column " + key + " in " + odd.getColumns()));
		ShardDecl shard = model.getDb().getShard_decl().get(1);
		shard.setEntity(odd);
		shard.setId(column);
		// the first shard's table as colocate_with target is plain; make it the odd one too
		model.getDb().getShard_decl().get(0).setEntity(odd);
		shard.setColocate(odd);
		return generator.PostgresJSON(model.getDb()).toString();
	}

	// parses with a strict reader (a TypeAdapter keeps the reader strict; JsonParser.parseReader
	// would make it lenient): no trailing data, no unescaped control characters, no NaN, etc.
	private static JsonObject parseStrict(String json) throws IOException {
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setStrictness(Strictness.STRICT);
		JsonElement element = new Gson().getAdapter(JsonElement.class).read(reader);
		Assertions.assertEquals(JsonToken.END_DOCUMENT, reader.peek(), "trailing data after the JSON");
		return element.getAsJsonObject();
	}

	private static JsonObject table(JsonObject json, int i) {
		return json.getAsJsonArray("tables").get(i).getAsJsonObject();
	}

	@Test
	public void testJsonEscapesAQuoteInAName() throws IOException {
		String json = oddJson("a\"b");
		Assertions.assertTrue(json.contains("\"distribution_column\": \"a\\\"b\""), json);
		JsonObject parsed = parseStrict(json);
		Assertions.assertEquals("a\"b", table(parsed, 1).get("distribution_column").getAsString());
	}

	@Test
	public void testJsonEscapesABackslashInANameAndTable() throws IOException {
		String json = oddJson("back\\slash");
		Assertions.assertTrue(json.contains("\"distribution_column\": \"back\\\\slash\""), json);
		Assertions.assertTrue(json.contains("\"name\": \"t\\\\x\""), json);
		Assertions.assertTrue(json.contains("\"colocate_with\": \"t\\\\x\""), json);
		JsonObject parsed = parseStrict(json);
		Assertions.assertEquals("back\\slash", table(parsed, 1).get("distribution_column").getAsString());
		Assertions.assertEquals("t\\x", table(parsed, 0).get("name").getAsString());
		Assertions.assertEquals("t\\x", table(parsed, 1).get("name").getAsString());
		Assertions.assertEquals("t\\x", table(parsed, 1).get("colocate_with").getAsString());
	}

	@Test
	public void testJsonEscapesAControlCharacterInAName() throws IOException {
		String json = oddJson("two\nlines");
		Assertions.assertTrue(json.contains("\"distribution_column\": \"two\\nlines\""), json);
		Assertions.assertEquals("two\nlines", table(parseStrict(json), 1).get("distribution_column").getAsString());
	}

	@Test
	public void testJsonStringEscapesPerRfc8259() {
		Assertions.assertEquals("\"plain\"", ShardMLGenerator.jsonString("plain"));
		Assertions.assertEquals("\"\\\"\\\\\\b\\f\\n\\r\\t\"", ShardMLGenerator.jsonString("\"\\\b\f\n\r\t"));
		Assertions.assertEquals("\"\\u0000\\u001f\"", ShardMLGenerator.jsonString("\u0000\u001f"));
		// not escaped: DEL, solidus, non-ASCII (the file is UTF-8)
		Assertions.assertEquals("\"\u007f/bıgınt\"", ShardMLGenerator.jsonString("\u007f/bıgınt"));
	}

	@Test
	public void testJsonOfThePlainModelParses() throws IOException {
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
		JsonObject parsed = parseStrict(json);
		Assertions.assertEquals("mybank", parsed.get("database").getAsString());
		Assertions.assertEquals("customer_id", table(parsed, 1).get("distribution_column").getAsString());
		Assertions.assertEquals("customers", table(parsed, 1).get("colocate_with").getAsString());
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
