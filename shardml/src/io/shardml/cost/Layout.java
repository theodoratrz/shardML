package io.shardml.cost;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.shardml.placement.Node;
import io.shardml.placement.Placement;
import io.shardml.placement.ShardSlot;

/* A layout as the cost model reads it, free of EMF (built by Layouts.of): the worker nodes, every
 * table the model declares in declaration order, where each shard lives, and the schema it was
 * written against. Phase 5 builds candidate layouts as records, without a model. */
public record Layout(String name, List<Node> nodes, List<TableLayout> tables, Placement placement, SchemaView schema) {

	public Layout {
		nodes = List.copyOf(nodes);
		tables = List.copyOf(tables);
	}

	public Optional<TableLayout> table(String name) {
		return tables.stream().filter(t -> t.table().equals(name)).findFirst();
	}

	// the colocation groups (their roots' stored names), in table order
	public List<String> groups() {
		return tables.stream().filter(t -> t instanceof HashTable).map(t -> ((HashTable) t).group()).distinct().toList();
	}

	// the node bucket b of a hash table lives on
	public Node nodeOf(HashTable table, int bucket) {
		return placement.nodeOf(table.qualified(), bucket);
	}

	// the same layout with every shard placed by the map instead (Phase 7); the map must place every
	// bucket of every hash table, on declared nodes only (Placement.of)
	public Layout withPlacement(Map<ShardSlot, Node> assignments) {
		return new Layout(name, nodes, tables, Placement.of(placement, assignments), schema);
	}
}
