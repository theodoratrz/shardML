package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
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
import io.shardml.schema.SqlTypes.SqlType;
import io.shardml.sql.AllTypes;
import io.shardml.sql.Column;
import io.shardml.sql.Schema;
import io.shardml.sql.SqlFactory;
import io.shardml.sql.Table;

// The PostgreSQL types of Plan-sql step 3 (section 4.4): each one parses, is read by SqlTypes as
// its PostgreSQL type, is generated as spelled, and round-trips (the generated text parses to the
// same type, and generating it again gives the same text). Also: what must stay a syntax error,
// type words as column names, the validators that read types, and the model-level fix for the
// default-INT hazard (Column.simpleType is unsettable).
@ExtendWith(InjectionExtension.class)
@InjectWith(ShardMLInjectorProvider.class)
public class ColumnTypesTest {

	@Inject
	private Provider<XtextResourceSet> resourceSets;
	@Inject
	private ShardMLGenerator generator;
	@Inject
	private IResourceValidator validator;

	private int resources;

	private Resource load(String text, String extension) throws Exception {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = resourceSets.get();
		Resource r = rs.createResource(URI.createURI("dummy:/r" + (resources++) + "." + extension));
		r.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), rs.getLoadOptions());
		return r;
	}

	private Schema parse(String msql) throws Exception {
		Resource r = load(msql, "msql");
		Assertions.assertEquals(List.of(), r.getErrors(), msql);
		return (Schema) r.getContents().get(0);
	}

	private static Column only(Schema schema) {
		return schema.getTables().get(0).getColumns().get(0);
	}

	// source spelling of the type, then what SqlTypes must read
	private record Case(String source, Base base, Integer size, Integer scale, String spelling) {
	}

	private static Case c(String source, Base base, String spelling) {
		return new Case(source, base, null, null, spelling);
	}

	private static Case c(String source, Base base, Integer size, Integer scale, String spelling) {
		return new Case(source, base, size, scale, spelling);
	}

	private static final List<Case> NEW_TYPES = List.of(
			c("INTEGER", Base.INT4, "INTEGER"),
			c("integer", Base.INT4, "INTEGER"),
			c("SMALLINT", Base.INT2, "SMALLINT"),
			c("SERIAL", Base.INT4, "SERIAL"),
			c("BIGSERIAL", Base.INT8, "BIGSERIAL"),
			c("bigserial", Base.INT8, "BIGSERIAL"),
			c("NUMERIC", Base.NUMERIC, "NUMERIC"),
			c("NUMERIC(12)", Base.NUMERIC, 12, null, "NUMERIC(12)"),
			c("NUMERIC(12, 2)", Base.NUMERIC, 12, 2, "NUMERIC(12,2)"),
			c("NUMERIC(12,0)", Base.NUMERIC, 12, 0, "NUMERIC(12,0)"),
			c("numeric(12,2)", Base.NUMERIC, 12, 2, "NUMERIC(12,2)"),
			c("DECIMAL(12,2)", Base.NUMERIC, 12, 2, "DECIMAL(12,2)"),
			c("decimal(12, 2)", Base.NUMERIC, 12, 2, "DECIMAL(12,2)"),
			c("REAL", Base.FLOAT4, "REAL"),
			c("FLOAT", Base.FLOAT8, "FLOAT"),
			c("DOUBLE PRECISION", Base.FLOAT8, "DOUBLE PRECISION"),
			c("double\n\tprecision", Base.FLOAT8, "DOUBLE PRECISION"),
			c("CHAR(2)", Base.BPCHAR, 2, null, "CHAR(2)"),
			c("CHARACTER(2)", Base.BPCHAR, 2, null, "CHARACTER(2)"),
			c("CHARACTER VARYING(20)", Base.VARCHAR, 20, null, "CHARACTER VARYING(20)"),
			c("character varying (20)", Base.VARCHAR, 20, null, "CHARACTER VARYING(20)"),
			c("TIMESTAMPTZ", Base.TIMESTAMPTZ, "TIMESTAMPTZ"),
			// the multi-word forms are written as their single-word equivalents: the same type
			c("TIMESTAMP WITH TIME ZONE", Base.TIMESTAMPTZ, "TIMESTAMPTZ"),
			c("timestamp with time zone", Base.TIMESTAMPTZ, "TIMESTAMPTZ"),
			c("TIMESTAMP WITHOUT TIME ZONE", Base.TIMESTAMP, "TIMESTAMP"),
			c("JSONB", Base.JSONB, "JSONB"),
			c("BYTEA", Base.BYTEA, "BYTEA"));

	// the types before step 3, unchanged
	private static final List<Case> OLD_TYPES = List.of(
			c("INT", Base.INT4, "INT"),
			c("BIGINT", Base.INT8, "BIGINT"),
			c("UUID", Base.UUID, "UUID"),
			c("DECIMAL", Base.NUMERIC, "DECIMAL"),
			c("TIMESTAMP", Base.TIMESTAMP, "TIMESTAMP"),
			c("DATE", Base.DATE, "DATE"),
			c("BOOLEAN", Base.BOOL, "BOOLEAN"),
			c("TEXT", Base.TEXT, "TEXT"),
			c("VARCHAR(5)", Base.VARCHAR, 5, null, "VARCHAR(5)"));

	private void checkParseGenerateRoundTrip(Case c) throws Exception {
		String source = "CREATE TABLE t (\n    c " + c.source() + " NOT NULL\n)\n";
		Schema schema = parse(source);
		SqlType type = SqlTypes.of(only(schema));
		Assertions.assertEquals(new SqlType(c.base(), c.size(), c.scale(), c.spelling()), type, c.source());
		Assertions.assertEquals(type, SchemaFacts.of(schema).get("t").column("c").type(), c.source());

		String emitted = generator.schemaDefinitions(schema).toString();
		Assertions.assertEquals("CREATE TABLE t (\n    c " + c.spelling() + " NOT NULL\n);\n", emitted, c.source());

		// round trip: the emitted text parses to the same type, and emitting it again is a fixed point
		Schema again = parse(emitted);
		Assertions.assertEquals(type, SqlTypes.of(only(again)), c.source() + " after a round trip");
		Assertions.assertEquals(emitted, generator.schemaDefinitions(again).toString(), c.source() + " fixed point");
	}

	@Test
	public void testEveryNewTypeParsesGeneratesAndRoundTrips() throws Exception {
		for (Case c : NEW_TYPES)
			checkParseGenerateRoundTrip(c);
	}

	@Test
	public void testTheOldTypesAreUnchanged() throws Exception {
		for (Case c : OLD_TYPES)
			checkParseGenerateRoundTrip(c);
	}

	@Test
	public void testEveryEnumLiteralIsCoveredAndIntStaysFirst() {
		// a literal added to the grammar needs a case above; INT keeps ordinal 0, the old eight their place
		Assertions.assertEquals(0, AllTypes.INT.getValue());
		Assertions.assertEquals(List.of("INT", "BIGINT", "UUID", "DECIMAL", "TIMESTAMP", "DATE", "BOOLEAN", "TEXT"),
				AllTypes.VALUES.subList(0, 8).stream().map(AllTypes::getName).toList());
		Assertions.assertEquals(19, AllTypes.VALUES.size());
		Column column = SqlFactory.eINSTANCE.createColumn();
		for (AllTypes literal : AllTypes.VALUES) {
			column.setSimpleType(literal);
			Assertions.assertTrue(SqlTypes.of(column).isKnown(), literal.getName());
		}
	}

	@Test
	public void testWhatStaysASyntaxError() throws Exception {
		// bare sized types, wrong arity, half a multi-word type: errors, and never an INT column
		List<String> bad = List.of("CHAR", "CHARACTER", "VARCHAR", "CHARACTER VARYING", "CHAR(2, 1)", "NUMERIC(1, 2, 3)",
				"NUMERIC()", "DOUBLE", "PRECISION", "TIMESTAMP WITH ZONE", "TIMESTAMP WITHOUT TIME", "TIME ZONE",
				"INTERVAL", "\"INTEGER\"");
		int recovered = 0;
		for (String type : bad) {
			Resource r = load("CREATE TABLE t ( c " + type + " NOT NULL, id INT )", "msql");
			Assertions.assertFalse(r.getErrors().isEmpty(), type + ": should be a syntax error");
			Schema schema = r.getContents().isEmpty() ? null : (Schema) r.getContents().get(0);
			Column column = schema == null || schema.getTables().isEmpty() ? null
					: schema.getTables().get(0).getColumns().stream().filter(x -> "c".equals(x.getName())).findFirst().orElse(null);
			if (column == null)
				continue;
			recovered++;
			Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(column).base(), type);
			Assertions.assertEquals(Base.UNKNOWN, SchemaFacts.of(schema).get("t").column("c").type().base(), type);
			Assertions.assertThrows(IllegalStateException.class, () -> generator.schemaDefinitions(schema), type);
		}
		Assertions.assertTrue(recovered >= 5, "only " + recovered + " columns recovered; the checks would be vacuous");
	}

	@Test
	public void testTypeWordsAreColumnNamesWherePostgresAllowsThem() throws Exception {
		// every type word of the grammar that PostgreSQL allows bare (category U or C, or not a
		// keyword), as a column name, typed by itself where it is a type. SqlKeywordTest checks the
		// list against the PostgreSQL 17 golden; this checks it parses, generates unquoted, and
		// round-trips. The same table is deployed by bench/integration/types.
		String msql = """
				CREATE TABLE t (
				    integer INTEGER,
				    smallint SMALLINT,
				    serial SERIAL,
				    bigserial BIGSERIAL,
				    numeric NUMERIC(3),
				    real REAL,
				    float FLOAT,
				    double DOUBLE PRECISION,
				    precision INT,
				    char CHAR(1),
				    character CHARACTER(1),
				    varying CHARACTER VARYING(3),
				    timestamptz TIMESTAMPTZ,
				    time TIMESTAMP WITHOUT TIME ZONE,
				    without INT,
				    zone TIMESTAMP WITH TIME ZONE,
				    jsonb JSONB,
				    bytea BYTEA
				)
				""";
		Schema schema = parse(msql);
		List<String> names = new ArrayList<>();
		for (Column column : schema.getTables().get(0).getColumns()) {
			names.add(column.getName());
			Assertions.assertTrue(SqlTypes.of(column).isKnown(), column.getName());
		}
		Assertions.assertEquals(List.of("integer", "smallint", "serial", "bigserial", "numeric", "real", "float", "double",
				"precision", "char", "character", "varying", "timestamptz", "time", "without", "zone", "jsonb", "bytea"), names);
		String emitted = generator.schemaDefinitions(schema).toString();
		Assertions.assertTrue(emitted.contains("    double DOUBLE PRECISION,\n"), emitted);
		Assertions.assertTrue(emitted.contains("    zone TIMESTAMPTZ,\n"), emitted);
		Assertions.assertFalse(emitted.contains("\""), "no name needs quotes: " + emitted);
		Assertions.assertEquals(emitted, generator.schemaDefinitions(parse(emitted)).toString());

		// WITH is reserved (R) in PostgreSQL: a name only when quoted
		Assertions.assertFalse(load("CREATE TABLE t ( with INT )", "msql").getErrors().isEmpty());
		Assertions.assertEquals("with", only(parse("CREATE TABLE t ( \"with\" INT )")).getName());
	}

	// --- the validators that read types ---

	private static final String PAIR_MSQL = """
			CREATE TABLE p (
			    id %s PRIMARY KEY
			)
			CREATE TABLE t (
			    id INT NOT NULL,
			    code %s NOT NULL,
			    PRIMARY KEY (code, id)
			)
			""";

	// t is sharded by code and colocated with p through `t belongs_to p via code`: SHML_021 reads
	// code's type, SHML_027 compares it with p.id's
	private static final String PAIR_SHARDML = """
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

	private List<String> codes(String parentType, String codeType) throws Exception {
		SQLStandaloneSetup.doSetup();
		XtextResourceSet rs = resourceSets.get();
		Resource sql = rs.createResource(URI.createURI("dummy:/s.msql"));
		sql.load(new ByteArrayInputStream(PAIR_MSQL.formatted(parentType, codeType).getBytes()), rs.getLoadOptions());
		Assertions.assertEquals(List.of(), sql.getErrors(), parentType + " / " + codeType);
		Resource model = rs.createResource(URI.createURI("dummy:/d.shardml"));
		model.load(new ByteArrayInputStream(PAIR_SHARDML.getBytes()), rs.getLoadOptions());
		List<Issue> issues = validator.validate(model, CheckMode.ALL, CancelIndicator.NullImpl);
		return issues.stream().map(Issue::getCode).filter(c -> "SHML_021".equals(c) || "SHML_027".equals(c)).sorted().toList();
	}

	@Test
	public void testValidatorsKnowTheNewTypes() throws Exception {
		// SHML_027: same PostgreSQL type at the link, whatever the spelling or length
		Assertions.assertEquals(List.of(), codes("BIGINT", "BIGSERIAL"));
		Assertions.assertEquals(List.of(), codes("BIGSERIAL", "BIGINT"));
		Assertions.assertEquals(List.of(), codes("INT", "INTEGER"));
		Assertions.assertEquals(List.of(), codes("SERIAL", "INT"));
		Assertions.assertEquals(List.of(), codes("INTEGER", "SERIAL"));
		Assertions.assertEquals(List.of("SHML_027"), codes("BIGINT", "INTEGER"));
		Assertions.assertEquals(List.of("SHML_027"), codes("BIGINT", "SERIAL"));
		// SHML_021, once per key that is not INT, BIGINT or UUID (p's and t's): SMALLINT, NUMERIC,
		// CHAR, VARCHAR, TIMESTAMPTZ, REAL, FLOAT keys
		Assertions.assertEquals(List.of("SHML_021", "SHML_027"), codes("INT", "SMALLINT"));
		Assertions.assertEquals(List.of("SHML_021", "SHML_021"), codes("NUMERIC(20,0)", "DECIMAL"));
		Assertions.assertEquals(List.of("SHML_021", "SHML_021"), codes("CHAR(3)", "CHARACTER(5)"));
		Assertions.assertEquals(List.of("SHML_021", "SHML_021", "SHML_027"), codes("CHAR(3)", "VARCHAR(3)"));
		Assertions.assertEquals(List.of("SHML_021", "SHML_021"), codes("TIMESTAMP WITH TIME ZONE", "TIMESTAMPTZ"));
		Assertions.assertEquals(List.of("SHML_021", "SHML_021", "SHML_027"), codes("TIMESTAMPTZ", "TIMESTAMP"));
		Assertions.assertEquals(List.of("SHML_021", "SHML_021"), codes("DOUBLE PRECISION", "FLOAT"));
		Assertions.assertEquals(List.of("SHML_021", "SHML_021", "SHML_027"), codes("REAL", "FLOAT"));
	}

	// --- the default-INT hazard, at the model ---

	@Test
	public void testAnUnparsedTypeIsUnsetInTheModel() throws Exception {
		// Column.simpleType is unsettable (io.shardml.build.SqlEcorePostProcessor): the parser sets it
		// only when it parsed a type, so isSetSimpleType() tells a parsed INT from no type. The getter
		// still returns the enum's default, which is why nothing but SqlTypes may read it
		Resource r = load("CREATE TABLE t ( a INT, b INTERVAL, c INT )", "msql");
		Assertions.assertFalse(r.getErrors().isEmpty());
		Table t = ((Schema) r.getContents().get(0)).getTables().get(0);
		Column a = t.getColumns().stream().filter(x -> "a".equals(x.getName())).findFirst().orElseThrow();
		Column b = t.getColumns().stream().filter(x -> "b".equals(x.getName())).findFirst().orElseThrow();
		Assertions.assertTrue(a.isSetSimpleType());
		Assertions.assertEquals(AllTypes.INT, a.getSimpleType());
		Assertions.assertFalse(b.isSetSimpleType(), "an unparsed type must be unset");
		Assertions.assertEquals(AllTypes.INT, b.getSimpleType(), "the getter's default: the trap SqlTypes avoids");
		Assertions.assertEquals(Base.INT4, SqlTypes.of(a).base());
		Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(b).base());
		Assertions.assertEquals(Base.UNKNOWN, SchemaFacts.of((Schema) r.getContents().get(0)).get("t").column("b").type().base());

		// a column built in code: unknown until a type is set, and again after it is unset. Before
		// step 3, a column without a node model was trusted and read INT
		Column built = SqlFactory.eINSTANCE.createColumn();
		built.setName("x");
		Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(built).base());
		built.setSimpleType(AllTypes.INT);
		Assertions.assertEquals(Base.INT4, SqlTypes.of(built).base());
		built.unsetSimpleType();
		Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(built).base());

		// the sized types: a length, precision or scale that was not set is not 0
		Column sized = SqlFactory.eINSTANCE.createColumn();
		sized.setVarchar(SqlFactory.eINSTANCE.createVarchar());
		Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(sized).base(), "VARCHAR without a length, not VARCHAR(0)");
		sized.getVarchar().setSize(0);
		Assertions.assertEquals("VARCHAR(0)", SqlTypes.of(sized).spelling(), "an explicit 0 is a length");
		Column numeric = SqlFactory.eINSTANCE.createColumn();
		numeric.setNumeric(SqlFactory.eINSTANCE.createNumeric());
		Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(numeric).base());
		numeric.getNumeric().setPrecision(5);
		numeric.getNumeric().setScale(0);
		Assertions.assertEquals("NUMERIC(5,0)", SqlTypes.of(numeric).spelling(), "an explicit scale 0 is kept");
	}

	@Test
	public void testAPartlyParsedSizedTypeIsUnknown() throws Exception {
		// recovery keeps what parsed: a VARCHAR whose length is missing, a CHAR(2, 1) completed as
		// CHAR(2). Neither may be read as a type
		for (String type : List.of("CHARACTER VARYING", "VARCHAR(", "CHAR(2, 1)", "NUMERIC(5,", "DECIMAL(5, 2, 1)")) {
			Resource r = load("CREATE TABLE t ( c " + type + " NOT NULL, id INT )", "msql");
			Assertions.assertFalse(r.getErrors().isEmpty(), type);
			Schema schema = (Schema) r.getContents().get(0);
			Column c = schema.getTables().get(0).getColumns().stream().filter(x -> "c".equals(x.getName())).findFirst()
					.orElse(null);
			if (c != null)
				Assertions.assertEquals(Base.UNKNOWN, SqlTypes.of(c).base(), type);
		}
	}
}
