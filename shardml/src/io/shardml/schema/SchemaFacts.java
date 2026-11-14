package io.shardml.schema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;

import io.shardml.schema.SqlTypes.Base;
import io.shardml.schema.SqlTypes.SqlType;
import io.shardml.sql.AlterTable;
import io.shardml.sql.Column;
import io.shardml.sql.Constraint;
import io.shardml.sql.ForeignKey;
import io.shardml.sql.Index;
import io.shardml.sql.IndexPart;
import io.shardml.sql.NotNull;
import io.shardml.sql.PrimaryAnnotation;
import io.shardml.sql.PrimaryKey;
import io.shardml.sql.RawKind;
import io.shardml.sql.RawSql;
import io.shardml.sql.RefAction;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.sql.TableConstraint;
import io.shardml.sql.UniqueAnnotation;
import io.shardml.sql.UniqueKey;

/* An EMF-free view of a .msql schema: what the cost model and the validators need to know about
 * tables and columns, read in one place. Column types come from SqlTypes, so a type that did not
 * parse is UNKNOWN here, never INT.
 *
 * It is the only reader of constraints (ColumnTypeAccessGuardTest). A constraint's meaning
 * depends on its class and on where it is declared, which is easy to get wrong one reader at a
 * time: an FK read as a UNIQUE, a column-level REFERENCES without its column, a missing
 * referenced column list that means the primary key. Here, every form of a key comes out in one
 * shape:
 *  - primary key: a column-level PRIMARY KEY, a table-level one, or one added by ALTER TABLE .. ADD
 *    (where pg_dump declares every key);
 *  - unique keys: column-level UNIQUE, table-level and ALTER TABLE UNIQUE, in declaration order,
 *    duplicates kept; then the unique indexes on plain columns without a WHERE. An expression or
 *    partial unique index is no key a foreign key may reference: it is in otherUniqueIndexes;
 *  - foreign keys: column-level REFERENCES, table-level and ALTER TABLE FOREIGN KEY, with the
 *    referencing columns filled in, and the referenced columns resolved to the referenced primary
 *    key (wherever it is declared) when the source omits them.
 * A table's constraints are its own and those ALTER TABLE adds to it (tableConstraints). CHECK,
 * DEFAULT and GENERATED are not keys, and never appear as one.
 *
 * The static methods that take a model object (foreignKey, primaryKeyDeclarations, defaultText)
 * are for validators, which report on that object. */
public final class SchemaFacts {

	private SchemaFacts() {
	}

	// a foreign key action; null in ForeignKeyFacts when the source does not write one (PostgreSQL
	// then applies NO ACTION)
	public enum Action {
		NO_ACTION, RESTRICT, CASCADE, SET_NULL, SET_DEFAULT
	}

	// nullable: no NOT NULL, not part of the primary key, not an identity and not a serial column.
	// defaultText: the DEFAULT expression as written, or null. storedGenerated: GENERATED ALWAYS AS
	// (..) STORED (an identity column is not one)
	public record ColumnFacts(String name, SqlType type, boolean nullable, String defaultText, boolean storedGenerated) {
	}

	// columns: the referencing columns, in order. refColumns: the referenced ones, the referenced
	// table's primary key if the source omits them (refColumnsDeclared false); empty if it omits them
	// and that table has no primary key. name: the constraint's name, or null
	public record ForeignKeyFacts(String name, String table, List<String> columns, String refTable,
			List<String> refColumns, boolean refColumnsDeclared, Action onDelete, Action onUpdate) {

		// the actions that write the referencing columns themselves, as "ON DELETE SET NULL": ON DELETE
		// SET NULL / SET DEFAULT, ON UPDATE SET NULL / SET DEFAULT / CASCADE (an ON DELETE CASCADE
		// deletes the row and writes no column). Citus rejects exactly these when the columns include
		// the distribution column (measured, Plan-sql section 11e)
		public List<String> actionsWritingColumns() {
			List<String> actions = new ArrayList<>();
			if (onDelete == Action.SET_NULL || onDelete == Action.SET_DEFAULT)
				actions.add("ON DELETE " + spelling(onDelete));
			if (onUpdate == Action.SET_NULL || onUpdate == Action.SET_DEFAULT || onUpdate == Action.CASCADE)
				actions.add("ON UPDATE " + spelling(onUpdate));
			return List.copyOf(actions);
		}

		private static String spelling(Action action) {
			return action.name().replace('_', ' ');
		}
	}

	// a unique index that is no key (an expression part, or a WHERE): name (or null), its plain column
	// parts in order, and which of the two it has. Citus still requires the distribution column among
	// the plain parts (measured, Plan-sql 11g)
	public record UniqueIndexFacts(String name, List<String> plainColumns, boolean expression, boolean partial) {
	}

