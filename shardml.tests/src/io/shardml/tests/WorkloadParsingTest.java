package io.shardml.tests;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.IResourceValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.sql.Table;
import io.shardml.workload.Domain;
import io.shardml.workload.JoinOn;
import io.shardml.workload.Let;
import io.shardml.workload.MappedColumn;
import io.shardml.workload.OverRows;
import io.shardml.workload.Statement;
import io.shardml.workload.TableData;
import io.shardml.workload.Transaction;
import io.shardml.workload.UniqueColumn;
import io.shardml.workload.WorkloadModel;
import io.shardml.workload.WorkloadPackage;
import io.shardml.workload.Zipf;

// The plan's example workloads, numbers, and the syntax that is meant not to parse
@ExtendWith(InjectionExtension.class)
@InjectWith(WorkloadInjectorProvider.class)
public class WorkloadParsingTest {

	static final String BANKING = """
			import "banking.msql"
			workload banking {
			    param s = 1.0
			    param overview_w = 2
			    param history_w = 48
			    param transfer_w = 50
			    domain customer { size: 10000 }
			    domain account  { size: 1000000 within customer zipf($s) }
			    table customers    { rows: per customer   id: customer }
			    table cards        { rows: per customer   customer_id: customer  unique id }
			    table accounts     { rows: per account    id: account  customer_id: account.customer }
			    table transactions { rows: 2000000 over account uniform
			                         account_id: account  customer_id: account.customer  unique id }
			    transaction overview weight $overview_w {
			        let c = customer uniform
			        read customers where id = $c
			        read accounts  where customer_id = $c
			        read cards     where customer_id = $c
			    }
			    transaction history weight $history_w {
			        let a = account uniform
			        read transactions where customer_id = $a.customer AND account_id = $a
			    }
			    transaction transfer weight $transfer_w {
			        let src = account uniform
			        let dst = account uniform distinct from $src
			        write accounts     where customer_id = $src.customer AND id = $src
			        write accounts     where customer_id = $dst.customer AND id = $dst
			        write transactions where customer_id = $src.customer AND account_id = $src
			        write transactions where customer_id = $dst.customer AND account_id = $dst
			    }
			}
			""";

	static final String TPCC_MSQL = """
			CREATE TABLE warehouse ( w_id INT PRIMARY KEY )
			CREATE TABLE district ( d_w_id INT NOT NULL, d_id INT NOT NULL, PRIMARY KEY (d_w_id, d_id) )
			CREATE TABLE customer ( c_w_id INT NOT NULL, c_d_id INT NOT NULL, c_id INT NOT NULL )
			CREATE TABLE history ( h_w_id INT NOT NULL, h_amount DECIMAL )
			CREATE TABLE orders ( o_w_id INT NOT NULL, o_id INT NOT NULL )
			CREATE TABLE new_order ( no_w_id INT NOT NULL, no_o_id INT NOT NULL )
			CREATE TABLE order_line ( ol_w_id INT NOT NULL, ol_o_id INT NOT NULL, ol_i_id INT NOT NULL )
			CREATE TABLE item ( i_id INT PRIMARY KEY )
			CREATE TABLE stock ( s_w_id INT NOT NULL, s_i_id INT NOT NULL, s_quantity INT )
			""";

	static final String TPCC = """
			import "tpcc.msql"
			workload tpcc {
			    param remote = 0.01      // per order line, the spec value
			    param pay_remote = 0.15

			    domain warehouse { size: 12 }
			    domain item      { size: 100000 }

			    table warehouse  { rows: per warehouse          w_id: warehouse }
			    table district   { rows: 10 per warehouse       d_w_id: warehouse }
			    table customer   { rows: 30000 per warehouse    c_w_id: warehouse }
			    table history    { rows: 30000 per warehouse    h_w_id: warehouse }
			    table orders     { rows: 30000 per warehouse    o_w_id: warehouse }
			    table new_order  { rows: 9000 per warehouse     no_w_id: warehouse }
			    table order_line { rows: 300000 per warehouse   ol_w_id: warehouse }
			    table stock      { rows: per warehouse, item    s_w_id: warehouse  s_i_id: item }
			    table item       { rows: per item               i_id: item }

			    transaction new_order weight 45 {
			        let w = warehouse uniform
			        read  warehouse where w_id = $w
			        write district  where d_w_id = $w
			        read  customer  where c_w_id = $w
			        write orders    where o_w_id = $w
			        write new_order where no_w_id = $w
			        repeat 10 {
			            let i  = item uniform
			            let sw = $w, else with $remote: warehouse uniform
			            read  item       where i_id = $i
			            write stock      where s_w_id = $sw AND s_i_id = $i
			            write order_line where ol_w_id = $w
			        }
			    }
			    transaction payment weight 43 {
			        let w  = warehouse uniform
			        let cw = $w, else with $pay_remote: warehouse uniform
			        write warehouse where w_id = $w
			        write district  where d_w_id = $w
			        write customer  where c_w_id = $cw
			        write history   where h_w_id = $w
			    }
			    transaction order_status weight 4 {
			        let w = warehouse uniform
			        read customer   where c_w_id = $w
			        read orders     where o_w_id = $w
			        read order_line where ol_w_id = $w
			    }
			    transaction delivery weight 4 {
			        let w = warehouse uniform
			        write new_order  where no_w_id = $w
			        write orders     where o_w_id = $w
			        write order_line where ol_w_id = $w
			        write customer   where c_w_id = $w
			    }
			    transaction stock_level weight 4 {
			        let w = warehouse uniform
			        read district where d_w_id = $w
			        read order_line join stock on order_line.ol_i_id = stock.s_i_id
			             where ol_w_id = $w AND s_w_id = $w
			    }
			}
			""";

