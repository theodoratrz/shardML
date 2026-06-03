package uk.ac.kcl.inf.mdd1.tests

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.generator.GeneratorContext
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import uk.ac.kcl.inf.mdd1.generator.ShardMLGenerator
import uk.ac.kcl.inf.mdd1.shardML.Model
import java.io.ByteArrayInputStream

@ExtendWith(InjectionExtension)
@InjectWith(ShardMLInjectorProvider)
class ShardMLGeneratorTest {

    static val TEST_MSQL = '''
        CREATE TABLE customers (
            id BIGINT PRIMARY KEY,
            name VARCHAR(255) NOT NULL
        )
        CREATE TABLE orders (
            id BIGINT PRIMARY KEY,
            customer_id BIGINT NOT NULL,
            total DECIMAL
        )
    '''

    @Inject ParseHelper<Model> parseHelper
    @Inject Provider<XtextResourceSet> rsProvider
    @Inject ShardMLGenerator generator

    def parseShardML(String msql, String shardml) {
        uk.ac.kcl.inf.mdd1.SQLStandaloneSetup.doSetup()
        val rs = rsProvider.get()
        val sqlResource = rs.createResource(URI.createURI("dummy:/test.msql"))
        sqlResource.load(new ByteArrayInputStream(msql.bytes), rs.loadOptions)
        val shardResource = rs.createResource(URI.createURI("dummy:/test.shardml"))
        shardResource.load(new ByteArrayInputStream(shardml.bytes), rs.loadOptions)
        return shardResource.contents.head as Model
    }

    // --- Citus (postgres) output ---

    @Test
    def void testPostgresJsonContainsDatabaseName() {
        val model = parseShardML(TEST_MSQL, '''
            import "test.msql"
            database mybank {
                type: postgres
                shard customers {
                    strategy: hash
                    key: id
                    buckets: 32
                }
            }
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val json = fsa.textFiles.get("DEFAULT_OUTPUT" + "mybank-sharding.json")
        Assertions.assertNotNull(json, "Citus JSON file should be generated")
        Assertions.assertTrue(json.toString.contains('"database": "mybank"'), "JSON should contain database name")
        Assertions.assertTrue(json.toString.contains('"target": "citus"'), "JSON should target citus")
    }

    @Test
    def void testPostgresJsonHashTable() {
        val model = parseShardML(TEST_MSQL, '''
            import "test.msql"
            database mybank {
                type: postgres
                shard customers {
                    strategy: hash
                    key: id
                    buckets: 64
                }
            }
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val json = fsa.textFiles.get("DEFAULT_OUTPUT" + "mybank-sharding.json")
        Assertions.assertTrue(json.toString.contains('"distribution_column": "id"'), "Should include distribution column")
        Assertions.assertTrue(json.toString.contains('"shard_count": 64'), "Should include shard count")
        Assertions.assertTrue(json.toString.contains('"distribution_type": "hash"'), "Should be hash distribution")
    }

    @Test
    def void testPostgresJsonReplicatedTable() {
        val model = parseShardML(TEST_MSQL, '''
            import "test.msql"
            database mybank {
                type: postgres
                shard customers {
                    strategy: replicated
                }
            }
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val json = fsa.textFiles.get("DEFAULT_OUTPUT" + "mybank-sharding.json")
        Assertions.assertTrue(json.toString.contains('"distribution_type": "reference"'), "Replicated table should use reference distribution")
    }

    @Test
    def void testPostgresJsonColocation() {
        val model = parseShardML(TEST_MSQL, '''
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
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val json = fsa.textFiles.get("DEFAULT_OUTPUT" + "mybank-sharding.json")
        Assertions.assertTrue(json.toString.contains('"colocate_with": "customers"'), "Should include colocate_with field")
    }

    @Test
    def void testPostgresSqlDistributionScript() {
        val model = parseShardML(TEST_MSQL, '''
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
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val sql = fsa.textFiles.get("DEFAULT_OUTPUT" + "mybank-distribution.sql")
        Assertions.assertNotNull(sql, "SQL distribution file should be generated")
        Assertions.assertTrue(sql.toString.contains("create_distributed_table"), "Hash table should use create_distributed_table")
        Assertions.assertTrue(sql.toString.contains("create_reference_table"), "Replicated table should use create_reference_table")
    }

    // --- Vitess (mysql) output ---

    @Test
    def void testMysqlJsonContainsKeyspace() {
        val model = parseShardML(TEST_MSQL, '''
            import "test.msql"
            database social {
                type: mysql
                shard customers {
                    strategy: hash
                    key: id
                    buckets: 32
                }
            }
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val json = fsa.textFiles.get("DEFAULT_OUTPUT" + "social-sharding.json")
        Assertions.assertNotNull(json, "Vitess JSON file should be generated")
        Assertions.assertTrue(json.toString.contains('"target": "vitess"'), "JSON should target vitess")
        Assertions.assertTrue(json.toString.contains('"keyspace": "social"'), "JSON should include keyspace")
    }

    @Test
    def void testMysqlJsonHashTable() {
        val model = parseShardML(TEST_MSQL, '''
            import "test.msql"
            database social {
                type: mysql
                shard customers {
                    strategy: hash
                    key: id
                    buckets: 16
                }
            }
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val json = fsa.textFiles.get("DEFAULT_OUTPUT" + "social-sharding.json")
        Assertions.assertTrue(json.toString.contains('"vindex": "id"'), "Should include vindex column")
        Assertions.assertTrue(json.toString.contains('"vindex_type": "hash"'), "Should use hash vindex type")
        Assertions.assertTrue(json.toString.contains('"buckets": 16'), "Should include bucket count")
    }

    @Test
    def void testMysqlSqlFileIsEmpty() {
        val model = parseShardML(TEST_MSQL, '''
            import "test.msql"
            database social {
                type: mysql
                shard customers {
                    strategy: hash
                    key: id
                    buckets: 32
                }
            }
        ''')
        val fsa = new InMemoryFileSystemAccess()
        generator.doGenerate(model.eResource, fsa, new GeneratorContext())
        val sql = fsa.textFiles.get("DEFAULT_OUTPUT" + "social-distribution.sql")
        Assertions.assertNotNull(sql, "SQL file should be generated even for Vitess")
        Assertions.assertTrue(sql.toString.trim.empty, "Vitess SQL file should be empty (Vitess uses VSchema, not SQL DDL)")
    }
}
