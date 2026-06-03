package uk.ac.kcl.inf.mdd1.scoping

import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import uk.ac.kcl.inf.mdd1.shardML.ShardDecl
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.EcoreUtil2
import uk.ac.kcl.inf.mdd1.shardML.Model
import org.eclipse.emf.common.util.URI
import uk.ac.kcl.inf.mdd1.sql.Schema
import org.eclipse.emf.ecore.EObject
import uk.ac.kcl.inf.mdd1.shardML.BelongsTo
import uk.ac.kcl.inf.mdd1.shardML.RouteRule
import uk.ac.kcl.inf.mdd1.shardML.HasOne
import uk.ac.kcl.inf.mdd1.shardML.HasMany
import uk.ac.kcl.inf.mdd1.shardML.WhereCondition
import uk.ac.kcl.inf.mdd1.shardML.SortClause
import uk.ac.kcl.inf.mdd1.shardML.GroupBy
import org.eclipse.xtext.scoping.IScope

/* resolves cross-references between ShardML and imported sql schema. */
class ShardMLScopeProvider extends AbstractDeclarativeScopeProvider {

	// manual resolve URI, because Shardml uses a custom import rather than Xtext's built-in
    def getSchema(EObject obj) {
        val root = EcoreUtil2.getRootContainer(obj)
        if (!(root instanceof Model)) 
        	return null
        val model = root as Model
        if (model.imports.empty) 
        	return null
        val importURI = model.imports.head.importURI
        val baseURI = obj.eResource.URI
        val uri = URI.createURI(importURI).resolve(baseURI)
        val importedSource = obj.eResource.resourceSet.getResource(uri, true)
        if (importedSource === null) 
        	return null
        return importedSource.contents.head as Schema
    }

	// return the scope containing all teblaes from the imported SQL schema
	// need this for resolving sql::Table objects
    def tableScope(EObject context) {
        val schema = getSchema(context)
        if (schema === null) 
        	return IScope.NULLSCOPE
        return Scopes.scopeFor(schema.tables)
    }

	// returns a scope containing all columns from all tables of the imported schema
    def columnScope(EObject context) {
        val schema = getSchema(context)
        if (schema === null) 
        	return IScope.NULLSCOPE
        return Scopes.scopeFor(schema.tables.flatMap[columns])
    }

	// tables cross-references
    def scope_ShardDecl_entity(ShardDecl context, EReference ref) { tableScope(context) }

    def scope_ShardDecl_colocate(ShardDecl context, EReference ref) { tableScope(context) }

	// scopes shard key to only the columns of the declared table
	// prevents false errors just because multiple tables share column names (eg: id)
    def scope_ShardDecl_id(ShardDecl context, EReference ref) {
        if (context.entity === null || context.entity.eIsProxy) 
        	return IScope.NULLSCOPE
        return Scopes.scopeFor(context.entity.columns)
    }

    def scope_RouteRule_entity(RouteRule context, EReference ref) { 
    	tableScope(context)
    }

    def scope_HasMany_subject(HasMany context, EReference ref) { 
    	tableScope(context)
    }
    
    def scope_HasMany_many(HasMany context, EReference ref) { 
    	tableScope(context)
    }

    def scope_HasOne_subject(HasOne context, EReference ref) { 
    	tableScope(context)
    }
    
    def scope_HasOne_one(HasOne context, EReference ref) { 
    	tableScope(context)
    }

    def scope_BelongsTo_subject(BelongsTo context, EReference ref) { 
    	tableScope(context)
    }
    
    def scope_BelongsTo_belongs(BelongsTo context, EReference ref) { 
    	tableScope(context)
    }

	// scope where, sort and group by column references to the columns of the table
	// declared in ShardDecl. So that the query patterns are validated against the correct table
    def scope_WhereCondition_field(WhereCondition context, EReference ref) {
        val shard = EcoreUtil2.getContainerOfType(context, ShardDecl)
        if (shard === null || shard.entity === null || shard.entity.eIsProxy) 
        	return columnScope(context)
        return Scopes.scopeFor(shard.entity.columns)
    }

    def scope_SortClause_field(SortClause context, EReference ref) {
        val shard = EcoreUtil2.getContainerOfType(context, ShardDecl)
        if (shard === null || shard.entity === null || shard.entity.eIsProxy) return columnScope(context)
        return Scopes.scopeFor(shard.entity.columns)
    }

    def scope_GroupBy_field(GroupBy context, EReference ref) {
        val shard = EcoreUtil2.getContainerOfType(context, ShardDecl)
        if (shard === null || shard.entity === null || shard.entity.eIsProxy) return columnScope(context)
        return Scopes.scopeFor(shard.entity.columns)
    }
}