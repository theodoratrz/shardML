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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.sql.Schema;

// The SQL grammar corpus (resources/sql-corpus, see its README) against the grammar, one test per
// file. Each case pins two things:
//  - the file's statements by kind, counted by a splitter in this test that does not depend on
//    the grammar. They are what the grammar must handle: the CREATE TABLEs, the keys in ALTER
//    TABLE, the pg_dump preamble that becomes one INFO issue per file (Plan-sql 11a: SET,
//    set_config, OWNER TO, the psql restrict / unrestrict lines, GRANT / REVOKE / COMMENT ON,
//    DROP TABLE), and
//    the statements that must become located errors;
//  - how the grammar parses the file today: its syntax errors and the tables it recovers.
// Each step of Plan-sql updates the second part, so progress shows here; the first stays fixed.
@ExtendWith(InjectionExtension.class)
@InjectWith(SQLInjectorProvider.class)
public class SqlCorpusTest {

	static final Path CORPUS = Path.of("resources", "sql-corpus");

	// the kinds whose statements are counted in the per-file INFO issue, never written back
	static final Set<String> PREAMBLE = Set.of("SET", "SELECT set_config", "ALTER .. OWNER TO", "\\restrict",
			"\\unrestrict", "GRANT", "REVOKE", "COMMENT ON", "DROP TABLE");

	@Inject
	private Provider<XtextResourceSet> resourceSets;

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

	// today: the parser's errors in order (line: message, the first few), and the tables it recovered
	record Today(int syntaxErrors, String firstError, int tables) {
	}

	private void check(String file, Map<String, Integer> statements, int preamble, Today today) throws IOException {
		String text = Files.readString(CORPUS.resolve(file), StandardCharsets.UTF_8);
		Map<String, Integer> actual = inventory(text);
		Assertions.assertEquals(new TreeMap<>(statements), actual, file + ": statements by kind");
		int counted = actual.entrySet().stream().filter(e -> PREAMBLE.contains(e.getKey())).mapToInt(Map.Entry::getValue).sum();
		Assertions.assertEquals(preamble, counted, file + ": preamble statements for the INFO issue");

		XtextResourceSet rs = resourceSets.get();
		Resource resource = rs.createResource(URI.createURI("dummy:/" + file.replace(".sql", ".msql")));
		resource.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), null);
		List<Resource.Diagnostic> errors = resource.getErrors();
		int tables = resource.getContents().isEmpty() ? 0 : ((Schema) resource.getContents().get(0)).getTables().size();
		Today actualToday = new Today(errors.size(),
				errors.isEmpty() ? null : "line " + errors.get(0).getLine() + ": " + errors.get(0).getMessage(), tables);
		Assertions.assertEquals(today, actualToday, file + ": parse result today");
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
				new Today(1, "line 7: missing EOF at 'DROP'", 0));
	}

	@Test
	public void testPgbench() throws IOException {
		check("pgbench.sql", Map.of("CREATE TABLE", 4, "ALTER TABLE .. ADD constraint", 8, "ALTER .. OWNER TO", 4, "SET", 12,
				"SELECT set_config", 1, "\\restrict", 1, "\\unrestrict", 1), 19,
				new Today(1, "line 7: missing EOF at '-'", 0));
	}

	@Test
	public void testBankingPgDump() throws IOException {
		check("banking-pg_dump.sql", Map.of("CREATE TABLE", 4, "ALTER TABLE .. ADD constraint", 4, "CREATE SCHEMA", 1,
				"ALTER .. OWNER TO", 5, "SET", 12, "SELECT set_config", 1, "\\restrict", 1, "\\unrestrict", 1), 20,
				new Today(1, "line 7: missing EOF at '-'", 0));
	}

	@Test
	public void testChinook() throws IOException {
		check("chinook.sql", Map.of("CREATE TABLE", 11, "ALTER TABLE .. ADD constraint", 11, "CREATE INDEX", 11), 0,
				new Today(2, "line 39: no viable alternative at input 'album_pkey'", 1));
	}

	@Test
	public void testNorthwind() throws IOException {
		check("northwind.sql", Map.of("CREATE TABLE", 14, "ALTER TABLE .. ADD constraint", 27, "DROP TABLE", 14, "SET", 8), 22,
				new Today(1, "line 8: missing EOF at '-'", 0));
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
		check("pagila-schema.sql", statements, 121, new Today(1, "line 8: missing EOF at '-'", 0));
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
