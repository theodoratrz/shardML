package io.shardml.validation;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;

import io.shardml.schema.SchemaFacts;
import io.shardml.schema.SchemaFacts.ForeignKeyFacts;
import io.shardml.schema.SchemaFacts.TableFacts;
import io.shardml.schema.SqlNames;
import io.shardml.schema.SqlStatements;
import io.shardml.schema.SqlStatements.Kind;
import io.shardml.schema.SqlStatements.Sorted;
import io.shardml.sql.AlterTable;
import io.shardml.sql.ForeignKey;
import io.shardml.sql.RawSql;
import io.shardml.sql.Schema;
import io.shardml.sql.SqlPackage;
import io.shardml.sql.Statement;
import io.shardml.sql.Table;

/* Checks on a .msql schema beyond its syntax (SHML_061 onward, Plan-sql section 7). The CLI
 * reports them with the schema's own file and line (ImportedSchemaValidation). Unknown tables and
 * columns (SHML_062, SHML_063) are linking errors: see SQLLinkingDiagnosticMessageProvider.
 * Constraints are read through SchemaFacts. */
public class SQLValidator extends AbstractSQLValidator {

	public static final String UNSUPPORTED_STATEMENT = "SHML_061";
	public static final String FK_COLUMN_COUNT = "SHML_064";
	public static final String FK_NOT_A_KEY = "SHML_065";
	public static final String DUPLICATE_TABLE = "SHML_066";
	public static final String MULTIPLE_PRIMARY_KEYS = "SHML_067";
	public static final String SEQUENCE_DEFAULT = "SHML_068";
	// 069-074 are ShardML codes (the schema's keys against the layout)
	public static final String PREAMBLE_LEFT_OUT = "SHML_075";
	public static final String SEARCH_PATH_LEFT_OUT = "SHML_076";

	// ShardML and workloads refer to a table by its unqualified name, so two tables of one name
	// would link to whichever comes first: also across schemas (a.t and b.t), for now
	@Check
	public void checkDuplicateTable(Schema schema) {
		Map<String, Table> seen = new HashMap<>();
		for (Table table : schema.getTables()) {
			if (table.getName() == null)
				continue;
			Table first = seen.putIfAbsent(table.getName(), table);
			if (first == null)
				continue;
			boolean otherSchema = !java.util.Objects.equals(first.getSchemaName(), table.getSchemaName());
			error("Duplicate table " + SqlNames.qualified(table) + ": " + (otherSchema
					? SqlNames.qualified(first) + " has the same name in another schema. ShardML refers to tables by their name "
							+ "without the schema, so names must be unique in a file."
					: "it is already declared."), table, SqlPackage.Literals.TABLE__NAME, DUPLICATE_TABLE);
		}
	}

	// a foreign key pairs its columns with the referenced ones one by one, and the referenced ones
	// must be the referenced table's primary key or one of its unique keys (PostgreSQL rejects the
	// DDL otherwise: "there is no unique constraint matching given keys", Plan-sql section 2, case 16)
	@Check
	public void checkForeignKey(ForeignKey key) {
		ForeignKeyFacts fk = SchemaFacts.foreignKey(key);
		if (fk == null)
			return; // unresolved: a linking error says where
		String refs = fk.refTable() + " (" + String.join(", ", fk.refColumns()) + ")";
		if (!fk.refColumnsDeclared() && fk.refColumns().isEmpty()) {
			error("Foreign key (" + String.join(", ", fk.columns()) + ") references " + fk.refTable()
					+ " without a column list, which means its primary key, but " + fk.refTable()
					+ " has no primary key. Name the referenced columns.", key, SqlPackage.Literals.FOREIGN_KEY__REF_TABLE,
					FK_NOT_A_KEY);
			return;
		}
		if (fk.columns().size() != fk.refColumns().size()) {
			error("Foreign key (" + String.join(", ", fk.columns()) + ") has " + fk.columns().size() + " column"
					+ (fk.columns().size() == 1 ? "" : "s") + ", but references " + fk.refColumns().size() + ": " + refs
					+ (fk.refColumnsDeclared() ? "" : ", the primary key of " + fk.refTable()) + ".", key,
					fk.refColumnsDeclared() ? SqlPackage.Literals.FOREIGN_KEY__REF_COLUMNS
							: SqlPackage.Literals.FOREIGN_KEY__REF_TABLE,
					FK_COLUMN_COUNT);
			return;
		}
		Schema schema = EcoreUtil2.getContainerOfType(key, Schema.class);
		TableFacts refTable = SchemaFacts.of(schema).get(fk.refTable());
		if (refTable != null && !refTable.isKey(fk.refColumns())) {
			error("Foreign key (" + String.join(", ", fk.columns()) + ") references " + refs
					+ ", which is neither the primary key of " + fk.refTable() + " nor one of its UNIQUE constraints. "
					+ "PostgreSQL rejects it: add a UNIQUE constraint on those columns, or reference a key.", key,
					SqlPackage.Literals.FOREIGN_KEY__REF_COLUMNS, FK_NOT_A_KEY);
		}
	}

