package io.shardml.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

import io.shardml.workload.WorkloadModel;

// Words that are keywords somewhere must still parse as names in a workload, in every
// position a name can take. Only syntax errors count here; whether the names link is the
// scoping tests' business. AND and OR are the exception: they stay reserved (upper case;
// lower-case and/or are plain names), so they are not tested as names.
@ExtendWith(InjectionExtension.class)
@InjectWith(WorkloadInjectorProvider.class)
public class WorkloadKeywordTest {

	// every word ShardML.xtext uses as a keyword, with the trailing colon dropped: a regression
	// test for the workload grammar inheriting ShardML's lexer
	private static final List<String> SHARDML_WORDS = List.of(
			"import", "database", "type", "node", "host", "port", "place", "strategy", "nodes", "spread", "pin",
			"postgres", "mysql", "has_many", "has_one", "belongs_to", "via", "shard", "key", "buckets",
			"colocate_with", "hash", "consistent_hash", "replicated", "route", "policy", "max_conditions", "allow",
			"warn", "deny", "query", "where", "sort", "group_by", "distinct", "true", "ASC", "DESC");

	// the workload language's own keywords, AND and OR excepted
	private static final List<String> WORKLOAD_WORDS = List.of(
			"import", "workload", "param", "domain", "size", "within", "table", "rows", "per", "over", "unique",
			"uniform", "zipf", "transaction", "weight", "let", "else", "with", "distinct", "from", "repeat", "join",
			"on", "where", "read", "write", "delete");

	@Inject
	private ParseHelper<WorkloadModel> parseHelper;

	// the word as the name of the workload, a param, a domain, a table, a column (mapped,
	// unique, in a condition, qualified, in a join), a variable (declared, referenced, as the
	// base of else with and distinct from) and a transaction, and as a domain in a projection
	private static String everywhere(String w) {
		return """
				workload %1$s {
				    param %1$s = 1
				    domain %1$s { size: 4 }
				    domain child { size: 8 within %1$s zipf($%1$s) }
				    table %1$s { rows: per %1$s  %1$s: %1$s  unique %1$s }
				    table other { rows: 5 over %1$s uniform  %1$s: child.%1$s }
				    transaction %1$s weight $%1$s {
				        let %1$s = %1$s uniform
				        let x = $%1$s, else with 0.5: %1$s uniform distinct from $%1$s
				        repeat 2 {
				            read %1$s join %1$s on %1$s.%1$s = %1$s.%1$s where %1$s = $%1$s.%1$s AND %1$s.%1$s = ?
				        }
				        write %1$s where %1$s = $%1$s
				    }
				}
				""".formatted(w);
	}

	private List<String> syntaxErrors(String text) throws Exception {
		WorkloadModel model = parseHelper.parse(text);
		List<String> errors = new ArrayList<>();
		model.eResource().getErrors().stream()
				.filter(d -> d instanceof XtextSyntaxDiagnostic)
				.forEach(d -> errors.add("line " + d.getLine() + ": " + d.getMessage()));
		return errors;
	}

	private void assertAllAreNames(List<String> words, String kind) throws Exception {
		List<String> failures = new ArrayList<>();
		for (String w : words) {
			List<String> errors = syntaxErrors(everywhere(w));
			if (!errors.isEmpty())
				failures.add(w + " -> " + errors.get(0));
		}
		Assertions.assertTrue(failures.isEmpty(), failures.size() + " of " + words.size() + " " + kind
				+ " fail as names:\n" + String.join("\n", failures));
	}

	@Test
	public void testShardMLKeywordsAreNames() throws Exception {
		assertAllAreNames(SHARDML_WORDS, "ShardML keywords");
	}

	@Test
	public void testWorkloadKeywordsAreNames() throws Exception {
		assertAllAreNames(WORKLOAD_WORDS, "workload keywords");
	}

	@Test
	public void testTemplateParsesWithPlainNames() throws Exception {
		// guards the template itself: with an ordinary name it must parse cleanly
		Assertions.assertEquals(List.of(), syntaxErrors(everywhere("plain")));
	}
}
