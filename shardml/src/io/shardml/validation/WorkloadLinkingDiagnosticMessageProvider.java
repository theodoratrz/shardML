package io.shardml.validation;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.google.inject.Inject;

import io.shardml.sql.Table;
import io.shardml.workload.ColumnRef;
import io.shardml.workload.TableData;
import io.shardml.workload.VarRef;
import io.shardml.workload.WorkloadPackage.Literals;

/* Gives a workload's unresolved references the ShardML issue codes, so they are reported
 * like every other problem: SHML_040 unknown table, SHML_041 unknown column, SHML_042 unknown
 * domain, variable or param, SHML_053 a projection to a domain that is not an ancestor.
 * A column of an unknown table (a table block's or a qualifier's), and a projection of an
 * unknown variable, get no second error: the first one already says what is wrong. */
public class WorkloadLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {

	public static final String UNKNOWN_TABLE = "SHML_040";
	public static final String UNKNOWN_COLUMN = "SHML_041";
	public static final String UNKNOWN_NAME = "SHML_042";
	public static final String NOT_AN_ANCESTOR = "SHML_053";

	@Inject
	private IScopeProvider scopeProvider;

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(ILinkingDiagnosticContext context) {
		EReference ref = context.getReference();
		EObject owner = context.getContext();
		String name = context.getLinkText();
		if (ref == Literals.TABLE_DATA__TABLE || ref == Literals.STATEMENT__TABLE || ref == Literals.JOIN__TABLE)
			return error("Unknown table '" + name + "' in the imported schema.", UNKNOWN_TABLE);
		if (ref == Literals.COLUMN_REF__TABLE)
			return error("'" + name + "' is not this statement's table or one of its joins." + visible(owner, ref, "Tables here"),
					UNKNOWN_TABLE);
		if (ref == Literals.COLUMN_MAP__COLUMN) {
			Table table = ((TableData) owner.eContainer()).getTable();
			if (table == null || table.eIsProxy())
				return null;
			return error("Unknown column '" + name + "' in this table." + visible(owner, ref, "Columns"), UNKNOWN_COLUMN);
		}
		if (ref == Literals.COLUMN_REF__COLUMN) {
			ColumnRef column = (ColumnRef) owner;
			if (column.getTable() != null && column.getTable().eIsProxy())
				return null;
			return error("Unknown column '" + name + "' in "
					+ (column.getTable() != null ? "'" + column.getTable().getName() + "'." : "this statement's tables."),
					UNKNOWN_COLUMN);
		}
		if (ref == Literals.VAR_REF__VARIABLE)
			return error("Unknown variable '" + name + "': a variable is visible after its let, in the same transaction, "
					+ "and not after the repeat block that declares it." + visible(owner, ref, "Visible here"), UNKNOWN_NAME);
		if (ref == Literals.NUM__PARAM)
			return error("Unknown param '" + name + "'." + visible(owner, ref, "Declared params"), UNKNOWN_NAME);
		if (ref == Literals.DOMAIN__PARENT || ref == Literals.PER_ROWS__DOMAINS || ref == Literals.OVER_ROWS__DOMAIN
				|| ref == Literals.KEY_PATH__DOMAIN || ref == Literals.DRAW__DOMAIN)
			return error("Unknown domain '" + name + "'." + visible(owner, ref, "Declared domains"), UNKNOWN_NAME);
		if (ref == Literals.KEY_PATH__PARENTS || ref == Literals.VAR_REF__PARENTS) {
			if (owner instanceof VarRef var && (var.getVariable() == null || var.getVariable().eIsProxy()))
				return null;
			return error("'" + name + "' is not an ancestor of this domain." + visible(owner, ref, "Ancestors"), NOT_AN_ANCESTOR);
		}
		return super.getUnresolvedProxyMessage(context);
	}

	private static DiagnosticMessage error(String message, String code) {
		return new DiagnosticMessage(message, Severity.ERROR, code);
	}

	// " <label>: a, b." from the reference's scope, or " There are none." when it is empty
	private String visible(EObject owner, EReference ref, String label) {
		String names = StreamSupport.stream(scopeProvider.getScope(owner, ref).getAllElements().spliterator(), false)
				.map(IEObjectDescription::getName).map(Object::toString).distinct().collect(Collectors.joining(", "));
		return names.isEmpty() ? " There are none." : " " + label + ": " + names + ".";
	}
}
