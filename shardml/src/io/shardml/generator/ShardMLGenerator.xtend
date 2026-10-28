package io.shardml.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext

import io.shardml.shardML.Model 
import io.shardml.shardML.Database 
import io.shardml.shardML.DatabaseType 
import io.shardml.shardML.Strategy 

/*Produces 2 output files per model:
 * 1. A platform specific json sharding config
 * 2. A SQL distribution script (only for Citus)
 * The generator dispatches on database type to avoid vendor lock in.
 */
 
class ShardMLGenerator extends AbstractGenerator {

	// generate both output files
    override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
        val model = resource.contents.head as Model
        val database = model.db
        fsa.generateFile(database.name + '-sharding.json', generateJson(database))
        fsa.generateFile(database.name + '-distribution.sql', generateSQL(database))
    }

    def generateJson(Database db) {
        if (db.type == DatabaseType.POSTGRES) PostgresJSON(db)
        else MySQLJSON(db)
    }

	// Generates a Citus-comoatible json configuration
	// hash-sharded tables include distrbution_column, shard_count and colocate_with field.
	// replicated tables use distribution_type: reference
    def PostgresJSON(Database db) '''
        {
            "database": "«db.name»",
            "target": "citus",
            "tables": [
                «FOR shard: db.shard_decl SEPARATOR ","»
                {
                    "name": "«shard.entity.name»",
                    «IF shard.strategy == Strategy.HASH || shard.strategy == Strategy.CONS_HASH»
                    "distribution_column": "«shard.id.name»",
                    "shard_count": «shard.buckets»,
                    "distribution_type": "hash"
                    «IF shard.colocate !== null»
                    ,"colocate_with": "«shard.colocate.name»"
                    «ENDIF»
                    «ELSE»
                    "distribution_type": "reference"
                    «ENDIF»
                }
                «ENDFOR»
            ]
        }
    '''

	// Generates a Vitess-compatible json configuration
	// uses vindex and vindex-type fields following Vitess VSchema conventions
	// colocate_with field is optional and is included when a colocation directive is presented
    def MySQLJSON(Database db) '''
        {
            "database": "«db.name»",
            "target": "vitess",
            "keyspace": "«db.name»",
            "tables": [
                «FOR shard: db.shard_decl SEPARATOR ","»
                {
                    "name": "«shard.entity.name»",
                    «IF shard.strategy == Strategy.HASH || shard.strategy == Strategy.CONS_HASH»
                    "vindex": "«shard.id.name»",
                    "vindex_type": "hash",
                    "buckets": «shard.buckets»
                    «IF shard.colocate !== null»
                    ,"colocate_with": "«shard.colocate.name»"
                    «ENDIF»
                    «ELSE»
                    "vindex_type": "reference"
                    «ENDIF»
                }
                «ENDFOR»
            ]
        }
    '''

	// generates SQL distribution script.
    def generateSQL(Database db) '''
        «IF db.type == DatabaseType.POSTGRES»
        «distributionDefinitions(db)»
        «ENDIF»
    '''

	// generates Citus distribution commands for each sharded table.
	// hash-sharded: create_distributed_table()
	// replicated: create_reference_table()
    def distributionDefinitions(Database db) 
	'''
		«FOR shard: db.shard_decl»
			«IF shard.strategy == Strategy.HASH || shard.strategy == Strategy.CONS_HASH»
				SELECT create_distributed_table('«shard.entity.name»', '«shard.id.name»' «IF shard.colocate !== null» ,colocate_with => '«shard.colocate.name»' «ENDIF	»);
			«ELSE»
				SELECT create_reference_table('«shard.entity.name»');
			«ENDIF»
		«ENDFOR»
	'''
    

}