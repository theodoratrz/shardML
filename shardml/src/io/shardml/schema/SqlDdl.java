package io.shardml.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.shardml.schema.SqlTypes.SqlType;
import io.shardml.sql.Column;
import io.shardml.sql.Constraint;
import io.shardml.sql.ForeignKey;
import io.shardml.sql.NamedConstraint;
import io.shardml.sql.NotNull;
import io.shardml.sql.Nullable;
import io.shardml.sql.PrimaryAnnotation;
import io.shardml.sql.PrimaryKey;
import io.shardml.sql.RawKind;
import io.shardml.sql.RawSql;
import io.shardml.sql.RefAction;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.sql.TableCheck;
import io.shardml.sql.TableConstraint;
import io.shardml.sql.UniqueAnnotation;
import io.shardml.sql.UniqueKey;

/* Writes a .msql schema back as PostgreSQL DDL: the generator's <db>-schema.sql and
 * <db>-foreign-keys.sql. It is the one writer of constraints, as SchemaFacts is the one reader
 * (ColumnTypeAccessGuardTest): writing back needs the syntax SchemaFacts abstracts away (which
 * names were quoted, the verbatim text, the order of declaration).
 *
 * What is written as the source wrote it:
 *  - names, quoted where the source quoted them or where PostgreSQL needs it (SqlNames), constraint
 *    names included;
 *  - DEFAULT, CHECK and GENERATED expressions and WITH (..) table options, verbatim (RawSql: the
 *    tokens as written, comments dropped, whitespace collapsed), and DEFERRABLE clauses;
 *  - the optional parts of a foreign key: no referenced column list stays omitted, and an action
 *    or MATCH appears only if the source wrote it.
 * Keywords are upper case. Foreign keys are not written in CREATE TABLE but as ALTER TABLE in
 * their own file, applied after the distribution script: Citus rejects a foreign key to a table
 * that is not distributed yet (Plan-sql section 2, case 8).
 *
 * Verbatim text is not checked, and some of it can fail on deploy although the .msql parsed:
 *  - a DEFAULT calling nextval names a sequence the schema does not create: SHML_068 rejects it;
 *  - other names of objects the schema does not create (a function, a type, a collation) cannot be
 *    told from built-ins without a catalog: the deploy finds them;
 *  - the text names columns, so it stays right only because nothing renames, drops or re-qualifies a
 *    column or table between parsing and writing. Nothing here does, and nothing may. */
public final class SqlDdl {

	private SqlDdl() {
	}

	// CREATE TABLE statements for every table, in schema order, without their foreign keys
	public static String createTables(Schema schema) {
		StringBuilder out = new StringBuilder();
		if (schema == null)
			return "";
		for (Table table : schema.getTables()) {
			if (out.length() > 0)
				out.append("\n");
			// table constraints go after the columns, as in most hand-written DDL
			List<String> lines = new ArrayList<>();
			for (Column column : table.getColumns()) {
				StringBuilder line = new StringBuilder(SqlNames.column(column)).append(" ").append(columnType(table, column));
				for (Constraint constraint : column.getConstraint()) {
					if (!(constraint instanceof ForeignKey))
						line.append(" ").append(named(constraint, columnConstraint(constraint)));
				}
				lines.add(line.toString());
			}
			for (TableConstraint constraint : table.getConstraints()) {
				if (!(constraint instanceof ForeignKey))
					lines.add(named(constraint, tableConstraint(constraint)));
			}
			// names as the schema wrote them: a quoted name stays quoted, a schema qualifier stays
			out.append("CREATE TABLE ").append(table.isIfNotExists() ? "IF NOT EXISTS " : "")
					.append(SqlNames.qualified(table)).append(" (\n    ");
			out.append(String.join(",\n    ", lines));
			out.append("\n)");
			if (table.getOptions() != null)
				out.append(" ").append(raw(table.getOptions()));
			out.append(";\n");
		}
		return out.toString();
	}

