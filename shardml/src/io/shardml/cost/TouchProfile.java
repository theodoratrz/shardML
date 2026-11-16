package io.shardml.cost;

import java.util.List;
import java.util.Locale;

import io.shardml.cost.SkewAndLoad.NodeValue;

/* Phase 4's model extension (Plan-phase4 1a, P4.0b): the load of SkewAndLoad split by touch kind, and the
 * statements per transaction by kind with their entry-node counts, for the calibrated predictor P1
 * (D_n = sum_kinds touches_n,kind * c_kind + statements_entry(n) * c_plan + ...). Additive: computed by the same
 * walk as SkewAndLoad.Load, which it does not change.
 *  - touches: per kind, per node, shard relations touched per transaction (weighted by the templates'
 *    probabilities, repeats multiplied). A statement's kind is its route's (Routing) and its read / write:
 *    every relation of a routed statement, a reference table joined into it included, is a routed touch.
 *    Summed over the read kinds this is Load.readPerNode, over the write kinds Load.writePerNode (up to
 *    floating-point summation order).
 *  - statements: executed statements per transaction by kind (repeats counted); a repartition join is not a
 *    kind (excluded from the load, as in SkewAndLoad) and is counted in repartitionStatements.
 *  - entryStatements: per kind, per node, the statements each node receives as the entry node when clients
 *    connect to the layout's nodes round-robin (Plan-phase4 2.6, mode (b)): statements / n on every node. In
 *    mode (a) (through the coordinator) the coordinator receives all of them: `statements`.
 *  - twoPhase: the probability a transaction commits with 2PC (CostReport's distributed twoPhase, the same
 *    number); twoPhasePerNode: per node, the probability that it commits with 2PC AND the node is one of its
 *    participants (a node one of its writes touches; DistributedTransactions.participation), P1's
 *    P2PC * participants_n (Plan-phase4 amendment 1, 4). */
public record TouchProfile(String layout, String workload, long rankOffset, List<String> nodes, List<KindNodes> touches,
		List<KindValue> statements, double repartitionStatements, List<KindNodes> entryStatements, double twoPhase,
		List<NodeValue> twoPhasePerNode) {

	public TouchProfile {
		nodes = List.copyOf(nodes);
		touches = List.copyOf(touches);
		statements = List.copyOf(statements);
		entryStatements = List.copyOf(entryStatements);
		twoPhasePerNode = List.copyOf(twoPhasePerNode);
	}

	public record KindNodes(TouchKind kind, List<NodeValue> perNode) {

		public KindNodes {
			perNode = List.copyOf(perNode);
		}

		public double total() {
			double sum = 0;
			for (NodeValue v : perNode)
				sum += v.value();
			return sum;
		}
	}

	public record KindValue(TouchKind kind, double value) {
	}

	public KindNodes touches(TouchKind kind) {
		return touches.get(kind.ordinal());
	}

	public double statements(TouchKind kind) {
		return statements.get(kind.ordinal()).value();
	}

	// touches per transaction on a node, over every kind
	public double touchesOn(String node) {
		int i = nodes.indexOf(node);
		if (i < 0)
			throw new IllegalArgumentException("no node '" + node + "' in layout '" + layout + "'");
		double sum = 0;
		for (KindNodes k : touches)
			sum += k.perNode().get(i).value();
		return sum;
	}

	public double totalStatements() {
		double sum = 0;
		for (KindValue v : statements)
			sum += v.value();
		return sum;
	}

	// the profile as text, 6 decimals, Locale.ROOT: byte-identical for equal inputs
	public String format() {
		StringBuilder out = new StringBuilder();
		out.append("layout ").append(layout).append(", workload ").append(workload).append(", rank offset ").append(rankOffset)
				.append('\n');
		out.append(String.format(Locale.ROOT, "%-16s", "touches/txn"));
		for (String n : nodes)
			out.append(String.format(Locale.ROOT, " %14s", n));
		out.append(String.format(Locale.ROOT, " %14s%n", "total"));
		for (KindNodes k : touches)
			row(out, k.kind().label(), k.perNode(), k.total());
		out.append(String.format(Locale.ROOT, "%-16s", "statements/txn"));
		for (String n : nodes)
			out.append(String.format(Locale.ROOT, " %14s", "entry " + n));
		out.append(String.format(Locale.ROOT, " %14s%n", "total"));
		for (KindNodes k : entryStatements)
			row(out, k.kind().label(), k.perNode(), statements(k.kind()));
		out.append(String.format(Locale.ROOT, "%-16s %s%n", "repartition", SkewAndLoad.num(repartitionStatements)));
		out.append(String.format(Locale.ROOT, "%-16s", "2PC participant"));
		for (NodeValue v : twoPhasePerNode)
			out.append(String.format(Locale.ROOT, " %14s", SkewAndLoad.num(v.value())));
		out.append(String.format(Locale.ROOT, " %14s%n", "2PC " + SkewAndLoad.num(twoPhase)));
		return out.toString();
	}

	private static void row(StringBuilder out, String label, List<NodeValue> values, double total) {
		out.append(String.format(Locale.ROOT, "%-16s", label));
		for (NodeValue v : values)
			out.append(String.format(Locale.ROOT, " %14s", SkewAndLoad.num(v.value())));
		out.append(String.format(Locale.ROOT, " %14s%n", SkewAndLoad.num(total)));
	}

	// JSON, the same numbers (6 decimals), for the calibration harness and the run manifest
	public String json() {
		CostReportFormat.Json out = new CostReportFormat.Json();
		out.open();
		out.field("layout").string(layout);
		out.field("workload").string(workload);
		out.field("rank_offset").raw(Long.toString(rankOffset));
		out.field("nodes").strings(nodes);
		out.field("touches");
		kinds(out, touches);
		out.field("statements").open();
		for (KindValue v : statements)
			out.field(v.kind().key()).number(v.value());
		out.close();
		out.field("repartition_statements").number(repartitionStatements);
		out.field("entry_statements");
		kinds(out, entryStatements);
		out.field("two_phase").number(twoPhase);
		out.field("two_phase_participation").open();
		for (NodeValue v : twoPhasePerNode)
			out.field(v.node()).number(v.value());
		out.close();
		out.close();
		return out.toString();
	}

	private static void kinds(CostReportFormat.Json out, List<KindNodes> kinds) {
		out.open();
		for (KindNodes k : kinds) {
			out.field(k.kind().key()).open();
			for (NodeValue v : k.perNode())
				out.field(v.node()).number(v.value());
			out.close();
		}
		out.close();
	}
}
