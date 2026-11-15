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
	// built by Placement.of from an explicit map, not by PlacementResolver: its groups' round-robin
	// targets do not describe its assignments, so no placement script may be written from it
	private final boolean derived;

	// a placement with every shard placed by an explicit map (Phase 7), for the tables and nodes of
	// shape: the map must place every shard shape places, and nothing else, on shape's nodes only.
	// groups() still describes shape's groups (tables and buckets; their round-robin targets are
	// shape's); assignments() and nodeOf are the map. isDerived() is true, and the generator refuses
	// to write a placement script from it
	public static Placement of(Placement shape, Map<ShardSlot, Node> assignments) {
		for (ShardSlot slot : shape.assignments.keySet()) {
			if (!assignments.containsKey(slot))
				throw new IllegalArgumentException("The placement does not place bucket " + slot.bucket() + " of " + slot.table() + ".");
		}
		for (Map.Entry<ShardSlot, Node> entry : assignments.entrySet()) {
			ShardSlot slot = entry.getKey();
			if (!shape.assignments.containsKey(slot))
				throw new IllegalArgumentException("The placement places bucket " + slot.bucket() + " of " + slot.table()
						+ ", which the layout does not have.");
			if (!shape.nodes.contains(entry.getValue()))
				throw new IllegalArgumentException("The placement puts bucket " + slot.bucket() + " of " + slot.table()
						+ " on " + entry.getValue() + ", which the layout does not declare.");
		}
		// in shape's order, so equal placements iterate alike
		Map<ShardSlot, Node> ordered = new LinkedHashMap<>();
		for (ShardSlot slot : shape.assignments.keySet())
			ordered.put(slot, assignments.get(slot));
		return new Placement(shape.nodes, shape.groups, ordered);
	}

	private Placement(List<Node> nodes, List<ColocationGroup> groups, Map<ShardSlot, Node> assignments) {
		this.nodes = List.copyOf(nodes);
		this.groups = List.copyOf(groups);
		this.assignments = Collections.unmodifiableMap(assignments);
		this.derived = true;
	}

	// true for a placement built by Placement.of: only assignments() and nodeOf describe it
	public boolean isDerived() {
		return derived;
	}

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
		this.derived = false;
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

	// value equality: the same nodes, groups and shard map
	@Override
	public boolean equals(Object o) {
		return o instanceof Placement p && derived == p.derived && nodes.equals(p.nodes) && groups.equals(p.groups)
				&& assignments.equals(p.assignments);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(derived, nodes, groups, assignments);
	}

	@Override
	public String toString() {
		return "Placement[derived=" + derived + ", nodes=" + nodes + ", groups=" + groups + ", assignments=" + assignments + "]";
	}
}