	// otherUniqueIndexes: the unique indexes that are not in uniqueKeys
	public record TableFacts(String schema, String name, List<ColumnFacts> columns, List<String> primaryKey,
			List<List<String>> uniqueKeys, List<ForeignKeyFacts> foreignKeys, List<UniqueIndexFacts> otherUniqueIndexes) {

		public ColumnFacts column(String name) {
			return columns.stream().filter(c -> c.name().equals(name)).findFirst().orElse(null);
		}

		// whether these columns, in any order, are the primary key or a unique key: what a foreign key
		// may reference (PostgreSQL: "there is no unique constraint matching given keys")
		public boolean isKey(List<String> keyColumns) {
			if (keyColumns.isEmpty())
				return false;
			if (sameSet(primaryKey, keyColumns))
				return true;
			return uniqueKeys.stream().anyMatch(u -> sameSet(u, keyColumns));
		}

		private static boolean sameSet(List<String> a, List<String> b) {
			return a.size() == b.size() && a.containsAll(b) && b.containsAll(a);
		}
	}

	// tables by name, in schema order; tables or columns without a name (a syntax error) are left out
	public static Map<String, TableFacts> of(Schema schema) {
		Map<String, TableFacts> tables = new LinkedHashMap<>();
		if (schema == null)
			return tables;
		for (Table table : schema.getTables()) {
			if (table.getName() == null)
				continue;
			List<String> primaryKey = primaryKey(table);
			List<ColumnFacts> columns = new ArrayList<>();
			List<List<String>> uniqueKeys = new ArrayList<>();
			List<ForeignKeyFacts> foreignKeys = new ArrayList<>();
			for (Column column : table.getColumns()) {
				if (column.getName() == null)
					continue;
				SqlType type = SqlTypes.of(column);
				boolean notNull = primaryKey.contains(column.getName()) || isSerial(type);
				String defaultText = null;
				boolean storedGenerated = false;
				for (Constraint constraint : column.getConstraint()) {
					if (constraint instanceof NotNull)
						notNull = true;
					else if (constraint instanceof UniqueAnnotation)
						uniqueKeys.add(List.of(column.getName()));
					else if (constraint instanceof RawSql raw && raw.getKind() == RawKind.DEFAULT)
						defaultText = raw.getText();
					else if (constraint instanceof RawSql raw && raw.getKind() == RawKind.GENERATED) {
						if (isIdentity(raw))
							notNull = true;
						else
							storedGenerated = true;
					}
				}
				columns.add(new ColumnFacts(column.getName(), type, !notNull, defaultText, storedGenerated));
			}
			// table-level keys after the column-level ones; a foreign key comes out at its position among
			// the column-level REFERENCES and the table-level and ALTER TABLE FOREIGN KEYs, in source order
			for (EObject key : keysInOrder(table)) {
				if (key instanceof ForeignKey fk) {
					ForeignKeyFacts facts = foreignKey(fk);
					if (facts != null)
						foreignKeys.add(facts);
				} else if (key instanceof UniqueKey unique) {
					List<String> names = names(unique.getColumns());
					if (names != null)
						uniqueKeys.add(names);
				}
			}
			List<UniqueIndexFacts> otherUniqueIndexes = new ArrayList<>();
			for (Index index : schema.getIndexes()) {
				if (!index.isUnique() || index.getTable() != table)
					continue;
				if (isPlain(index)) {
					List<String> names = indexColumns(index);
					if (names != null)
						uniqueKeys.add(names);
				} else {
					List<Column> plain = new ArrayList<>();
					for (IndexPart part : index.getParts()) {
						if (part.getExpression() == null)
							plain.add(part.getColumn());
					}
					List<String> names = names(plain);
					if (names != null)
						otherUniqueIndexes.add(new UniqueIndexFacts(index.getName(), names,
								index.getParts().stream().anyMatch(p -> p.getExpression() != null), isPartial(index)));
				}
			}
			tables.putIfAbsent(table.getName(), new TableFacts(table.getSchemaName(), table.getName(),
					List.copyOf(columns), primaryKey, List.copyOf(uniqueKeys), List.copyOf(foreignKeys),
					List.copyOf(otherUniqueIndexes)));
		}
		return tables;
	}

	// column-level foreign keys (in column order), then the table-level constraints
	private static List<EObject> keysInOrder(Table table) {
		List<EObject> keys = new ArrayList<>();
		for (Column column : table.getColumns()) {
			for (Constraint constraint : column.getConstraint()) {
				if (constraint instanceof ForeignKey)
					keys.add(constraint);
			}
		}
		keys.addAll(tableConstraints(table));
		return keys;
	}

	// a table's own table constraints, then those ALTER TABLE .. ADD adds to it, in source order
	public static List<TableConstraint> tableConstraints(Table table) {
		List<TableConstraint> constraints = new ArrayList<>(table.getConstraints());
		if (table.eContainer() instanceof Schema schema) {
			for (AlterTable alter : schema.getAlters()) {
				if (alter.getConstraint() != null && alter.getTable() == table)
					constraints.add(alter.getConstraint());
			}
		}
		return constraints;
	}

