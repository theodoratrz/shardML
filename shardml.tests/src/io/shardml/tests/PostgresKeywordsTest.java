package io.shardml.tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

import io.shardml.services.SQLGrammarAccess;

// PostgreSQL 17's keywords and their categories (resources/pg17-keywords.tsv, taken from
// pg_get_keywords() on the bench cluster). PostgreSQL decides which keywords may be bare table or
// column names: U (unreserved) and C (column name) may, T and R may not. The .msql grammar will
// follow the same rule (Plan-sql 4.2); this pins the list it is checked against, and the
// categories the grammar design relies on.
@ExtendWith(InjectionExtension.class)
@InjectWith(SQLInjectorProvider.class)
public class PostgresKeywordsTest {

	static final Path KEYWORDS = Path.of("resources", "pg17-keywords.tsv");

	@Inject
	private SQLGrammarAccess grammar;

	// upper-case word -> category, from the golden (comment lines and the header skipped)
	static Map<String, String> keywords() throws IOException {
		Map<String, String> words = new LinkedHashMap<>();
		for (String line : Files.readAllLines(KEYWORDS, StandardCharsets.UTF_8)) {
			if (line.startsWith("#") || line.startsWith("word\t"))
				continue;
			String[] fields = line.split("\t");
			words.put(fields[0].toUpperCase(), fields[1]);
		}
		return words;
	}

	@Test
	public void testGoldenIsPostgres17() throws IOException {
		Map<String, String> words = keywords();
		Assertions.assertEquals(491, words.size());
		Map<String, Long> byCategory = new TreeMap<>(
				words.values().stream().collect(Collectors.groupingBy(c -> c, Collectors.counting())));
		Assertions.assertEquals(Map.of("C", 63L, "R", 78L, "T", 23L, "U", 327L), byCategory);
	}

	@Test
	public void testCategoriesThePlanReliesOn() throws IOException {
		Map<String, String> words = keywords();
		// every word that starts a column or table constraint is reserved, so a name can never
		// start one and the grammar can tell a column from a constraint by its first token
		for (String w : List.of("PRIMARY", "UNIQUE", "FOREIGN", "CHECK", "CONSTRAINT", "DEFAULT", "NOT", "NULL", "REFERENCES",
				"CREATE", "TABLE", "WITH"))
			Assertions.assertEquals("R", words.get(w), w);
		// names PostgreSQL accepts bare, though they are keywords
		for (String w : List.of("KEY", "TEXT", "TYPE", "NAME", "VALUE", "DOUBLE", "VARYING", "ZONE", "GENERATED", "INDEX", "SCHEMA"))
			Assertions.assertEquals("U", words.get(w), w);
		for (String w : List.of("INT", "INTEGER", "BIGINT", "SMALLINT", "NUMERIC", "DECIMAL", "CHAR", "CHARACTER", "VARCHAR",
				"REAL", "FLOAT", "PRECISION", "BOOLEAN", "TIMESTAMP", "TIME"))
			Assertions.assertEquals("C", words.get(w), w);
		// type names that are not PostgreSQL keywords at all
		for (String w : List.of("DATE", "UUID", "TIMESTAMPTZ", "SERIAL", "BIGSERIAL", "JSONB", "BYTEA", "BOOL"))
			Assertions.assertNull(words.get(w), w);
	}

	@Test
	public void testTodaysGrammarKeywords() throws IOException {
		// the .msql grammar's words today, with PostgreSQL's category ("-" when not a keyword there).
		// Step 2 of Plan-sql added IF and EXISTS (IF NOT EXISTS) and made every word whose category
		// is U, C or "-" a name too, in any case (SqlKeywordTest checks that against this golden);
		// in step 1 the words were case-sensitive and none of them could be a name.
		Map<String, String> words = keywords();
		Set<String> grammarWords = new TreeSet<>(GrammarUtil.getAllKeywords(grammar.getGrammar()));
		grammarWords.removeIf(w -> !Character.isLetter(w.charAt(0)));
		Map<String, String> categories = new TreeMap<>();
		for (String w : grammarWords)
			categories.put(w, words.getOrDefault(w, "-"));
		Assertions.assertEquals(Map.ofEntries(Map.entry("BIGINT", "C"), Map.entry("BOOLEAN", "C"), Map.entry("CREATE", "R"),
				Map.entry("DATE", "-"), Map.entry("DECIMAL", "C"), Map.entry("EXISTS", "C"), Map.entry("IF", "U"),
				Map.entry("INT", "C"), Map.entry("KEY", "U"),
				Map.entry("NOT", "R"), Map.entry("NULL", "R"), Map.entry("PRIMARY", "R"), Map.entry("TABLE", "R"),
				Map.entry("TEXT", "U"), Map.entry("TIMESTAMP", "C"), Map.entry("UNIQUE", "R"), Map.entry("UUID", "-"),
				Map.entry("VARCHAR", "C")), categories);
	}
}
