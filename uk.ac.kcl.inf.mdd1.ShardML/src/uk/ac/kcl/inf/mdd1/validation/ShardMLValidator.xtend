package uk.ac.kcl.inf.mdd1.validation

import org.eclipse.xtext.validation.Check
import uk.ac.kcl.inf.mdd1.shardML.ShardDecl
import uk.ac.kcl.inf.mdd1.shardML.Strategy
import uk.ac.kcl.inf.mdd1.shardML.ShardMLPackage
import uk.ac.kcl.inf.mdd1.shardML.QueryPattern
import uk.ac.kcl.inf.mdd1.shardML.RouteRule
import uk.ac.kcl.inf.mdd1.shardML.Database
import uk.ac.kcl.inf.mdd1.shardML.HasMany
import uk.ac.kcl.inf.mdd1.shardML.HasOne
import uk.ac.kcl.inf.mdd1.shardML.BelongsTo
import uk.ac.kcl.inf.mdd1.sql.AllTypes
import uk.ac.kcl.inf.mdd1.shardML.Policy
import uk.ac.kcl.inf.mdd1.shardML.QueryType
import uk.ac.kcl.inf.mdd1.shardML.Link

class ShardMLValidator extends AbstractShardMLValidator {

// Unique issue codes — enables programmatic quick-fix registration
static val EMPTY_DATABASE            = "SHML_001"
static val STRATEGY_KEY_REQUIRED     = "SHML_002"
static val REPLICATED_KEY_FORBIDDEN  = "SHML_003"
static val INVALID_BUCKET_COUNT      = "SHML_004"
static val SELF_COLOCATION           = "SHML_005"
static val SHARD_KEY_NOT_IN_TABLE    = "SHML_006"
static val QUERY_NO_WHERE            = "SHML_007"
static val SHARD_KEY_NOT_IN_WHERE    = "SHML_008"
static val DISTINCT_CROSS_SHARD      = "SHML_009"
static val GROUP_BY_NON_SHARD_KEY    = "SHML_010"
static val ORDER_BY_NON_SHARD_KEY    = "SHML_011"
static val OR_IN_WHERE               = "SHML_012"
static val TABLES_NOT_COLOCATED      = "SHML_013"
static val COLOCATION_TYPE_MISMATCH  = "SHML_014"
static val CIRCULAR_COLOCATION       = "SHML_015"
static val LONG_COLOCATION_CHAIN     = "SHML_016"
static val BUCKET_COUNT_NOT_POWER2   = "SHML_017"
static val BUCKET_COUNT_TOO_HIGH     = "SHML_018"
static val DUPLICATE_SHARD_DECL      = "SHML_019"
static val DUPLICATE_ROUTE_RULE      = "SHML_020"
static val SUBOPTIMAL_KEY_TYPE       = "SHML_021"
static val MISSING_ROUTE_RULE        = "SHML_022"
static val RELATIONSHIP_NOT_COLOCATED = "SHML_023"

// restrict validation to shardML only
override isLanguageSpecific() {
    true
}

// no meaning in a database without shard declarations
@Check
def checkEmptyDatabase(Database db){
	if(db.shard_decl.empty){
		error(
			"Database has no shard declarations.", db,
            ShardMLPackage.Literals.DATABASE__SHARD_DECL, EMPTY_DATABASE
		)
	}
}

// Hash and consistent hash strategies need shard key
@Check
def checkStrategyKeyExists(ShardDecl shard){
    if ((shard.strategy == Strategy.HASH || shard.strategy == Strategy.CONS_HASH) && (shard.id === null)){
        error(
            "Key is required for hash sharding", shard, ShardMLPackage.Literals.SHARD_DECL__STRATEGY, STRATEGY_KEY_REQUIRED
        )
    }
}

// Replicated tables don't need shard key -> copied to every shard
@Check
def checkReplicatedKeyNotExists(ShardDecl shard){
    if (shard.strategy == Strategy.REPLICATED && shard.id !== null){
        error(
            "No key is required for replicated sharding", shard, ShardMLPackage.Literals.SHARD_DECL__STRATEGY, REPLICATED_KEY_FORBIDDEN
        )
    }
}

// bucket count must be >0 for hash and undefined for replicated
@Check
def checkBuckets(ShardDecl shard){
    if ((shard.strategy == Strategy.HASH || shard.strategy == Strategy.CONS_HASH) && (shard.buckets <= 0)){
        error(
            "Buckets must be greater than 0 in hash sharding.", shard, ShardMLPackage.Literals.SHARD_DECL__BUCKETS, INVALID_BUCKET_COUNT
        )
    } else if (shard.strategy == Strategy.REPLICATED && shard.buckets > 0){
        error(
            "In replicated sharding we don't define buckets.", shard, ShardMLPackage.Literals.SHARD_DECL__BUCKETS, INVALID_BUCKET_COUNT
        )
    }
}

// A table cannot colocate with itself
@Check
def checkSelfColocation(ShardDecl shard){
    if (shard.colocate === shard.entity){
        error(
            "Cannot colocate a table with itself", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, SELF_COLOCATION
        )
    }
}

// shard key must be a column belonging to the declared table
@Check
def checkShardKeyInShardTable(ShardDecl shard){
    if (shard.entity !== null && shard.id !== null){
        if (!shard.entity.columns.contains(shard.id)){
            error(
			    "Shard column must belong to sharded table",
			    shard,
			    ShardMLPackage.Literals.SHARD_DECL__ID,
			    SHARD_KEY_NOT_IN_TABLE
			)
        }
    }
}

// Helper: issue warning or error based on the route rule policy
def issuePolicy(Policy policy, String message, QueryPattern query, String code){
	switch(policy){
		case Policy.ALLOW: {}
		case Policy.WARN:
			 warning(
			    message,
			    query,
			    ShardMLPackage.Literals.QUERY_PATTERN__NAME,
			    code
			)
		case Policy.DENY: 
			error(
				    message,
				    query,
				    ShardMLPackage.Literals.QUERY_PATTERN__NAME,
				    code
				)
	}	
}

// Queries with no WHERE clause scatter to all shards
// DELETE without WHERE is always an error — can delete all rows across all shards
@Check
def checkQueryPatternNoWhere(QueryPattern query){
    if (query.where === null){
    	val route = query.eContainer as RouteRule
    	val policy = route.policy
    	switch(query.queryType){
    		case QueryType.DELETE:
    		error(
			    "Delete query without where clause will affect all shards.",
			    query,
			    ShardMLPackage.Literals.QUERY_PATTERN__NAME,
			    QUERY_NO_WHERE
			)
			
			case QueryType.WRITE:
				issuePolicy(policy, "Write query without where clause will affect all shards.", query, QUERY_NO_WHERE)
			
			case QueryType.READ:
				issuePolicy(policy, "Read query without where clause is a scatter-gather risk.", query, QUERY_NO_WHERE)
    	}
    }
}

// WHERE clause should include shard key, else scatter-gather risk
@Check
def checkShardingKeyNotInQuery(QueryPattern query){
    val route = query.eContainer as RouteRule
    val db = route.eContainer as Database
    val policy = route.policy
    if (query.where !== null){
        val shard = db.shard_decl.findFirst[s | s.entity !== null && !s.entity.eIsProxy && s.entity === route.entity]
        if (shard !== null && shard.id !== null && !shard.id.eIsProxy){
            val hasShardKey = query.where.first.field === shard.id || query.where.rest.exists[c | c.field === shard.id]
            if (!hasShardKey){
            	issuePolicy(policy, "Where clause should include shard key", query, SHARD_KEY_NOT_IN_WHERE)
            }
        }
    }
}

// DISTINCT requires deduplication across all shards
@Check
def checkDistinctInQuery(QueryPattern query){
    val route = query.eContainer as RouteRule
    if (query.distinct === true)
		issuePolicy(route.policy, "DISTINCT requires cross-shard validation and may cause performance issues", query, DISTINCT_CROSS_SHARD)
}

// GROUP BY on a non shard key column requires local grouping on each shard, then cross-shard merge (expensive)
@Check
def checkShardingKeyNotInGroupBy(QueryPattern query){
    val route = query.eContainer as RouteRule
    val db = route.eContainer as Database
    val policy = route.policy
    if (query.group !== null){
        val shard = db.shard_decl.findFirst[s | s.entity !== null && !s.entity.eIsProxy && s.entity === route.entity]
        if (shard !== null && shard.id !== null && !shard.id.eIsProxy){
            if (shard.id !== query.group.field){
            	issuePolicy(policy, "GROUP BY on a non shard-key column requires cross shard aggregation and may cause performance issues.", query, GROUP_BY_NON_SHARD_KEY)
            }
        }
    }
}

// ORDER BY on a non shard key column requires a merge sort across all shards
@Check
def checkShardingKeyNotInOrderBy(QueryPattern query){
    val route = query.eContainer as RouteRule
    val db = route.eContainer as Database
    val policy = route.policy
    if (query.sort !== null){
        val shard = db.shard_decl.findFirst[s | s.entity !== null && !s.entity.eIsProxy && s.entity === route.entity]
        if (shard !== null && shard.id !== null && !shard.id.eIsProxy){
            if (shard.id !== query.sort.field){
            	issuePolicy(policy, "ORDER BY on a non shard-key column requires cross shard aggregation and may cause performance issues.", query, ORDER_BY_NON_SHARD_KEY)
            }
        }
    }
}

// Use of OR is automatically a scatter-gather risk
@Check
def checkOrInWhereClause(QueryPattern query){
	val route = query.eContainer as RouteRule
    val policy = route.policy
	if (query.where.link.contains(Link.OR))
		issuePolicy(policy, "Use of OR in a Where clause can lead to scatter-gather.", query, OR_IN_WHERE)
}

// Related tables that are not colocated require cross-shard joins
@Check
def checkTablesColocation(Database db){
    for (temp : db.relationships){
        val targetTable = switch temp {
            HasMany: temp.many
            HasOne: temp.one
            BelongsTo: temp.belongs
        }
        val subjectTable = temp.subject
        val subjectShard = db.shard_decl.findFirst[s | s.entity === subjectTable]
        val targetShard = db.shard_decl.findFirst[s | s.entity === targetTable]
        if (subjectShard !== null && targetShard !== null){
            if (subjectShard.strategy !== Strategy.REPLICATED && targetShard.strategy !== Strategy.REPLICATED){
                if (subjectShard.colocate !== targetShard.entity && subjectShard.entity !== targetShard.colocate){
                    warning(
                        "Tables related to each other should be colocated", db,
                        ShardMLPackage.Literals.DATABASE__SHARD_DECL, TABLES_NOT_COLOCATED
                    )
                }
            }
        }
    }
}

// Cannot colocate with a replicated table — replicated tables exist on all shards
@Check
def checkColocationTypeMismatch(ShardDecl shard){
	if (shard.colocate !== null){
		val db = shard.eContainer as Database
		val targetShard = db.shard_decl.findFirst[s | s.entity === shard.colocate]
		if(targetShard !== null && targetShard.strategy === Strategy.REPLICATED){
			error(
				"Cannot colocate with replicated table.", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, COLOCATION_TYPE_MISMATCH)
		}
	}
}

// Circular colocation: A->B, B->A is a config error
// Citus cannot resolve the colocation group
@Check
def checkCircularColocation(ShardDecl shard){
	if (shard.colocate !== null){
		val db = shard.eContainer as Database
		val targetShard = db.shard_decl.findFirst[s | s.entity === shard.colocate]
		if(targetShard !== null && targetShard.colocate === shard.entity){
			error(
				"Circular colocation detected: two tables cannot colocate with each other.", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, CIRCULAR_COLOCATION)
		}
	}
}

// Deep colocation chains (A->B->C->D->...) increase join complexity
@Check
def checkLongColocationChain(ShardDecl shard){
	if (shard.colocate === null || shard.colocate === shard.entity) return null
	if (shard.colocate !== null){
		val db = shard.eContainer as Database
		var count = 0
		var current = shard
		
		val visited = newHashSet()
		visited.add(shard.entity.name)

		while(current.colocate !== null){
		    val target = current.colocate
		    val next = db.shard_decl.findFirst[s | s.entity.name == target.name]
		    if (next === null || next.entity === null) return null	
		    if (visited.contains(next.entity.name)) return null
		    visited.add(next.entity.name)
		    current = next
		    count = count + 1
		}
		
		if (count > 3){
			warning(
				"Long colocation chain found.", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, LONG_COLOCATION_CHAIN
			)
		}
	}
}

// Power-of-2 bucket count allows efficient rebalancing via bit-shifting
@Check
def checkBucketCountPowerOfTwo(ShardDecl shard){
	val numOfBuckets = shard.buckets
	if ((shard.strategy !== Strategy.REPLICATED) && !(numOfBuckets.bitwiseAnd(numOfBuckets - 1) === 0)) {
		warning(
            "Bucket count should be a power of 2 for optimal performance.", shard,
            ShardMLPackage.Literals.SHARD_DECL__BUCKETS, BUCKET_COUNT_NOT_POWER2
        )
	}
}

// Excessive shard counts increase coordination overhead
@Check
def checkMaxBucketCount(ShardDecl shard){
	val numOfBuckets = shard.buckets
	if (numOfBuckets > 2048) {
		warning(
            "Bucket count should not exceed 2048 for optimal performance.", shard,
            ShardMLPackage.Literals.SHARD_DECL__BUCKETS, BUCKET_COUNT_TOO_HIGH
        )
	}
}

// Duplicate shard declarations for the same table
@Check
def checkDuplicateShardDecl(ShardDecl shard){
	val db = shard.eContainer as Database
	for (temp: db.shard_decl){
		if(temp.entity === shard.entity && temp !== shard){
			error(
				"The same table cannot appear in 2 shard declarations.", shard, ShardMLPackage.Literals.SHARD_DECL__ENTITY, DUPLICATE_SHARD_DECL
			)
		}
	}
}

// Duplicate route rules for the same table produce ambiguous query analysis
@Check
def checkDuplicateRouteRule(RouteRule rule){
	val db = rule.eContainer as Database
	for (temp: db.route_rules){
		if(temp.entity === rule.entity && temp !== rule){
			error(
				"The same table cannot have 2 route rules defined.", rule, ShardMLPackage.Literals.ROUTE_RULE__ENTITY, DUPLICATE_ROUTE_RULE
			)
		}
	}
}

// INT, BIGINT, UUID distribute evenly and avoid hotspots
@Check 
def checkKeyTypeWarning(ShardDecl shard){
	if (shard.strategy !== Strategy.REPLICATED){
		if (shard.id !== null){
			if (shard.id.varchar !== null || (shard.id.simpleType !== AllTypes.INT && shard.id.simpleType !== AllTypes.BIGINT && shard.id.simpleType !== AllTypes.UUID)){
			warning(
				"Shard key should be of type INT, BIGINT or UUID for optimal performance.", shard, ShardMLPackage.Literals.SHARD_DECL__ID, SUBOPTIMAL_KEY_TYPE
			)
			}
		}
	}
}

// No route rule -> query patterns cannot be validated
@Check 
def checkMissingRouteRule(ShardDecl shard){
	if (shard.strategy !== Strategy.REPLICATED){
		val db = shard.eContainer as Database
		val target = db.route_rules.findFirst[r | r.entity === shard.entity]
		if (target === null){
			warning(
				"No route rule defined for table '" + shard.entity.name + "'. Query patterns will not get validated", shard, ShardMLPackage.Literals.SHARD_DECL__ENTITY, MISSING_ROUTE_RULE
			)
		}
	}
}

// Related tables should be colocated since they are queried together
@Check
def checkRelationshipWithoutColocation(Database db){
	for (temp : db.relationships){
        val targetTable = switch temp {
            HasMany: temp.many
            HasOne: temp.one
            BelongsTo: temp.belongs
        }
        val subjectTable = temp.subject
        val subjectShard = db.shard_decl.findFirst[s | s.entity === subjectTable]
        val targetShard = db.shard_decl.findFirst[s | s.entity === targetTable]
        
        if(subjectShard !== null && targetShard !== null && !(subjectShard.colocate === targetShard.entity || targetShard.colocate === subjectShard.entity)){
        	warning(
        		"Subject table: '" + subjectTable.name + "' and target table: '" + targetTable.name + "' have a relationship, but are not colocated.", db, ShardMLPackage.Literals.DATABASE__SHARD_DECL, RELATIONSHIP_NOT_COLOCATED
        	)
        }
     }
}
}
