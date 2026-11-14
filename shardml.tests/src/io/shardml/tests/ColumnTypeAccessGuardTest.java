package io.shardml.tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// Source scans that keep two readings of the schema in one place each:
//  - Column types. A column's type is one of four features (simpleType, varchar, numeric, char),
//    and simpleType reads INT, the enum's default, when no type parsed; only isSetSimpleType()
//    tells (see ColumnTypeReadersTest). Only SqlTypes reads them, and SchemaFacts is built on it.
//  - Identifier names. PostgreSQL folds unquoted names and keeps quoted ones (see SqlNames), so a name
//    is written to output in one of two forms: quoted where needed (SqlNames.table/column/qualified)
//    or as stored in the catalog (SqlNames.stored). Code that reads getName() for output or quotes
//    and folds names itself gets one of the two wrong for some name, e.g. "Order" or "order".
// The scans cover the hand-written sources (not src-gen). Comment lines are skipped.
public class ColumnTypeAccessGuardTest {

	private static final Path CORE = Path.of("..", "shardml", "src");
	private static final Path CLI = Path.of("..", "shardml.cli", "src");
	private static final List<Path> SOURCES = List.of(CORE, CLI);
	// the code paths that write names into generated files
	private static final List<Path> OUTPUT_PATHS = List.of(CORE.resolve("io/shardml/generator"),
			CORE.resolve("io/shardml/placement"));

	private static final Set<String> TYPE_ALLOWED = Set.of("SqlTypes.java", "SchemaFacts.java");
	private static final Set<String> NAME_ALLOWED = Set.of("SqlNames.java", "SchemaFacts.java");

	// any of a column's four type features, through its getter, setter, isSet / unset, or literal
	private static final Pattern DIRECT_ACCESS = Pattern.compile(
			"\\b(get|set|isSet|unset)(SimpleType|Varchar|Numeric|Char)\\s*\\(|\\bCOLUMN__(SIMPLE_TYPE|VARCHAR|NUMERIC|CHAR)\\b");

	// a name read straight off the model in an output path
	private static final Pattern NAME_READ = Pattern.compile("\\.(getName|getSchemaName)\\s*\\(");

	// quoting or folding by hand: a lone " as a Java string or char, a doubled "" in a Java string,
	// or a case conversion
	private static final Pattern QUOTE_OR_FOLD = Pattern
			.compile("\"\\\\\"\"|\\\\\"\\\\\"|'\"'|'\\\\\"'|\\b(toLowerCase|toUpperCase)\\s*\\(");

	// file name, a fragment of the allowed line, and why it is not a name written to output.
	// Every entry must still match a line, so the list cannot go stale
	private record Allowed(String file, String fragment, String reason) {
	}

	private static final List<Allowed> NAME_READ_ALLOWED = List.of(
			new Allowed("ShardMLGenerator.java", "database.getName()", "the ShardML database name, used for file names"),
			new Allowed("ShardMLGenerator.java", "db.getName()", "the ShardML database name in the JSON"),
			new Allowed("ShardMLGenerator.java", "eClass().getName()", "an EMF class name in an exception"),
			new Allowed("PlacementResolver.java", "node.getName()", "a ShardML node name, not a SQL identifier"));

	private static final List<Allowed> QUOTE_OR_FOLD_ALLOWED = List.of(
			new Allowed("SQLValueConverters.java", "text.equals(\"\\\"\\\"\")",
					"rejects the empty quoted name token; reading is SqlNames.read"),
			new Allowed("ShardMLCli.java", "getSeverity().name().toLowerCase()", "a severity, not a name"),
			new Allowed("ShardMLGenerator.java", "case '\"' -> out.append(\"\\\\\\\"\");",
					"JSON string escaping in jsonString, not identifier quoting"),
			new Allowed("ShardMLGenerator.java", "StringBuilder(value.length() + 2).append('\"')",
					"opens a JSON string literal in jsonString"),
			new Allowed("ShardMLGenerator.java", "return out.append('\"').toString();",
					"closes a JSON string literal in jsonString"));

	@Test
	public void testOnlySqlTypesReadsTheColumnType() throws IOException {
		List<String> offenders = scan(SOURCES, TYPE_ALLOWED, DIRECT_ACCESS, List.of(), 20);
		Assertions.assertEquals(List.of(), offenders,
				"Read column types through io.shardml.schema.SqlTypes.of(column), not the Column's type features: "
						+ "an unparsed type reads as INT through getSimpleType()");
	}

	@Test
	public void testOutputPathsTakeNamesFromSqlNames() throws IOException {
		List<String> offenders = scan(OUTPUT_PATHS, NAME_ALLOWED, NAME_READ, NAME_READ_ALLOWED, 5);
		Assertions.assertEquals(List.of(), offenders,
				"Write table and column names through io.shardml.schema.SqlNames: table/column/qualified where SQL "
						+ "parses the name, stored where the text is matched against the catalog as given "
						+ "(a JSON value, create_distributed_table's distribution column). A name read with "
						+ "getName() loses its quotes in SQL. If the line is not a SQL name, add it to "
						+ "NAME_READ_ALLOWED with the reason");
	}

