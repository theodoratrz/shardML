package io.shardml.validation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.xtext.validation.Check;

import io.shardml.shardML.BelongsTo;
import io.shardml.shardML.Database;
import io.shardml.shardML.DatabaseType;
import io.shardml.shardML.HasMany;
import io.shardml.shardML.HasOne;
import io.shardml.shardML.Link;
import io.shardml.shardML.Policy;
import io.shardml.shardML.QueryPattern;
import io.shardml.shardML.Relationship;
import io.shardml.shardML.RouteRule;
import io.shardml.shardML.ShardDecl;
import io.shardml.shardML.ShardMLPackage;
import io.shardml.shardML.Strategy;
import io.shardml.shardML.WhereCondition;
import io.shardml.sql.AllTypes;
import io.shardml.sql.Table;

public class ShardMLValidator extends AbstractShardMLValidator {

	// Unique issue codes — enables programmatic quick-fix registration
	private static final String EMPTY_DATABASE            = "SHML_001";
	private static final String STRATEGY_KEY_REQUIRED     = "SHML_002";
	private static final String REPLICATED_KEY_FORBIDDEN  = "SHML_003";
	private static final String INVALID_BUCKET_COUNT      = "SHML_004";
	private static final String SELF_COLOCATION           = "SHML_005";
	private static final String SHARD_KEY_NOT_IN_TABLE    = "SHML_006";
	private static final String QUERY_NO_WHERE            = "SHML_007";
	private static final String SHARD_KEY_NOT_IN_WHERE    = "SHML_008";
	private static final String DISTINCT_CROSS_SHARD      = "SHML_009";
	private static final String GROUP_BY_NON_SHARD_KEY    = "SHML_010";
	private static final String ORDER_BY_NON_SHARD_KEY    = "SHML_011";
	private static final String OR_IN_WHERE               = "SHML_012";
	private static final String TABLES_NOT_COLOCATED      = "SHML_013";
	private static final String COLOCATION_TYPE_MISMATCH  = "SHML_014";
	private static final String CIRCULAR_COLOCATION       = "SHML_015";
	private static final String LONG_COLOCATION_CHAIN     = "SHML_016";
	private static final String BUCKET_COUNT_NOT_POWER2   = "SHML_017";
	private static final String BUCKET_COUNT_TOO_HIGH     = "SHML_018";
	private static final String DUPLICATE_SHARD_DECL      = "SHML_019";
	private static final String DUPLICATE_ROUTE_RULE      = "SHML_020";
	private static final String SUBOPTIMAL_KEY_TYPE       = "SHML_021";
	private static final String MISSING_ROUTE_RULE        = "SHML_022";
	private static final String RELATIONSHIP_NOT_COLOCATED = "SHML_023";
	private static final String UNSUPPORTED_TARGET        = "SHML_025";

	// restrict validation to shardML only
	@Override
	public boolean isLanguageSpecific() {
		return true;
	}

	// Helper: the shard declaration for a table, or null
	private static ShardDecl shardFor(Database db, Table table) {
		for (ShardDecl s : db.getShard_decl()) {
			if (s.getEntity() == table)
				return s;
		}
		return null;
	}

	// Helper: the shard declaration for a route rule's table, ignoring unresolved tables
	private static ShardDecl shardForRoute(Database db, RouteRule route) {
		for (ShardDecl s : db.getShard_decl()) {
			if (s.getEntity() != null && !s.getEntity().eIsProxy() && s.getEntity() == route.getEntity())
				return s;
		}
		return null;
	}

	// Helper: the table on the other side of a relationship
	private static Table targetOf(Relationship rel) {
		if (rel instanceof HasMany hasMany)
			return hasMany.getMany();
		if (rel instanceof HasOne hasOne)
			return hasOne.getOne();
		if (rel instanceof BelongsTo belongsTo)
			return belongsTo.getBelongs();
		return null;
	}

	private static boolean isHashed(ShardDecl shard) {
		return shard.getStrategy() == Strategy.HASH || shard.getStrategy() == Strategy.CONS_HASH;
	}

	// no meaning in a database without shard declarations
	@Check
	public void checkEmptyDatabase(Database db) {
		if (db.getShard_decl().isEmpty()) {
			error("Database has no shard declarations.", db,
					ShardMLPackage.Literals.DATABASE__SHARD_DECL, EMPTY_DATABASE);
		}
	}

	// Citus is the only target; Vitess (mysql) output was dropped
	@Check
	public void checkSupportedTarget(Database db) {
		if (db.getType() == DatabaseType.MYSQL) {
			error("Target 'mysql' (Vitess) is not supported; only 'postgres' (Citus) is.", db,
					ShardMLPackage.Literals.DATABASE__TYPE, UNSUPPORTED_TARGET);
		}
	}

