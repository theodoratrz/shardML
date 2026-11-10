package io.shardml.tests;

import java.util.List;

import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.workload.WorkloadModel;
import io.shardml.workload.WorkloadPackage.Literals;

// The workload validators (SHML_043-060). Each rule has a case where it fires, checked at
// the exact location, and one where it does not; every case asserts the codes of all issues,
// so a follow-on error or warning fails it. SHML_044 is not tested: numbers in the grammar are
// unsigned, so a negative exponent is a syntax error and the check could never fire.
@ExtendWith(InjectionExtension.class)
@InjectWith(WorkloadInjectorProvider.class)
public class WorkloadValidationTest {

	private static final String MSQL = """
			CREATE TABLE customers ( id BIGINT PRIMARY KEY, name TEXT )
			CREATE TABLE accounts ( id BIGINT NOT NULL, customer_id BIGINT NOT NULL, PRIMARY KEY (customer_id, id) )
			CREATE TABLE cards ( id BIGINT NOT NULL, customer_id BIGINT NOT NULL )
			CREATE TABLE ledger ( account_id BIGINT NOT NULL, amount DECIMAL )
			CREATE TABLE audit ( customer_id BIGINT NOT NULL, note TEXT )
			""";

	// the params, domains and table blocks every case starts from; a case adds its own after them
	private static final String PARAMS = """
			    param p = 0.5
			    param zero = 0
			    param two = 2
			""";
	private static final String DOMAINS = """
			    domain customer { size: 4 }
			    domain account { size: 8 within customer uniform }
			    domain one { size: 1 }
			""";
	private static final String TABLES = """
			    table customers { rows: per customer  id: customer }
			    table accounts { rows: per account  id: account  customer_id: account.customer }
			    table cards { rows: 2 per customer  customer_id: customer  unique id }
			""";

	@Inject
	private ParseHelper<WorkloadModel> parseHelper;
	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private IResourceValidator validator;
	@Inject
	private ValidationTestHelper validationTestHelper;

	private WorkloadTestSupport support;

	@BeforeEach
	public void setUp() throws Exception {
		support = new WorkloadTestSupport(parseHelper, resourceSets.get(), validator);
		support.schema("dummy:/s.msql", MSQL);
	}

	private WorkloadModel parse(String params, String domains, String body) throws Exception {
		return support.parse("dummy:/w.workload",
				"import \"s.msql\"\nworkload w {\n" + PARAMS + params + DOMAINS + domains + TABLES + body + "}\n");
	}

	// body: table blocks, then transactions
	private WorkloadModel parse(String body) throws Exception {
		return parse("", "", body);
	}

	private int at(String snippet, int occurrence, int shift) {
		return support.at(snippet, occurrence, shift);
	}

	private int at(String snippet) {
		return support.at(snippet);
	}

	private List<String> codes(WorkloadModel model) {
		return support.issues(model).stream().map(Issue::getCode).toList();
	}

	private static List<String> repeat(String code, int times) {
		return java.util.Collections.nCopies(times, code);
	}

	@Test
	public void testHeadIsClean() throws Exception {
		// `rows: per customer` has no count; it is not read as a count of 0
		support.assertNoIssues(parse(""));
	}

	// --- SHML_043: weight ---

