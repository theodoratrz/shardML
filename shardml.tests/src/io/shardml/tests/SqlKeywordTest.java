package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.schema.SqlNames;
import io.shardml.services.SQLGrammarAccess;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;

// Keywords as names, driven by PostgreSQL 17's keyword golden (resources/pg17-keywords.tsv).
// PostgreSQL lets a keyword be a bare table or column name when its category is U or C (or when
// the word is not a PostgreSQL keyword at all); R and T words must be quoted. The .msql grammar
// follows exactly that rule:
//  - the keywords of rule Name are exactly the grammar's keywords that PostgreSQL allows bare;
//  - each of them works as a table name, a column name and a key column, in any case;
//  - each other keyword fails as a bare name, and works quoted;
//  - SqlNames.RESERVED, which decides when the generator quotes a name, is the golden's R and T.
@ExtendWith(InjectionExtension.class)
@InjectWith(SQLInjectorProvider.class)
public class SqlKeywordTest {

	@Inject
	private SQLGrammarAccess grammar;
	@Inject
	private Provider<XtextResourceSet> resourceSets;

	private int n;

	private Resource parse(String text) throws Exception {
		Resource resource = resourceSets.get().createResource(URI.createURI("dummy:/k" + (n++) + ".msql"));
		resource.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), null);
		return resource;
	}

	// the grammar's words (punctuation left out), upper case
	private Set<String> grammarKeywords() {
		Set<String> words = new TreeSet<>(GrammarUtil.getAllKeywords(grammar.getGrammar()));
		words.removeIf(w -> !Character.isLetter(w.charAt(0)));
		return words.stream().map(w -> w.toUpperCase(Locale.ROOT)).collect(Collectors.toCollection(TreeSet::new));
	}

	private static boolean bareNameInPostgres(Map<String, String> pg, String word) {
		String category = pg.get(word);
		return category == null || category.equals("U") || category.equals("C");
	}

	@Test
	public void testNameRuleIsExactlyTheWordsPostgresAllowsBare() throws Exception {
		Map<String, String> pg = PostgresKeywordsTest.keywords();
		Set<String> inName = GrammarUtil.containedKeywords(grammar.getNameRule()).stream().map(Keyword::getValue)
				.map(w -> w.toUpperCase(Locale.ROOT)).collect(Collectors.toCollection(TreeSet::new));
		Set<String> expected = grammarKeywords().stream().filter(w -> bareNameInPostgres(pg, w))
				.collect(Collectors.toCollection(TreeSet::new));
		Assertions.assertEquals(expected, inName,
				"rule Name must list exactly the grammar's keywords whose pg_get_keywords() category is U or C, "
						+ "or that are not PostgreSQL keywords");
	}

	@Test
	public void testAllowedKeywordsAreTableAndColumnNames() throws Exception {
		Map<String, String> pg = PostgresKeywordsTest.keywords();
		List<String> failures = new ArrayList<>();
		for (String word : grammarKeywords()) {
			if (!bareNameInPostgres(pg, word))
				continue;
			for (String spelling : List.of(word, word.toLowerCase(Locale.ROOT), mixed(word))) {
				// as a table name, a column name and a key column, and as the type of the column
				// next to it where it is a type
				Resource r = parse("CREATE TABLE " + spelling + " (\n  " + spelling + " INT NOT NULL,\n  other " + "TEXT,\n"
						+ "  PRIMARY KEY (" + spelling + ")\n);\n");
				String folded = word.toLowerCase(Locale.ROOT);
				if (!r.getErrors().isEmpty()) {
					failures.add(spelling + ": " + r.getErrors().get(0).getMessage());
					continue;
				}
				Table table = ((Schema) r.getContents().get(0)).getTables().get(0);
				if (!folded.equals(table.getName()) || !folded.equals(table.getColumns().get(0).getName())
						|| table.getConstraints().get(0).getColumns().get(0) != table.getColumns().get(0))
					failures.add(spelling + ": not read as the name '" + folded + "' everywhere");
			}
		}
		Assertions.assertEquals(List.of(), failures);
	}

	@Test
	public void testReservedKeywordsAreNamesOnlyQuoted() throws Exception {
		Map<String, String> pg = PostgresKeywordsTest.keywords();
		List<String> failures = new ArrayList<>();
		List<String> reserved = new ArrayList<>();
		for (String word : grammarKeywords()) {
			if (bareNameInPostgres(pg, word))
				continue;
			reserved.add(word);
			if (parse("CREATE TABLE " + word + " ( id INT )").getErrors().isEmpty())
				failures.add(word + " parsed as a bare table name");
			if (parse("CREATE TABLE t ( " + word.toLowerCase(Locale.ROOT) + " INT )").getErrors().isEmpty())
				failures.add(word + " parsed as a bare column name");
			Resource quoted = parse("CREATE TABLE \"" + word + "\" ( \"" + word.toLowerCase(Locale.ROOT) + "\" INT )");
			if (!quoted.getErrors().isEmpty())
				failures.add(word + " quoted: " + quoted.getErrors().get(0).getMessage());
			else {
				Table table = ((Schema) quoted.getContents().get(0)).getTables().get(0);
				if (!word.equals(table.getName()) || !word.toLowerCase(Locale.ROOT).equals(table.getColumns().get(0).getName()))
					failures.add(word + " quoted: the name is not kept as written");
			}
		}
		// the reserved words of the grammar today: step 3 added WITH (TIMESTAMP WITH TIME ZONE)
		Assertions.assertEquals(List.of("CREATE", "NOT", "NULL", "PRIMARY", "TABLE", "UNIQUE", "WITH"), reserved);
		Assertions.assertEquals(List.of(), failures);
	}

	@Test
	public void testGeneratorQuotesExactlyPostgresReservedWords() throws Exception {
		Set<String> golden = PostgresKeywordsTest.keywords().entrySet().stream()
				.filter(e -> e.getValue().equals("R") || e.getValue().equals("T")).map(e -> e.getKey().toLowerCase(Locale.ROOT))
				.collect(Collectors.toCollection(TreeSet::new));
		Assertions.assertEquals(golden, new TreeSet<>(SqlNames.RESERVED));
		Assertions.assertEquals("\"table\"", SqlNames.write("table"));
		Assertions.assertEquals("key", SqlNames.write("key"));
		Assertions.assertEquals("int", SqlNames.write("int"));
	}

	private static String mixed(String word) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < word.length(); i++)
			sb.append(i % 2 == 0 ? Character.toUpperCase(word.charAt(i)) : Character.toLowerCase(word.charAt(i)));
		return sb.toString();
	}
}
