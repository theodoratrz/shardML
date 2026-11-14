package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.generator.ShardMLGenerator;
import io.shardml.schema.SchemaFacts;
import io.shardml.sql.Schema;

// The SQL grammar corpus (resources/sql-corpus, see its README) against the grammar, one test per
// file. Each case pins two things:
//  - the file's statements by kind, counted by a splitter in this test that does not depend on
//    the grammar. They are what the grammar must handle: the CREATE TABLEs, the keys in ALTER
//    TABLE, the pg_dump preamble that becomes one INFO issue per file (Plan-sql 11a: SET,
//    set_config, OWNER TO, the psql restrict / unrestrict lines, GRANT / REVOKE / COMMENT ON,
//    DROP TABLE), and
//    the statements that must become located errors;
//  - how the grammar reads the whole file today: what the model holds, the errors by kind, the
//    INFO issue, and for a file with no error, that the generated schema round-trips.
// Each step of Plan-sql updates the second part, saying why each result moved, so progress shows
// here; the first part (and with it the INFO counts) does not depend on the grammar and stays fixed.
@ExtendWith(InjectionExtension.class)
@InjectWith(SQLInjectorProvider.class)
public class SqlCorpusTest {

	static final Path CORPUS = Path.of("resources", "sql-corpus");

	// the kinds whose statements are counted in the per-file INFO issue, never written back
	static final Set<String> PREAMBLE = Set.of("SET", "SELECT set_config", "ALTER .. OWNER TO", "\\restrict",
			"\\unrestrict", "GRANT", "REVOKE", "COMMENT ON", "DROP TABLE");

	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private IResourceValidator validator;
	@Inject
	private ShardMLGenerator generator;

	// --- the grammar-independent statement inventory ---

	// A statement ends at a line ending in ';' outside a dollar-quoted body; a psql meta-command
	// (a line starting with a backslash) is a statement of its own. Comment-only fragments are
	// dropped.
	static List<String> statements(String text) {
		List<String> out = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		boolean dollar = false;
		Pattern dollarTag = Pattern.compile("\\$[A-Za-z_]*\\$");
		for (String line : text.split("\n", -1)) {
			String trimmed = line.strip();
			if (current.isEmpty() && (trimmed.isEmpty() || trimmed.startsWith("--")))
				continue;
			current.append(line).append('\n');
			Matcher m = dollarTag.matcher(line);
			int tags = 0;
			while (m.find())
				tags++;
			if (tags % 2 == 1)
				dollar = !dollar;
			if (!dollar && (trimmed.endsWith(";") || trimmed.startsWith("\\"))) {
				out.add(current.toString());
				current.setLength(0);
			}
		}
		out.add(current.toString());
		return out.stream().filter(s -> !uncommented(s).isEmpty()).toList();
	}

	private static String uncommented(String statement) {
		return statement.replaceAll("(?s)/\\*.*?\\*/", " ").replaceAll("--[^\n]*", " ").strip();
	}

	private static final List<Map.Entry<String, Pattern>> KINDS = List.of(
			Map.entry("CREATE TABLE", Pattern.compile("^CREATE TABLE ")),
			Map.entry("CREATE SCHEMA", Pattern.compile("^CREATE SCHEMA ")),
			Map.entry("CREATE INDEX", Pattern.compile("^CREATE (UNIQUE )?INDEX ")),
			Map.entry("DROP TABLE", Pattern.compile("^DROP TABLE ")),
			Map.entry("ALTER .. OWNER TO", Pattern.compile("^ALTER \\w+ .* OWNER TO ")),
			Map.entry("ALTER TABLE .. ADD constraint",
					Pattern.compile("^ALTER TABLE (ONLY )?\\S+ ADD (CONSTRAINT|PRIMARY|FOREIGN|UNIQUE|CHECK) ")),
			Map.entry("SET", Pattern.compile("^SET ")),
			Map.entry("SELECT set_config", Pattern.compile("^SELECT PG_CATALOG\\.SET_CONFIG")),
			Map.entry("GRANT", Pattern.compile("^GRANT ")),
			Map.entry("REVOKE", Pattern.compile("^REVOKE ")),
			Map.entry("COMMENT ON", Pattern.compile("^COMMENT ON ")));

	// the kind of a statement; anything else is "other: " and its first two words
	static String kind(String statement) {
		String text = uncommented(statement).replaceAll("\\s+", " ");
		if (text.startsWith("\\"))
			return text.split(" ")[0];
		String upper = text.toUpperCase();
		for (Map.Entry<String, Pattern> kind : KINDS) {
			if (kind.getValue().matcher(upper).find())
				return kind.getKey();
		}
		String[] words = upper.split(" ");
		return "other: " + words[0] + (words.length > 1 ? " " + words[1] : "");
	}