	@Test
	public void testWeightNotPositive() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 0 { }
				    transaction u weight $zero { }
				""");
		validationTestHelper.assertError(model, Literals.TRANSACTION, "SHML_043", at("weight 0", 1, 7), 1,
				"The weight of 't' is 0; a transaction's weight must be greater than 0.");
		validationTestHelper.assertError(model, Literals.TRANSACTION, "SHML_043", at("$zero"), 5,
				"The weight of 'u' is $zero = 0;");
		Assertions.assertEquals(repeat("SHML_043", 2), codes(model));
	}

	@Test
	public void testWeightPositive() throws Exception {
		support.assertNoIssues(parse("""
				    transaction t weight 0.5 { }
				    transaction u weight $p { }
				"""));
	}

	// --- SHML_045: else with probability ---

	@Test
	public void testProbabilityAboveOne() throws Exception {
		// below 0 cannot be written: numbers are unsigned
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let b = $a, else with 1.5: account uniform
				        let c = $a, else with $two: account uniform
				        write accounts where id = $b AND id = $c
				    }
				""");
		validationTestHelper.assertError(model, Literals.LET, "SHML_045", at("1.5"), 3,
				"The probability of `else with` is 1.5; it must be between 0 and 1.");
		validationTestHelper.assertError(model, Literals.LET, "SHML_045", at("$two"), 4,
				"The probability of `else with` is $two = 2;");
		Assertions.assertEquals(repeat("SHML_045", 2), codes(model));
	}

	@Test
	public void testProbabilityInRange() throws Exception {
		support.assertNoIssues(parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let b = $a, else with 0: account uniform
				        let c = $a, else with 1: account uniform
				        let d = $a, else with $p: account uniform
				        write accounts where id = $b AND id = $c AND id = $d
				    }
				"""));
	}

	// --- SHML_046: sizes and counts ---

	@Test
	public void testCountsBelowOne() throws Exception {
		WorkloadModel model = parse("", "    domain empty { size: 0 }\n", """
				    table ledger { rows: 0 over account uniform  account_id: account }
				    table audit { rows: 0 per customer  customer_id: customer }
				    transaction t weight 1 {
				        repeat 0 { }
				    }
				""");
		validationTestHelper.assertError(model, Literals.DOMAIN, "SHML_046", at("size: 0", 1, 6), 1,
				"The size of domain 'empty' is 0; it must be at least 1.");
		validationTestHelper.assertError(model, Literals.OVER_ROWS, "SHML_046", at("rows: 0 over", 1, 6), 1,
				"The row count is 0; it must be at least 1.");
		validationTestHelper.assertError(model, Literals.PER_ROWS, "SHML_046", at("rows: 0 per", 1, 6), 1,
				"The row count is 0;");
		validationTestHelper.assertError(model, Literals.REPEAT, "SHML_046", at("repeat 0", 1, 7), 1,
				"The repeat count is 0; it must be at least 1.");
		Assertions.assertEquals(repeat("SHML_046", 4), codes(model));
	}

	@Test
	public void testCountsOfOne() throws Exception {
		// domain `one` (size 1) is in the head
		support.assertNoIssues(parse("""
				    table ledger { rows: 1 over account uniform  account_id: account }
				    table audit { rows: 1 per customer  customer_id: customer }
				    transaction t weight 1 {
				        repeat 1 { let a = account uniform  write accounts where id = $a }
				    }
				"""));
	}

	// --- SHML_047: a variable bound to a column of another domain ---

	@Test
	public void testVariableOfAnotherDomain() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let c = customer uniform
				        let k = customer uniform
				        read accounts where customer_id = $a
				        read accounts where id = $c
				        read cards where id = $k
				    }
				""");
		// customer is an ancestor of account, so the message says how to project
		validationTestHelper.assertError(model, Literals.CONDITION, "SHML_047", at("customer_id = $a", 1, 14), 2,
				"$a is a value of 'account', but 'customer_id' of 'accounts' maps to 'customer'. Write $a.customer.");
		// account is not an ancestor of customer: no hint
		validationTestHelper.assertError(model, Literals.CONDITION, "SHML_047", at("id = $c", 1, 5), 2,
				"$c is a value of 'customer', but 'id' of 'accounts' maps to 'account'.");
		validationTestHelper.assertError(model, Literals.CONDITION, "SHML_047", at("$k", 1, 0), 2,
				"'id' of 'cards' is unique (a fresh value per row), so it has no domain a variable could bind");
		Assertions.assertEquals(repeat("SHML_047", 3), codes(model));
	}

	@Test
	public void testVariableOfTheColumnsDomain() throws Exception {
		support.assertNoIssues(parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let c = customer uniform
				        read accounts where customer_id = $a.customer AND id = $a
				        read accounts where customer_id = $c
				        read customers where id > $c
				        read cards where id = ? AND customer_id = $a.customer
				    }
				"""));
	}

	// --- SHML_048: statement on a table without a table block ---

	@Test
	public void testStatementOnTableWithoutBlock() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        read ledger where amount = ?
				        read customers join ledger on customers.id = ledger.account_id
				    }
				""");
		validationTestHelper.assertError(model, Literals.STATEMENT, "SHML_048", at("read ledger", 1, 5), 6,
				"'ledger' has no table block, so the workload says nothing about its rows. Add `table ledger { rows: ... }`.");
		validationTestHelper.assertError(model, Literals.JOIN, "SHML_048", at("join ledger", 1, 5), 6,
				"'ledger' has no table block");
		Assertions.assertEquals(repeat("SHML_048", 2), codes(model));
	}

	@Test
	public void testStatementOnTableWithBlock() throws Exception {
		support.assertNoIssues(parse("""
				    table ledger { rows: per account  account_id: account }
				    transaction t weight 1 {
				        read ledger where amount = ?
				        read customers join ledger on customers.id = ledger.account_id
				    }
				"""));
	}

	// --- SHML_049: a column bound with = that its table block does not map ---

	@Test
	public void testBoundColumnNotMapped() throws Exception {
		WorkloadModel model = parse("""
				    table ledger { rows: per account  account_id: account }
				    transaction t weight 1 {
				        let a = account uniform
				        read ledger where amount = $a
				    }
				""");
		validationTestHelper.assertError(model, Literals.CONDITION, "SHML_049", at("amount = $a"), 6,
				"'amount' of 'ledger' is bound to $a, but the table block of 'ledger' does not map it. Add `amount: account` there.");
		Assertions.assertEquals(List.of("SHML_049"), codes(model));
	}

	@Test
	public void testUnmappedColumnNotBound() throws Exception {
		// `?` and a range bind nothing, so an unmapped column is fine there
		support.assertNoIssues(parse("""
				    table ledger { rows: per account  account_id: account }
				    transaction t weight 1 {
				        let a = account uniform
				        read ledger where account_id = $a AND amount = ? AND amount > $a
				    }
				"""));
	}

	// --- SHML_050: duplicate names ---

	@Test
	public void testDuplicateNames() throws Exception {
		WorkloadModel model = parse("    param p = 1\n", "    domain one { size: 2 }\n", """
				    table customers { rows: per customer }
				    table ledger { rows: per account  account_id: account  account_id: account }
				    transaction t weight 1 { }
				    transaction t weight 1 {
				        let a = account uniform
				        let a = account uniform
				        write accounts where id = $a
				    }
				""");
		validationTestHelper.assertError(model, Literals.PARAM, "SHML_050", at("param p = 1", 1, 6), 1, "Duplicate param 'p'.");
		validationTestHelper.assertError(model, Literals.DOMAIN, "SHML_050", at("domain one", 2, 7), 3, "Duplicate domain 'one'.");
		validationTestHelper.assertError(model, Literals.TABLE_DATA, "SHML_050", at("table customers", 2, 6), 9,
				"Duplicate table block for 'customers'.");
		validationTestHelper.assertError(model, Literals.MAPPED_COLUMN, "SHML_050", at("account_id: account", 2, 0), 10,
				"Duplicate mapping of column 'account_id'.");
		validationTestHelper.assertError(model, Literals.TRANSACTION, "SHML_050", at("transaction t", 2, 12), 1,
				"Duplicate transaction 't'.");
		// the second a is not also reported as unused (SHML_055)
		validationTestHelper.assertError(model, Literals.LET, "SHML_050", at("let a", 2, 4), 1, "Duplicate variable 'a'.");
		Assertions.assertEquals(repeat("SHML_050", 6), codes(model));
	}

	@Test
	public void testSameNameInDifferentKindsOrBlocks() throws Exception {
		// a param, domain, transaction and variable may share a name; a repeat may shadow an
		// outer variable, and sibling repeats may each declare one
		support.assertNoIssues(parse("    param x = 1\n", "    domain x { size: 2 }\n", """
				    transaction x weight $x {
				        let x = x uniform
				        let a = customer uniform
				        read customers where id = $a AND name > $x
				        repeat 2 { let a = account uniform  write accounts where id = $a }
				        repeat 2 { let a = account uniform  write accounts where id = $a }
				    }
				"""));
	}

	// --- SHML_051: distinct from / else with across domains, or on a domain of size 1 ---

	@Test
	public void testDependencyAcrossDomainsOrOnOneValue() throws Exception {
		WorkloadModel model = parse("""
				    table ledger { rows: per one  account_id: one }
				    transaction t weight 1 {
				        let a = account uniform
				        let c = customer uniform distinct from $a
				        let d = $a, else with 0.1: customer uniform
				        let o = one uniform
				        let o2 = one uniform distinct from $o
				        read customers where id = $c AND id = $d
				        read ledger where account_id = $o2
				    }
				""");
		validationTestHelper.assertError(model, Literals.DRAW, "SHML_051", at("from $a", 1, 5), 2,
				"$a is a value of 'account', but the draw is from 'customer'; `distinct from` compares values of one domain.");
		validationTestHelper.assertError(model, Literals.LET, "SHML_051", at("= $a,", 1, 2), 2,
				"$a is a value of 'account', but the draw is from 'customer'; `else with` compares values of one domain.");
		validationTestHelper.assertError(model, Literals.DRAW, "SHML_051", at("one uniform distinct"), 3,
				"Domain 'one' has one value, so a draw that excludes $o has nothing to draw from.");
		Assertions.assertEquals(repeat("SHML_051", 3), codes(model));
	}

	@Test
	public void testDependencyInOneDomain() throws Exception {
		// a projected base is compared by the projection's domain
		support.assertNoIssues(parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let b = account uniform distinct from $a
				        let d = $a, else with 0.1: account uniform
				        let e = $a.customer, else with 0.1: customer uniform
				        write accounts where id = $b AND id = $d
				        read customers where id = $e
				    }
				"""));
	}

	// --- SHML_052: what a dependent draw may depend on ---

	@Test
	public void testDependencyOnADependentOrRepeatedVariable() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let b = account uniform distinct from $a
				        let c = $b, else with 0.1: account uniform
				        let d = account uniform distinct from $c
				        let k = $a, else with 0.1: account uniform distinct from $a
				        repeat 2 {
				            let i = account uniform
				            let j = $i, else with 0.1: account uniform
				            write accounts where id = $j
				        }
				        write accounts where id = $d AND id = $k
				    }
				""");
		validationTestHelper.assertError(model, Literals.LET, "SHML_052", at("$b,"), 2,
				"$b depends on another variable itself; `else with` needs an independent draw (`let b = D dist`).");
		validationTestHelper.assertError(model, Literals.DRAW, "SHML_052", at("from $c", 1, 5), 2,
				"$c depends on another variable itself; `distinct from` needs an independent draw");
		validationTestHelper.assertError(model, Literals.DRAW, "SHML_052", at("from $a", 2, 5), 2,
				"`else with` already excludes $a, so it cannot also be `distinct from` $a.");
		validationTestHelper.assertError(model, Literals.LET, "SHML_052", at("$i,"), 2,
				"$i is declared in a repeat block; `else with` needs a variable declared in the transaction itself, drawn once.");
		Assertions.assertEquals(repeat("SHML_052", 4), codes(model));
	}

	@Test
	public void testDependencyOnATopLevelIndependentVariable() throws Exception {
		// the TPC-C shape: dependents in a repeat on a variable drawn once
		support.assertNoIssues(parse("""
				    transaction t weight 1 {
				        let w = customer uniform
				        let v = customer uniform distinct from $w
				        read customers where id = $w AND id = $v
				        repeat 3 {
				            let i = account uniform
				            let sw = $w, else with 0.01: customer uniform
				            let x = customer uniform distinct from $w
				            read customers where id = $sw AND id = $x
				            write accounts where id = $i
				        }
				    }
				"""));
	}

	// --- SHML_054: a key path starts at a domain of the rows ---

	@Test
	public void testMappingNotFromTheRowDomain() throws Exception {
		WorkloadModel model = parse("""
				    table ledger { rows: per account  account_id: customer }
				    table audit { rows: 5 over customer uniform  customer_id: account }
				""");
		validationTestHelper.assertError(model, Literals.MAPPED_COLUMN, "SHML_054", at("account_id: customer", 1, 12), 8,
				"'account_id' maps to 'customer', but the rows are per 'account': a key path starts at a domain of the rows. "
						+ "Write `account_id: account.customer`.");
		// account is a child of customer, not an ancestor: no hint
		validationTestHelper.assertError(model, Literals.MAPPED_COLUMN, "SHML_054", at("customer_id: account }", 1, 13), 7,
				"'customer_id' maps to 'account', but the rows are per 'customer': a key path starts at a domain of the rows.");
		Assertions.assertEquals(repeat("SHML_054", 2), codes(model));
	}

	@Test
	public void testMappingFromTheRowDomain() throws Exception {
		support.assertNoIssues(parse("""
				    table ledger { rows: per customer, one  account_id: one }
				    table audit { rows: 5 over account zipf(1.0)  customer_id: account.customer }
				"""));
	}

	// --- SHML_055: unused variables ---

	@Test
	public void testUnusedVariables() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let b = account uniform distinct from $a
				        repeat 2 { let i = account uniform }
				    }
				""");
		// a is used, by b's distinct from
		validationTestHelper.assertWarning(model, Literals.LET, "SHML_055", at("let b", 1, 4), 1, "Variable 'b' is never used.");
		validationTestHelper.assertWarning(model, Literals.LET, "SHML_055", at("let i", 1, 4), 1, "Variable 'i' is never used.");
		Assertions.assertEquals(repeat("SHML_055", 2), codes(model));
	}

	@Test
	public void testUsedVariables() throws Exception {
		support.assertNoIssues(parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        let b = $a, else with 0.5: account uniform
				        let c = account uniform distinct from $a
				        write accounts where id = $b AND id = $c
				    }
				"""));
	}

	// --- SHML_056: a within domain that leaves parents without children ---

	@Test
	public void testChildlessParents() throws Exception {
		WorkloadModel model = parse("", """
				    domain kid { size: 3 within customer uniform }
				    domain parent { size: 10000 }
				    domain big { size: 200000 within parent zipf(1.3) }
				""", "");
		validationTestHelper.assertWarning(model, Literals.DOMAIN, "SHML_056", at("customer uniform }", 2, 9), 7,
				"25.0% of 'customer' values (1 of 4) get no 'kid': largest-remainder rounding of 3 over uniform leaves them none.");
		// the plan's figure for 200k accounts at s = 1.3
		validationTestHelper.assertWarning(model, Literals.DOMAIN, "SHML_056", at("zipf(1.3)"), 9,
				"23.9% of 'parent' values (2385 of 10000) get no 'big': largest-remainder rounding of 200000 over zipf(1.3)");
		Assertions.assertEquals(repeat("SHML_056", 2), codes(model));
	}

	@Test
	public void testFewChildlessParents() throws Exception {
		// 1 of 100 (exactly 1%) is not more than 1%; 1M at s = 1.4 leaves none (the plan's figure)
		support.assertNoIssues(parse("", """
				    domain hundred { size: 100 }
				    domain kid { size: 99 within hundred uniform }
				    domain parent { size: 10000 }
				    domain big { size: 1000000 within parent zipf(1.4) }
				""", ""));
	}

	// --- SHML_057: the import is a .msql schema ---

	@Test
	public void testImportOfALayout() throws Exception {
		WorkloadModel model = support.parse("dummy:/v.workload", """
				import "banking.shardml"
				workload w {
				    domain d { size: 2 }
				}
				""");
		validationTestHelper.assertError(model, Literals.IMPORT, "SHML_057", at("\"banking.shardml\""), 17,
				"'banking.shardml' is not a .msql schema. A workload imports the schema its tables come from, never a .shardml layout");
		Assertions.assertEquals(List.of("SHML_057"), codes(model));
	}

	@Test
	public void testImportOfAMissingSchema() throws Exception {
		// the table cannot link either; that is SHML_040, at the table
		WorkloadModel model = support.parse("dummy:/v.workload", """
				import "missing.msql"
				workload w {
				    domain d { size: 2 }
				    table customers { rows: per d }
				}
				""");
		validationTestHelper.assertError(model, Literals.IMPORT, "SHML_057", at("\"missing.msql\""), 14,
				"'missing.msql' could not be loaded as a schema: the file is missing, empty or not valid SQL.");
		Assertions.assertEquals(List.of("SHML_040", "SHML_057"), codes(model));
	}

	@Test
	public void testImportOfASchema() throws Exception {
		support.schema("dummy:/dir/schemas/s2.msql", MSQL);
		support.assertNoIssues(support.parse("dummy:/dir/v.workload", """
				import "schemas/s2.msql"
				workload w {
				    domain d { size: 2 }
				    table customers { rows: per d  id: d }
				}
				"""));
	}

	// --- SHML_059: a domain that is its own ancestor ---

	@Test
	@Timeout(10)
	public void testDomainCycles() throws Exception {
		// c hangs below the a-b cycle without being on it: no SHML_059, and no SHML_056 either
		WorkloadModel model = parse("", """
				    domain self { size: 2 within self uniform }
				    domain a { size: 4 within b uniform }
				    domain b { size: 4 within a uniform }
				    domain c { size: 1 within a uniform }
				""", "");
		validationTestHelper.assertError(model, Literals.DOMAIN, "SHML_059", at("within self", 1, 7), 4,
				"Domain 'self' is its own ancestor: self within self.");
		validationTestHelper.assertError(model, Literals.DOMAIN, "SHML_059", at("within b", 1, 7), 1,
				"Domain 'a' is its own ancestor: a within b within a.");
		validationTestHelper.assertError(model, Literals.DOMAIN, "SHML_059", at("within a", 1, 7), 1,
				"Domain 'b' is its own ancestor: b within a within b.");
		Assertions.assertEquals(repeat("SHML_059", 3), codes(model));
	}

	@Test
	@Timeout(10)
	public void testDomainChainWithoutCycle() throws Exception {
		// a chain three deep, and two domains sharing a parent
		support.assertNoIssues(parse("", """
				    domain root { size: 2 }
				    domain mid { size: 4 within root uniform }
				    domain leaf { size: 8 within mid uniform }
				    domain leaf2 { size: 8 within mid zipf(0.5) }
				""", ""));
	}

	// --- SHML_060: a second import ---

	@Test
	public void testExtraImports() throws Exception {
		// only the first import is read; a later one gets SHML_060 alone, even when it is a
		// layout (no SHML_057 for it)
		WorkloadModel model = support.parse("dummy:/v.workload", """
				import "s.msql"
				import "other.msql"
				import "layout.shardml"
				workload w {
				    domain d { size: 2 }
				    table customers { rows: per d  id: d }
				}
				""");
		validationTestHelper.assertWarning(model, Literals.IMPORT, "SHML_060", at("\"other.msql\""), 12,
				"Only the first import is read; this one is ignored.");
		validationTestHelper.assertWarning(model, Literals.IMPORT, "SHML_060", at("\"layout.shardml\""), 16,
				"Only the first import is read; this one is ignored.");
		Assertions.assertEquals(repeat("SHML_060", 2), codes(model));
	}

	@Test
	public void testSingleImport() throws Exception {
		support.assertNoIssues(support.parse("dummy:/v.workload", """
				import "s.msql"
				workload w {
				    domain d { size: 2 }
				    table customers { rows: per d  id: d }
				}
				"""));
	}

	// --- SHML_058: an unqualified column more than one table of a join has ---

	@Test
	public void testAmbiguousColumn() throws Exception {
		WorkloadModel model = parse("""
				    table ledger { rows: per account  account_id: account }
				    transaction t weight 1 {
				        read customers join accounts on customers.id = accounts.customer_id where id = ?
				        read ledger join customers on ledger.account_id = customers.id join accounts on accounts.id = ledger.account_id where id = ?
				        read customers join accounts on id = customer_id
				    }
				""");
		validationTestHelper.assertWarning(model, Literals.COLUMN_REF, "SHML_058", at("where id", 1, 6), 2,
				"'id' is a column of 'customers', 'accounts'; unqualified, it links to 'customers', the statement's own table. "
						+ "Qualify it (e.g. accounts.id) to say which.");
		validationTestHelper.assertWarning(model, Literals.COLUMN_REF, "SHML_058", at("where id", 2, 6), 2,
				"unqualified, it links to 'customers', the first join that has it.");
		validationTestHelper.assertWarning(model, Literals.COLUMN_REF, "SHML_058", at("on id", 1, 3), 2,
				"unqualified, it links to 'customers', the statement's own table.");
		Assertions.assertEquals(repeat("SHML_058", 3), codes(model));
	}

	@Test
	public void testUnambiguousColumn() throws Exception {
		// columns only one table has, a qualified column, and an unqualified one without a join
		support.assertNoIssues(parse("""
				    transaction t weight 1 {
				        read customers join accounts on customers.id = accounts.customer_id where name = ? AND customer_id = ? AND accounts.id = ?
				        read customers where id = ?
				    }
				"""));
	}
}
