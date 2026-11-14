package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.SQLStandaloneSetup;

// The schema's keys against the ShardML layout (Plan-sql step 4):
//  - SHML_024 reads PRIMARY KEY and UNIQUE through SchemaFacts, so a FOREIGN KEY or a CHECK on a
//    sharded table is never mistaken for a UNIQUE that misses the shard key. Before step 4 the
//    check labelled every table constraint that was not a PRIMARY KEY "UNIQUE", so the first test
//    below would fail with "UNIQUE (product_id) .. does not include the shard key";
//  - SHML_069-072, the foreign key rules measured on Citus 14.2 (Plan-sql section 2; the action
//    matrix of SHML_072 in section 11e). Each case names the probe case it encodes. Case 7
//    (local -> hash-sharded) has no check, by decision;
//  - SHML_074, a stored generated column as the shard key (measured: Citus rejects it).
@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class ShardMLForeignKeyValidationTest {

	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private IResourceValidator validator;

	private static final Set<String> CODES = Set.of("SHML_024", "SHML_069", "SHML_070", "SHML_071", "SHML_072",
			"SHML_074");

	// the issues of these codes the model gets, as "code: message", after asserting the schema is clean
	private List<String> issues(String msql, String shards) throws Exception {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = resourceSets.get();
		Resource sql = rs.createResource(URI.createURI("dummy:/s.msql"));
		sql.load(new ByteArrayInputStream(msql.getBytes(StandardCharsets.UTF_8)), rs.getLoadOptions());
		Assertions.assertEquals(List.of(), sql.getErrors(), msql);
		Resource model = rs.createResource(URI.createURI("dummy:/d.shardml"));
		String shardml = "import \"s.msql\"\n\ndatabase d {\n    type: postgres\n" + shards + "}\n";
		model.load(new ByteArrayInputStream(shardml.getBytes(StandardCharsets.UTF_8)), rs.getLoadOptions());
		Assertions.assertEquals(List.of(), model.getErrors(), shardml);
		List<Issue> issues = validator.validate(model, CheckMode.ALL, CancelIndicator.NullImpl);
		return issues.stream().filter(i -> CODES.contains(i.getCode())).map(i -> i.getCode() + ": " + i.getMessage()).toList();
	}

	private List<String> codes(String msql, String shards) throws Exception {
		return issues(msql, shards).stream().map(i -> i.substring(0, i.indexOf(':'))).sorted().toList();
	}

	private static String hash(String table, String key) {
		return "    shard " + table + " {\n        strategy: hash\n        key: " + key + "\n        buckets: 4\n    }\n";
	}

	private static String hash(String table, String key, String colocateWith) {
		return "    shard " + table + " {\n        strategy: hash\n        key: " + key + "\n        buckets: 4\n"
				+ "        colocate_with: " + colocateWith + "\n    }\n";
	}

	private static String replicated(String table) {
		return "    shard " + table + " {\n        strategy: replicated\n    }\n";
	}

	private static final String SHOP = """
			CREATE TABLE products (
			    id INT PRIMARY KEY
			)
			CREATE TABLE orders (
			    customer_id INT,
			    id INT,
			    product_id INT REFERENCES products,
			    quantity INT CHECK (quantity > 0),
			    PRIMARY KEY (customer_id, id),
			    CONSTRAINT orders_product_fkey FOREIGN KEY (product_id) REFERENCES products (id),
			    CHECK (id > 0)
			)
			""";

	@Test
	public void testForeignKeysAndChecksAreNotUniqueKeys() throws Exception {
		// orders is sharded by customer_id; its foreign keys and checks name other columns, and none is a UNIQUE
		Assertions.assertEquals(List.of(), issues(SHOP, hash("orders", "customer_id") + replicated("products")));
		// a real UNIQUE without the shard key still gets SHML_024, column- or table-level
		String unique = SHOP.replace("CHECK (id > 0)", "UNIQUE (product_id)").replace("quantity INT CHECK (quantity > 0)",
				"quantity INT UNIQUE");
		Assertions.assertEquals(List.of(
				"SHML_024: UNIQUE (quantity) on 'orders' does not include the shard key 'customer_id'. Citus enforces uniqueness "
						+ "only within one shard, so it cannot enforce this constraint. Options: enforce it in the application; "
						+ "a lookup table with PRIMARY KEY (quantity), distributed by quantity and written in the same transaction; "
						+ "or, if 'orders' is small, make it a reference table (strategy: replicated).",
				"SHML_024: UNIQUE (product_id) on 'orders' does not include the shard key 'customer_id'. Citus enforces uniqueness "
						+ "only within one shard, so it cannot enforce this constraint. Options: enforce it in the application; "
						+ "a lookup table with PRIMARY KEY (product_id), distributed by product_id and written in the same "
						+ "transaction; or, if 'orders' is small, make it a reference table (strategy: replicated)."),
				issues(unique, hash("orders", "customer_id") + replicated("products")));
		// and the primary key, named or not
		Assertions.assertEquals(List.of("SHML_024"), codes(SHOP.replace("PRIMARY KEY (customer_id, id)",
				"CONSTRAINT orders_pkey PRIMARY KEY (id)"), hash("orders", "customer_id") + replicated("products")));
	}

	private static final String TENANTS = """
			CREATE TABLE companies (
			    id BIGINT PRIMARY KEY
			)
			CREATE TABLE campaigns (
			    company_id BIGINT,
			    id BIGINT,
			    PRIMARY KEY (company_id, id),
			    FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
			)
			CREATE TABLE ads (
			    company_id BIGINT,
			    campaign_id BIGINT,
			    id BIGINT,
			    PRIMARY KEY (company_id, id),
			    FOREIGN KEY (campaign_id, company_id) REFERENCES campaigns (id, company_id)
			)
			""";

	@Test
	public void testColocatedForeignKeysThatPairTheShardKeys() throws Exception {
		// case 1 (one column), case 15 (composite, the shard keys at different positions), case 13
		// (ON DELETE CASCADE on the distribution column)
		Assertions.assertEquals(List.of(), issues(TENANTS, hash("companies", "id") + hash("campaigns", "company_id", "companies")
				+ hash("ads", "company_id", "campaigns")));
	}

	@Test
	public void testKeysNotPaired() throws Exception {
		// case 3: ads is sharded by campaign_id, which the key pairs with campaigns.id, not with campaigns.company_id
		// (its primary key also misses that shard key: SHML_024, not the subject here)
		Assertions.assertEquals(List.of("SHML_069: Foreign key ads (campaign_id, company_id) -> campaigns (id, company_id) "
				+ "does not pair the shard key ads.campaign_id with the shard key campaigns.company_id. Citus accepts a foreign "
				+ "key between two hash-sharded tables only when it does, because only then is every referenced row in the "
				+ "same shard. Shard 'ads' by the column that references campaigns.company_id, or replicate 'campaigns'."),
				issues(TENANTS, hash("companies", "id") + hash("campaigns", "company_id", "companies")
						+ hash("ads", "campaign_id", "campaigns")).stream().filter(i -> i.startsWith("SHML_069")).toList());
		// a self-reference is no exception
		Assertions.assertEquals(List.of("SHML_069"), codes("""
				CREATE TABLE employees (
				    id INT PRIMARY KEY,
				    manager_id INT REFERENCES employees
				)
				""", hash("employees", "id")));
	}

	@Test
	public void testPairedButNotColocated() throws Exception {
		// case 4: both pair, but ads is in a colocation group of its own
		Assertions.assertEquals(List.of("SHML_070: Foreign key ads (campaign_id, company_id) -> campaigns (id, company_id) "
				+ "pairs the shard keys of 'ads' and 'campaigns', but the tables are not colocated, and Citus requires it. "
				+ "Add `colocate_with: campaigns` to 'ads'."),
				issues(TENANTS, hash("companies", "id") + hash("campaigns", "company_id", "companies") + hash("ads", "company_id")));
	}

	@Test
	public void testReferenceAndLocalTables() throws Exception {
		// case 2: hash-sharded -> replicated is fine
		Assertions.assertEquals(List.of(), issues(TENANTS, replicated("companies") + hash("campaigns", "company_id")
				+ hash("ads", "company_id", "campaigns")));
		// case 5: replicated -> hash-sharded is not
		Assertions.assertEquals(List.of("SHML_071: Foreign key campaigns (company_id) -> companies (id) goes from the "
				+ "replicated table 'campaigns' to the hash-sharded table 'companies'. Citus does not support a foreign key from "
				+ "a reference table to a distributed one: every node holds all of 'campaigns', but only some of 'companies'. "
				+ "Shard 'campaigns' too, or replicate 'companies'."),
				issues(TENANTS, hash("companies", "id") + replicated("campaigns")));
		// replicated -> replicated is fine
		Assertions.assertEquals(List.of(), issues(TENANTS, replicated("companies") + replicated("campaigns")));
		// case 7, by decision: a key from or to a table the model does not shard is not checked
		Assertions.assertEquals(List.of(), issues(TENANTS, hash("companies", "id")));
		Assertions.assertEquals(List.of(), issues(TENANTS, hash("campaigns", "company_id")));
	}

	// what Citus 14.2 did with each action on a key over the child's distribution column, to a
	// hash-sharded parent (layout A of the measurement) and to a reference table (layout B): the same
	// in both. Everything else was accepted
	private static final List<String> REJECTED = List.of("ON DELETE SET NULL", "ON DELETE SET DEFAULT",
			"ON UPDATE SET NULL", "ON UPDATE SET DEFAULT", "ON UPDATE CASCADE");
	private static final List<String> ACCEPTED = List.of("ON DELETE CASCADE", "ON DELETE RESTRICT", "ON DELETE NO ACTION",
			"ON UPDATE RESTRICT", "ON UPDATE NO ACTION");

	@Test
	public void testActionsThatWriteTheShardKey() throws Exception {
		for (String action : REJECTED) {
			String msql = TENANTS.replace("ON DELETE CASCADE", action);
			Assertions.assertEquals(List.of("SHML_072: Foreign key campaigns (company_id) -> companies (id) has " + action
					+ ", which would write its columns, and they include the shard key campaigns.company_id. Citus rejects "
					+ "a foreign key that writes a distribution column: ON DELETE SET NULL / SET DEFAULT and ON UPDATE SET "
					+ "NULL / SET DEFAULT / CASCADE. Use ON DELETE CASCADE, RESTRICT or NO ACTION, and ON UPDATE RESTRICT "
					+ "or NO ACTION."),
					issues(msql, hash("companies", "id") + hash("campaigns", "company_id", "companies")), action);
			// to a reference table too: the column is still the distribution column (layout B)
			Assertions.assertEquals(List.of("SHML_072"), codes(msql, replicated("companies") + hash("campaigns", "company_id")),
					action);
			// not when the key leaves the shard key out (layout C: every action accepted)
			Assertions.assertEquals(List.of(), codes(msql, replicated("companies") + hash("campaigns", "id")), action);
			// nor between two reference tables, which have no distribution column
			Assertions.assertEquals(List.of(), codes(msql, replicated("companies") + replicated("campaigns")), action);
		}
		for (String action : ACCEPTED) {
			String msql = TENANTS.replace("ON DELETE CASCADE", action);
			Assertions.assertEquals(List.of(), codes(msql, hash("companies", "id") + hash("campaigns", "company_id", "companies")),
					action);
			Assertions.assertEquals(List.of(), codes(msql, replicated("companies") + hash("campaigns", "company_id")), action);
		}
		// both written: the message names both (layout D, composite, is the same rule)
		String both = TENANTS.replace("FOREIGN KEY (campaign_id, company_id) REFERENCES campaigns (id, company_id)",
				"FOREIGN KEY (campaign_id, company_id) REFERENCES campaigns (id, company_id) ON DELETE SET DEFAULT ON UPDATE CASCADE");
		Assertions.assertTrue(issues(both, hash("companies", "id") + hash("campaigns", "company_id", "companies")
				+ hash("ads", "company_id", "campaigns")).stream().anyMatch(i -> i.startsWith("SHML_072: Foreign key ads "
						+ "(campaign_id, company_id) -> campaigns (id, company_id) has ON DELETE SET DEFAULT and ON UPDATE CASCADE,")));
	}

	@Test
	public void testStoredGeneratedShardKey() throws Exception {
		String msql = """
				CREATE TABLE events (
				    tenant_id INT GENERATED ALWAYS AS (id % 16) STORED,
				    id INT GENERATED ALWAYS AS IDENTITY,
				    seq BIGINT GENERATED BY DEFAULT AS IDENTITY,
				    PRIMARY KEY (tenant_id, id)
				)
				""";
		Assertions.assertEquals(List.of("SHML_074: Shard key 'tenant_id' of 'events' is a stored generated column "
				+ "(GENERATED ALWAYS AS (..) STORED). Citus rejects it as a distribution column. Shard by a column the "
				+ "application writes; an identity column (GENERATED .. AS IDENTITY) is fine."),
				issues(msql, hash("events", "tenant_id")));
		// an identity shard key is fine (measured: int or bigint, ALWAYS or BY DEFAULT); seq gets SHML_024 only, as
		// the primary key leaves it out
		Assertions.assertEquals(List.of(), codes(msql, hash("events", "id")));
		Assertions.assertEquals(List.of("SHML_024"), codes(msql, hash("events", "seq")));
		// a replicated table has no distribution column
		Assertions.assertEquals(List.of(), codes(msql, replicated("events")));
	}

	@Test
	public void testKeysFromAlterTableAndUniqueIndexes() throws Exception {
		// pg_dump's shape (step 5): bare tables, keys in ALTER TABLE, unique indexes. SHML_024 and the
		// foreign key rules read them exactly as if they were declared inline
		String dump = """
				CREATE TABLE companies (id bigint NOT NULL);
				CREATE TABLE campaigns (company_id bigint NOT NULL, id bigint NOT NULL, code text);
				ALTER TABLE ONLY companies ADD CONSTRAINT companies_pkey PRIMARY KEY (id);
				ALTER TABLE ONLY campaigns ADD CONSTRAINT campaigns_pkey PRIMARY KEY (company_id, id);
				ALTER TABLE ONLY campaigns ADD CONSTRAINT campaigns_company_fkey FOREIGN KEY (company_id) REFERENCES companies(id);
				CREATE UNIQUE INDEX campaigns_code_key ON campaigns USING btree (code);
				""";
		Assertions.assertEquals(List.of("SHML_024: UNIQUE (code) on 'campaigns' does not include the shard key 'company_id'. "
				+ "Citus enforces uniqueness only within one shard, so it cannot enforce this constraint. Options: enforce it in "
				+ "the application; a lookup table with PRIMARY KEY (code), distributed by code and written in the same "
				+ "transaction; or, if 'campaigns' is small, make it a reference table (strategy: replicated)."),
				issues(dump, hash("companies", "id") + hash("campaigns", "company_id", "companies")));
		// sharded by code: the ALTER TABLE primary key misses it (SHML_024; the unique index has it), and
		// the ALTER TABLE foreign key does not pair it with companies.id (SHML_069)
		Assertions.assertEquals(List.of("SHML_024", "SHML_069"),
				codes(dump, hash("companies", "id") + hash("campaigns", "code", "companies")));
		// with the index unique on the shard key too, nothing; a partial or plain index is no key
		Assertions.assertEquals(List.of(), codes(dump.replace("btree (code)", "btree (company_id, code)"),
				hash("companies", "id") + hash("campaigns", "company_id", "companies")));
		Assertions.assertEquals(List.of(), codes(dump.replace("CREATE UNIQUE INDEX", "CREATE INDEX"),
				hash("companies", "id") + hash("campaigns", "company_id", "companies")));
	}
}