	static final String FIXTURE_MSQL = """
			CREATE TABLE customers ( id BIGINT PRIMARY KEY )
			CREATE TABLE accounts ( id BIGINT NOT NULL, customer_id BIGINT NOT NULL, PRIMARY KEY (customer_id, id) )
			""";

	static final String T6 = """
			import "fixture.msql"
			workload t6 {
			    domain customer { size: 4 }
			    table customers { rows: per customer  id: customer }
			    table accounts  { rows: per customer  customer_id: customer }
			    transaction transfer weight 1 {
			        let a = customer uniform
			        let b = customer uniform distinct from $a
			        write accounts where customer_id = $a
			        write accounts where customer_id = $b
			    }
			}
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
		support.schema("dummy:/banking.msql", Files.readString(Path.of("..", "examples", "banking.msql")));
		support.schema("dummy:/tpcc.msql", TPCC_MSQL);
		support.schema("dummy:/fixture.msql", FIXTURE_MSQL);
	}

	private WorkloadModel parse(String workload) throws Exception {
		return support.parse("dummy:/test.workload", workload);
	}

	private List<String> syntaxErrors(WorkloadModel model) {
		return model.eResource().getErrors().stream().filter(d -> d instanceof XtextSyntaxDiagnostic)
				.map(d -> "line " + d.getLine() + ": " + d.getMessage()).toList();
	}

	private static Transaction transaction(WorkloadModel model, String name) {
		return model.getWorkload().getTransactions().stream().filter(t -> t.getName().equals(name)).findFirst().orElseThrow();
	}

	// --- the plan's examples: no syntax errors, every name links ---

	@Test
	public void testBankingParses() throws Exception {
		WorkloadModel model = parse(BANKING);
		support.assertNoIssues(model);
		Assertions.assertEquals(List.of("overview", "history", "transfer"),
				model.getWorkload().getTransactions().stream().map(Transaction::getName).toList());
	}

	@Test
	public void testTpccParses() throws Exception {
		WorkloadModel model = parse(TPCC);
		support.assertNoIssues(model);
		Assertions.assertEquals(9, model.getWorkload().getTables().size());
		// the join's qualified columns link to the joined tables
		Statement stockLevel = (Statement) transaction(model, "stock_level").getSteps().get(2);
		JoinOn on = stockLevel.getJoins().get(0).getOn().get(0);
		Assertions.assertEquals("order_line", ((Table) on.getLeft().getColumn().eContainer()).getName());
		Assertions.assertEquals("stock", ((Table) on.getRight().getColumn().eContainer()).getName());
	}

	@Test
	public void testT6FixtureParses() throws Exception {
		WorkloadModel model = parse(T6);
		support.assertNoIssues(model);
		Let b = (Let) transaction(model, "transfer").getSteps().get(1);
		Assertions.assertEquals("a", b.getDraw().getDistinctFrom().getVariable().getName());
	}

	// --- numbers ---

	@Test
	public void testCountsAndDecimals() throws Exception {
		WorkloadModel model = parse(BANKING);
		Domain account = model.getWorkload().getDomains().get(1);
		Assertions.assertEquals(1000000L, account.getSize());
		Assertions.assertEquals("s", ((Zipf) account.getChildren()).getS().getParam().getName());
		OverRows rows = (OverRows) model.getWorkload().getTables().get(3).getRows();
		Assertions.assertEquals(2000000L, rows.getCount());
		Assertions.assertEquals(new BigDecimal("1.0"), model.getWorkload().getParams().get(0).getValue());
	}

	@Test
	public void testZipfLiteralIsExactDecimal() throws Exception {
		WorkloadModel model = parse("""
				import "fixture.msql"
				workload w {
				    domain customer { size: 3000000000 }
				    domain child { size: 8 within customer zipf(1.2) }
				    table customers { rows: 2500000000 over customer zipf(0.05)  id: customer }
				}
				""");
		support.assertNoIssues(model);
		Assertions.assertEquals(3000000000L, model.getWorkload().getDomains().get(0).getSize());
		Zipf zipf = (Zipf) model.getWorkload().getDomains().get(1).getChildren();
		Assertions.assertEquals(new BigDecimal("1.2"), zipf.getS().getValue());
		Assertions.assertNull(zipf.getS().getParam());
		OverRows rows = (OverRows) model.getWorkload().getTables().get(0).getRows();
		Assertions.assertEquals(2500000000L, rows.getCount());
		Assertions.assertEquals(new BigDecimal("0.05"), ((Zipf) rows.getDistribution()).getS().getValue());
	}

	// --- keywords in the places the plan relies on ---

	@Test
	public void testReadReadWhereRead() throws Exception {
		support.schema("dummy:/read.msql", "CREATE TABLE read ( read BIGINT PRIMARY KEY )");
		WorkloadModel model = support.parse("dummy:/read.workload", """
				import "read.msql"
				workload read {
				    domain read { size: 4 }
				    table read { rows: per read  read: read }
				    transaction read weight 1 {
				        let read = read uniform
				        read read where read = $read
				    }
				}
				""");
		support.assertNoIssues(model);
		Statement statement = (Statement) transaction(model, "read").getSteps().get(1);
		Assertions.assertEquals("read", statement.getFirst().getValue().getVariable().getName());
	}

	@Test
	public void testUniqueColonVersusUniquePrefix() throws Exception {
		support.schema("dummy:/u.msql", "CREATE TABLE t ( unique BIGINT NOT NULL, id BIGINT PRIMARY KEY )");
		WorkloadModel model = support.parse("dummy:/u.workload", """
				import "u.msql"
				workload u {
				    domain d { size: 4 }
				    table t { rows: per d  unique: d  unique id }
				}
				""");
		support.assertNoIssues(model);
		TableData t = model.getWorkload().getTables().get(0);
		Assertions.assertInstanceOf(MappedColumn.class, t.getColumns().get(0));
		Assertions.assertEquals("unique", t.getColumns().get(0).getColumn().getName());
		Assertions.assertInstanceOf(UniqueColumn.class, t.getColumns().get(1));
		Assertions.assertEquals("id", t.getColumns().get(1).getColumn().getName());
	}

	@Test
	public void testLetFromAndDollarFrom() throws Exception {
		WorkloadModel model = parse("""
				import "fixture.msql"
				workload w {
				    domain customer { size: 4 }
				    table accounts { rows: per customer  customer_id: customer }
				    transaction t weight 1 {
				        let from = customer uniform
				        let to = customer uniform distinct from $from
				        write accounts where customer_id = $from
				    }
				}
				""");
		support.assertNoIssues(model);
		Let to = (Let) transaction(model, "t").getSteps().get(1);
		Assertions.assertEquals("from", to.getDraw().getDistinctFrom().getVariable().getName());
	}

	// --- what is meant not to parse, or not to mean what it seems ---

	@Test
	public void testColonUniqueIsADomainReference() throws Exception {
		// the plan's old `id: unique` parses, but as a mapping to a domain called unique
		WorkloadModel model = parse("""
				import "fixture.msql"
				workload w {
				    domain customer { size: 4 }
				    table customers { rows: per customer  id: unique }
				}
				""");
		Assertions.assertEquals(List.of(), syntaxErrors(model));
		validationTestHelper.assertError(model, WorkloadPackage.Literals.KEY_PATH, "SHML_042", support.at("unique"), 6,
				"Unknown domain 'unique'.");
	}

	@Test
	public void testParamAsSizeDoesNotParse() throws Exception {
		WorkloadModel model = parse("""
				workload w {
				    param n = 4
				    domain customer { size: $n }
				}
				""");
		Assertions.assertFalse(syntaxErrors(model).isEmpty());
	}

	@Test
	public void testNegativeAndExponentNumbersDoNotParse() throws Exception {
		Assertions.assertFalse(syntaxErrors(support.parse("dummy:/neg.workload", "workload w { param s = -1 }")).isEmpty());
		Assertions.assertFalse(syntaxErrors(support.parse("dummy:/exp.workload", "workload w { param s = 1e6 }")).isEmpty());
	}

	@Test
	public void testSpacedDecimalDoesNotParse() throws Exception {
		// the parser accepts `1 . 5`, but the value converter gets the text with its spaces and rejects it
		List<String> errors = syntaxErrors(parse("workload w { param s = 1 . 5 }"));
		Assertions.assertEquals(1, errors.size(), errors.toString());
		Assertions.assertTrue(errors.get(0).contains("is neither a decimal digit number"), errors.get(0));
	}

	@Test
	public void testRepeatCountIsALiteral() throws Exception {
		WorkloadModel model = parse("""
				workload w {
				    param k = 3
				    domain d { size: 4 }
				    transaction t weight 1 { repeat $k { let x = d uniform } }
				}
				""");
		Assertions.assertFalse(syntaxErrors(model).isEmpty());
	}
}
