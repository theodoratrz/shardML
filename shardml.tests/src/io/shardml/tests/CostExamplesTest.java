package io.shardml.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.shardml.command.CostCommand;

// The cost examples (examples/cost, Plan-phase3 5.0): every output in examples/cost/expected is regenerated
// through `shardml cost` (CostCommand) and compared byte for byte. The commands are read from
// examples/cost/regenerate.sh itself, so the test and the script cannot drift apart, and the expected
// directory must hold exactly the files the script writes.
public class CostExamplesTest {

	private static final Path DIR = Path.of("..", "examples", "cost");

	// file -> arguments, from the script's `cost <file> <args>...` lines, its variables expanded
	static Map<String, List<String>> commands() throws Exception {
		Map<String, String> vars = new LinkedHashMap<>();
		vars.put("HERE", DIR.toString());
		Map<String, List<String>> commands = new LinkedHashMap<>();
		Pattern assignment = Pattern.compile("^([A-Z_]+)=\"?([^\"]*)\"?$");
		for (String line : Files.readAllLines(DIR.resolve("regenerate.sh"), StandardCharsets.UTF_8)) {
			String text = line.strip();
			Matcher m = assignment.matcher(text);
			if (m.matches() && !text.startsWith("JAR=") && !text.contains("$(")) {
				vars.put(m.group(1), expand(m.group(2), vars));
				continue;
			}
			if (!text.startsWith("cost ") || text.startsWith("cost()"))
				continue;
			List<String> words = new ArrayList<>();
			for (String word : text.substring("cost ".length()).split(" +"))
				words.add(expand(word.replace("\"", ""), vars));
			commands.put(words.get(0), List.copyOf(words.subList(1, words.size())));
		}
		return commands;
	}

	private static String expand(String text, Map<String, String> vars) {
		String out = text;
		for (Map.Entry<String, String> v : vars.entrySet())
			out = out.replace("$" + v.getKey(), v.getValue());
		return out;
	}

	@Test
	public void testEveryExpectedOutput() throws Exception {
		Map<String, List<String>> commands = commands();
		Assertions.assertTrue(commands.size() >= 19, "only " + commands.size() + " commands read from regenerate.sh");
		try (Stream<Path> files = Files.list(DIR.resolve("expected"))) {
			Assertions.assertEquals(new TreeSet<>(commands.keySet()),
					files.map(p -> p.getFileName().toString()).filter(n -> !n.startsWith(".")).collect(java.util.stream.Collectors.toCollection(TreeSet::new)),
					"examples/cost/expected holds exactly the files regenerate.sh writes");
		}
		List<String> differ = new ArrayList<>();
		for (Map.Entry<String, List<String>> c : commands.entrySet()) {
			String out = run(c.getValue());
			if (!out.equals(Files.readString(DIR.resolve("expected").resolve(c.getKey()), StandardCharsets.UTF_8)))
				differ.add(c.getKey());
		}
		Assertions.assertEquals(List.of(), differ, "outputs that differ from examples/cost/expected (run examples/cost/regenerate.sh)");
	}

	@Test
	public void testSweepsAreDeterministic() throws Exception {
		List<String> sweep = commands().get("banking-sweep-s.csv");
		Assertions.assertEquals(run(sweep), run(sweep));
		List<String> tpcc = commands().get("tpcc-sweep-remote.csv");
		Assertions.assertEquals(run(tpcc), run(tpcc));
	}

	private static String run(List<String> args) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		int exit = CostCommand.run(args, new PrintStream(out, true, StandardCharsets.UTF_8),
				new PrintStream(err, true, StandardCharsets.UTF_8));
		Assertions.assertEquals(0, exit, args + "\n" + err.toString(StandardCharsets.UTF_8));
		return out.toString(StandardCharsets.UTF_8);
	}
}
