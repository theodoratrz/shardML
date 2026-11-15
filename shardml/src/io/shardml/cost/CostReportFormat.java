package io.shardml.cost;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.shardml.cost.CostReport.TransactionCost;
import io.shardml.cost.SkewAndLoad.NodeRows;
import io.shardml.cost.SkewAndLoad.NodeValue;
import io.shardml.cost.SkewAndLoad.RepartitionJoin;
import io.shardml.cost.SkewAndLoad.TableRows;
import io.shardml.cost.Workload.Param;

/* A CostReport as text: a readable table, or JSON. Hand-written, like the generator's JSON: numbers
 * with 6 decimals in Locale.ROOT, fields and lists in a fixed order, so equal reports give
 * byte-identical output. */
public final class CostReportFormat {

	private CostReportFormat() {
	}

	public static String table(CostReport r) {
		StringBuilder out = new StringBuilder();
		out.append("shardml cost: ").append(r.layout()).append(" x ").append(r.workload()).append("   nodes: ")
				.append(String.join(", ", r.nodes())).append("   rank offset: ").append(r.rankOffset()).append('\n');
		row(out, "metric", "value", "note");
		row(out, "distributed txns (2PC)", num(r.distributed().twoPhase()), "");
		row(out, "multi-node txns", num(r.distributed().multiNode()), "");
		row(out, "fan-out (mean tasks/stmt)", num(r.fanOut().mean()),
				"multi-shard " + num(r.fanOut().multiShardShare()) + ", max " + r.fanOut().max());
		row(out, "data skew (max/mean rows)", num(r.data().maxOverMean()), "max on " + r.data().maxNode());
		row(out, "bottleneck load (touches/txn)", num(r.load().bottleneck()), "on " + r.load().maxNode());
		row(out, "load skew (max/mean touches)", num(r.load().maxOverMean()), "max on " + r.load().maxNode());
		row(out, "repartition joins", r.repartitions().isEmpty() ? "none" : String.valueOf(r.repartitions().size()),
				r.repartitions().isEmpty() ? "" : "needs citus.enable_repartition_joins");
		if (!r.params().isEmpty()) {
			List<String> params = new ArrayList<>();
			for (Param p : r.params())
				params.add(p.name() + "=" + plain(p.value()) + (p.overridden() ? " (set)" : ""));
			out.append("params: ").append(String.join(", ", params)).append('\n');
		}
		out.append('\n');
		out.append(String.format(Locale.ROOT, "%-12s %14s %14s %14s %14s%n", "node", "rows", "load", "read", "write"));
		for (int i = 0; i < r.nodes().size(); i++) {
			out.append(String.format(Locale.ROOT, "%-12s %14d %14s %14s %14s%n", r.nodes().get(i),
					r.data().rowsPerNode().get(i).rows(), num(r.load().perNode().get(i).value()),
					num(r.load().readPerNode().get(i).value()), num(r.load().writePerNode().get(i).value())));
		}
		out.append('\n');
		out.append(String.format(Locale.ROOT, "%-16s %10s %10s %10s %10s %6s %10s%n", "transaction", "p", "2PC", "multi-node",
				"fan-out", "max", "touches"));
		for (TransactionCost t : r.transactions()) {
			out.append(String.format(Locale.ROOT, "%-16s %10s %10s %10s %10s %6d %10s%n", t.name(), num(t.probability()),
					num(t.twoPhase()), num(t.multiNode()), num(t.fanOutMean()), t.maxFanOut(), num(t.touches())));
		}
		for (RepartitionJoin j : r.repartitions()) {
			out.append("repartition: ").append(j.transaction()).append(" statement ").append(j.statement()).append(" (")
					.append(j.table()).append("), p ").append(num(j.probability())).append(" x").append(j.times()).append(": ")
					.append(j.reason()).append('\n');
		}
		return out.toString();
	}

	private static void row(StringBuilder out, String metric, String value, String note) {
		out.append(String.format(Locale.ROOT, "%-31s %-9s %s", metric, value, note).stripTrailing()).append('\n');
	}