	@Test
	public void testOnlySqlNamesQuotesOrFoldsNames() throws IOException {
		List<String> offenders = scan(SOURCES, NAME_ALLOWED, QUOTE_OR_FOLD, QUOTE_OR_FOLD_ALLOWED, 20);
		Assertions.assertEquals(List.of(), offenders,
				"Quote, unquote and fold identifier names through io.shardml.schema.SqlNames "
						+ "(read, write, quote, stored), which follows PostgreSQL's rules and its keyword list. "
						+ "If the line does not handle a name, add it to QUOTE_OR_FOLD_ALLOWED with the reason");
	}

	// the offending lines as file:line: text, plus one entry per allowlist entry that matched nothing
	private static List<String> scan(List<Path> roots, Set<String> allowedFiles, Pattern pattern, List<Allowed> allowed,
			int minFiles) throws IOException {
		List<String> offenders = new ArrayList<>();
		Set<Allowed> used = new HashSet<>();
		int scanned = 0;
		for (Path root : roots) {
			Assertions.assertTrue(Files.isDirectory(root), root + " is not a directory; the scan would find nothing");
			try (Stream<Path> files = Files.walk(root)) {
				for (Path file : files.filter(f -> f.toString().endsWith(".java") || f.toString().endsWith(".xtend"))
						.toList()) {
					scanned++;
					String name = file.getFileName().toString();
					if (allowedFiles.contains(name))
						continue;
					List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
					for (int i = 0; i < lines.size(); i++) {
						String line = lines.get(i).strip();
						if (isComment(line) || !pattern.matcher(line).find())
							continue;
						Allowed hit = allowed.stream().filter(a -> a.file().equals(name) && line.contains(a.fragment()))
								.findFirst().orElse(null);
						if (hit != null)
							used.add(hit);
						else
							offenders.add(file.normalize() + ":" + (i + 1) + ": " + line);
					}
				}
			}
		}
		Assertions.assertTrue(scanned >= minFiles, "only " + scanned + " source files scanned under " + roots);
		for (Allowed a : allowed) {
			if (!used.contains(a))
				offenders.add("stale allowlist entry, matches no line: " + a.file() + " \"" + a.fragment() + "\"");
		}
		return offenders;
	}

	private static boolean isComment(String line) {
		return line.startsWith("//") || line.startsWith("/*") || line.startsWith("*");
	}

	@Test
	public void testThePatternsCatchDirectUse() {
		// the scans are only as good as their patterns
		Assertions.assertTrue(DIRECT_ACCESS.matcher("return column.getSimpleType().getLiteral();").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("AllTypes t = c.getSimpleType ();").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("c.eGet(SqlPackage.Literals.COLUMN__SIMPLE_TYPE)").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("if (column.isSetSimpleType())").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("int p = column.getNumeric().getPrecision();").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("Char c = column.getChar();").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("column.getVarchar().getSize()").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("c.eGet(SqlPackage.Literals.COLUMN__NUMERIC)").find());
		Assertions.assertFalse(DIRECT_ACCESS.matcher("SqlType type = SqlTypes.of(column);").find());
		Assertions.assertFalse(DIRECT_ACCESS.matcher("char c = token.charAt(0);").find());

		Assertions.assertTrue(NAME_READ.matcher(".append(shard.getId().getName()).append(\"' \");").find());
		Assertions.assertTrue(NAME_READ.matcher("String s = table.getSchemaName ();").find());
		Assertions.assertFalse(NAME_READ.matcher(".append(SqlNames.stored(shard.getId()))").find());
		Assertions.assertFalse(NAME_READ.matcher(".append(SqlNames.qualified(shard.getEntity()))").find());

		// the lines below are Java source text, as the scan sees it
		Assertions.assertTrue(QUOTE_OR_FOLD.matcher("return \"\\\"\" + name + \"\\\"\";").find());
		Assertions.assertTrue(QUOTE_OR_FOLD.matcher("name.replace(\"\\\"\", \"\\\"\\\"\")").find());
		Assertions.assertTrue(QUOTE_OR_FOLD.matcher("if (token.charAt(0) == '\"')").find());
		Assertions.assertTrue(QUOTE_OR_FOLD.matcher("if (token.charAt(0) == '\\\"')").find());
		Assertions.assertTrue(QUOTE_OR_FOLD.matcher("String key = name.toLowerCase(Locale.ROOT);").find());
		Assertions.assertTrue(QUOTE_OR_FOLD.matcher("String key = name.toUpperCase();").find());
		// JSON keys and SQL string literals are not names
		Assertions.assertFalse(QUOTE_OR_FOLD.matcher("out.append(\"    \\\"database\\\": \\\"\").append(x).append(\"\\\",\\n\");").find());
		Assertions.assertFalse(QUOTE_OR_FOLD.matcher("return \"'\" + value.replace(\"'\", \"''\") + \"'\";").find());

		Assertions.assertTrue(isComment("// return greeting.getName();"));
		Assertions.assertFalse(isComment("out.append(x); // trailing"));
	}
}
