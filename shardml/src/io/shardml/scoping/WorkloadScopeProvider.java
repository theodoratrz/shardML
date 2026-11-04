package io.shardml.scoping;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;

import io.shardml.sql.Column;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.workload.ColumnMap;
import io.shardml.workload.ColumnRef;
import io.shardml.workload.Domain;
import io.shardml.workload.Draw;
import io.shardml.workload.Join;
import io.shardml.workload.KeyPath;
import io.shardml.workload.Let;
import io.shardml.workload.Num;
import io.shardml.workload.OverRows;
import io.shardml.workload.PerRows;
import io.shardml.workload.Repeat;
import io.shardml.workload.Statement;
import io.shardml.workload.Step;
import io.shardml.workload.TableData;
import io.shardml.workload.Transaction;
import io.shardml.workload.VarRef;
import io.shardml.workload.Workload;

/* Resolves a workload's cross-references: tables and columns against the imported .msql
 * schema, domains and params within the workload, and variables within their transaction.
 * A variable is visible after its let, in the same block and in blocks nested in it; a let
 * inside a repeat is not visible after that repeat. */
public class WorkloadScopeProvider extends AbstractDeclarativeScopeProvider {

	// tables of the imported schema
	private IScope tableScope(EObject context) {
		Schema schema = ImportedSchema.of(context);
		if (schema == null)
			return IScope.NULLSCOPE;
		return Scopes.scopeFor(schema.getTables());
	}

	private static Workload workload(EObject context) {
		return EcoreUtil2.getContainerOfType(context, Workload.class);
	}

	private IScope domainScope(EObject context) {
		return Scopes.scopeFor(workload(context).getDomains());
	}

	public IScope scope_TableData_table(TableData context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_Statement_table(Statement context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_Join_table(Join context, EReference ref) {
		return tableScope(context);
	}

	// a table block maps the columns of its own table
	public IScope scope_ColumnMap_column(ColumnMap context, EReference ref) {
		Table table = ((TableData) context.eContainer()).getTable();
		if (table == null || table.eIsProxy())
			return IScope.NULLSCOPE;
		return Scopes.scopeFor(table.getColumns());
	}

	// the statement's table first, then its joined tables, in order
	private static List<Table> statementTables(EObject context) {
		Statement statement = EcoreUtil2.getContainerOfType(context, Statement.class);
		List<Table> tables = new ArrayList<>();
		if (statement == null)
			return tables;
		if (statement.getTable() != null && !statement.getTable().eIsProxy())
			tables.add(statement.getTable());
		for (Join join : statement.getJoins()) {
			if (join.getTable() != null && !join.getTable().eIsProxy())
				tables.add(join.getTable());
		}
		return tables;
	}

	// a qualifier names the statement's table or one of its joins
	public IScope scope_ColumnRef_table(ColumnRef context, EReference ref) {
		return Scopes.scopeFor(new LinkedHashSet<>(statementTables(context)));
	}

	// a qualified column belongs to its qualifier; an unqualified one to any of the
	// statement's tables, the first table that has it winning
	public IScope scope_ColumnRef_column(ColumnRef context, EReference ref) {
		List<Table> tables = context.getTable() != null ? List.of(context.getTable()) : statementTables(context);
		List<Column> columns = new ArrayList<>();
		for (Table table : tables) {
			if (!table.eIsProxy())
				columns.addAll(table.getColumns());
		}
		return Scopes.scopeFor(columns);
	}

	public IScope scope_Domain_parent(Domain context, EReference ref) {
		return domainScope(context);
	}

	public IScope scope_PerRows_domains(PerRows context, EReference ref) {
		return domainScope(context);
	}

	public IScope scope_OverRows_domain(OverRows context, EReference ref) {
		return domainScope(context);
	}

	public IScope scope_KeyPath_domain(KeyPath context, EReference ref) {
		return domainScope(context);
	}

	public IScope scope_Draw_domain(Draw context, EReference ref) {
		return domainScope(context);
	}

	public IScope scope_Num_param(Num context, EReference ref) {
		return Scopes.scopeFor(workload(context).getParams());
	}

	// a projection names an ancestor of the path's domain
	public IScope scope_KeyPath_parents(KeyPath context, EReference ref) {
		return Scopes.scopeFor(ancestors(context.getDomain()));
	}

	// ... or of the variable's domain
	public IScope scope_VarRef_parents(VarRef context, EReference ref) {
		Let let = context.getVariable();
		if (let == null || let.eIsProxy() || let.getDraw() == null)
			return IScope.NULLSCOPE;
		return Scopes.scopeFor(ancestors(let.getDraw().getDomain()));
	}

	// the parent, its parent, ...; stops at a cycle or an unresolved domain
	public static List<Domain> ancestors(Domain domain) {
		Set<Domain> seen = new LinkedHashSet<>();
		Domain current = domain == null || domain.eIsProxy() ? null : domain.getParent();
		while (current != null && !current.eIsProxy() && current != domain && seen.add(current))
			current = current.getParent();
		return new ArrayList<>(seen);
	}

	// the lets declared before the reference's step in its block, then in each enclosing
	// block before the step that holds it; inner lets shadow outer ones
	public IScope scope_VarRef_variable(VarRef context, EReference ref) {
		List<Let> visible = new ArrayList<>();
		EObject step = context;
		while (step != null && !(step.eContainer() instanceof Transaction) && !(step.eContainer() instanceof Repeat))
			step = step.eContainer();
		while (step != null) {
			EObject block = step.eContainer();
			EList<Step> steps = block instanceof Repeat repeat ? repeat.getSteps() : ((Transaction) block).getSteps();
			for (Step earlier : steps.subList(0, steps.indexOf(step))) {
				if (earlier instanceof Let let)
					visible.add(let);
			}
			step = block instanceof Repeat ? block : null;
		}
		return Scopes.scopeFor(visible);
	}
}
