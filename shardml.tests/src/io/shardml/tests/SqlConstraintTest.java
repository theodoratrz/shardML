package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import io.shardml.schema.SchemaFacts.ColumnFacts;
import io.shardml.schema.SchemaFacts.ForeignKeyFacts;
import io.shardml.schema.SchemaFacts.TableFacts;
import io.shardml.sql.Schema;

// Constraints of Plan-sql step 4 (section 4.5): named constraints, explicit NULL, DEFAULT, CHECK,
// GENERATED, WITH (..) table options and foreign keys. Each construct parses with no issue, is
// read by SchemaFacts, is generated (foreign keys into their own file), and round-trips: the
// generated text, with its foreign keys put back into their tables, parses to the same facts, and
// generating it again gives the same text. Then what stays a syntax error, and SHML_062-068.
@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class SqlConstraintTest {

	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private ShardMLGenerator generator;

	private Resource load(XtextResourceSet rs, String name, String text) throws Exception {
		SQLStandaloneSetup.doSetup();
		Resource r = rs.createResource(URI.createURI("dummy:/" + name));
		r.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), rs.getLoadOptions());
		return r;
	}

	private Resource load(String text) throws Exception {
		return load(resourceSets.get(), "s.msql", text);
	}

	// all the issues of a schema (syntax, linking, validation) as "code: message"
	private static List<String> issues(Resource r) {
		List<Issue> issues = ((XtextResource) r).getResourceServiceProvider().getResourceValidator().validate(r,
				CheckMode.ALL, CancelIndicator.NullImpl);
		return issues.stream().map(i -> i.getCode() + ": " + i.getMessage()).toList();
	}

	private Schema parseClean(String msql) throws Exception {
		Resource r = load(msql);
		Assertions.assertEquals(List.of(), issues(r), msql);
		return (Schema) r.getContents().get(0);
	}

	private static final String PARENT = """
			CREATE TABLE parent (
			    id INT PRIMARY KEY,
			    x INT,
			    y INT,
			    UNIQUE (x, y)
			)
			""";

	private static final String PARENT_OUT = """
			CREATE TABLE parent (
			    id INT PRIMARY KEY,
			    x INT,
			    y INT,
			    UNIQUE (x, y)
			);
			""";

	// a line of table t (after its columns a and b), and options after it; what t is written as;
	// and the foreign key file
	private record Case(String line, String options, String table, String foreignKeys) {
	}

	private static Case c(String line, String written) {
		return new Case(line, "", "    " + written, "");
	}

	private static Case fk(String line, String written, String foreignKey) {
		return new Case(line, "", "    " + written, foreignKey + "\n");
	}

	private static final String HEADER = "-- Foreign keys of d. Apply after d-distribution.sql: Citus accepts a\n"
			+ "-- foreign key only once the tables on both sides are distributed or reference tables.\n";

	private static final List<Case> CASES = List.of(
			// explicit NULL, named constraints (quoted where the source quoted them)
			c("c INT NULL", "c INT NULL"),
			c("c int constraint c_nn not null", "c INT CONSTRAINT c_nn NOT NULL"),
			c("c INT CONSTRAINT c_key UNIQUE", "c INT CONSTRAINT c_key UNIQUE"),
			c("c INT CONSTRAINT \"C Key\" UNIQUE", "c INT CONSTRAINT \"C Key\" UNIQUE"),
			c("CONSTRAINT t_pkey PRIMARY KEY (a)", "CONSTRAINT t_pkey PRIMARY KEY (a)"),
			c("CONSTRAINT \"t_ab\" UNIQUE (a, b)", "CONSTRAINT \"t_ab\" UNIQUE (a, b)"),
			// DEFAULT: the expression as written, comments dropped and whitespace collapsed
			c("c INT DEFAULT 0", "c INT DEFAULT 0"),
			c("c INT DEFAULT -1", "c INT DEFAULT -1"),
			c("c INT DEFAULT NULL", "c INT DEFAULT NULL"),
			c("c NUMERIC(5,2) DEFAULT 1.50 NOT NULL", "c NUMERIC(5,2) DEFAULT 1.50 NOT NULL"),
			c("c TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", "c TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"),
			c("c TEXT DEFAULT 'it''s' -- a comment\n        NOT NULL", "c TEXT DEFAULT 'it''s' NOT NULL"),
			c("c TEXT DEFAULT 'x'::text", "c TEXT DEFAULT 'x'::text"),
			c("c TIMESTAMPTZ DEFAULT now()   AT\n TIME ZONE 'utc'", "c TIMESTAMPTZ DEFAULT now() AT TIME ZONE 'utc'"),
			c("c TIMESTAMPTZ DEFAULT '2026-01-01'::timestamp with time zone",
					"c TIMESTAMPTZ DEFAULT '2026-01-01'::timestamp with time zone"),
			c("c TEXT DEFAULT lower( 'A' /* inline */ ) UNIQUE", "c TEXT DEFAULT lower( 'A' ) UNIQUE"),
			c("c INT CONSTRAINT c_def DEFAULT (1 + 2) NULL", "c INT CONSTRAINT c_def DEFAULT (1 + 2) NULL"),
			// CHECK, column and table level; keywords and parentheses inside the text
			c("c INT CHECK (c > 0)", "c INT CHECK (c > 0)"),
			c("c INT CONSTRAINT c_range CHECK (c > 0 AND c < 10)", "c INT CONSTRAINT c_range CHECK (c > 0 AND c < 10)"),
			c("c TEXT CHECK (c IS NOT NULL AND c <> ')')", "c TEXT CHECK (c IS NOT NULL AND c <> ')')"),
			c("CHECK (a IN (1, 2) OR b IS NULL)", "CHECK (a IN (1, 2) OR b IS NULL)"),
			c("CONSTRAINT a_lt_b CHECK (a < b)", "CONSTRAINT a_lt_b CHECK (a < b)"),
			// GENERATED
			c("id BIGINT GENERATED ALWAYS AS IDENTITY", "id BIGINT GENERATED ALWAYS AS IDENTITY"),
			c("id BIGINT generated by default as identity (start with 10 increment by 5) PRIMARY KEY",
					"id BIGINT GENERATED by default as identity (start with 10 increment by 5) PRIMARY KEY"),
			c("c NUMERIC GENERATED ALWAYS AS (a * 2) STORED", "c NUMERIC GENERATED ALWAYS AS (a * 2) STORED"),
			// foreign keys: column-level and table-level, written to the foreign key file as given
			fk("p INT REFERENCES parent", "p INT", "ALTER TABLE t ADD FOREIGN KEY (p) REFERENCES parent;"),
			fk("p INT NOT NULL REFERENCES parent (id) ON DELETE CASCADE", "p INT NOT NULL",
					"ALTER TABLE t ADD FOREIGN KEY (p) REFERENCES parent (id) ON DELETE CASCADE;"),
			fk("p INT CONSTRAINT t_p_fkey REFERENCES parent (id) ON UPDATE NO ACTION NOT NULL", "p INT NOT NULL",
					"ALTER TABLE t ADD CONSTRAINT t_p_fkey FOREIGN KEY (p) REFERENCES parent (id) ON UPDATE NO ACTION;"),
			fk("FOREIGN KEY (a) REFERENCES parent (id)", "", "ALTER TABLE t ADD FOREIGN KEY (a) REFERENCES parent (id);"),
			fk("CONSTRAINT t_ab_fkey FOREIGN KEY (a, b) REFERENCES parent (x, y) MATCH FULL ON DELETE SET NULL "
					+ "ON UPDATE CASCADE DEFERRABLE INITIALLY DEFERRED", "",
					"ALTER TABLE t ADD CONSTRAINT t_ab_fkey FOREIGN KEY (a, b) REFERENCES parent (x, y) MATCH FULL "
							+ "ON DELETE SET NULL ON UPDATE CASCADE DEFERRABLE INITIALLY DEFERRED;"),
			// the two actions in either order; written ON DELETE first
			fk("FOREIGN KEY (a) REFERENCES parent ON UPDATE RESTRICT ON DELETE SET DEFAULT NOT DEFERRABLE", "",
					"ALTER TABLE t ADD FOREIGN KEY (a) REFERENCES parent ON DELETE SET DEFAULT ON UPDATE RESTRICT NOT DEFERRABLE;"),
			fk("foreign key (b) references parent match simple initially immediate", "",
					"ALTER TABLE t ADD FOREIGN KEY (b) REFERENCES parent MATCH SIMPLE initially immediate;"));

	@Test
	public void testEachConstructParsesGeneratesAndRoundTrips() throws Exception {
		for (Case c : CASES) {
			String source = PARENT + "CREATE TABLE t (\n    a INT,\n    b INT,\n    " + c.line() + "\n)" + c.options() + "\n";
			Schema schema = parseClean(source);
			String table = c.table().isBlank() ? "CREATE TABLE t (\n    a INT,\n    b INT\n);\n"
					: "CREATE TABLE t (\n    a INT,\n    b INT,\n" + c.table() + "\n);\n";
			String emitted = generator.schemaDefinitions(schema).toString();
			Assertions.assertEquals(PARENT_OUT + "\n" + table, emitted, c.line());
			String keys = generator.foreignKeyDefinitions("d", schema).toString();
			Assertions.assertEquals(c.foreignKeys().isEmpty() ? "" : HEADER + c.foreignKeys(), keys, c.line());

			// round trip: the generated schema with its foreign keys back in their tables
			Schema again = parseClean(inline(emitted, keys));
			Assertions.assertEquals(SchemaFacts.of(schema), SchemaFacts.of(again), c.line() + " after a round trip");
			Assertions.assertEquals(emitted, generator.schemaDefinitions(again).toString(), c.line() + " fixed point");
			Assertions.assertEquals(keys, generator.foreignKeyDefinitions("d", again).toString(), c.line() + " fixed point");
		}
	}

	// puts each ALTER TABLE x ADD <constraint>; back into CREATE TABLE x as a table constraint
	private static String inline(String schema, String foreignKeys) {
		String out = schema;
		for (String line : foreignKeys.split("\n")) {
			if (!line.startsWith("ALTER TABLE "))
				continue;
			String table = line.substring("ALTER TABLE ".length(), line.indexOf(" ADD "));
			String constraint = line.substring(line.indexOf(" ADD ") + 5, line.length() - 1);
			int start = out.indexOf("CREATE TABLE " + table + " (");
			int end = out.indexOf("\n)", start);
			out = out.substring(0, end) + ",\n    " + constraint + out.substring(end);
		}
		return out;
	}

	@Test
	public void testTableOptions() throws Exception {
		String source = "CREATE TABLE public.t (\n    a INT\n)\nWITH (fillfactor='100', autovacuum_enabled = false);\n";
		Schema schema = parseClean(source);
		String emitted = generator.schemaDefinitions(schema).toString();
		Assertions.assertEquals("CREATE TABLE public.t (\n    a INT\n) WITH (fillfactor='100', autovacuum_enabled = false);\n",
				emitted);
		Assertions.assertEquals(emitted, generator.schemaDefinitions(parseClean(emitted)).toString());
	}

	@Test
	public void testFacts() throws Exception {
		Schema schema = parseClean(PARENT + """
				CREATE TABLE t (
				    id BIGINT GENERATED ALWAYS AS IDENTITY,
				    s SERIAL,
				    a INT CONSTRAINT a_key UNIQUE REFERENCES parent,
				    b INT DEFAULT 7 CHECK (b > 0),
				    c INT,
				    d INT GENERATED ALWAYS AS (c * 2) STORED,
				    CONSTRAINT t_pk PRIMARY KEY (a, b),
				    UNIQUE (c),
				    CHECK (c > 0),
				    CONSTRAINT t_bc FOREIGN KEY (b, c) REFERENCES parent (x, y) ON DELETE SET NULL
				)
				""");
		TableFacts t = SchemaFacts.of(schema).get("t");
		// keys: the PK; column-level then table-level UNIQUEs; never a FOREIGN KEY or a CHECK
		Assertions.assertEquals(List.of("a", "b"), t.primaryKey());
		Assertions.assertEquals(List.of(List.of("a"), List.of("c")), t.uniqueKeys());
		// the column-level REFERENCES gets its column, and the parent's primary key for its omitted list
		Assertions.assertEquals(List.of(
				new ForeignKeyFacts(null, "t", List.of("a"), "parent", List.of("id"), false, null, null),
				new ForeignKeyFacts("t_bc", "t", List.of("b", "c"), "parent", List.of("x", "y"), true, Action.SET_NULL, null)),
				t.foreignKeys());
		Assertions.assertEquals(List.of("ON DELETE SET NULL"), t.foreignKeys().get(1).actionsWritingColumns());
		Assertions.assertEquals(List.of(), t.foreignKeys().get(0).actionsWritingColumns());
		// nullability: an identity and a serial column are NOT NULL, a stored generated one is not; only
		// the stored generated one is storedGenerated
		Assertions.assertEquals(List.of(
				new ColumnFacts("id", t.column("id").type(), false, null, false),
				new ColumnFacts("s", t.column("s").type(), false, null, false),
				new ColumnFacts("a", t.column("a").type(), false, null, false),
				new ColumnFacts("b", t.column("b").type(), false, "7", false),
				new ColumnFacts("c", t.column("c").type(), true, null, false),
				new ColumnFacts("d", t.column("d").type(), true, null, true)), t.columns());
		Assertions.assertTrue(SchemaFacts.of(schema).get("parent").isKey(List.of("y", "x")));
		Assertions.assertFalse(SchemaFacts.of(schema).get("parent").isKey(List.of("x")));
	}

	@Test
	public void testActionsWritingColumns() {
		// the actions Citus rejects on a key over the distribution column (Plan-sql section 11e): every
		// combination of ON DELETE and ON UPDATE, none written included
		List<Action> all = new ArrayList<>(List.of(Action.values()));
		all.add(null);
		for (Action onDelete : all) {
			for (Action onUpdate : all) {
				List<String> expected = new ArrayList<>();
				if (onDelete == Action.SET_NULL || onDelete == Action.SET_DEFAULT)
					expected.add("ON DELETE " + onDelete.name().replace('_', ' '));
				if (onUpdate == Action.SET_NULL || onUpdate == Action.SET_DEFAULT || onUpdate == Action.CASCADE)
					expected.add("ON UPDATE " + onUpdate.name().replace('_', ' '));
				Assertions.assertEquals(expected, new ForeignKeyFacts(null, "c", List.of("a"), "p", List.of("id"), true,
						onDelete, onUpdate).actionsWritingColumns(), onDelete + " / " + onUpdate);
			}
		}
		Assertions.assertEquals(List.of("ON DELETE SET DEFAULT", "ON UPDATE CASCADE"), new ForeignKeyFacts(null, "c",
				List.of("a"), "p", List.of("id"), true, Action.SET_DEFAULT, Action.CASCADE).actionsWritingColumns());
	}

	@Test
	public void testWhatStaysASyntaxError() throws Exception {
		Map<String, String> cases = Map.of(
				"c INT GENERATED ALWAYS AS (1) VIRTUAL", "PostgreSQL 18 syntax; the bench cluster runs 17",
				"c INT GENERATED ALWAYS AS (1)", "STORED is required in PostgreSQL 17",
				"c INT DEFAULT (1", "unbalanced parenthesis",
				"c INT CHECK c > 0", "CHECK needs parentheses",
				"c INT REFERENCES", "REFERENCES needs a table",
				"FOREIGN KEY (c) REFERENCES t ON DELETE CASCADE ON DELETE RESTRICT", "each action at most once",
				"FOREIGN KEY (c) REFERENCES t ON DELETE SET NULL (c)", "a column list for SET NULL is not supported",
				"FOREIGN KEY c REFERENCES t", "FOREIGN KEY needs its column list",
				"c INT DEFAULT 1; DROP TABLE x", "no top-level ';' inside a DEFAULT",
				"c INT DEFAULT 1 GENERATED", "GENERATED starts a constraint, it does not continue a DEFAULT");
		List<String> failures = new ArrayList<>();
		for (Map.Entry<String, String> e : cases.entrySet()) {
			Resource r = load("CREATE TABLE t (\n    c INT,\n    " + e.getKey() + "\n)\n");
			if (r.getErrors().isEmpty())
				failures.add(e.getKey() + " parsed, but should not: " + e.getValue());
		}
		Assertions.assertEquals(List.of(), failures);
	}

	// --- SHML_062-068 ---

	private List<String> codes(String msql) throws Exception {
		return issues(load(msql)).stream().map(i -> i.substring(0, i.indexOf(':'))).sorted().toList();
	}

	@Test
	public void testUnknownTableAndColumn() throws Exception {
		Assertions.assertEquals(List.of("SHML_062: Unknown table nowhere: no table of that name in this file."),
				issues(load("CREATE TABLE t (\n    a INT REFERENCES nowhere\n)\n")));
		Assertions.assertEquals(List.of("SHML_062: Unknown table parent: no table of that name in schema s of this file."),
				issues(load(PARENT + "CREATE TABLE t (\n    a INT REFERENCES s.parent\n)\n")));
		Assertions.assertEquals(List.of("SHML_063: Unknown column nope in table parent."),
				issues(load(PARENT + "CREATE TABLE t (\n    a INT REFERENCES parent (nope)\n)\n")));
		Assertions.assertEquals(List.of("SHML_063: Unknown column nope in table t."),
				issues(load(PARENT + "CREATE TABLE t (\n    a INT,\n    FOREIGN KEY (nope) REFERENCES parent\n)\n")));
		// a table of another file in the same resource set is not in scope
		XtextResourceSet rs = resourceSets.get();
		load(rs, "other.msql", PARENT);
		Assertions.assertEquals(List.of("SHML_062: Unknown table parent: no table of that name in this file."),
				issues(load(rs, "s.msql", "CREATE TABLE t (\n    a INT REFERENCES parent\n)\n")));
	}

	@Test
	public void testQualifiedAndQuotedReferencesLink() throws Exception {
		// a schema-qualified reference, a quoted name with a dot in it, a reference to a table declared later
		Schema schema = parseClean("""
				CREATE TABLE s.child (
				    "x.y" INT REFERENCES s."a.b" ("x.y"),
				    z INT REFERENCES later,
				    PRIMARY KEY ("x.y")
				)
				CREATE TABLE s."a.b" (
				    "x.y" INT PRIMARY KEY
				)
				CREATE TABLE later (
				    id INT PRIMARY KEY
				)
				""");
		Assertions.assertEquals(List.of(
				new ForeignKeyFacts(null, "child", List.of("x.y"), "a.b", List.of("x.y"), true, null, null),
				new ForeignKeyFacts(null, "child", List.of("z"), "later", List.of("id"), false, null, null)),
				SchemaFacts.of(schema).get("child").foreignKeys());
		Assertions.assertEquals("ALTER TABLE s.child ADD FOREIGN KEY (\"x.y\") REFERENCES s.\"a.b\" (\"x.y\");\n"
				+ "ALTER TABLE s.child ADD FOREIGN KEY (z) REFERENCES later;\n",
				generator.foreignKeyDefinitions("d", schema).toString().replace(HEADER, ""));
	}

	@Test
	public void testForeignKeyValidators() throws Exception {
		// SHML_064: the column counts differ, with a list or through the primary key
		Assertions.assertEquals(List.of("SHML_064"), codes(PARENT + "CREATE TABLE t (\n    a INT REFERENCES parent (x, y)\n)\n"));
		Assertions.assertEquals(List.of("SHML_064: Foreign key (a, b) has 2 columns, but references 1: parent (id), "
				+ "the primary key of parent."),
				issues(load(PARENT + "CREATE TABLE t (\n    a INT,\n    b INT,\n    FOREIGN KEY (a, b) REFERENCES parent\n)\n")));
		// SHML_065: not a key (one column of the composite UNIQUE), and no list with no primary key
		Assertions.assertEquals(List.of("SHML_065: Foreign key (a) references parent (x), which is neither the primary key "
				+ "of parent nor one of its UNIQUE constraints. PostgreSQL rejects it: add a UNIQUE constraint on those "
				+ "columns, or reference a key."),
				issues(load(PARENT + "CREATE TABLE t (\n    a INT REFERENCES parent (x)\n)\n")));
		Assertions.assertEquals(List.of("SHML_065"),
				codes("CREATE TABLE p (\n    id INT\n)\nCREATE TABLE t (\n    a INT REFERENCES p\n)\n"));
		// the referenced key in another order is still the key; a UNIQUE works as well as the PK
		Assertions.assertEquals(List.of(), codes(PARENT + "CREATE TABLE t (\n    a INT,\n    b INT,\n"
				+ "    FOREIGN KEY (a, b) REFERENCES parent (y, x)\n)\n"));
		// an unresolved referenced table is only the linking error
		Assertions.assertEquals(List.of("SHML_062"), codes("CREATE TABLE t (\n    a INT,\n    FOREIGN KEY (a) REFERENCES p (id)\n)\n"));
	}

	@Test
	public void testMultiplePrimaryKeys() throws Exception {
		Assertions.assertEquals(List.of("SHML_067"), codes("CREATE TABLE t (\n    a INT PRIMARY KEY,\n    b INT,\n"
				+ "    CONSTRAINT t_pkey PRIMARY KEY (b)\n)\n"));
		Assertions.assertEquals(List.of("SHML_067", "SHML_067"),
				codes("CREATE TABLE t (\n    a INT PRIMARY KEY,\n    b INT PRIMARY KEY,\n    c INT PRIMARY KEY\n)\n"));
		Assertions.assertEquals(List.of(), codes("CREATE TABLE t (\n    a INT,\n    b INT,\n    PRIMARY KEY (a, b)\n)\n"));
	}

	@Test
	public void testSequenceDefault() throws Exception {
		Assertions.assertEquals(List.of("SHML_068: DEFAULT nextval('public.t_id_seq'::regclass) uses a sequence, and "
				+ "sequences are not modelled: the generated schema would not create it. Use SERIAL / BIGSERIAL, or "
				+ "GENERATED ... AS IDENTITY."),
				issues(load("CREATE TABLE t (\n    id INT DEFAULT nextval('public.t_id_seq'::regclass) NOT NULL\n)\n")));
		Assertions.assertEquals(List.of("SHML_068"), codes("CREATE TABLE t (\n    id INT DEFAULT pg_catalog.NEXTVAL ('s')\n)\n"));
		// only DEFAULT: a CHECK may mention the word
		Assertions.assertEquals(List.of(), codes("CREATE TABLE t (\n    id INT DEFAULT 1 CHECK (id <> length('nextval('))\n)\n"));
	}
}
