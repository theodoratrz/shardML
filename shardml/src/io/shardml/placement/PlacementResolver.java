package io.shardml.placement;

import java.util.ArrayList;
import java.util.List;

import io.shardml.shardML.Database;
import io.shardml.shardML.ShardDecl;

/* Decides which node every shard lives on, as a pure function of the model: no randomness,
 * no I/O, no look at the cluster. Placement is per colocation group: bucket i of every table
 * in the group goes to the group's target node i mod n (see ColocationGroups.targetNodes).
 * Reference tables are left out, since Citus keeps them on every node.
 * Expects a model without validation errors; throws IllegalStateException otherwise. */
public final class PlacementResolver {

	private PlacementResolver() {
	}

	public static Placement resolve(Database db) {
		List<Node> nodes = new ArrayList<>();
		for (io.shardml.shardML.Node node : db.getNodes())
			nodes.add(toNode(node));
		if (nodes.isEmpty())
			return new Placement(nodes, List.of());

		List<ColocationGroup> groups = new ArrayList<>();
		for (ShardDecl root : db.getShard_decl()) {
			if (!ColocationGroups.isHashed(root) || !ColocationGroups.isRoot(root))
				continue;
			String name = root.getEntity().getName();
			if (root.getBuckets() <= 0)
				throw new IllegalStateException("'" + name + "' has no buckets to place");
			List<String> tables = new ArrayList<>(List.of(name));
			for (ShardDecl shard : db.getShard_decl()) {
				if (shard != root && ColocationGroups.isHashed(shard) && ColocationGroups.rootOf(db, shard) == root)
					tables.add(shard.getEntity().getName());
			}
			List<Node> targets = new ArrayList<>();
			for (io.shardml.shardML.Node node : ColocationGroups.targetNodes(db, root))
				targets.add(toNode(node));
			if (targets.isEmpty())
				throw new IllegalStateException("'" + name + "' has no nodes to be placed on");
			groups.add(new ColocationGroup(name, tables, root.getBuckets(), targets));
		}
		return new Placement(nodes, groups);
	}

	private static Node toNode(io.shardml.shardML.Node node) {
		return new Node(node.getName(), node.getHost(), node.getPort());
	}
}