	// the table a constraint, index part or ALTER TABLE belongs to: the CREATE TABLE around it, or the
	// table an ALTER TABLE or index names; null if that is unresolved
	public static Table owningTable(EObject object) {
		for (EObject o = object; o != null; o = o.eContainer()) {
			if (o instanceof Table table)
				return table;
			if (o instanceof AlterTable alter)
				return alter.getTable() == null || alter.getTable().eIsProxy() ? null : alter.getTable();
			if (o instanceof Index index)
				return index.getTable() == null || index.getTable().eIsProxy() ? null : index.getTable();
		}
		return null;
	}

	// a unique index on plain columns, without a WHERE: one a foreign key may reference
	private static final Pattern WHERE = Pattern.compile("(?i)\\bWHERE\\b");

	private static boolean isPlain(Index index) {
		return index.getParts().stream().allMatch(p -> p.getExpression() == null) && !isPartial(index);
	}

	private static boolean isPartial(Index index) {
		return index.getTail() != null && WHERE.matcher(index.getTail()).find();
	}

	private static List<String> indexColumns(Index index) {
		List<Column> columns = new ArrayList<>();
		for (IndexPart part : index.getParts())
			columns.add(part.getColumn());
		return names(columns);
	}

	// a foreign key, normalised; null if its table, its referenced table or one of its columns is
	// unresolved (a linking error is reported there) or unnamed
	public static ForeignKeyFacts foreignKey(ForeignKey key) {
		Table table = owningTable(key);
		Table refTable = key.getRefTable();
		if (table == null || table.getName() == null || refTable == null || refTable.eIsProxy() || refTable.getName() == null)
			return null;
		List<String> columns;
		if (key.eContainer() instanceof Column column) {
			if (column.getName() == null)
				return null;
			columns = List.of(column.getName());
		} else {
			columns = names(key.getColumns());
		}
		List<String> refColumns;
		boolean declared = !key.getRefColumns().isEmpty();
		if (declared)
			refColumns = names(key.getRefColumns());
		else
			refColumns = primaryKey(refTable);
		if (columns == null || refColumns == null)
			return null;
		return new ForeignKeyFacts(key.getName(), table.getName(), columns, refTable.getName(), refColumns, declared,
				key.isSetOnDelete() ? action(key.getOnDelete()) : null,
				key.isSetOnUpdate() ? action(key.getOnUpdate()) : null);
	}

	private static Action action(RefAction action) {
		return switch (action) {
			case NO_ACTION -> Action.NO_ACTION;
			case RESTRICT -> Action.RESTRICT;
			case CASCADE -> Action.CASCADE;
			case SET_NULL -> Action.SET_NULL;
			case SET_DEFAULT -> Action.SET_DEFAULT;
		};
	}

	// every PRIMARY KEY a table declares, column-level, table-level and by ALTER TABLE, in source
	// order. More than one is an error (SHML_067); of() reads the first
	public static List<EObject> primaryKeyDeclarations(Table table) {
		List<EObject> keys = new ArrayList<>();
		for (Column column : table.getColumns()) {
			for (Constraint constraint : column.getConstraint()) {
				if (constraint instanceof PrimaryAnnotation)
					keys.add(constraint);
			}
		}
		for (TableConstraint constraint : tableConstraints(table)) {
			if (constraint instanceof PrimaryKey)
				keys.add(constraint);
		}
		return keys;
	}

	// the expression of a DEFAULT as written, or null if this is another kind of verbatim text
	public static String defaultText(RawSql raw) {
		return raw.getKind() == RawKind.DEFAULT && raw.eContainer() instanceof Column ? raw.getText() : null;
	}

	// a column-level PRIMARY KEY, or the columns of a table-level one; the first declared
	private static List<String> primaryKey(Table table) {
		for (EObject key : primaryKeyDeclarations(table)) {
			if (key instanceof PrimaryAnnotation && key.eContainer() instanceof Column column && column.getName() != null)
				return List.of(column.getName());
			if (key instanceof PrimaryKey pk) {
				List<String> columns = names(pk.getColumns());
				return columns == null ? List.of() : columns;
			}
		}
		return List.of();
	}

	// the names of resolved columns, or null if one is unresolved
	private static List<String> names(List<Column> columns) {
		List<String> names = new ArrayList<>();
		for (Column column : columns) {
			if (column.eIsProxy() || column.getName() == null)
				return null;
			names.add(column.getName());
		}
		return List.copyOf(names);
	}

	// GENERATED .. AS IDENTITY makes a column NOT NULL; a stored generated column may be null
	private static final Pattern IDENTITY = Pattern.compile("(?i)\\bAS\\s+IDENTITY\\b");

	private static boolean isIdentity(RawSql raw) {
		return raw.getKind() == RawKind.GENERATED && IDENTITY.matcher(raw.getText()).find();
	}

	// SERIAL and BIGSERIAL are NOT NULL (with a sequence default)
	private static boolean isSerial(SqlType type) {
		return (type.base() == Base.INT4 || type.base() == Base.INT8)
				&& ("SERIAL".equalsIgnoreCase(type.spelling()) || "BIGSERIAL".equalsIgnoreCase(type.spelling()));
	}
}
