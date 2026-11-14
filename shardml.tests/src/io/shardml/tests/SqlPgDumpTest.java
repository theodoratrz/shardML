package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.SQLStandaloneSetup;
import io.shardml.generator.ShardMLGenerator;
import io.shardml.schema.SchemaFacts;
import io.shardml.schema.SchemaFacts.Action;
import io.shardml.schema.SchemaFacts.ForeignKeyFacts;
import io.shardml.schema.SchemaFacts.TableFacts;
import io.shardml.sql.Schema;

// pg_dump input (Plan-sql step 5): ALTER TABLE .. ADD constraint, CREATE [UNIQUE] INDEX, CREATE
// SCHEMA, the preamble (one INFO issue per file, SHML_075) and every other statement (a located
// SHML_061). Each modelled construct parses with no issue, is read by SchemaFacts as the same key
// declared inline would be, is generated (keys inline, foreign keys in their own file, indexes after
// the tables) and round-trips: the generated text parses to the same facts and generates the same
// text again.
@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class SqlPgDumpTest {

	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private ShardMLGenerator generator;

	private int n;

	private Resource load(String text) throws Exception {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = resourceSets.get();
		Resource r = rs.createResource(URI.createURI("dummy:/s" + (n++) + ".msql"));
		r.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), rs.getLoadOptions());
		return r;
	}

	private static List<Issue> validate(Resource r) {
		return ((XtextResource) r).getResourceServiceProvider().getResourceValidator().validate(r, CheckMode.ALL,
				CancelIndicator.NullImpl);
	}

	// every issue as "line: code: message", in line order
	private List<String> issues(String msql) throws Exception {
		return validate(load(msql)).stream().sorted(java.util.Comparator.comparing(Issue::getLineNumber))
				.map(i -> i.getLineNumber() + ": " + i.getCode() + ": " + i.getMessage()).toList();
	}

	private List<String> codes(String msql) throws Exception {
		return validate(load(msql)).stream().map(Issue::getCode).toList();
	}

	private Schema parseClean(String msql) throws Exception {
		Resource r = load(msql);
		Assertions.assertEquals(List.of(), validate(r).stream().map(i -> i.getCode() + ": " + i.getMessage()).toList(), msql);
		return (Schema) r.getContents().get(0);
	}

	// the generated schema, then the generated foreign keys
	private String generate(Schema schema) {
		return generator.schemaDefinitions(schema) + "--\n" + generator.foreignKeyDefinitions("d", schema);
	}

	// the generated text parses with no issue to the same facts, and generates the same text again
	private void assertRoundTrips(Schema schema) throws Exception {
		String emitted = generate(schema);
		Schema again = parseClean(emitted.replace("--\n", ""));
		Assertions.assertEquals(SchemaFacts.of(schema), SchemaFacts.of(again), emitted);
		Assertions.assertEquals(emitted, generate(again));
	}

	private static final String HEADER = "-- Foreign keys of d. Apply after d-distribution.sql: Citus accepts a\n"
			+ "-- foreign key only once the tables on both sides are distributed or reference tables.\n";

	@Test
	public void testAlterTableAddsKeys() throws Exception {
		// pg_dump's shape: the tables bare, then every key in ALTER TABLE ONLY .. ADD CONSTRAINT, a
		// foreign key before the primary key it references, which needs no column list
		Schema schema = parseClean("""
				CREATE SCHEMA shop;
				CREATE TABLE shop.orders (
				    customer_id bigint NOT NULL,
				    id bigint NOT NULL,
				    product_id integer,
				    quantity integer
				);
				CREATE TABLE shop.products (
				    id integer NOT NULL,
				    sku text
				);
				ALTER TABLE ONLY shop.orders
				    ADD CONSTRAINT orders_product_fkey FOREIGN KEY (product_id) REFERENCES shop.products ON DELETE RESTRICT;
				ALTER TABLE ONLY shop.orders
				    ADD CONSTRAINT orders_pkey PRIMARY KEY (customer_id, id);
				ALTER TABLE IF EXISTS shop.orders ADD CHECK (quantity > 0);
				ALTER TABLE shop.products ADD CONSTRAINT products_pkey PRIMARY KEY (id);
				alter table only shop.products add unique (sku);
				""");
		TableFacts orders = SchemaFacts.of(schema).get("orders");
		Assertions.assertEquals(List.of("customer_id", "id"), orders.primaryKey());
		Assertions.assertEquals(List.of(), orders.uniqueKeys(), "a CHECK and a FOREIGN KEY are no UNIQUE");
		Assertions.assertEquals(List.of(new ForeignKeyFacts("orders_product_fkey", "orders", List.of("product_id"), "products",
				List.of("id"), false, Action.RESTRICT, null)), orders.foreignKeys());
		TableFacts products = SchemaFacts.of(schema).get("products");
		Assertions.assertEquals(List.of("id"), products.primaryKey());
		Assertions.assertEquals(List.of(List.of("sku")), products.uniqueKeys());
		Assertions.assertFalse(products.column("id").nullable());

		// the keys inline, in the table they belong to; the foreign key in its own file
		Assertions.assertEquals("""
				CREATE SCHEMA IF NOT EXISTS shop;

				CREATE TABLE shop.orders (
				    customer_id BIGINT NOT NULL,
				    id BIGINT NOT NULL,
				    product_id INTEGER,
				    quantity INTEGER,
				    CONSTRAINT orders_pkey PRIMARY KEY (customer_id, id),
				    CHECK (quantity > 0)
				);

				CREATE TABLE shop.products (
				    id INTEGER NOT NULL,
				    sku TEXT,
				    CONSTRAINT products_pkey PRIMARY KEY (id),
				    UNIQUE (sku)
				);
				--
				""" + HEADER + """
				ALTER TABLE shop.orders ADD CONSTRAINT orders_product_fkey FOREIGN KEY (product_id) REFERENCES shop.products ON DELETE RESTRICT;
				""", generate(schema));
		assertRoundTrips(schema);
	}

	@Test
	public void testIndexes() throws Exception {
		Schema schema = parseClean("""
				CREATE TABLE t (
				    a int,
				    b text,
				    c int,
				    "Mixed" int
				);
				CREATE UNIQUE INDEX t_a_key ON t USING btree (a);
				create index if not exists t_b_idx on t (b text_pattern_ops DESC NULLS LAST, "Mixed");
				CREATE INDEX ON t ((lower(b)));
				CREATE UNIQUE INDEX t_c_partial ON t (c) WHERE (c > 0);
				CREATE UNIQUE INDEX t_lower_b ON t ((lower(b)), a);
				CREATE INDEX t_cover ON t USING btree (a) INCLUDE (b) WITH (fillfactor = 70) WHERE (a IS NOT NULL);
				""");
		// a unique index on plain columns is a unique key; a partial or expression one is not (a
		// foreign key cannot reference it)
		Assertions.assertEquals(List.of(List.of("a")), SchemaFacts.of(schema).get("t").uniqueKeys());
		Assertions.assertEquals("""
				CREATE TABLE t (
				    a INT,
				    b TEXT,
				    c INT,
				    "Mixed" INT
				);

				CREATE UNIQUE INDEX t_a_key ON t USING btree (a);
				CREATE INDEX IF NOT EXISTS t_b_idx ON t (b text_pattern_ops DESC NULLS LAST, "Mixed");
				CREATE INDEX ON t ((lower(b)));
				CREATE UNIQUE INDEX t_c_partial ON t (c) WHERE (c > 0);
				CREATE UNIQUE INDEX t_lower_b ON t ((lower(b)), a);
				CREATE INDEX t_cover ON t USING btree (a) INCLUDE (b) WITH (fillfactor = 70) WHERE (a IS NOT NULL);
				--
				""", generate(schema));
		assertRoundTrips(schema);
		// a foreign key may reference the unique index's column
		parseClean("CREATE TABLE t (a int);\nCREATE UNIQUE INDEX ON t (a);\nCREATE TABLE u (a int REFERENCES t (a));\n");
	}

	@Test
	public void testPreambleIsOneInfoAndNotWrittenBack() throws Exception {
		String msql = """
				\\restrict AbC123
				SET statement_timeout = 0;
				SET client_encoding = 'UTF8';
				SELECT pg_catalog.set_config('search_path', '', false);
				DROP TABLE IF EXISTS t CASCADE;
				drop table u;
				CREATE TABLE t (a int);
				ALTER TABLE t OWNER TO postgres;
				ALTER TABLE public.some_sequence_or_view OWNER TO postgres;
				ALTER SCHEMA public OWNER TO "Owner";
				ALTER FUNCTION public.f(integer, text) OWNER TO postgres;
				GRANT ALL ON SCHEMA public TO PUBLIC;
				REVOKE USAGE ON SCHEMA public FROM PUBLIC;
				COMMENT ON TABLE t IS 'a; b';
				\\unrestrict AbC123
				""";
		// on the file's first statement
		Assertions.assertEquals(List.of("1: SHML_075: Not written back: 2 SET, 1 set_config, 4 ALTER .. OWNER TO, 2 psql "
				+ "restrict / unrestrict, 1 GRANT, 1 REVOKE, 1 COMMENT ON, 2 DROP TABLE. Session settings, ownership, privileges, "
				+ "comments and DROP TABLE cleanup (the pg_dump preamble): the generated schema needs none of them."),
				issues(msql));
		Schema schema = (Schema) load(msql).getContents().get(0);
		Assertions.assertEquals("CREATE TABLE t (\n    a INT\n);\n--\n", generate(schema));
		// a file without any gets none
		Assertions.assertEquals(List.of(), issues("CREATE TABLE t (a int);"));
	}

	@Test
	public void testEveryOtherStatementIsALocatedError() throws Exception {
		String migration = "Functions, procedures, triggers, views, rules and aggregates do not affect sharding and are not "
				+ "modelled: keep them in a separate migration, applied after the generated files.";
		String sequences = "Sequences are not modelled yet; they are the planned follow-up to pg_dump input (Plan-sql "
				+ "section 3). Until then, declare the column SERIAL / BIGSERIAL or GENERATED .. AS IDENTITY and leave the "
				+ "sequence statements out.";
		String types = "User-defined types, domains and extensions are not supported: a column type must be a built-in "
				+ "type of the .msql grammar.";
		String partitions = "Partitioned tables are not supported: declare the table without PARTITION BY, and leave its "
				+ "partitions out.";
		String other = "Only CREATE TABLE, ALTER TABLE .. ADD constraint, CREATE [UNIQUE] INDEX, CREATE SCHEMA and the "
				+ "pg_dump preamble are read from a schema file; remove the statement or apply it separately.";
		Assertions.assertEquals(List.of(
				"2: SHML_061: Unsupported statement CREATE FUNCTION. " + migration,
				"5: SHML_061: Unsupported statement CREATE OR REPLACE FUNCTION. " + migration,
				"6: SHML_061: Unsupported statement CREATE VIEW. " + migration,
				"7: SHML_061: Sequence statement CREATE SEQUENCE. " + sequences,
				"8: SHML_061: Sequence statement ALTER SEQUENCE. " + sequences,
				"9: SHML_061: Unsupported statement CREATE TYPE. " + types,
				"10: SHML_061: Unsupported statement CREATE EXTENSION. " + types,
				"12: SHML_061: Unsupported statement PARTITION BY. " + partitions,
				"13: SHML_061: Unsupported statement ALTER TABLE .. ATTACH PARTITION. " + partitions,
				"14: SHML_061: Sequence statement ALTER TABLE .. ALTER. " + sequences,
				"15: SHML_061: Unsupported statement ALTER TABLE .. ALTER. " + other,
				"16: SHML_061: Unsupported statement DROP VIEW. Only DROP TABLE is recognised (and left out of the generated "
						+ "schema).",
				"17: SHML_061: Unsupported statement INSERT. " + other,
				"18: SHML_061: Unsupported statement SELECT. " + other,
				"19: SHML_061: Unsupported statement psql meta-command \\connect. psql meta-commands are not SQL: remove it "
						+ "from the schema file.",
				"20: SHML_061: Unsupported statement CREATE TEMP. " + other),
				issues("""
						CREATE TABLE t (a int, b int);
						CREATE FUNCTION f() RETURNS int LANGUAGE sql AS $$
						    SELECT 1; SELECT 2;
						$$;
						CREATE OR REPLACE FUNCTION g() RETURNS int LANGUAGE sql AS $_$ SELECT 1; $_$;
						CREATE VIEW v AS SELECT a FROM t;
						CREATE SEQUENCE t_a_seq;
						ALTER SEQUENCE t_a_seq OWNED BY t.a;
						CREATE TYPE mood AS ENUM ('sad', 'ok');
						CREATE EXTENSION IF NOT EXISTS vector WITH SCHEMA public;
						CREATE TABLE p (a int)
						PARTITION BY RANGE (a);
						ALTER TABLE ONLY p ATTACH PARTITION p1 FOR VALUES FROM (1) TO (2);
						ALTER TABLE ONLY t ALTER COLUMN a SET DEFAULT nextval('t_a_seq'::regclass);
						ALTER TABLE t ALTER COLUMN b SET NOT NULL;
						DROP VIEW v;
						INSERT INTO t VALUES (1, 2);
						SELECT 1;
						\\connect other
						CREATE TEMP TABLE scratch (a int);
						"""));
	}

	@Test
	public void testABrokenCreateTableIsOneSyntaxError() throws Exception {
		// the parser resumes after an unsupported column type; the rest of the statement is not an
		// unsupported statement of its own, and the statements after it are read as usual
		Assertions.assertEquals(List.of("org.eclipse.xtext.diagnostics.Diagnostic.Syntax", "SHML_061"), codes("""
				CREATE TABLE film (
				    id int,
				    rating public.mpaa_rating DEFAULT 'G'::public.mpaa_rating
				);
				CREATE VIEW v AS SELECT 1;
				"""));
	}

	@Test
	public void testKeysOfAlterTableAreChecked() throws Exception {
		// unknown table, unknown column, and the columns of an unknown table get no message of their own
		Assertions.assertEquals(List.of(
				"2: SHML_062: Unknown table nope: no table of that name in this file.",
				"3: SHML_062: Unknown table t: no table of that name in schema other of this file.",
				"4: SHML_063: Unknown column zz in table t.",
				"5: SHML_062: Unknown table nope: no table of that name in this file.",
				"6: SHML_063: Unknown column zz in table t."),
				issues("""
						CREATE TABLE t (a int);
						ALTER TABLE nope ADD PRIMARY KEY (a, b);
						ALTER TABLE other.t ADD PRIMARY KEY (a);
						ALTER TABLE t ADD UNIQUE (zz);
						CREATE INDEX ON nope (a);
						CREATE INDEX ON t (zz);
						"""));
		// a second PRIMARY KEY, however declared, is SHML_067 at the second; a foreign key to columns that
		// ALTER TABLE makes a key is fine, and one to columns that are none is SHML_065
		Assertions.assertEquals(List.of("SHML_067"), codes("""
				CREATE TABLE t (a int PRIMARY KEY, b int);
				ALTER TABLE t ADD CONSTRAINT t_pkey PRIMARY KEY (b);
				"""));
		Assertions.assertEquals(List.of("SHML_065"), codes("""
				CREATE TABLE p (a int, b int);
				CREATE TABLE c (x int);
				ALTER TABLE p ADD UNIQUE (a);
				ALTER TABLE c ADD FOREIGN KEY (x) REFERENCES p (a);
				ALTER TABLE c ADD FOREIGN KEY (x) REFERENCES p (b);
				"""));
	}

	@Test
	public void testDollarQuotesAndSemicolons() throws Exception {
		// pg_dump's tags: a ';' inside the body does not end the statement
		for (String tag : List.of("$$", "$_$", "$__$"))
			Assertions.assertEquals(List.of("SHML_061"),
					codes("CREATE FUNCTION f() RETURNS void LANGUAGE plpgsql AS " + tag + " BEGIN; END; " + tag + ";\n"), tag);
		// statements without ';' between them still read as in .msql files, for the modelled ones
		parseClean("CREATE TABLE t (a int)\nCREATE TABLE u (a int)\nALTER TABLE u ADD PRIMARY KEY (a)\nCREATE INDEX ON t (a)\n"
				+ "CREATE SCHEMA s\n");
	}

	@Test
	public void testSearchPath() throws Exception {
		// pg_dump's own, empty (every name it writes is qualified), and DEFAULT: preamble, the INFO only
		Assertions.assertEquals(List.of("SHML_075"), codes("""
				SELECT pg_catalog.set_config('search_path', '', false);
				SET search_path = '';
				set search_path to default;
				CREATE TABLE t (a int);
				"""));
		Assertions.assertTrue(issues("SET search_path = '';\nCREATE TABLE t (a int);").get(0)
				.startsWith("1: SHML_075: Not written back: 1 SET."));
		// a non-empty path decides where the unqualified CREATE TABLEs land, and is not written back: a
		// warning on each, and they are not counted in the INFO; the other SETs still are
		String after = " is not written back, so the unqualified names in the generated schema resolve against the "
				+ "connection's default schema instead (usually public). Qualify the tables (CREATE TABLE s.t), or apply "
				+ "the generated files with that search_path.";
		Assertions.assertEquals(List.of(
				"1: SHML_075: Not written back: 1 SET. Session settings, ownership, privileges, comments and DROP TABLE "
						+ "cleanup (the pg_dump preamble): the generated schema needs none of them.",
				"1: SHML_076: SET search_path to shop, public" + after,
				"2: SHML_076: SET search_path to \"Shop\"" + after,
				"3: SHML_076: SET search_path to = 'x'".replace("to = ", "to ") + after,
				"4: SHML_076: set_config('search_path', ..) to 'shop, public'" + after),
				issues("""
						SET search_path = shop, public;
						SET LOCAL search_path TO "Shop";
						SET SESSION search_path = 'x';
						SELECT pg_catalog.set_config('search_path', 'shop, public', false);
						SET client_encoding = 'UTF8';
						CREATE TABLE t (a int);
						"""));
	}

	@Test
	public void testLimitsAreLocatedErrors() throws Exception {
		// (a) a dollar quote with another tag than pg_dump's: the statement ends at the first ';' of the
		// body, which gives located errors at the function and in its body, and the statements after it
		// are still read
		Resource r = load("""
				CREATE TABLE before_it (a int);
				CREATE FUNCTION f() RETURNS int LANGUAGE plpgsql AS $body$
				BEGIN
				    RETURN 1;
				END;
				$body$;
				CREATE TABLE after_it (a int);
				ALTER TABLE after_it ADD PRIMARY KEY (a);
				""");
		String tag = ". It uses the dollar quote $body$, which is not supported (only $$, $_$ and $__$, the tags pg_dump "
				+ "writes): the statement was cut at the first ';' of the quoted body, and the rest of the body reads as "
				+ "statements of their own. Keep functions in a separate migration, applied after the generated files.";
		Assertions.assertEquals(List.of(
				"2: SHML_061: Unsupported statement CREATE FUNCTION" + tag,
				"5: SHML_061: Unsupported statement END. Only CREATE TABLE, ALTER TABLE .. ADD constraint, CREATE [UNIQUE] "
						+ "INDEX, CREATE SCHEMA and the pg_dump preamble are read from a schema file; remove the statement or "
						+ "apply it separately.",
				"6: SHML_061: Unsupported statement $body$" + tag),
				validate(r).stream().sorted(java.util.Comparator.comparing(Issue::getLineNumber))
						.map(i -> i.getLineNumber() + ": " + i.getCode() + ": " + i.getMessage()).toList());
		Schema schema = (Schema) r.getContents().get(0);
		Assertions.assertEquals(List.of("before_it", "after_it"), schema.getTables().stream().map(t -> t.getName()).toList());
		Assertions.assertEquals(List.of("a"), SchemaFacts.of(schema).get("after_it").primaryKey());
		// (b) index clauses outside the subset: each a located error that says what it is. NULLS and
		// TABLESPACE end the index (';' is optional) and read as statements of their own; lower(b)
		// reads as a column lower with options (b)
		Assertions.assertEquals(List.of(
				"2: SHML_061: Unsupported statement NULLS NOT. It is a clause of the statement before it, which the grammar "
						+ "does not support there (CREATE INDEX .. NULLS [NOT] DISTINCT, TABLESPACE): leave it out.",
				"3: SHML_061: Unsupported statement TABLESPACE fast. It is a clause of the statement before it, which the "
						+ "grammar does not support there (CREATE INDEX .. NULLS [NOT] DISTINCT, TABLESPACE): leave it out.",
				"4: SHML_063: Unknown column lower in table t. If lower(b) is an expression, write it in parentheses, as "
						+ "pg_dump does: (lower(b))."), issues("""
				CREATE TABLE t (a int, b text);
				CREATE UNIQUE INDEX t_a ON t (a) NULLS NOT DISTINCT;
				CREATE INDEX t_b ON t (b) TABLESPACE fast;
				CREATE INDEX t_lower ON t (lower(b));
				CREATE TABLE u (a int);
				"""));
	}
}
