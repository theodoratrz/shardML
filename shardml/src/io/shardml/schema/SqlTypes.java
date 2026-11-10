package io.shardml.schema;

import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import io.shardml.sql.Column;
import io.shardml.sql.SqlPackage;

/* The one place that reads a column's type. Everything that needs a type (validators, the
 * generator, the cost model) asks here instead of reading Column.getSimpleType(), because that
 * getter returns INT, the enum's default, when no type was parsed: a column whose type is a
 * syntax error (CHAR(2) before the grammar knows CHAR) would otherwise be read as INT and
 * generated as `INT`. Such a column is UNKNOWN here. */
public final class SqlTypes {

	private SqlTypes() {
	}

	/* PostgreSQL's type, the one Citus compares when it colocates tables: the length is not part
	 * of it (VARCHAR(50) colocates with VARCHAR(100)). */
	public enum Base {
		INT4, INT8, UUID, NUMERIC, TIMESTAMP, DATE, BOOL, TEXT, VARCHAR,
		// the type did not parse; the .msql has a syntax error at this column
		UNKNOWN
	}

	// spelling: the type as the schema writes it ("INT", "VARCHAR(255)"), for messages and output
	public record SqlType(Base base, Integer size, String spelling) {

		public boolean isKnown() {
			return base != Base.UNKNOWN;
		}

		// same PostgreSQL type, ignoring the length
		public boolean sameBase(SqlType other) {
			return isKnown() && base == other.base;
		}
	}

	public static final SqlType UNKNOWN = new SqlType(Base.UNKNOWN, null, "?");

	public static SqlType of(Column column) {
		if (column.getVarchar() != null)
			return new SqlType(Base.VARCHAR, column.getVarchar().getSize(), "VARCHAR(" + column.getVarchar().getSize() + ")");
		if (!hasSimpleType(column))
			return UNKNOWN;
		return switch (column.getSimpleType()) {
			case INT -> simple(Base.INT4, "INT");
			case BIGINT -> simple(Base.INT8, "BIGINT");
			case UUID -> simple(Base.UUID, "UUID");
			case DECIMAL -> simple(Base.NUMERIC, "DECIMAL");
			case TIMESTAMP -> simple(Base.TIMESTAMP, "TIMESTAMP");
			case DATE -> simple(Base.DATE, "DATE");
			case BOOLEAN -> simple(Base.BOOL, "BOOLEAN");
			case TEXT -> simple(Base.TEXT, "TEXT");
		};
	}

	private static SqlType simple(Base base, String spelling) {
		return new SqlType(base, null, spelling);
	}

	// A parsed column has a node for its type. eIsSet cannot tell: an explicit INT equals the
	// default and reads as unset. A column built in code, without a node model, is trusted.
	private static boolean hasSimpleType(Column column) {
		INode node = NodeModelUtils.getNode(column);
		if (node == null)
			return true;
		return !NodeModelUtils.findNodesForFeature(column, SqlPackage.Literals.COLUMN__SIMPLE_TYPE).isEmpty();
	}
}
