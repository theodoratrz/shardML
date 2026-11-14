package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.shardml.SQLStandaloneSetup;
import io.shardml.generator.ShardMLGenerator;
import io.shardml.schema.SqlNames;
import io.shardml.shardML.Model;
import io.shardml.sql.Schema;
import io.shardml.sql.SqlPackage;
import io.shardml.sql.Table;
import io.shardml.validation.ImportedSchemaValidation;

// Lexing and names in a .msql file, as PostgreSQL reads them (Plan-sql step 2): keywords in any
// case, -- and slash-star comments, ';' between statements, "quoted" names kept as written and
// unquoted ones folded to lower case, IF NOT EXISTS, schema-qualified tables; what the generator
// writes back; and SHML_066, two tables of one name.
@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class SqlLexicalTest {

	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private ShardMLGenerator generator;
	@Inject
	private IResourceValidator validator;
	@Inject
	private ValidationTestHelper validationTestHelper;

	private XtextResourceSet rs;
	private String text;

	private Resource parse(String msql) throws Exception {
		SQLStandaloneSetup.doSetup();
		rs = resourceSets.get();
		text = msql;
		Resource r = rs.createResource(URI.createURI("dummy:/s.msql"));
		r.load(new ByteArrayInputStream(msql.getBytes(StandardCharsets.UTF_8)), rs.getLoadOptions());
		return r;
	}

	private static Schema schema(Resource r) {
		Assertions.assertEquals(List.of(), r.getErrors());
		return (Schema) r.getContents().get(0);
	}

	private static Table table(Resource r, int i) {
		return schema(r).getTables().get(i);
	}

	private List<String> codes(Resource r) {
		return validator.validate(r, CheckMode.ALL, CancelIndicator.NullImpl).stream().map(Issue::getCode).toList();
	}

	private Model model(String shardml) throws Exception {
		Resource m = rs.createResource(URI.createURI("dummy:/d.shardml"));
		m.load(new ByteArrayInputStream(shardml.getBytes(StandardCharsets.UTF_8)), rs.getLoadOptions());
		return (Model) m.getContents().get(0);
	}

	private String generated(Model model, String file) {
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
		generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
		return fsa.getTextFiles().get("DEFAULT_OUTPUT" + file).toString();
	}

	// --- reading ---

	@Test
	public void testKeywordsInAnyCase() throws Exception {
		Resource r = parse("""
				create table a ( id bigint primary key, n varchar(10) not null unique )
				Create Table b ( id Int, PRIMARY key (id) )
				""");
		Assertions.assertEquals(2, schema(r).getTables().size());
		Assertions.assertEquals("a", table(r, 0).getName());
		Assertions.assertEquals(10, table(r, 0).getColumns().get(1).getVarchar().getSize());
	}

	@Test
	public void testCommentsAndSemicolons() throws Exception {
		Resource r = parse("""
				-- a line comment
				CREATE TABLE a ( id INT ); -- after a statement
				/* a block
				   comment */
				CREATE TABLE b ( id INT -- inside
				);
				CREATE TABLE c ( id INT )
				""");
		Assertions.assertEquals(List.of("a", "b", "c"), schema(r).getTables().stream().map(Table::getName).toList());
	}

	@Test
	public void testSlashCommentsAndCaretEscapeAreGone() throws Exception {
		// // is not an SQL comment, and ^name is not SQL: a keyword is quoted instead
		Assertions.assertFalse(parse("CREATE TABLE a ( id INT ) // no").getErrors().isEmpty());
		Assertions.assertFalse(parse("CREATE TABLE a ( ^table INT )").getErrors().isEmpty());
	}

	@Test
	public void testUnquotedNamesFoldQuotedNamesStay() throws Exception {
		// (a plain string: the quoted name "say ""hi""" would end a Java text block)
		Resource r = parse("CREATE TABLE Customers ( ID BIGINT, \"Name\" TEXT, \"say \"\"hi\"\"\" TEXT, \"bıgınt\" INT, PRIMARY KEY (Id) )\n");
		Table t = table(r, 0);
		Assertions.assertEquals("customers", t.getName());
		Assertions.assertEquals(List.of("id", "Name", "say \"hi\"", "bıgınt"),
				t.getColumns().stream().map(c -> c.getName()).toList());
		// the reference folds too, so it links to the column declared ID
		Assertions.assertSame(t.getColumns().get(0), t.getConstraints().get(0).getColumns().get(0));
		Assertions.assertFalse(SqlNames.isQuoted(t, SqlPackage.Literals.TABLE__NAME));
		Assertions.assertTrue(SqlNames.isQuoted(t.getColumns().get(1), SqlPackage.Literals.COLUMN__NAME));
	}

	@Test
	public void testQuotedLowerCaseNameKeepsItsQuotedFlag() throws Exception {
		// "orders" and orders are the same name; the flag only decides how it is written back
		Table t = table(parse("CREATE TABLE \"orders\" ( id INT )"), 0);
		Assertions.assertEquals("orders", t.getName());
		Assertions.assertTrue(SqlNames.isQuoted(t, SqlPackage.Literals.TABLE__NAME));
	}

	@Test
	public void testEmptyQuotedNameIsAnError() throws Exception {
		Resource r = parse("CREATE TABLE \"\" ( id INT )");
		Assertions.assertEquals(1, r.getErrors().size(), r.getErrors().toString());
		Assertions.assertEquals("A quoted name cannot be empty.", r.getErrors().get(0).getMessage());
	}

	@Test
	public void testIfNotExistsAndSchemaQualifiedNames() throws Exception {
		Resource r = parse("""
				CREATE TABLE IF NOT EXISTS banking.accounts ( id BIGINT )
				CREATE TABLE "Public"."Users" ( id BIGINT )
				CREATE TABLE if ( id INT )
				CREATE TABLE IF NOT EXISTS exists ( id INT )
				""");
		Table accounts = table(r, 0);
		Assertions.assertTrue(accounts.isIfNotExists());
		Assertions.assertEquals("banking", accounts.getSchemaName());
		Assertions.assertEquals("accounts", accounts.getName());
		Assertions.assertEquals("Public", table(r, 1).getSchemaName());
		Assertions.assertEquals("Users", table(r, 1).getName());
		// IF is unreserved in PostgreSQL, so it is also a table name
		Assertions.assertFalse(table(r, 2).isIfNotExists());
		Assertions.assertEquals("if", table(r, 2).getName());
		Assertions.assertEquals("exists", table(r, 3).getName());
	}

	@Test
	public void testShardMLLinksToFoldedNames() throws Exception {
		parse("CREATE TABLE Customers ( ID BIGINT PRIMARY KEY )");
		Model model = model("""
				import "s.msql"
				database d {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				""");
		validationTestHelper.assertNoErrors(model);
	}

	// --- writing back ---

	@Test
	public void testGeneratorWritesNamesBackAsWritten() throws Exception {
		parse("""
				CREATE TABLE IF NOT EXISTS app.Customers ( Id BIGINT PRIMARY KEY, "Name" TEXT, "orders" INT, "table" INT )
				CREATE TABLE "Line Items" ( id BIGINT, key INT, PRIMARY KEY (id, Key) )
				""");
		Model model = model("""
				import "s.msql"
				database d {
				    type: postgres
				    shard customers {
				        strategy: hash
				        key: id
				        buckets: 32
				    }
				}
				""");
		Assertions.assertEquals("""
				CREATE TABLE IF NOT EXISTS app.customers (
				    id BIGINT PRIMARY KEY,
				    "Name" TEXT,
				    "orders" INT,
				    "table" INT
				);

				CREATE TABLE "Line Items" (
				    id BIGINT,
				    key INT,
				    PRIMARY KEY (id, key)
				);
				""", generated(model, "d-schema.sql"));
		// the distribution names the table as a regclass, schema included
		Assertions.assertEquals("SELECT create_distributed_table('app.customers', 'id'  ,shard_count => 32 ,colocate_with => 'none' );\n",
				generated(model, "d-distribution.sql"));
	}

	@Test
	public void testWrittenBackSchemaReadsBackTheSame() throws Exception {
		// the generated schema, parsed again, gives the same names and is written back identically
		parse("""
				CREATE TABLE IF NOT EXISTS app.Customers ( Id BIGINT PRIMARY KEY, "Name" TEXT, "table" INT )
				CREATE TABLE "Line Items" ( id BIGINT, key INT, PRIMARY KEY (id, Key) )
				""");
		String first = generator.schemaDefinitions(schema(rs.getResources().get(0))).toString();
		Resource again = parse(first);
		Assertions.assertEquals(List.of("customers", "Line Items"), schema(again).getTables().stream().map(Table::getName).toList());
		Assertions.assertEquals(List.of("id", "Name", "table"),
				table(again, 0).getColumns().stream().map(c -> c.getName()).toList());
		Assertions.assertEquals(first, generator.schemaDefinitions(schema(again)).toString());
	}

	// --- SHML_066: one name, two tables ---

	@Test
	public void testDuplicateTableNames() throws Exception {
		Resource r = parse("""
				CREATE TABLE accounts ( id INT )
				CREATE TABLE Accounts ( id INT )
				CREATE TABLE a.users ( id INT )
				CREATE TABLE b.users ( id INT )
				""");
		List<Issue> issues = validator.validate(r, CheckMode.ALL, CancelIndicator.NullImpl);
		Assertions.assertEquals(List.of("SHML_066", "SHML_066"), issues.stream().map(Issue::getCode).toList());
		// at the second name, exactly
		Assertions.assertEquals(text.indexOf("Accounts"), issues.get(0).getOffset());
		Assertions.assertEquals(8, issues.get(0).getLength());
		Assertions.assertEquals("Duplicate table accounts: it is already declared.", issues.get(0).getMessage());
		Assertions.assertEquals(text.indexOf("users", text.indexOf("b.users")), issues.get(1).getOffset());
		Assertions.assertEquals(5, issues.get(1).getLength());
		Assertions.assertEquals("Duplicate table b.users: a.users has the same name in another schema. ShardML refers to "
				+ "tables by their name without the schema, so names must be unique in a file.", issues.get(1).getMessage());
	}

	@Test
	public void testDistinctTableNames() throws Exception {
		// "Users" and users are different names in PostgreSQL
		Resource r = parse("""
				CREATE TABLE users ( id INT )
				CREATE TABLE "Users" ( id INT )
				CREATE TABLE a.orders ( id INT )
				""");
		Assertions.assertEquals(List.of(), codes(r));
	}

	@Test
	public void testCliReportsSchemaIssues() throws Exception {
		// SHML_066 reaches the CLI through the imported schema's validation
		parse("CREATE TABLE t ( id INT )\nCREATE TABLE T ( id INT )\n");
		Model model = model("""
				import "s.msql"
				database d {
				    type: postgres
				    shard t {
				        strategy: replicated
				    }
				}
				""");
		List<Issue> issues = ImportedSchemaValidation.issues(model.eResource());
		Assertions.assertEquals(List.of("SHML_066"), issues.stream().map(Issue::getCode).toList());
		Assertions.assertEquals(2, issues.get(0).getLineNumber());
	}
}