	// Hash and consistent hash strategies need shard key
	@Check
	public void checkStrategyKeyExists(ShardDecl shard) {
		if (isHashed(shard) && shard.getId() == null) {
			error("Key is required for hash sharding", shard, ShardMLPackage.Literals.SHARD_DECL__STRATEGY, STRATEGY_KEY_REQUIRED);
		}
	}

	// Replicated tables don't need shard key -> copied to every shard
	@Check
	public void checkReplicatedKeyNotExists(ShardDecl shard) {
		if (shard.getStrategy() == Strategy.REPLICATED && shard.getId() != null) {
			error("No key is required for replicated sharding", shard, ShardMLPackage.Literals.SHARD_DECL__STRATEGY, REPLICATED_KEY_FORBIDDEN);
		}
	}

	// bucket count must be >0 for hash and undefined for replicated
	@Check
	public void checkBuckets(ShardDecl shard) {
		if (isHashed(shard) && shard.getBuckets() <= 0) {
			error("Buckets must be greater than 0 in hash sharding.", shard, ShardMLPackage.Literals.SHARD_DECL__BUCKETS, INVALID_BUCKET_COUNT);
		} else if (shard.getStrategy() == Strategy.REPLICATED && shard.getBuckets() > 0) {
			error("In replicated sharding we don't define buckets.", shard, ShardMLPackage.Literals.SHARD_DECL__BUCKETS, INVALID_BUCKET_COUNT);
		}
	}

	// A table cannot colocate with itself
	@Check
	public void checkSelfColocation(ShardDecl shard) {
		if (shard.getColocate() == shard.getEntity()) {
			error("Cannot colocate a table with itself", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, SELF_COLOCATION);
		}
	}

	// shard key must be a column belonging to the declared table
	@Check
	public void checkShardKeyInShardTable(ShardDecl shard) {
		if (shard.getEntity() != null && shard.getId() != null) {
			if (!shard.getEntity().getColumns().contains(shard.getId())) {
				error("Shard column must belong to sharded table", shard,
						ShardMLPackage.Literals.SHARD_DECL__ID, SHARD_KEY_NOT_IN_TABLE);
			}
		}
	}

	// Helper: issue warning or error based on the route rule policy
	public void issuePolicy(Policy policy, String message, QueryPattern query, String code) {
		switch (policy) {
			case ALLOW -> {
			}
			case WARN -> warning(message, query, ShardMLPackage.Literals.QUERY_PATTERN__NAME, code);
			case DENY -> error(message, query, ShardMLPackage.Literals.QUERY_PATTERN__NAME, code);
		}
	}

	// Queries with no WHERE clause scatter to all shards
	// DELETE without WHERE is always an error — can delete all rows across all shards
	@Check
	public void checkQueryPatternNoWhere(QueryPattern query) {
		if (query.getWhere() == null) {
			RouteRule route = (RouteRule) query.eContainer();
			Policy policy = route.getPolicy();
			switch (query.getQueryType()) {
				case DELETE -> error("Delete query without where clause will affect all shards.", query,
						ShardMLPackage.Literals.QUERY_PATTERN__NAME, QUERY_NO_WHERE);
				case WRITE -> issuePolicy(policy, "Write query without where clause will affect all shards.", query, QUERY_NO_WHERE);
				case READ -> issuePolicy(policy, "Read query without where clause is a scatter-gather risk.", query, QUERY_NO_WHERE);
			}
		}
	}

	// WHERE clause should include shard key, else scatter-gather risk
	@Check
	public void checkShardingKeyNotInQuery(QueryPattern query) {
		RouteRule route = (RouteRule) query.eContainer();
		Database db = (Database) route.eContainer();
		if (query.getWhere() != null) {
			ShardDecl shard = shardForRoute(db, route);
			if (shard != null && shard.getId() != null && !shard.getId().eIsProxy()) {
				boolean hasShardKey = query.getWhere().getFirst().getField() == shard.getId();
				for (WhereCondition c : query.getWhere().getRest())
					hasShardKey |= c.getField() == shard.getId();
				if (!hasShardKey)
					issuePolicy(route.getPolicy(), "Where clause should include shard key", query, SHARD_KEY_NOT_IN_WHERE);
			}
		}
	}

