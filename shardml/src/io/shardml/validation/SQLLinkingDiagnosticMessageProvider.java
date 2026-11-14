package io.shardml.validation;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

import io.shardml.schema.SchemaFacts;
import io.shardml.sql.AlterTable;
import io.shardml.sql.ForeignKey;
import io.shardml.sql.Index;
import io.shardml.sql.IndexPart;
import io.shardml.sql.SqlPackage;
import io.shardml.sql.Table;

/* Unresolved references in a .msql schema get their own issue codes: SHML_062 for a table (named by
 * REFERENCES, ALTER TABLE or CREATE INDEX), SHML_063 for a column (of a key, an index, or a foreign
 * key's referenced columns). The message says where the name was looked up. The columns of an
 * unknown table get no message of their own: SHML_062 says so, once, at the table's name. */
public class SQLLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {

	public static final String UNKNOWN_TABLE = "SHML_062";
	public static final String UNKNOWN_COLUMN = "SHML_063";

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(ILinkingDiagnosticContext context) {
		String name = context.getLinkText();
		EReference reference = context.getReference();
		if (reference == SqlPackage.Literals.FOREIGN_KEY__REF_TABLE)
			return unknownTable(name, ((ForeignKey) context.getContext()).getRefSchema());
		if (reference == SqlPackage.Literals.ALTER_TABLE__TABLE)
			return unknownTable(name, ((AlterTable) context.getContext()).getSchemaName());
		if (reference == SqlPackage.Literals.INDEX__TABLE)
			return unknownTable(name, ((Index) context.getContext()).getSchemaName());
		if (reference == SqlPackage.Literals.FOREIGN_KEY__REF_COLUMNS)
			return unknownColumn(name, ((ForeignKey) context.getContext()).getRefTable());
		if (reference == SqlPackage.Literals.TABLE_CONSTRAINT__COLUMNS || reference == SqlPackage.Literals.INDEX_PART__COLUMN) {
			Table table = SchemaFacts.owningTable(context.getContext());
			if (table == null && EcoreUtil2.getContainerOfType(context.getContext(), Table.class) == null)
				return null; // an ALTER TABLE or index on an unknown table
			DiagnosticMessage message = unknownColumn(name, table);
			// lower(b): a function call written without its own parentheses reads as the column lower
			// with the options (b)
			if (message != null && context.getContext() instanceof IndexPart part && part.getOptions() != null
					&& part.getOptions().startsWith("("))
				return new DiagnosticMessage(message.getMessage() + " If " + name + part.getOptions() + " is an expression, "
						+ "write it in parentheses, as pg_dump does: (" + name + part.getOptions() + ").", Severity.ERROR,
						UNKNOWN_COLUMN);
			return message;
		}
		return super.getUnresolvedProxyMessage(context);
	}

	private static DiagnosticMessage unknownTable(String name, String schema) {
		String where = schema == null ? "this file" : "schema " + schema + " of this file";
		return new DiagnosticMessage("Unknown table " + name + ": no table of that name in " + where + ".", Severity.ERROR,
				UNKNOWN_TABLE);
	}

	// null when the table itself is unknown
	private static DiagnosticMessage unknownColumn(String name, Table table) {
		if (table == null || table.eIsProxy())
			return null;
		String in = table.getName() == null ? "this table" : "table " + table.getName();
		return new DiagnosticMessage("Unknown column " + name + " in " + in + ".", Severity.ERROR, UNKNOWN_COLUMN);
	}
}
