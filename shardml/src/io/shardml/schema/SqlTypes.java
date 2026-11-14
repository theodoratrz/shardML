package io.shardml.schema;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import io.shardml.sql.Char;
import io.shardml.sql.Column;
import io.shardml.sql.Numeric;
import io.shardml.sql.Varchar;

/* The one place that reads a column's type. Everything that needs a type (validators, the
 * generator, the cost model) asks here instead of reading the Column's type features.
 *
 * A column whose type did not parse is UNKNOWN. Xtext's error recovery keeps such a column, and
 * its enum feature would read INT, the first literal: before this class, `code CHAR(2) NOT NULL`
 * (CHAR was not in the grammar yet) was generated as `code INT`. Two checks, both needed:
 *  - the type's attributes are unsettable (io.shardml.build.SqlEcorePostProcessor), so
 *    isSetSimpleType() tells a parsed INT from no type, and isSetSize() a VARCHAR whose length did
 *    not parse (it would read VARCHAR(0)). This also covers columns built in code;
 *  - a parsed column with a syntax error in it is UNKNOWN, and so is a sized type that does not
 *    end in a parsed ')': recovery completes a type from a prefix (`CHAR(2, 1)` reads CHAR(2), the
 *    parser inserting the missing ')' and reporting it at the next token, outside the column).
 *    The .msql's syntax error is what gets reported. */
public final class SqlTypes {

	private SqlTypes() {
	}

	/* PostgreSQL's type, the one Citus compares when it colocates tables, named as in pg_type.
	 * Lengths, precision and scale are not part of it (VARCHAR(50) colocates with VARCHAR(100)),
	 * and neither is the spelling: INT and INTEGER are int4, SERIAL is int4 with a sequence
	 * default, FLOAT is float8, TIMESTAMP WITHOUT TIME ZONE is timestamp. */
	public enum Base {
		INT2, INT4, INT8, NUMERIC, FLOAT4, FLOAT8, UUID, TIMESTAMP, TIMESTAMPTZ, DATE, BOOL, TEXT, VARCHAR, BPCHAR, JSONB,
		BYTEA,
		// the type did not parse; the .msql has a syntax error at this column
		UNKNOWN
	}

	/* size: the length of VARCHAR(n) / CHAR(n), or the precision of NUMERIC(p[,s]); scale: s.
	 * spelling: the type as the generator writes it back ("INTEGER", "DECIMAL(12,2)", "CHARACTER
	 * VARYING(20)"); a multi-word form is written as its single-word equivalent (TIMESTAMP WITH
	 * TIME ZONE as TIMESTAMPTZ), the same type in the catalog. */
	public record SqlType(Base base, Integer size, Integer scale, String spelling) {

		public boolean isKnown() {
			return base != Base.UNKNOWN;
		}

		// same PostgreSQL type, ignoring length, precision and scale
		public boolean sameBase(SqlType other) {
			return isKnown() && base == other.base;
		}
	}

	public static final SqlType UNKNOWN = new SqlType(Base.UNKNOWN, null, null, "?");

	public static SqlType of(Column column) {
		if (hasSyntaxError(column))
			return UNKNOWN;
		Varchar varchar = column.getVarchar();
		if (varchar != null) {
			if (!varchar.isSetSize() || !closed(varchar))
				return UNKNOWN;
			return new SqlType(Base.VARCHAR, varchar.getSize(), null,
					(varchar.isVarying() ? "CHARACTER VARYING(" : "VARCHAR(") + varchar.getSize() + ")");
		}
		Numeric numeric = column.getNumeric();
		if (numeric != null) {
			if (!numeric.isSetPrecision() || !closed(numeric))
				return UNKNOWN;
			Integer scale = numeric.isSetScale() ? numeric.getScale() : null;
			return new SqlType(Base.NUMERIC, numeric.getPrecision(), scale, (numeric.isDecimal() ? "DECIMAL(" : "NUMERIC(")
					+ numeric.getPrecision() + (scale == null ? "" : "," + scale) + ")");
		}
		Char fixed = column.getChar();
		if (fixed != null) {
			if (!fixed.isSetSize() || !closed(fixed))
				return UNKNOWN;
			return new SqlType(Base.BPCHAR, fixed.getSize(), null, (fixed.isCharacter() ? "CHARACTER(" : "CHAR(") + fixed.getSize() + ")");
		}
		if (!column.isSetSimpleType())
			return UNKNOWN;
		return switch (column.getSimpleType()) {
			case INT -> simple(Base.INT4, "INT");
			case INTEGER -> simple(Base.INT4, "INTEGER");
			case SERIAL -> simple(Base.INT4, "SERIAL");
			case SMALLINT -> simple(Base.INT2, "SMALLINT");
			case BIGINT -> simple(Base.INT8, "BIGINT");
			case BIGSERIAL -> simple(Base.INT8, "BIGSERIAL");
			case DECIMAL -> simple(Base.NUMERIC, "DECIMAL");
			case NUMERIC -> simple(Base.NUMERIC, "NUMERIC");
			case REAL -> simple(Base.FLOAT4, "REAL");
			case FLOAT -> simple(Base.FLOAT8, "FLOAT");
			case DOUBLE_PRECISION -> simple(Base.FLOAT8, "DOUBLE PRECISION");
			case UUID -> simple(Base.UUID, "UUID");
			case TIMESTAMP -> simple(Base.TIMESTAMP, "TIMESTAMP");
			case TIMESTAMPTZ -> simple(Base.TIMESTAMPTZ, "TIMESTAMPTZ");
			case DATE -> simple(Base.DATE, "DATE");
			case BOOLEAN -> simple(Base.BOOL, "BOOLEAN");
			case TEXT -> simple(Base.TEXT, "TEXT");
			case JSONB -> simple(Base.JSONB, "JSONB");
			case BYTEA -> simple(Base.BYTEA, "BYTEA");
		};
	}

	private static SqlType simple(Base base, String spelling) {
		return new SqlType(base, null, null, spelling);
	}

	// a sized type whose last token is a ')' the parser read, not one it inserted; built in code: true
	private static boolean closed(EObject sized) {
		ICompositeNode node = NodeModelUtils.getNode(sized);
		if (node == null)
			return true;
		ILeafNode last = null;
		for (ILeafNode leaf : node.getLeafNodes()) {
			if (!leaf.isHidden())
				last = leaf;
		}
		return last != null && ")".equals(last.getText());
	}

	// a syntax error inside the column's own text; a column built in code has no node and none
	private static boolean hasSyntaxError(Column column) {
		ICompositeNode node = NodeModelUtils.getNode(column);
		if (node == null)
			return false;
		for (INode child : node.getAsTreeIterable()) {
			if (child.getSyntaxErrorMessage() != null)
				return true;
		}
		return false;
	}
}