	// DISTINCT requires deduplication across all shards
	@Check
	public void checkDistinctInQuery(QueryPattern query) {
		RouteRule route = (RouteRule) query.eContainer();
		if (query.isDistinct())
			issuePolicy(route.getPolicy(), "DISTINCT requires cross-shard validation and may cause performance issues", query, DISTINCT_CROSS_SHARD);
	}

	// GROUP BY on a non shard key column requires local grouping on each shard, then cross-shard merge (expensive)
	@Check
	public void checkShardingKeyNotInGroupBy(QueryPattern query) {
		RouteRule route = (RouteRule) query.eContainer();
		Database db = (Database) route.eContainer();
		if (query.getGroup() != null) {
			ShardDecl shard = shardForRoute(db, route);
			if (shard != null && shard.getId() != null && !shard.getId().eIsProxy()) {
				if (shard.getId() != query.getGroup().getField()) {
					issuePolicy(route.getPolicy(), "GROUP BY on a non shard-key column requires cross shard aggregation and may cause performance issues.", query, GROUP_BY_NON_SHARD_KEY);
				}
			}
		}
	}

	// ORDER BY on a non shard key column requires a merge sort across all shards
	@Check
	public void checkShardingKeyNotInOrderBy(QueryPattern query) {
		RouteRule route = (RouteRule) query.eContainer();
		Database db = (Database) route.eContainer();
		if (query.getSort() != null) {
			ShardDecl shard = shardForRoute(db, route);
			if (shard != null && shard.getId() != null && !shard.getId().eIsProxy()) {
				if (shard.getId() != query.getSort().getField()) {
					issuePolicy(route.getPolicy(), "ORDER BY on a non shard-key column requires cross shard aggregation and may cause performance issues.", query, ORDER_BY_NON_SHARD_KEY);
				}
			}
		}
	}

	// Use of OR is automatically a scatter-gather risk
	@Check
	public void checkOrInWhereClause(QueryPattern query) {
		RouteRule route = (RouteRule) query.eContainer();
		if (query.getWhere() != null && query.getWhere().getLink().contains(Link.OR))
			issuePolicy(route.getPolicy(), "Use of OR in a Where clause can lead to scatter-gather.", query, OR_IN_WHERE);
	}

	// Related tables that are not colocated require cross-shard joins
	@Check
	public void checkTablesColocation(Database db) {
		for (Relationship temp : db.getRelationships()) {
			ShardDecl subjectShard = shardFor(db, temp.getSubject());
			ShardDecl targetShard = shardFor(db, targetOf(temp));
			if (subjectShard != null && targetShard != null) {
				if (subjectShard.getStrategy() != Strategy.REPLICATED && targetShard.getStrategy() != Strategy.REPLICATED) {
					if (subjectShard.getColocate() != targetShard.getEntity() && subjectShard.getEntity() != targetShard.getColocate()) {
						warning("Tables related to each other should be colocated", db,
								ShardMLPackage.Literals.DATABASE__SHARD_DECL, TABLES_NOT_COLOCATED);
					}
				}
			}
		}
	}

	// Cannot colocate with a replicated table — replicated tables exist on all shards
	@Check
	public void checkColocationTypeMismatch(ShardDecl shard) {
		if (shard.getColocate() != null) {
			Database db = (Database) shard.eContainer();
			ShardDecl targetShard = shardFor(db, shard.getColocate());
			if (targetShard != null && targetShard.getStrategy() == Strategy.REPLICATED) {
				error("Cannot colocate with replicated table.", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, COLOCATION_TYPE_MISMATCH);
			}
		}
	}

	// Circular colocation: A->B, B->A is a config error
	// Citus cannot resolve the colocation group
	@Check
	public void checkCircularColocation(ShardDecl shard) {
		if (shard.getColocate() != null) {
			Database db = (Database) shard.eContainer();
			ShardDecl targetShard = shardFor(db, shard.getColocate());
			if (targetShard != null && targetShard.getColocate() == shard.getEntity()) {
				error("Circular colocation detected: two tables cannot colocate with each other.", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, CIRCULAR_COLOCATION);
			}
		}
	}

	// Deep colocation chains (A->B->C->D->...) increase join complexity
	@Check
	public void checkLongColocationChain(ShardDecl shard) {
		if (shard.getColocate() == null || shard.getColocate() == shard.getEntity())
			return;
		Database db = (Database) shard.eContainer();
		int count = 0;
		ShardDecl current = shard;

		Set<String> visited = new HashSet<>();
		visited.add(shard.getEntity().getName());

		while (current.getColocate() != null) {
			Table target = current.getColocate();
			ShardDecl next = null;
			for (ShardDecl s : db.getShard_decl()) {
				if (Objects.equals(s.getEntity().getName(), target.getName())) {
					next = s;
					break;
				}
			}
			if (next == null || next.getEntity() == null)
				return;
			if (visited.contains(next.getEntity().getName()))
				return;
			visited.add(next.getEntity().getName());
			current = next;
			count++;
		}

		if (count > 3) {
			warning("Long colocation chain found.", shard, ShardMLPackage.Literals.SHARD_DECL__COLOCATE, LONG_COLOCATION_CHAIN);
		}
	}

