package io.shardml.tests;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// The cost model's numbers must be reproducible across JVMs and CPUs: Math's transcendental functions
// may be intrinsics whose last bit differs by platform; StrictMath's are fdlibm's, the same everywhere.
// Exact operations (min, max, abs, floor, floorMod, multiplyExact) are fine in Math. A scan of the
// production sources of io.shardml.cost; comment lines are skipped.
public class StrictMathGuardTest {

	private static final Path COST = Path.of("..", "shardml", "src", "io", "shardml", "cost");

	static final Pattern TRANSCENDENTAL = Pattern.compile("(?<![A-Za-z_])Math\\.(pow|log|log10|log1p|exp|expm1|sqrt|cbrt|sin|cos|tan"
			+ "|asin|acos|atan|atan2|sinh|cosh|tanh|hypot)\\s*\\(");

	@Test
	public void testNoMathTranscendentalsInTheCostModel() throws Exception {
		List<String> offenders = new ArrayList<>();
		int scanned = 0;
		try (Stream<Path> files = Files.list(COST)) {
			for (Path file : files.filter(f -> f.toString().endsWith(".java")).sorted().toList()) {
				scanned++;
				List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
				for (int i = 0; i < lines.size(); i++) {
					String line = lines.get(i).strip();
					if (line.startsWith("//") || line.startsWith("/*") || line.startsWith("*"))
						continue;
					if (TRANSCENDENTAL.matcher(line).find())
						offenders.add(file.getFileName() + ":" + (i + 1) + ": " + line);
				}
			}
		}
		Assertions.assertTrue(scanned >= 20, "only " + scanned + " files scanned");
		Assertions.assertEquals(List.of(), offenders, "use StrictMath for transcendental functions in io.shardml.cost");
	}

	@Test
	public void testThePatternCatchesMathButNotStrictMath() {
		Assertions.assertTrue(TRANSCENDENTAL.matcher("double x = Math.pow(r, -s);").find());
		Assertions.assertTrue(TRANSCENDENTAL.matcher("y = Math.log (p);").find());
		Assertions.assertTrue(TRANSCENDENTAL.matcher("z = Math.sqrt(v) + Math.exp(w);").find());
		Assertions.assertFalse(TRANSCENDENTAL.matcher("double x = StrictMath.pow(r, -s);").find());
		Assertions.assertFalse(TRANSCENDENTAL.matcher("int m = Math.max(a, b);").find());
		Assertions.assertFalse(TRANSCENDENTAL.matcher("long f = Math.floorMod(a, b);").find());
	}
}
