package io.shardml.validation;

import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

import io.shardml.sql.ForeignKey;
import io.shardml.sql.SqlPackage;
import io.shardml.sql.Table;

/* Unresolved references in a .msql schema get their own issue codes: SHML_062 for a table (a
 * foreign key's REFERENCES), SHML_063 for a column (of a key, or a foreign key's referenced
 * columns). The message says where the name was looked up. The referenced columns of an unknown
 * table get no message of their own. */
public class SQLLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {

	public static final String UNKNOWN_TABLE = "SHML_062";
	public static final String UNKNOWN_COLUMN = "SHML_063";

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(ILinkingDiagnosticContext context) {
		String name = context.getLinkText();
		if (context.getReference() == SqlPackage.Literals.FOREIGN_KEY__REF_TABLE) {
			ForeignKey key = (ForeignKey) context.getContext();
			String where = key.getRefSchema() == null ? "this file" : "schema " + key.getRefSchema() + " of this file";
			return new DiagnosticMessage("Unknown table " + name + ": no table of that name in " + where + ".",
					Severity.ERROR, UNKNOWN_TABLE);
		}
		if (context.getReference() == SqlPackage.Literals.FOREIGN_KEY__REF_COLUMNS) {
			Table table = ((ForeignKey) context.getContext()).getRefTable();
			if (table == null || table.eIsProxy())
				return null; // the table is unknown: SHML_062 says so, once, at the table's name
			return new DiagnosticMessage("Unknown column " + name + " in table " + table.getName() + ".", Severity.ERROR,
					UNKNOWN_COLUMN);
		}
		if (context.getReference() == SqlPackage.Literals.TABLE_CONSTRAINT__COLUMNS) {
			Table table = (Table) org.eclipse.xtext.EcoreUtil2.getContainerOfType(context.getContext(), Table.class);
			String in = table == null || table.getName() == null ? "this table" : "table " + table.getName();
			return new DiagnosticMessage("Unknown column " + name + " in " + in + ".", Severity.ERROR, UNKNOWN_COLUMN);
		}
		return super.getUnresolvedProxyMessage(context);
	}
}
