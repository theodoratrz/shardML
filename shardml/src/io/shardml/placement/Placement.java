package io.shardml.placement;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* Which node every shard of a model lives on. Built by PlacementResolver; empty for a model
 * that declares no nodes. Iteration order is deterministic: groups in the order of their roots,
 * tables within a group root first, buckets ascending. */
public final class Placement {

	private final List<Node> nodes;
	private final List<ColocationGroup> groups;
	private final Map<ShardSlot, Node> assignments;

	Placement(List<Node> nodes, List<ColocationGroup> groups) {
		this.nodes = List.copyOf(nodes);
		this.groups = List.copyOf(groups);
		Map<ShardSlot, Node> map = new LinkedHashMap<>();
		for (ColocationGroup group : groups) {
			for (String table : group.tables()) {
				for (int bucket = 0; bucket < group.buckets(); bucket++)
					map.put(new ShardSlot(table, bucket), group.targetOf(bucket));
			}
		}
		this.assignments = Collections.unmodifiableMap(map);
	}

	// the declared nodes, in declaration order
	public List<Node> nodes() {
		return nodes;
	}

	public List<ColocationGroup> groups() {
		return groups;
	}

	// (table, bucket) -> node for every shard of every hash-distributed table
	public Map<ShardSlot, Node> assignments() {
		return assignments;
	}

	// the node a shard lives on, or null if the model has no such shard
	public Node nodeOf(String table, int bucket) {
		return assignments.get(new ShardSlot(table, bucket));
	}

	// true iff the model declares no nodes, so placement is left to Citus
	public boolean isEmpty() {
		return nodes.isEmpty();
	}
}