	public static String json(CostReport r) {
		Json j = new Json();
		j.open();
		j.field("layout").string(r.layout());
		j.field("workload").string(r.workload());
		j.field("nodes").strings(r.nodes());
		j.field("rankOffset").raw(String.valueOf(r.rankOffset()));
		j.field("params").openArray();
		for (Param p : r.params()) {
			j.open();
			j.field("name").string(p.name());
			j.field("value").number(p.value());
			j.field("overridden").raw(String.valueOf(p.overridden()));
			j.close();
		}
		j.closeArray();
		j.field("distributed").open();
		j.field("twoPhase").number(r.distributed().twoPhase());
		j.field("multiNode").number(r.distributed().multiNode());
		j.close();
		j.field("fanOut").open();
		j.field("mean").number(r.fanOut().mean());
		j.field("multiShardShare").number(r.fanOut().multiShardShare());
		j.field("max").raw(String.valueOf(r.fanOut().max()));
		j.close();
		j.field("dataSkew").open();
		j.field("maxOverMean").number(r.data().maxOverMean());
		j.field("maxNode").string(r.data().maxNode());
		j.field("rowsPerNode").openArray();
		for (NodeRows n : r.data().rowsPerNode())
			nodeRows(j, n);
		j.closeArray();
		j.field("tables").openArray();
		for (TableRows t : r.data().tables()) {
			j.open();
			j.field("table").string(t.table());
			j.field("reference").raw(String.valueOf(t.reference()));
			j.field("rowsPerNode").openArray();
			for (NodeRows n : t.rowsPerNode())
				nodeRows(j, n);
			j.closeArray();
			j.close();
		}
		j.closeArray();
		j.close();
		j.field("load").open();
		j.field("total").number(r.load().total());
		j.field("bottleneck").number(r.load().bottleneck());
		j.field("maxOverMean").number(r.load().maxOverMean());
		j.field("maxNode").string(r.load().maxNode());
		nodeValues(j, "perNode", r.load().perNode());
		nodeValues(j, "readPerNode", r.load().readPerNode());
		nodeValues(j, "writePerNode", r.load().writePerNode());
		j.close();
		j.field("repartitions").openArray();
		for (RepartitionJoin rj : r.repartitions()) {
			j.open();
			j.field("transaction").string(rj.transaction());
			j.field("statement").raw(String.valueOf(rj.statement()));
			j.field("table").string(rj.table());
			j.field("reason").string(rj.reason());
			j.field("probability").number(rj.probability());
			j.field("times").raw(String.valueOf(rj.times()));
			j.close();
		}
		j.closeArray();
		j.field("transactions").openArray();
		for (TransactionCost t : r.transactions()) {
			j.open();
			j.field("name").string(t.name());
			j.field("probability").number(t.probability());
			j.field("twoPhase").number(t.twoPhase());
			j.field("multiNode").number(t.multiNode());
			j.field("fanOutMean").number(t.fanOutMean());
			j.field("maxFanOut").raw(String.valueOf(t.maxFanOut()));
			j.field("touches").number(t.touches());
			j.close();
		}
		j.closeArray();
		j.close();
		return j.toString();
	}

	private static void nodeRows(Json j, NodeRows n) {
		j.open();
		j.field("node").string(n.node());
		j.field("rows").raw(String.valueOf(n.rows()));
		j.close();
	}

	private static void nodeValues(Json j, String name, List<NodeValue> values) {
		j.field(name).openArray();
		for (NodeValue v : values) {
			j.open();
			j.field("node").string(v.node());
			j.field("value").number(v.value());
			j.close();
		}
		j.closeArray();
	}

	static String num(double value) {
		return String.format(Locale.ROOT, "%.6f", value);
	}

	// a param as written: no trailing zeros beyond what it needs
	private static String plain(double value) {
		return java.math.BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
	}

	// a small JSON writer: two-space indentation, one value per line, commas where they belong
	private static final class Json {
		private static final char QUOTE = '"';
		private final StringBuilder out = new StringBuilder();
		private int depth;
		private boolean first = true;
		private boolean afterField;

		void open() {
			value("{");
			depth++;
			first = true;
		}

		void close() {
			depth--;
			out.append('\n').append("  ".repeat(depth)).append('}');
			first = false;
		}

		void openArray() {
			value("[");
			depth++;
			first = true;
		}

		void closeArray() {
			depth--;
			if (first)
				out.append(']');
			else
				out.append('\n').append("  ".repeat(depth)).append(']');
			first = false;
		}

		Json field(String name) {
			separate();
			out.append(QUOTE).append(name).append(QUOTE).append(": ");
			afterField = true;
			return this;
		}

		void string(String s) {
			StringBuilder e = new StringBuilder().append(QUOTE);
			for (char c : s.toCharArray()) {
				switch (c) {
					case '\\' -> e.append("\\\\");
					case '\n' -> e.append("\\n");
					case '\t' -> e.append("\\t");
					case '\r' -> e.append("\\r");
					default -> {
						if (c == QUOTE)
							e.append('\\').append(QUOTE);
						else if (c < 0x20)
							e.append(String.format(Locale.ROOT, "\\u%04x", (int) c));
						else
							e.append(c);
					}
				}
			}
			value(e.append(QUOTE).toString());
		}

		void strings(List<String> values) {
			openArray();
			for (String v : values)
				string(v);
			closeArray();
		}

		void number(double v) {
			value(num(v));
		}

		void raw(String v) {
			value(v);
		}

		private void value(String text) {
			if (!afterField)
				separate();
			afterField = false;
			out.append(text);
			first = false;
		}

		private void separate() {
			if (depth == 0 && out.length() == 0)
				return;
			if (!first)
				out.append(',');
			out.append('\n').append("  ".repeat(depth));
		}

		@Override
		public String toString() {
			return out.append('\n').toString();
		}
	}
}