	// Power-of-2 bucket count allows efficient rebalancing via bit-shifting
	@Check
	public void checkBucketCountPowerOfTwo(ShardDecl shard) {
		int numOfBuckets = shard.getBuckets();
		if (shard.getStrategy() != Strategy.REPLICATED && (numOfBuckets & (numOfBuckets - 1)) != 0) {
			warning("Bucket count should be a power of 2 for optimal performance.", shard,
					ShardMLPackage.Literals.SHARD_DECL__BUCKETS, BUCKET_COUNT_NOT_POWER2);
		}
	}

	// Excessive shard counts increase coordination overhead
	@Check
	public void checkMaxBucketCount(ShardDecl shard) {
		if (shard.getBuckets() > 2048) {
			warning("Bucket count should not exceed 2048 for optimal performance.", shard,
					ShardMLPackage.Literals.SHARD_DECL__BUCKETS, BUCKET_COUNT_TOO_HIGH);
		}
	}

	// Duplicate shard declarations for the same table
	@Check
	public void checkDuplicateShardDecl(ShardDecl shard) {
		Database db = (Database) shard.eContainer();
		for (ShardDecl temp : db.getShard_decl()) {
			if (temp.getEntity() == shard.getEntity() && temp != shard) {
				error("The same table cannot appear in 2 shard declarations.", shard, ShardMLPackage.Literals.SHARD_DECL__ENTITY, DUPLICATE_SHARD_DECL);
			}
		}
	}

	// Duplicate route rules for the same table produce ambiguous query analysis
	@Check
	public void checkDuplicateRouteRule(RouteRule rule) {
		Database db = (Database) rule.eContainer();
		for (RouteRule temp : db.getRoute_rules()) {
			if (temp.getEntity() == rule.getEntity() && temp != rule) {
				error("The same table cannot have 2 route rules defined.", rule, ShardMLPackage.Literals.ROUTE_RULE__ENTITY, DUPLICATE_ROUTE_RULE);
			}
		}
	}

	// INT, BIGINT, UUID distribute evenly and avoid hotspots
	@Check
	public void checkKeyTypeWarning(ShardDecl shard) {
		if (shard.getStrategy() != Strategy.REPLICATED && shard.getId() != null) {
			AllTypes type = shard.getId().getSimpleType();
			if (shard.getId().getVarchar() != null || (type != AllTypes.INT && type != AllTypes.BIGINT && type != AllTypes.UUID)) {
				warning("Shard key should be of type INT, BIGINT or UUID for optimal performance.", shard, ShardMLPackage.Literals.SHARD_DECL__ID, SUBOPTIMAL_KEY_TYPE);
			}
		}
	}

	// No route rule -> query patterns cannot be validated
	@Check
	public void checkMissingRouteRule(ShardDecl shard) {
		if (shard.getStrategy() != Strategy.REPLICATED) {
			Database db = (Database) shard.eContainer();
			boolean hasRoute = db.getRoute_rules().stream().anyMatch(r -> r.getEntity() == shard.getEntity());
			if (!hasRoute) {
				warning("No route rule defined for table '" + shard.getEntity().getName() + "'. Query patterns will not get validated", shard, ShardMLPackage.Literals.SHARD_DECL__ENTITY, MISSING_ROUTE_RULE);
			}
		}
	}

	// Related tables should be colocated since they are queried together
	@Check
	public void checkRelationshipWithoutColocation(Database db) {
		for (Relationship temp : db.getRelationships()) {
			Table subjectTable = temp.getSubject();
			Table targetTable = targetOf(temp);
			ShardDecl subjectShard = shardFor(db, subjectTable);
			ShardDecl targetShard = shardFor(db, targetTable);

			if (subjectShard != null && targetShard != null
					&& !(subjectShard.getColocate() == targetShard.getEntity() || targetShard.getColocate() == subjectShard.getEntity())) {
				warning("Subject table: '" + subjectTable.getName() + "' and target table: '" + targetTable.getName() + "' have a relationship, but are not colocated.", db, ShardMLPackage.Literals.DATABASE__SHARD_DECL, RELATIONSHIP_NOT_COLOCATED);
			}
		}
	}
}