	static Map<String, Integer> inventory(String text) {
		Map<String, Integer> counts = new TreeMap<>();
		for (String statement : statements(text))
			counts.merge(kind(statement), 1, Integer::sum);
		return counts;
	}

	// --- one case per file ---

	// the whole file, parsed and validated (step 5: was the per-table result, which showed progress
	// while the whole file still stopped at its preamble): what the model holds (CREATE TABLE, ALTER
	// TABLE, CREATE INDEX, CREATE SCHEMA, other statements), the errors by kind with their count, and
	// the INFO issue (SHML_075) counting the preamble left out. An error's kind is its code and the
	// message up to its first '.', or "syntax" and the message for a parser error, with its line
	record Result(int tables, int alters, int indexes, int schemas, int statements, Map<String, Integer> errors,
			String info) {
	}

	private void check(String file, Map<String, Integer> statements, int preamble, Result expected) throws IOException {
		String text = Files.readString(CORPUS.resolve(file), StandardCharsets.UTF_8);
		Map<String, Integer> actual = inventory(text);
		Assertions.assertEquals(new TreeMap<>(statements), actual, file + ": statements by kind");
		int counted = actual.entrySet().stream().filter(e -> PREAMBLE.contains(e.getKey())).mapToInt(Map.Entry::getValue).sum();
		Assertions.assertEquals(preamble, counted, file + ": preamble statements for the INFO issue");

		Schema schema = load(text, file);
		Map<String, Integer> errors = new TreeMap<>();
		String info = null;
		for (Issue issue : validator.validate(schema.eResource(), CheckMode.ALL, CancelIndicator.NullImpl)) {
			if (issue.getSeverity() == Severity.INFO) {
				Assertions.assertNull(info, file + ": one INFO issue per file");
				info = issue.getCode() + ": " + issue.getMessage();
				continue;
			}
			errors.merge(kindOf(issue), 1, Integer::sum);
		}
		Assertions.assertEquals(expected, new Result(schema.getTables().size(), schema.getAlters().size(),
				schema.getIndexes().size(), schema.getSchemas().size(), schema.getStatements().size(), errors, info),
				file + ": parse and validation result");
		// the INFO issue counts exactly the preamble the grammar-independent inventory finds
		int inInfo = 0;
		if (info != null) {
			Matcher m = Pattern.compile("(\\d+) [^,.]+").matcher(info.substring(info.indexOf("Not written back: ")));
			while (m.find())
				inInfo += Integer.parseInt(m.group(1));
		}
		Assertions.assertEquals(preamble, inInfo, file + ": the INFO issue counts the preamble");

		if (errors.isEmpty()) {
			// round trip (Plan-sql 6.2): the generated schema and foreign keys parse with no error, as
			// the same tables and keys, and generate the same text again
			String emitted = generator.schemaDefinitions(schema) + "\n" + generator.foreignKeyDefinitions("d", schema);
			Schema again = load(emitted, "again-" + file);
			Assertions.assertEquals(List.of(), again.eResource().getErrors(), file + ": the generated schema parses");
			Assertions.assertEquals(List.of(), validator.validate(again.eResource(), CheckMode.ALL, CancelIndicator.NullImpl),
					file + ": the generated schema validates with no issue (the preamble is gone)");
			Assertions.assertEquals(SchemaFacts.of(schema), SchemaFacts.of(again), file + ": the same tables and keys");
			Assertions.assertEquals(emitted,
					generator.schemaDefinitions(again) + "\n" + generator.foreignKeyDefinitions("d", again),
					file + ": fixed point");
		}
	}

	private int loaded;

