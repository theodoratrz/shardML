package io.shardml.placement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.shardml.shardML.Database;
import io.shardml.shardML.Node;
import io.shardml.shardML.PlaceDecl;
import io.shardml.shardML.PlacementStrategy;
import io.shardml.shardML.ShardDecl;
import io.shardml.shardML.Strategy;
import io.shardml.sql.Table;

/* Colocation groups and their target nodes, shared by the validator, the placement
 * resolver and the generator so they cannot disagree.
 * A group's root is the table reached by following colocate_with to its end; placement
 * is declared on the root, and every table colocated with it shares its shards' nodes. */
public final class ColocationGroups {

	private ColocationGroups() {
	}

	// the shard declaration for a table, or null
	public static ShardDecl shardFor(Database db, Table table) {
		for (ShardDecl s : db.getShard_decl()) {
			if (s.getEntity() == table)
				return s;
		}
		return null;
	}

	public static boolean isHashed(ShardDecl shard) {
		return shard.getStrategy() == Strategy.HASH || shard.getStrategy() == Strategy.CONS_HASH;
	}

	// the root of a shard's colocation group; stops at a cycle or an undeclared table
	public static ShardDecl rootOf(Database db, ShardDecl shard) {
		Set<ShardDecl> visited = new HashSet<>();
		ShardDecl current = shard;
		while (visited.add(current) && current.getColocate() != null) {
			ShardDecl next = shardFor(db, current.getColocate());
			if (next == null)
				break;
			current = next;
		}
		return current;
	}

	public static boolean isRoot(ShardDecl shard) {
		return shard.getColocate() == null;
	}

	// the place block for a table, or null; the first one if there are several
	public static PlaceDecl placeFor(Database db, Table table) {
		for (PlaceDecl p : db.getPlacements()) {
			if (p.getEntity() == table)
				return p;
		}
		return null;
	}

	// the nodes a root's buckets go to round-robin, in order, each node once:
	// the listed nodes for pin, else every declared node
	public static List<Node> targetNodes(Database db, ShardDecl root) {
		PlaceDecl place = placeFor(db, root.getEntity());
		List<Node> nodes = place != null && place.getStrategy() == PlacementStrategy.PIN ? place.getNodes() : db.getNodes();
		Set<Node> distinct = new LinkedHashSet<>();
		for (Node node : nodes) {
			if (node != null && !node.eIsProxy())
				distinct.add(node);
		}
		return new ArrayList<>(distinct);
	}
}
