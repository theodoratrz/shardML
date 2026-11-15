package io.shardml.cost;

import java.util.List;
import java.util.Locale;

/* Step 6's results (Plan-phase3 4.3 M3, M4; 4.4c): data skew, load skew and bottleneck load of a
 * workload under a layout. Step 7 composes these with fan-out and distributed transactions into the
 * full CostReport. Nodes in declaration order; a max goes to the first node that has it. */
public record SkewAndLoad(String layout, String workload, long rankOffset, List<String> nodes, DataSkew data, Load load,
		List<RepartitionJoin> repartitions) {

	public SkewAndLoad {
		nodes = List.copyOf(nodes);
		repartitions = List.copyOf(repartitions);
	}

	// rows per node of the hash tables (reference tables excluded), max / mean over every declared
	// node; tables: every hash table, and every reference table the workload gives rows to (on every node)
	public record DataSkew(double maxOverMean, String maxNode, List<NodeRows> rowsPerNode, List<TableRows> tables) {

		public DataSkew {
			rowsPerNode = List.copyOf(rowsPerNode);
			tables = List.copyOf(tables);
		}
	}

	public record TableRows(String table, boolean reference, List<NodeRows> rowsPerNode) {

		public TableRows {
			rowsPerNode = List.copyOf(rowsPerNode);
		}
	}

	public record NodeRows(String node, long rows) {
	}

	// touches per transaction: shard relations a statement touches on a node, weighted by the
	// templates' probabilities. total: the sum over nodes. maxOverMean is 0 when nothing adds load
	public record Load(double total, double bottleneck, double maxOverMean, String maxNode, List<NodeValue> perNode,
			List<NodeValue> readPerNode, List<NodeValue> writePerNode) {

		public Load {
			perNode = List.copyOf(perNode);
			readPerNode = List.copyOf(readPerNode);
			writePerNode = List.copyOf(writePerNode);
		}
	}

	public record NodeValue(String node, double value) {
	}

	// a statement excluded from the load (and from fan-out): statement is its 1-based position in the
	// transaction, repeats included; probability the template's; times its repeat multiplicity
	public record RepartitionJoin(String transaction, int statement, String table, String reason, double probability,
			long times) {
	}

	// the report as text, 6 decimals, Locale.ROOT: byte-identical for equal inputs
	public String format() {
		StringBuilder out = new StringBuilder();
		out.append("layout ").append(layout).append(", workload ").append(workload).append(", rank offset ").append(rankOffset)
				.append('\n');
		out.append("data skew ").append(num(data.maxOverMean())).append(" (max on ").append(data.maxNode()).append(")\n");
		for (NodeRows r : data.rowsPerNode())
			out.append("  rows ").append(r.node()).append(' ').append(r.rows()).append('\n');
		for (TableRows t : data.tables()) {
			out.append("  table ").append(t.table()).append(t.reference() ? " (reference)" : "");
			for (NodeRows r : t.rowsPerNode())
				out.append(' ').append(r.node()).append('=').append(r.rows());
			out.append('\n');
		}
		out.append("load total ").append(num(load.total())).append(", bottleneck ").append(num(load.bottleneck())).append(" on ")
				.append(load.maxNode()).append(", skew ").append(num(load.maxOverMean())).append('\n');
		for (int i = 0; i < load.perNode().size(); i++) {
			out.append("  load ").append(load.perNode().get(i).node()).append(' ').append(num(load.perNode().get(i).value()))
					.append(" read ").append(num(load.readPerNode().get(i).value())).append(" write ")
					.append(num(load.writePerNode().get(i).value())).append('\n');
		}
		for (RepartitionJoin r : repartitions) {
			out.append("repartition ").append(r.transaction()).append(" #").append(r.statement()).append(' ').append(r.table())
					.append(" p=").append(num(r.probability())).append(" x").append(r.times()).append(": ").append(r.reason())
					.append('\n');
		}
		return out.toString();
	}

	static String num(double value) {
		return String.format(Locale.ROOT, "%.6f", value);
	}
}