	private Schema load(String text, String file) throws IOException {
		XtextResourceSet rs = resourceSets.get();
		Resource resource = rs.createResource(URI.createURI("dummy:/" + (loaded++) + file.replace(".sql", ".msql")));
		resource.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), null);
		return (Schema) resource.getContents().get(0);
	}

	private static String kindOf(Issue issue) {
		if (issue.getCode() != null && issue.getCode().startsWith("SHML_")) {
			// up to the end of the first sentence: a '.' that is not part of '..', then a space
			Matcher end = Pattern.compile("(?<!\\.)\\.(?!\\.)\\s").matcher(issue.getMessage());
			return issue.getCode() + ": " + (end.find() ? issue.getMessage().substring(0, end.start()) : issue.getMessage());
		}
		return "syntax, line " + issue.getLineNumber() + ": " + issue.getMessage();
	}

	private static String info(String counts) {
		return "SHML_075: Not written back: " + counts + ". Session settings, ownership, privileges, comments and DROP "
				+ "TABLE cleanup (the pg_dump preamble): the generated schema needs none of them.";
	}

	@Test
	public void testCorpusHasExactlyTheseFiles() throws IOException {
		// a file added to the corpus needs a case below
		try (Stream<Path> files = Files.list(CORPUS)) {
			Assertions.assertEquals(
					List.of("banking-pg_dump.sql", "chinook.sql", "northwind.sql", "pagila-schema.sql", "pgbench.sql", "tpcc.sql"),
					files.map(p -> p.getFileName().toString()).filter(n -> n.endsWith(".sql")).sorted().collect(Collectors.toList()));
		}
	}

	@Test
	public void testTpcc() throws IOException {
		check("tpcc.sql", Map.of("CREATE TABLE", 9, "DROP TABLE", 9, "CREATE INDEX", 1), 9,
				// step 2: the whole file still stops at its first statement, DROP TABLE (step 5). Its tables
				// got further: lower-case `int` is now a type, so warehouse stops at decimal(12, 2)
				// (step 3), where step 1 stopped at `int`
				// step 3: decimal(p, s), char(n), varchar(n), timestamp and integer are types, so warehouse
				// and item parse. The other seven stop at step-4 syntax: stock, district and order_line at
				// FOREIGN KEY, customer, history and oorder at a DEFAULT, new_order at an explicit NULL
				// (ol_delivery_d timestamp NULL). The whole file is unchanged (DROP TABLE, step 5)
				// step 4: FOREIGN KEY .. REFERENCES .. ON DELETE CASCADE (composite included), DEFAULT
				// CURRENT_TIMESTAMP / DEFAULT NULL and the explicit NULL parse, so all nine tables do. The
				// whole file is unchanged (DROP TABLE, step 5)
				// step 5: the whole file parses and validates with no error. Its nine DROP TABLE IF EXISTS ..
				// CASCADE are preamble (the INFO issue; not written back), and CREATE INDEX idx_customer_name
				// is modelled and written back after the tables
				new Result(9, 0, 1, 0, 9, Map.of(), info("9 DROP TABLE")));
	}

	@Test
	public void testPgbench() throws IOException {
		check("pgbench.sql", Map.of("CREATE TABLE", 4, "ALTER TABLE .. ADD constraint", 8, "ALTER .. OWNER TO", 4, "SET", 12,
				"SELECT set_config", 1, "\\restrict", 1, "\\unrestrict", 1), 19,
				// step 2: -- comments are comments now, so the whole file moves from line 7 (a comment) to
				// the psql restrict line at 11 (step 5). Its tables moved from public. (qualified names,
				// now read) to the type integer (step 3)
				// step 3: integer, character(n), bigint, timestamp are types. pgbench_history, the one
				// table without storage options, parses; the other three stop after their closing
				// parenthesis at WITH (fillfactor='100'), table options (step 4, RawSql)
				// step 4: WITH (..) table options parse (kept verbatim), so all four tables do. Their keys
				// and foreign keys are in ALTER TABLE (step 5); the whole file is unchanged
				// step 5: the whole file parses and validates with no error. Its 3 primary keys and 5
				// foreign keys come from ALTER TABLE ONLY .. ADD CONSTRAINT (alters: 8 ADD plus the 4
				// ALTER TABLE .. OWNER TO); the rest is preamble, counted in the INFO issue
				new Result(4, 12, 0, 0, 15, Map.of(),
						info("12 SET, 1 set_config, 4 ALTER .. OWNER TO, 2 psql restrict / unrestrict")));
	}

	@Test
	public void testBankingPgDump() throws IOException {
		check("banking-pg_dump.sql", Map.of("CREATE TABLE", 4, "ALTER TABLE .. ADD constraint", 4, "CREATE SCHEMA", 1,
				"ALTER .. OWNER TO", 5, "SET", 12, "SELECT set_config", 1, "\\restrict", 1, "\\unrestrict", 1), 20,
				// step 2: as pgbench, the whole file now stops at the psql restrict line (step 5); its
				// tables moved from banking. (qualified, now read) to the type numeric (step 3)
				// step 3: numeric(p, s), character varying(n), timestamp with time zone and bigint are
				// types, so all four tables parse (their keys are in ALTER TABLE, step 5). The whole
				// file is unchanged
				// step 5: the whole file parses and validates with no error: CREATE SCHEMA banking is
				// modelled (written back IF NOT EXISTS), the four primary keys come from ALTER TABLE ONLY ..
				// ADD CONSTRAINT, and the rest is preamble (ALTER SCHEMA .. OWNER TO among the 5 OWNER TO)
				new Result(4, 8, 0, 1, 16, Map.of(),
						info("12 SET, 1 set_config, 5 ALTER .. OWNER TO, 2 psql restrict / unrestrict")));
	}

	@Test
	public void testChinook() throws IOException {
		check("chinook.sql", Map.of("CREATE TABLE", 11, "ALTER TABLE .. ADD constraint", 11, "CREATE INDEX", 11), 0,
				// step 2: unchanged. Chinook is upper case with no comments or qualified names, so it stops,
				// as in step 1, at the named CONSTRAINT album_pkey of its first table (step 4)
				// step 3: unchanged; all 11 tables still stop at their named CONSTRAINT .. PRIMARY KEY
				// (step 4), each after its column types, which parse
				// step 4: named CONSTRAINT x PRIMARY KEY (..) parses, so all 11 tables do, alone and in the
				// whole file, which now stops after them, at its first ALTER TABLE .. ADD CONSTRAINT ..
				// FOREIGN KEY (line 160, step 5)
				// step 5: the whole file parses and validates with no error: its 11 foreign keys (ALTER
				// TABLE .. ADD CONSTRAINT, ON DELETE / ON UPDATE NO ACTION) and 11 indexes are modelled. It
				// has no preamble, so no INFO issue
				new Result(11, 11, 11, 0, 0, Map.of(), null));
	}

	@Test
	public void testNorthwind() throws IOException {
		check("northwind.sql", Map.of("CREATE TABLE", 14, "ALTER TABLE .. ADD constraint", 27, "DROP TABLE", 14, "SET", 8), 22,
				// step 2: -- comments are read, so the whole file moves from line 8 (a comment) to its first
				// SET at line 12 (step 5). Its tables stop at smallint, as in step 1 (step 3)
				// step 3: smallint, real, bytea, character varying(n) and character(n) are types, so all
				// 14 tables parse (keys and FKs are in ALTER TABLE, steps 4 and 5). The whole file is
				// unchanged
				// step 5: the whole file parses and validates with no error: 14 primary keys and 13 foreign
				// keys from ALTER TABLE ONLY .. ADD CONSTRAINT, some without a referenced column list
				// (REFERENCES customers: the primary key, itself declared by a later ALTER TABLE). SET and
				// DROP TABLE IF EXISTS are preamble
				new Result(14, 27, 0, 0, 22, Map.of(), info("8 SET, 14 DROP TABLE")));
	}

	@Test
	public void testPagila() throws IOException {
		// 106 statements of kinds the grammar will not support: each must become a located error
		Map<String, Integer> statements = new TreeMap<>(Map.of("CREATE TABLE", 71, "ALTER TABLE .. ADD constraint", 52,
				"CREATE INDEX", 38, "ALTER .. OWNER TO", 106, "SET", 11, "SELECT set_config", 1, "GRANT", 1, "REVOKE", 1,
				"COMMENT ON", 1));
		statements.putAll(Map.of("other: ALTER TABLE", 55, "other: CREATE AGGREGATE", 1, "other: CREATE DOMAIN", 2,
				"other: CREATE EXTENSION", 1, "other: CREATE FUNCTION", 9, "other: CREATE MATERIALIZED", 1,
				"other: CREATE SEQUENCE", 13, "other: CREATE TRIGGER", 16, "other: CREATE TYPE", 1, "other: CREATE VIEW", 7));
		// step 2: comments are read, so the whole file moves from line 8 to its first SET at line 15
		// (step 5); its tables moved from public. (now read) to the type integer (step 3)
		// step 3: still no table parses, but every one now gets past its types: 70 stop at a DEFAULT
		// (nextval, now(), literals: step 4, and SHML_068 for nextval), the 71st
		// (public.film_embedding) at public.vector(20), a pgvector type that stays unsupported. The whole
		// file is unchanged
		// step 4: DEFAULT parses (kept verbatim), so 68 of the 71 tables do. The 13 with a
		// DEFAULT nextval(..) parse too: that is SHML_068, a validation error, not a syntax error. Three
		// stop: public.film at its column type public.mpaa_rating (a CREATE TYPE enum, unsupported),
		// public.film_embedding at public.vector(20) (pgvector) and public.payment, the partitioned
		// parent, at PARTITION BY (unsupported). The whole file is unchanged
		// step 5: the whole file is read to its end, every statement in the model, and every error is
		// located. Pagila stays a negative fixture; these are its remaining kinds, none of which this
		// branch implements:
		//  - 2 syntax errors, user types: film at public.mpaa_rating (CREATE TYPE .. AS ENUM), and
		//    film_embedding at public.vector(20) (pgvector). The parser resumes after each; the rest of
		//    the statement gets no SHML_061 of its own;
		//  - 5 SHML_063, a consequence of the first: film keeps only the columns before the error, so its
		//    keys, foreign keys and index name columns it lost;
		//  - 107 SHML_061: 55 ALTER TABLE .. ATTACH PARTITION and 1 PARTITION BY (partitions), 13 CREATE
		//    SEQUENCE (sequences, deferred), 16 triggers, 9 functions, 7 views, 1 materialized view, 1
		//    aggregate (kept in a separate migration), 2 domains, 1 enum type, 1 extension;
		//  - 68 SHML_068: DEFAULT nextval(..), in the 13 tables with a serial key and their 55 partitions;
		//  - 1 SHML_062: a unique index on the materialized view rental_by_category, which is no table.
		// The preamble, ALTER .. OWNER TO of sequences, views, functions and types included, is the INFO
		Map<String, Integer> errors = new TreeMap<>();
		errors.put("syntax, line 449: no viable alternative at input 'public'", 1);
		errors.put("syntax, line 512: no viable alternative at input 'public'", 1);
		errors.put("SHML_063: Unknown column fulltext in table film.", 1);
		errors.put("SHML_063: Unknown column language_id in table film.", 2);
		errors.put("SHML_063: Unknown column original_language_id in table film.", 2);
		errors.put("SHML_061: Unsupported statement ALTER TABLE .. ATTACH PARTITION", 55);
		errors.put("SHML_061: Unsupported statement PARTITION BY", 1);
		errors.put("SHML_061: Sequence statement CREATE SEQUENCE", 13);
		errors.put("SHML_061: Unsupported statement CREATE TRIGGER", 16);
		errors.put("SHML_061: Unsupported statement CREATE FUNCTION", 9);
		errors.put("SHML_061: Unsupported statement CREATE VIEW", 7);
		errors.put("SHML_061: Unsupported statement CREATE MATERIALIZED", 1);
		errors.put("SHML_061: Unsupported statement CREATE AGGREGATE", 1);
		errors.put("SHML_061: Unsupported statement CREATE DOMAIN", 2);
		errors.put("SHML_061: Unsupported statement CREATE TYPE", 1);
		errors.put("SHML_061: Unsupported statement CREATE EXTENSION", 1);
		errors.put("SHML_062: Unknown table rental_by_category: no table of that name in schema public of this file.", 1);
		check("pagila-schema.sql", statements, 121, new Result(71, 199, 38, 0, 83, withNextval(errors),
				info("11 SET, 1 set_config, 106 ALTER .. OWNER TO, 1 GRANT, 1 REVOKE, 1 COMMENT ON")));
	}

	// adds the 68 SHML_068 of Pagila, one kind per sequence, as the message names it
	private static Map<String, Integer> withNextval(Map<String, Integer> errors) throws IOException {
		String text = Files.readString(CORPUS.resolve("pagila-schema.sql"), StandardCharsets.UTF_8);
		Matcher m = Pattern.compile("DEFAULT (nextval\\('[^']+'::regclass\\))").matcher(text);
		int n = 0;
		while (m.find()) {
			errors.merge("SHML_068: DEFAULT " + m.group(1) + " uses a sequence, and sequences are not modelled: the generated "
					+ "schema would not create it", 1, Integer::sum);
			n++;
		}
		Assertions.assertEquals(68, n);
		return errors;
	}

	@Test
	public void testExamplesStillParse() throws IOException {
		// the baseline every step keeps: the examples' schemas parse with no error
		for (String name : List.of("banking", "social")) {
			XtextResourceSet rs = resourceSets.get();
			Resource resource = rs.createResource(URI.createURI("dummy:/" + name + ".msql"));
			resource.load(new ByteArrayInputStream(Files.readAllBytes(Path.of("..", "examples", name + ".msql"))), null);
			Assertions.assertEquals(List.of(), resource.getErrors(), name);
		}
	}
}
