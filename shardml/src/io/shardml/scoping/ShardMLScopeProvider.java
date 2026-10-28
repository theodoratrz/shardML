package io.shardml.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;

import com.google.common.collect.Iterables;

import io.shardml.shardML.BelongsTo;
import io.shardml.shardML.GroupBy;
import io.shardml.shardML.HasMany;
import io.shardml.shardML.HasOne;
import io.shardml.shardML.RouteRule;
import io.shardml.shardML.ShardDecl;
import io.shardml.shardML.SortClause;
import io.shardml.shardML.WhereCondition;
import io.shardml.sql.Schema;

/* resolves cross-references between ShardML and imported sql schema. */
public class ShardMLScopeProvider extends AbstractDeclarativeScopeProvider {

	public Schema getSchema(EObject obj) {
		return ImportedSchema.of(obj);
	}

	// return the scope containing all teblaes from the imported SQL schema
	// need this for resolving sql::Table objects
	public IScope tableScope(EObject context) {
		Schema schema = getSchema(context);
		if (schema == null)
			return IScope.NULLSCOPE;
		return Scopes.scopeFor(schema.getTables());
	}

	// returns a scope containing all columns from all tables of the imported schema
	public IScope columnScope(EObject context) {
		Schema schema = getSchema(context);
		if (schema == null)
			return IScope.NULLSCOPE;
		return Scopes.scopeFor(Iterables.concat(Iterables.transform(schema.getTables(), t -> t.getColumns())));
	}

	// tables cross-references
	public IScope scope_ShardDecl_entity(ShardDecl context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_ShardDecl_colocate(ShardDecl context, EReference ref) {
		return tableScope(context);
	}

	// scopes shard key to only the columns of the declared table
	// prevents false errors just because multiple tables share column names (eg: id)
	public IScope scope_ShardDecl_id(ShardDecl context, EReference ref) {
		if (context.getEntity() == null || context.getEntity().eIsProxy())
			return IScope.NULLSCOPE;
		return Scopes.scopeFor(context.getEntity().getColumns());
	}

	public IScope scope_RouteRule_entity(RouteRule context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_HasMany_subject(HasMany context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_HasMany_many(HasMany context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_HasOne_subject(HasOne context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_HasOne_one(HasOne context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_BelongsTo_subject(BelongsTo context, EReference ref) {
		return tableScope(context);
	}

	public IScope scope_BelongsTo_belongs(BelongsTo context, EReference ref) {
		return tableScope(context);
	}

	// the foreign key column lives on the subject table
	public IScope scope_BelongsTo_via(BelongsTo context, EReference ref) {
		if (context.getSubject() == null || context.getSubject().eIsProxy())
			return IScope.NULLSCOPE;
		return Scopes.scopeFor(context.getSubject().getColumns());
	}

	// scope where, sort and group by column references to the columns of the table
	// declared in ShardDecl. So that the query patterns are validated against the correct table
	public IScope scope_WhereCondition_field(WhereCondition context, EReference ref) {
		ShardDecl shard = EcoreUtil2.getContainerOfType(context, ShardDecl.class);
		if (shard == null || shard.getEntity() == null || shard.getEntity().eIsProxy())
			return columnScope(context);
		return Scopes.scopeFor(shard.getEntity().getColumns());
	}

	public IScope scope_SortClause_field(SortClause context, EReference ref) {
		ShardDecl shard = EcoreUtil2.getContainerOfType(context, ShardDecl.class);
		if (shard == null || shard.getEntity() == null || shard.getEntity().eIsProxy())
			return columnScope(context);
		return Scopes.scopeFor(shard.getEntity().getColumns());
	}

	public IScope scope_GroupBy_field(GroupBy context, EReference ref) {
		ShardDecl shard = EcoreUtil2.getContainerOfType(context, ShardDecl.class);
		if (shard == null || shard.getEntity() == null || shard.getEntity().eIsProxy())
			return columnScope(context);
		return Scopes.scopeFor(shard.getEntity().getColumns());
	}
}
