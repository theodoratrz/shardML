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
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.workload.Let;
import io.shardml.workload.Repeat;
import io.shardml.workload.Statement;
import io.shardml.workload.WorkloadModel;
import io.shardml.workload.WorkloadPackage;

// What a workload's names link to (SHML_040-042, SHML_053); every issue is checked at its
// exact location, and each scoping rule has a case that links and one that does not
@ExtendWith(InjectionExtension.class)
@InjectWith(WorkloadInjectorProvider.class)
public class WorkloadScopingTest {

	private static final String MSQL = """
			CREATE TABLE customers ( id BIGINT PRIMARY KEY, name TEXT )
			CREATE TABLE accounts ( id BIGINT NOT NULL, customer_id BIGINT NOT NULL, PRIMARY KEY (customer_id, id) )
			CREATE TABLE ledger ( account_id BIGINT NOT NULL, amount DECIMAL )
			""";

	// the domains, params and table blocks every case starts from
	private static final String HEAD = """
			import "s.msql"
			workload w {
			    param p = 0.5
			    domain customer { size: 4 }
			    domain account { size: 8 within customer uniform }
			    table customers { rows: per customer  id: customer }
			    table accounts { rows: per account  id: account  customer_id: account.customer }
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

	private WorkloadModel parse(String body) throws Exception {
		return support.parse("dummy:/w.workload", HEAD + body + "}\n");
	}

	private int at(String snippet, int occurrence, int shift) {
		return support.at(snippet, occurrence, shift);
	}

	private int at(String snippet) {
		return support.at(snippet);
	}

	// the codes of all issues, so a case also proves there is no second, follow-on error
	private List<String> codes(WorkloadModel model) {
		return support.issues(model).stream().map(Issue::getCode).toList();
	}

	@Test
	public void testHeadLinks() throws Exception {
		support.assertNoIssues(parse(""));
	}

	// --- imports ---

	@Test
	public void testImportResolvesRelativeToTheWorkload() throws Exception {
		support.schema("dummy:/dir/schemas/rel.msql", MSQL);
		WorkloadModel model = support.parse("dummy:/dir/w.workload", """
				import "schemas/rel.msql"
				workload w {
				    domain customer { size: 4 }
				    table customers { rows: per customer  id: customer }
				}
				""");
		support.assertNoIssues(model);
	}

	@Test
	public void testImportOfSomethingElseLinksNoTables() throws Exception {
		// the import does not resolve to a schema, so every table is unknown (SHML_057 will say why)
		support.schema("dummy:/not-a-schema.msql", "");
		WorkloadModel model = support.parse("dummy:/w2.workload", """
				import "not-a-schema.msql"
				workload w {
				    domain customer { size: 4 }
				    table customers { rows: per customer }
				}
				""");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.TABLE_DATA, "SHML_040", at("customers"), 9,
				"Unknown table 'customers' in the imported schema.");
	}

	// --- SHML_040: tables ---

	@Test
	public void testUnknownTableInTableBlock() throws Exception {
		// its column mappings get no second error
		WorkloadModel model = parse("    table cards { rows: per customer  customer_id: customer  unique id }\n");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.TABLE_DATA, "SHML_040", at("cards"), 5,
				"Unknown table 'cards' in the imported schema.");
		Assertions.assertEquals(List.of("SHML_040"), codes(model));
	}

	@Test
	public void testUnknownTableInStatementAndJoin() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        read cards where id = ?
				        read customers join cards on customers.id = cards.id
				    }
				""");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.STATEMENT, "SHML_040", at("cards", 1, 0), 5,
				"Unknown table 'cards'");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.JOIN, "SHML_040", at("cards", 2, 0), 5,
				"Unknown table 'cards'");
	}

	@Test
	public void testQualifierMustBeTheStatementsTables() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        read customers join accounts on customers.id = accounts.customer_id where ledger.amount = ?
				    }
				""");
		// ledger exists in the schema, but is not this statement's table or a join; its column gets no second error
		validationTestHelper.assertError(model, WorkloadPackage.Literals.COLUMN_REF, "SHML_040", at("ledger"), 6,
				"'ledger' is not this statement's table or one of its joins. Tables here: customers, accounts.");
		Assertions.assertEquals(List.of("SHML_040"), codes(model));
	}

	// --- SHML_041: columns ---

	@Test
	public void testTableBlockMapsItsOwnColumns() throws Exception {
		// customer_id is a column of accounts, not of customers
		WorkloadModel model = parse("    table ledger { rows: per account  account_id: account  customer_id: account.customer }\n");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.MAPPED_COLUMN, "SHML_041", at("customer_id: account.customer", 2, 0), 11,
				"Unknown column 'customer_id' in this table. Columns: account_id, amount.");
		Assertions.assertEquals(List.of("SHML_041"), codes(model));
	}

	@Test
	public void testStatementColumnsComeFromItsTableAndJoins() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        read customers join accounts on customers.id = accounts.customer_id where customer_id = $a.customer AND name = ?
				        read customers where customer_id = ?
				        read customers join accounts on customers.id = accounts.name
				    }
				""");
		// customer_id links through the join in the first statement, not in the second
		validationTestHelper.assertError(model, WorkloadPackage.Literals.COLUMN_REF, "SHML_041", at("customer_id = ?"), 11,
				"Unknown column 'customer_id' in this statement's tables.");
		// a qualified column must belong to its qualifier
		validationTestHelper.assertError(model, WorkloadPackage.Literals.COLUMN_REF, "SHML_041", at("accounts.name", 1, 9), 4,
				"Unknown column 'name' in 'accounts'.");
		Assertions.assertEquals(List.of("SHML_041", "SHML_041"), codes(model));
	}

	// --- SHML_042: domains and params ---

	@Test
	public void testUnknownDomainsAndParams() throws Exception {
		WorkloadModel model = parse("""
				    table ledger { rows: 10 over region uniform  account_id: account }
				    transaction t weight $q {
				        let a = branch zipf($p)
				    }
				""");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.OVER_ROWS, "SHML_042", at("region"), 6,
				"Unknown domain 'region'. Declared domains: customer, account.");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.NUM, "SHML_042", at("q {", 1, 0), 1,
				"Unknown param 'q'. Declared params: p.");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.DRAW, "SHML_042", at("branch"), 6,
				"Unknown domain 'branch'.");
		Assertions.assertEquals(List.of("SHML_042", "SHML_042", "SHML_042"), codes(model));
	}

	// --- SHML_042: variables, scoped per transaction, after their let, repeat-local ---

	@Test
	public void testVariablesLinkToEarlierLetsAndOuterBlocks() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        repeat 2 {
				            let b = account uniform distinct from $a
				            write accounts where customer_id = $b.customer AND id = $b
				        }
				        let c = $a, else with $p: account uniform
				        write accounts where id = $c
				    }
				""");
		support.assertNoIssues(model);
		Repeat repeat = (Repeat) model.getWorkload().getTransactions().get(0).getSteps().get(1);
		Let b = (Let) repeat.getSteps().get(0);
		Assertions.assertEquals("a", b.getDraw().getDistinctFrom().getVariable().getName());
	}

	@Test
	public void testVariableOfAnotherTransaction() throws Exception {
		WorkloadModel model = parse("""
				    transaction t1 weight 1 {
				        let a = account uniform
				    }
				    transaction t2 weight 1 {
				        write accounts where id = $a
				    }
				""");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.VAR_REF, "SHML_042", at("$a", 1, 1), 1,
				"Unknown variable 'a'", "There are none.");
		Assertions.assertEquals(List.of("SHML_042"), codes(model));
	}

	@Test
	public void testVariableBeforeItsLet() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = account uniform
				        write accounts where id = $b
				        let b = account uniform distinct from $b
				    }
				""");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.VAR_REF, "SHML_042", at("$b", 1, 1), 1,
				"Unknown variable 'b'", "Visible here: a.");
		// a let cannot refer to itself either
		validationTestHelper.assertError(model, WorkloadPackage.Literals.VAR_REF, "SHML_042", at("$b", 2, 1), 1,
				"Unknown variable 'b'", "Visible here: a.");
		Assertions.assertEquals(List.of("SHML_042", "SHML_042"), codes(model));
	}

	@Test
	public void testRepeatLocalVariableAfterTheBlock() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        repeat 2 {
				            let i = account uniform
				            write accounts where id = $i
				        }
				        write accounts where id = $i
				    }
				""");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.VAR_REF, "SHML_042", at("$i", 2, 1), 1,
				"Unknown variable 'i'");
		Assertions.assertEquals(List.of("SHML_042"), codes(model));
	}

	@Test
	public void testInnerLetShadowsOuter() throws Exception {
		WorkloadModel model = parse("""
				    transaction t weight 1 {
				        let a = customer uniform
				        repeat 2 {
				            let a = account uniform
				            write accounts where id = $a
				        }
				    }
				""");
		support.assertNoIssues(model);
		Repeat repeat = (Repeat) model.getWorkload().getTransactions().get(0).getSteps().get(1);
		Statement write = (Statement) repeat.getSteps().get(1);
		Assertions.assertSame(repeat.getSteps().get(0), write.getFirst().getValue().getVariable());
	}

	// --- SHML_053: projections ---

	@Test
	public void testProjectionToAnAncestor() throws Exception {
		WorkloadModel model = parse("""
				    table ledger { rows: per account  account_id: account.account }
				    transaction t weight 1 {
				        let c = customer uniform
				        let a = account uniform
				        write accounts where customer_id = $a.customer AND id = $c.customer
				        write accounts where id = $z.customer
				    }
				""");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.KEY_PATH, "SHML_053", at("account.account", 1, 8), 7,
				"'account' is not an ancestor of this domain. Ancestors: customer.");
		validationTestHelper.assertError(model, WorkloadPackage.Literals.VAR_REF, "SHML_053", at("$c.customer", 1, 3), 8,
				"'customer' is not an ancestor of this domain. There are none.");
		// an unknown variable is one error, not two
		validationTestHelper.assertError(model, WorkloadPackage.Literals.VAR_REF, "SHML_042", at("$z", 1, 1), 1,
				"Unknown variable 'z'");
		Assertions.assertEquals(List.of("SHML_053", "SHML_053", "SHML_042"), codes(model));
	}
}
