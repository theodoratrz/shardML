package io.shardml.cost;

import java.util.ArrayList;
import java.util.List;

import io.shardml.cost.Workload.Condition;
import io.shardml.cost.Workload.Connective;
import io.shardml.cost.Workload.Join;
import io.shardml.cost.Workload.JoinOn;
import io.shardml.cost.Workload.Op;
import io.shardml.cost.Workload.Statement;
import io.shardml.cost.Workload.VarRef;

/* How Citus runs a workload statement under a layout (Plan-phase3 4.4c, 1), shared by the load
 * metrics (Step 6) and fan-out / distributed transactions (Step 7):
 *  - ReferenceOnly: every table is a reference table;
 *  - Repartition: two hash tables in different colocation groups, or a join between hash tables
 *    that does not equate the joined table's key with the key of a hash table already in the
 *    statement (Citus needs a repartition join, off by default);
 *  - Routed: colocated, and the top-level AND chain (no OR) has `key = $var` on one of the
 *    statement's hash tables: that one value pins every table of the group to one bucket;
 *  - AllShards: colocated, but nothing routes (OR, ranges, `?`, no where): every bucket.
 * relations: every table of the statement, hash and reference, in statement order; each is one
 * shard relation per task. Expects CostInputs.check to have passed (every table distributed). */
public final class Routing {

	private Routing() {
	}

	public sealed interface Route permits ReferenceOnly, Repartition, Routed, AllShards {
	}

	public record ReferenceOnly(List<ReferenceTable> relations) implements Route {

		public ReferenceOnly {
			relations = List.copyOf(relations);
		}
	}

	public record Repartition(String reason) implements Route {
	}

	// keyTable: the hash table whose key the condition binds; value: the variable bound to it
	public record Routed(String group, HashTable keyTable, VarRef value, List<TableLayout> relations) implements Route {

		public Routed {
			relations = List.copyOf(relations);
		}
	}

	// any hash table of the group (for its buckets and their nodes)
	public record AllShards(String group, HashTable anyTable, List<TableLayout> relations) implements Route {

		public AllShards {
			relations = List.copyOf(relations);
		}
	}

	public static Route route(Statement statement, Layout layout) {
		List<TableLayout> relations = new ArrayList<>();
		relations.add(table(layout, statement.table()));
		List<HashTable> hashes = new ArrayList<>();
		if (relations.get(0) instanceof HashTable first)
			hashes.add(first);
		for (Join join : statement.joins()) {
			TableLayout joined = table(layout, join.table());
			relations.add(joined);
			if (!(joined instanceof HashTable hash))
				continue;
			if (!hashes.isEmpty()) {
				if (!hash.group().equals(hashes.get(0).group()))
					return new Repartition("join " + statement.table() + " with " + hash.table() + ": colocation groups "
							+ hashes.get(0).group() + " and " + hash.group());
				if (!joinsOnKeys(join, hash, hashes))
					return new Repartition("join " + hash.table() + " not on the distribution columns");
			}
			hashes.add(hash);
		}
		if (hashes.isEmpty())
			return new ReferenceOnly(relations.stream().map(t -> (ReferenceTable) t).toList());
		HashTable any = hashes.get(0);
		if (statement.connectives().stream().noneMatch(c -> c == Connective.OR)) {
			for (Condition condition : statement.conditions()) {
				if (condition.op() != Op.EQ || condition.value() == null)
					continue;
				for (HashTable hash : hashes) {
					if (hash.table().equals(condition.column().table()) && hash.keyColumn().equals(condition.column().column()))
						return new Routed(any.group(), hash, condition.value(), relations);
				}
			}
		}
		return new AllShards(any.group(), any, relations);
	}

	// an `on` pair equating the joined table's key with the key of a hash table already joined
	private static boolean joinsOnKeys(Join join, HashTable joined, List<HashTable> before) {
		for (JoinOn on : join.on()) {
			for (HashTable other : before) {
				if (isKey(on.left(), joined) && isKey(on.right(), other) || isKey(on.right(), joined) && isKey(on.left(), other))
					return true;
			}
		}
		return false;
	}

	private static boolean isKey(Workload.ColumnRef column, HashTable table) {
		return column.table().equals(table.table()) && column.column().equals(table.keyColumn());
	}

	private static TableLayout table(Layout layout, String name) {
		return layout.table(name).orElseThrow(() -> new IllegalArgumentException(
				"Table '" + name + "' is not distributed by layout '" + layout.name() + "'; run CostInputs.check first."));
	}
}