	// one ALTER TABLE .. ADD .. FOREIGN KEY per foreign key, table by table in schema order and in
	// source order within a table; empty if the schema has none
	public static String foreignKeys(Schema schema) {
		StringBuilder out = new StringBuilder();
		if (schema == null)
			return "";
		for (Table table : schema.getTables()) {
			for (Column column : table.getColumns()) {
				for (Constraint constraint : column.getConstraint()) {
					if (constraint instanceof ForeignKey key)
						appendForeignKey(out, table, SqlNames.column(column), key);
				}
			}
			for (TableConstraint constraint : table.getConstraints()) {
				if (constraint instanceof ForeignKey key)
					appendForeignKey(out, table, columns(key.getColumns()), key);
			}
		}
		return out.toString();
	}

	private static void appendForeignKey(StringBuilder out, Table table, String columns, ForeignKey key) {
		out.append("ALTER TABLE ").append(SqlNames.qualified(table)).append(" ADD ")
				.append(named(key, "FOREIGN KEY (" + columns + ") REFERENCES " + references(key))).append(";\n");
	}

	// the referenced table as declared (its schema qualifier from its CREATE TABLE), then the parts
	// of the key the source wrote
	private static String references(ForeignKey key) {
		StringBuilder out = new StringBuilder(SqlNames.qualified(key.getRefTable()));
		if (!key.getRefColumns().isEmpty())
			out.append(" (").append(columns(key.getRefColumns())).append(")");
		if (key.isSetMatch())
			out.append(" MATCH ").append(key.getMatch().getName());
		if (key.isSetOnDelete())
			out.append(" ON DELETE ").append(action(key.getOnDelete()));
		if (key.isSetOnUpdate())
			out.append(" ON UPDATE ").append(action(key.getOnUpdate()));
		if (key.getDeferrable() != null)
			out.append(" ").append(key.getDeferrable());
		return out.toString();
	}

	private static String action(RefAction action) {
		return switch (action) {
			case NO_ACTION -> "NO ACTION";
			case RESTRICT -> "RESTRICT";
			case CASCADE -> "CASCADE";
			case SET_NULL -> "SET NULL";
			case SET_DEFAULT -> "SET DEFAULT";
		};
	}

	// through SqlTypes: a type that did not parse must stop generation, not come out as the enum's
	// default INT. Validation reports the syntax error first, so this only guards direct callers
	private static String columnType(Table table, Column column) {
		SqlType type = SqlTypes.of(column);
		if (!type.isKnown())
			throw new IllegalStateException("The type of " + SqlNames.stored(table) + "." + SqlNames.stored(column)
					+ " did not parse; fix the .msql before generating.");
		return type.spelling();
	}

	private static String named(NamedConstraint constraint, String text) {
		String name = SqlNames.constraint(constraint);
		return name == null ? text : "CONSTRAINT " + name + " " + text;
	}

	private static String columnConstraint(Constraint constraint) {
		if (constraint instanceof PrimaryAnnotation)
			return "PRIMARY KEY";
		if (constraint instanceof UniqueAnnotation)
			return "UNIQUE";
		if (constraint instanceof NotNull)
			return "NOT NULL";
		if (constraint instanceof Nullable)
			return "NULL";
		if (constraint instanceof RawSql raw)
			return raw(raw);
		throw new IllegalArgumentException("Unknown constraint: " + constraint.eClass().getName());
	}

	private static String tableConstraint(TableConstraint constraint) {
		if (constraint instanceof PrimaryKey)
			return "PRIMARY KEY (" + columns(constraint.getColumns()) + ")";
		if (constraint instanceof UniqueKey)
			return "UNIQUE (" + columns(constraint.getColumns()) + ")";
		if (constraint instanceof TableCheck check)
			return raw(check.getCheck());
		throw new IllegalArgumentException("Unknown table constraint: " + constraint.eClass().getName());
	}

	// the keyword, then the text as written
	private static String raw(RawSql raw) {
		return keyword(raw.getKind()) + " " + raw.getText();
	}

	private static String keyword(RawKind kind) {
		return switch (kind) {
			case DEFAULT -> "DEFAULT";
			case CHECK -> "CHECK";
			case GENERATED -> "GENERATED";
			case WITH -> "WITH";
		};
	}

	private static String columns(List<Column> columns) {
		return columns.stream().map(SqlNames::column).collect(Collectors.joining(", "));
	}
}
