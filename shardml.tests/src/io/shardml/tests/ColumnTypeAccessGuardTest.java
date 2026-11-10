package io.shardml.tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// Column.getSimpleType() returns INT, the enum's default, when a column's type did not parse, so
// reading it directly turns a syntax error into an INT column (see ColumnTypeReadersTest). Only
// SqlTypes reads the type feature, and SchemaFacts is built on it; every other production class
// must ask SqlTypes. This scans the hand-written sources (not src-gen) for the generated getter,
// its setter, and the feature literal used with eGet / eIsSet.
public class ColumnTypeAccessGuardTest {

	private static final List<Path> SOURCES = List.of(Path.of("..", "shardml", "src"), Path.of("..", "shardml.cli", "src"));

	private static final Set<String> ALLOWED = Set.of("SqlTypes.java", "SchemaFacts.java");

	private static final Pattern DIRECT_ACCESS = Pattern.compile("\\b(getSimpleType|setSimpleType)\\s*\\(|\\bCOLUMN__SIMPLE_TYPE\\b");

	@Test
	public void testOnlySqlTypesReadsTheColumnType() throws IOException {
		List<String> offenders = new ArrayList<>();
		int scanned = 0;
		for (Path root : SOURCES) {
			Assertions.assertTrue(Files.isDirectory(root), root + " is not a directory; the scan would find nothing");
			try (Stream<Path> files = Files.walk(root)) {
				for (Path file : files.filter(f -> f.toString().endsWith(".java")).toList()) {
					scanned++;
					if (ALLOWED.contains(file.getFileName().toString()))
						continue;
					List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
					for (int i = 0; i < lines.size(); i++) {
						if (DIRECT_ACCESS.matcher(lines.get(i)).find())
							offenders.add(file.normalize() + ":" + (i + 1) + ": " + lines.get(i).strip());
					}
				}
			}
		}
		Assertions.assertTrue(scanned > 20, "only " + scanned + " source files scanned");
		Assertions.assertEquals(List.of(), offenders,
				"Read column types through io.shardml.schema.SqlTypes.of(column), not Column.getSimpleType(): "
						+ "an unparsed type reads as INT there");
	}

	@Test
	public void testThePatternCatchesDirectReads() {
		// the scan is only as good as its pattern
		Assertions.assertTrue(DIRECT_ACCESS.matcher("return column.getSimpleType().getLiteral();").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("AllTypes t = c.getSimpleType ();").find());
		Assertions.assertTrue(DIRECT_ACCESS.matcher("c.eGet(SqlPackage.Literals.COLUMN__SIMPLE_TYPE)").find());
		Assertions.assertFalse(DIRECT_ACCESS.matcher("SqlType type = SqlTypes.of(column);").find());
	}
}
