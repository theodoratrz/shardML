package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
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
import io.shardml.schema.SchemaFacts;
import io.shardml.schema.SqlTypes;
import io.shardml.schema.SqlTypes.Base;
import io.shardml.scoping.ImportedSchema;
import io.shardml.shardML.Model;
import io.shardml.sql.AllTypes;
import io.shardml.sql.Column;
import io.shardml.sql.Schema;
import io.shardml.validation.ImportedSchemaValidation;

// A column whose type does not parse must never be read as INT. Column.getSimpleType() returns
// INT, the enum's default, when no type was parsed, and Xtext's error recovery keeps the column:
// before SqlTypes, `code CHAR(2) NOT NULL` (CHAR is not in the grammar yet) passed
// `shardml validate` and was generated as `code INT`, its NOT NULL and primary key gone. Every
// reader of column types is checked here: SqlTypes, SchemaFacts, SHML_027, SHML_021, the
// generator, and the validation of the imported schema the CLI reports. Since step 3, CHAR(2) is a
// type (testCharIsNowReadAsChar), so the unparsed type here is INTERVAL, still not in the grammar,
// and Column.simpleType is unsettable, so the model itself says no type was parsed.
@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class ColumnTypeReadersTest {

	private static final String MSQL = """
			CREATE TABLE p (
			    id BIGINT PRIMARY KEY
			)
			CREATE TABLE t (
			    id INT NOT NULL,
			    code %s NOT NULL,
			    PRIMARY KEY (code, id)
			)
			""";

	// t is sharded by code, colocated with p through `t belongs_to p via code`, so both the key
	// type warning (SHML_021) and the link type check (SHML_027) read code's type
	private static final String SHARDML = """
			import "s.msql"
			database d {
			    type: postgres
			    shard p {
			        strategy: hash
			        key: id
			        buckets: 32
			    }
			    shard t {
			        strategy: hash
			        key: code
			        buckets: 32
			        colocate_with: p
			    }
			    t belongs_to p via code
			}
			""";

	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private IResourceValidator validator;
	@Inject
	private ShardMLGenerator generator;

	private Model parse(String codeType) throws Exception {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = resourceSets.get();
		Resource sql = rs.createResource(URI.createURI("dummy:/s.msql"));
		sql.load(new ByteArrayInputStream(MSQL.formatted(codeType).getBytes()), rs.getLoadOptions());
		Resource model = rs.createResource(URI.createURI("dummy:/d.shardml"));
		model.load(new ByteArrayInputStream(SHARDML.getBytes()), rs.getLoadOptions());
		return (Model) model.getContents().get(0);
	}

	private static Column column(Model model, String table, String name) {
		Schema schema = ImportedSchema.of(model);
		return schema.getTables().stream().filter(t -> table.equals(t.getName())).findFirst().orElseThrow()
				.getColumns().stream().filter(c -> name.equals(c.getName())).findFirst().orElseThrow();
	}

	private List<Issue> modelIssues(Model model) {
		return validator.validate(model.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
	}

	@Test
	public void testUnparsedTypeIsNeverInt() throws Exception {
		Model model = parse("INTERVAL");
		Column code = column(model, "t", "code");
		// the trap this test guards against: the recovered column reads as INT through EMF's getter,
		// and since step 3 the model says the value was never set
		Assertions.assertEquals(AllTypes.INT, code.getSimpleType());
		Assertions.assertFalse(code.isSetSimpleType());

		// SqlTypes and SchemaFacts: UNKNOWN, not INT
		Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(code).base());
		Assertions.assertFalse(SqlTypes.of(code).isKnown());
		Assertions.assertEquals(Base.UNKNOWN,
				SchemaFacts.of(ImportedSchema.of(model)).get("t").column("code").type().base());

		// SHML_027 and SHML_021 make no claim about code's type: before, SHML_027 said
		// "t.code is INT, but ... p.id is BIGINT"
		List<Issue> issues = modelIssues(model);
		Assertions.assertTrue(issues.stream().noneMatch(i -> "SHML_027".equals(i.getCode()) || "SHML_021".equals(i.getCode())),
				issues.toString());
		Assertions.assertTrue(issues.stream().noneMatch(i -> i.getMessage().contains("is INT")), issues.toString());

		// the generator refuses instead of writing `code INT`
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
		IllegalStateException e = Assertions.assertThrows(IllegalStateException.class,
				() -> generator.doGenerate(model.eResource(), fsa, new GeneratorContext()));
		Assertions.assertEquals("The type of t.code did not parse; fix the .msql before generating.", e.getMessage());

		// what the CLI reports first: the schema's own syntax error, at the column's line
		List<Issue> schemaIssues = ImportedSchemaValidation.issues(model.eResource());
		Assertions.assertFalse(schemaIssues.isEmpty());
		Issue first = schemaIssues.get(0);
		Assertions.assertEquals("s.msql", first.getUriToProblem().lastSegment());
		Assertions.assertEquals(6, first.getLineNumber(), first.toString());
	}

	@Test
	public void testParsedTypesAreReadAsWritten() throws Exception {
		// the same model with a type the grammar knows: every reader sees it, and SHML_027 still
		// fires on a real mismatch
		Model model = parse("VARCHAR(2)");
		Column code = column(model, "t", "code");
		Assertions.assertEquals(Base.VARCHAR, SqlTypes.of(code).base());
		Assertions.assertEquals("VARCHAR(2)", SqlTypes.of(code).spelling());
		Assertions.assertEquals(Base.VARCHAR, SchemaFacts.of(ImportedSchema.of(model)).get("t").column("code").type().base());
		Assertions.assertFalse(SchemaFacts.of(ImportedSchema.of(model)).get("t").column("code").nullable());

		List<Issue> issues = modelIssues(model);
		Assertions.assertTrue(issues.stream().anyMatch(i -> "SHML_027".equals(i.getCode()) && i.getMessage().startsWith(
				"Foreign key t.code is VARCHAR(2), but the shard key it references, p.id, is BIGINT.")), issues.toString());
		Assertions.assertTrue(issues.stream().anyMatch(i -> "SHML_021".equals(i.getCode())), issues.toString());
		Assertions.assertTrue(ImportedSchemaValidation.issues(model.eResource()).isEmpty());

		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
		generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
		String schemaSql = fsa.getTextFiles().get("DEFAULT_OUTPUTd-schema.sql").toString();
		Assertions.assertTrue(schemaSql.contains("    code VARCHAR(2) NOT NULL,\n"), schemaSql);
	}

	@Test
	public void testTypeKeywordsAsNamesDoNotBecomeTypes() throws Exception {
		// Since step 2, type keywords may be column names (INT, TEXT, DATE, ...) and keywords match
		// in any case. A column that is only a name, or whose "type" is quoted or not yet in the
		// grammar, must stay a syntax error with an UNKNOWN type: never a defaulted INT
		SQLStandaloneSetup.doSetup();
		Map<String, String> cases = new LinkedHashMap<>();
		cases.put("CREATE TABLE t ( int )", "int");
		cases.put("CREATE TABLE t ( int NOT NULL, id INT )", "int");
		cases.put("CREATE TABLE t ( text, id INT )", "text");
		cases.put("CREATE TABLE t ( date PRIMARY KEY )", "date");
		cases.put("CREATE TABLE t ( x \"INT\" )", "x");
		cases.put("CREATE TABLE t ( x interval NOT NULL )", "x"); // was integer, a type since step 3
		cases.put("CREATE TABLE t ( x VARCHAR )", "x");
		int n = 0;
		int checked = 0;
		for (Map.Entry<String, String> c : cases.entrySet()) {
			XtextResourceSet rs = resourceSets.get();
			Resource r = rs.createResource(URI.createURI("dummy:/m" + (n++) + ".msql"));
			r.load(new ByteArrayInputStream(c.getKey().getBytes()), rs.getLoadOptions());
			Assertions.assertFalse(r.getErrors().isEmpty(), c.getKey() + ": should be a syntax error");
			Schema schema = r.getContents().isEmpty() ? null : (Schema) r.getContents().get(0);
			Column column = schema == null || schema.getTables().isEmpty() ? null
					: schema.getTables().get(0).getColumns().stream().filter(col -> c.getValue().equals(col.getName())).findFirst()
							.orElse(null);
			if (column == null)
				continue; // recovery dropped the column: nothing can read it
			checked++;
			Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(column).base(), c.getKey());
			Assertions.assertEquals(Base.UNKNOWN, SchemaFacts.of(schema).get("t").column(c.getValue()).type().base(), c.getKey());
			Assertions.assertThrows(IllegalStateException.class, () -> generator.schemaDefinitions(schema), c.getKey());
		}
		// most cases keep the column through recovery, so the checks above are not vacuous
		Assertions.assertTrue(checked >= 3, "only " + checked + " columns recovered");
	}

	@Test
	public void testCharIsNowReadAsChar() throws Exception {
		// the column that started this test class: `code CHAR(2) NOT NULL`, generated as `code INT`
		// before step 1. Since step 3 it is a CHAR(2), with its NOT NULL and its primary key
		Model model = parse("CHAR(2)");
		Column code = column(model, "t", "code");
		Assertions.assertEquals(Base.BPCHAR, SqlTypes.of(code).base());
		Assertions.assertEquals("CHAR(2)", SqlTypes.of(code).spelling());
		Assertions.assertEquals(Base.BPCHAR, SchemaFacts.of(ImportedSchema.of(model)).get("t").column("code").type().base());
		Assertions.assertTrue(ImportedSchemaValidation.issues(model.eResource()).isEmpty());

		List<Issue> issues = modelIssues(model);
		Assertions.assertTrue(issues.stream().anyMatch(i -> "SHML_027".equals(i.getCode()) && i.getMessage().startsWith(
				"Foreign key t.code is CHAR(2), but the shard key it references, p.id, is BIGINT.")), issues.toString());

		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
		generator.doGenerate(model.eResource(), fsa, new GeneratorContext());
		String schemaSql = fsa.getTextFiles().get("DEFAULT_OUTPUTd-schema.sql").toString();
		Assertions.assertTrue(schemaSql.contains("    code CHAR(2) NOT NULL,\n"), schemaSql);
		Assertions.assertTrue(schemaSql.contains("    PRIMARY KEY (code, id)\n"), schemaSql);
	}

	@Test
	public void testExplicitIntIsInt() throws Exception {
		// an explicit INT equals the enum default, so eIsSet cannot tell it from a missing type;
		// Column.simpleType is unsettable, and isSetSimpleType() can
		Model model = parse("VARCHAR(2)");
		Column id = column(model, "t", "id");
		Assertions.assertEquals(Base.INT4, SqlTypes.of(id).base());
		Assertions.assertEquals("INT", SqlTypes.of(id).spelling());
		Assertions.assertEquals(Base.INT8, SqlTypes.of(column(model, "p", "id")).base());
	}
}
