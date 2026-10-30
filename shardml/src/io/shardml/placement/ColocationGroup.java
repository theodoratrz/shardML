package io.shardml.placement;

import java.util.List;

/* A colocation group as placement sees it: the root, every table in the group (root first,
 * then in declaration order), the shared bucket count, and the nodes its buckets go to
 * round-robin. */
public record ColocationGroup(String root, List<String> tables, int buckets, List<Node> targets) {

	public ColocationGroup {
		tables = List.copyOf(tables);
		targets = List.copyOf(targets);
	}

	// the node bucket i of every table in the group lives on
	public Node targetOf(int bucket) {
		return targets.get(bucket % targets.size());
	}
}