	// PostgreSQL allows one primary key per table, however it is declared
	@Check
	public void checkSinglePrimaryKey(Table table) {
		List<EObject> keys = SchemaFacts.primaryKeyDeclarations(table);
		for (int i = 1; i < keys.size(); i++) {
			error("Table " + SqlNames.qualified(table) + " declares more than one PRIMARY KEY; PostgreSQL allows one. "
					+ "For a key over several columns, use one table-level PRIMARY KEY (a, b).", keys.get(i), null,
					MULTIPLE_PRIMARY_KEYS);
		}
	}

	// sequences are not modelled, so a DEFAULT that calls nextval names a sequence the generated
	// schema does not create, and the deploy fails
	private static final Pattern NEXTVAL = Pattern.compile("(?i)\\bnextval\\s*\\(");

	@Check
	public void checkSequenceDefault(RawSql raw) {
		String text = SchemaFacts.defaultText(raw);
		if (text != null && NEXTVAL.matcher(text).find()) {
			error("DEFAULT " + text + " uses a sequence, and sequences are not modelled: the generated schema would "
					+ "not create it. Use SERIAL / BIGSERIAL, or GENERATED ... AS IDENTITY.", raw,
					SqlPackage.Literals.RAW_SQL__TEXT, SEQUENCE_DEFAULT);
		}
	}

	// A statement the grammar does not model is either pg_dump preamble (counted below) or an error
	// here, at its first word, saying what to do with it (SqlStatements). Never skipped silently.
	// A statement that starts at a syntax error is the rest of a broken one, which the parser resumed
	// after (`rating public.mpaa_rating ..` in a CREATE TABLE): the syntax error says what broke there
	@Check
	public void checkStatement(Statement statement) {
		Sorted sorted = SqlStatements.of(statement);
		if (sorted.kind() == Kind.SEARCH_PATH) {
			warning(sorted.what() + " to " + sorted.advice() + " is not written back, so the unqualified names in the generated "
					+ "schema resolve against the connection's default schema instead (usually public). Qualify the "
					+ "tables (CREATE TABLE s.t), or apply the generated files with that search_path.", statement,
					SqlPackage.Literals.STATEMENT__VERB, SEARCH_PATH_LEFT_OUT);
			return;
		}
		if (!sorted.kind().isPreamble() && !startsAtSyntaxError(statement))
			error(unsupported(sorted), statement,
					statement.getMeta() != null ? SqlPackage.Literals.STATEMENT__META : SqlPackage.Literals.STATEMENT__VERB,
					UNSUPPORTED_STATEMENT);
	}

	@Check
	public void checkAlterTableAction(AlterTable alter) {
		if (alter.getBody() == null)
			return; // ADD constraint: modelled
		Sorted sorted = SqlStatements.of(alter);
		if (!sorted.kind().isPreamble())
			error(unsupported(sorted), alter, SqlPackage.Literals.ALTER_TABLE__BODY, UNSUPPORTED_STATEMENT);
	}

	private static boolean startsAtSyntaxError(Statement statement) {
		ICompositeNode node = NodeModelUtils.getNode(statement);
		if (node == null || statement.eResource() == null)
			return false;
		int start = node.getTotalOffset();
		int end = node.getTotalEndOffset();
		for (Resource.Diagnostic error : statement.eResource().getErrors()) {
			if (error instanceof XtextSyntaxDiagnostic syntax && syntax.getOffset() >= start && syntax.getOffset() < end)
				return true;
		}
		return false;
	}

	private static String unsupported(Sorted sorted) {
		return (sorted.kind() == Kind.SEQUENCE ? "Sequence statement " : "Unsupported statement ") + sorted.what() + ". "
				+ sorted.advice();
	}

	// one INFO issue per file: the pg_dump preamble it read and the generator leaves out, by kind
	@Check
	public void checkPreamble(Schema schema) {
		Map<Kind, Integer> counts = new EnumMap<>(Kind.class);
		for (Statement statement : schema.getStatements())
			counts.merge(SqlStatements.of(statement).kind(), 1, Integer::sum);
		for (AlterTable alter : schema.getAlters()) {
			if (alter.getBody() != null)
				counts.merge(SqlStatements.of(alter).kind(), 1, Integer::sum);
		}
		List<String> parts = counts.entrySet().stream().filter(e -> e.getKey().isPreamble())
				.map(e -> e.getValue() + " " + e.getKey().label()).toList();
		if (parts.isEmpty())
			return;
		info("Not written back: " + String.join(", ", parts) + ". Session settings, ownership, privileges, comments "
				+ "and DROP TABLE cleanup (the pg_dump preamble): the generated schema needs none of them.", schema, null,
				PREAMBLE_LEFT_OUT);
	}
}
