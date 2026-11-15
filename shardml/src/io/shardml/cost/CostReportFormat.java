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

	// --- the shardml cost command's output (Plan-phase3 4.5a) ---

	// the modelling assumptions, printed under every table
	public static final List<String> ASSUMPTIONS = List.of(
			"the shard of a key is Citus's own hash (worker_hash) for SMALLINT, INTEGER and BIGINT keys, reproduced exactly",
			"rank r of a distribution is value r, rotated by the rank offset for top-level domains; a child domain's values "
					+ "follow its parent's rank order",
			"load is counted in shard-relation touches per transaction, unweighted",
			"a reference-table read adds 1/n to every node; a reference-table write adds 1 to every node (it commits on all)",
			"a repartition join is flagged and left out of fan-out, distributed transactions and load",
			"no composite score: the metrics are reported side by side");

	// a single report: header, metrics, per-node vectors, per template, repartitions, assumptions
	public static String report(CostReport r) {
		StringBuilder out = new StringBuilder();
		out.append("shardml cost: ").append(r.layout()).append(" x ").append(r.workload()).append('\n');
		out.append("nodes: ").append(String.join(", ", r.nodes())).append('\n');
		out.append("params: ").append(params(r)).append("; rank offset ").append(r.rankOffset()).append("\n\n");
		row(out, "metric", "value", "note");
		row(out, "distributed txns (2PC)", num(r.distributed().twoPhase()), "");
		row(out, "multi-node txns", num(r.distributed().multiNode()), "");
		row(out, "fan-out (mean tasks/stmt)", num(r.fanOut().mean()),
				"multi-shard " + num(r.fanOut().multiShardShare()) + ", max " + r.fanOut().max());
		row(out, "data skew (max/mean rows)", num(r.data().maxOverMean()), "max on " + r.data().maxNode());
		row(out, "load skew (max/mean touches)", num(r.load().maxOverMean()), "max on " + r.load().maxNode());
		row(out, "bottleneck load (touches/txn)", num(r.load().bottleneck()), "on " + r.load().maxNode());
		row(out, "repartition joins", r.repartitions().isEmpty() ? "none" : String.valueOf(r.repartitions().size()),
				r.repartitions().isEmpty() ? "" : "need citus.enable_repartition_joins");
		out.append('\n');
		out.append(String.format(Locale.ROOT, "%-12s %14s %14s %14s %14s%n", "node", "rows", "read load", "write load",
				"total load"));
		for (int i = 0; i < r.nodes().size(); i++) {
			out.append(String.format(Locale.ROOT, "%-12s %14d %14s %14s %14s%n", r.nodes().get(i),
					r.data().rowsPerNode().get(i).rows(), num(r.load().readPerNode().get(i).value()),
					num(r.load().writePerNode().get(i).value()), num(r.load().perNode().get(i).value())));
		}
		out.append('\n');
		out.append(String.format(Locale.ROOT, "%-16s %10s %10s %10s %10s %6s %10s%n", "transaction", "p", "2PC", "multi-node",
				"fan-out", "max", "touches"));
		for (TransactionCost t : r.transactions()) {
			out.append(String.format(Locale.ROOT, "%-16s %10s %10s %10s %10s %6d %10s%n", t.name(), num(t.probability()),
					num(t.twoPhase()), num(t.multiNode()), num(t.fanOutMean()), t.maxFanOut(), num(t.touches())));
		}
		if (!r.repartitions().isEmpty())
			out.append('\n');
		for (RepartitionJoin j : r.repartitions()) {
			out.append("repartition join: ").append(j.transaction()).append(" statement ").append(j.statement()).append(" (")
					.append(j.table()).append("), p ").append(num(j.probability())).append(" x").append(j.times()).append(": ")
					.append(j.reason()).append('\n');
		}
		return out.append(assumptions()).toString();
	}

	// two or more reports of one workload: metric rows x layout columns, no ranking
	public static String comparison(List<CostReport> reports) {
		StringBuilder out = new StringBuilder();
		CostReport first = reports.get(0);
		out.append("shardml cost: ").append(reports.size()).append(" layouts x ").append(first.workload()).append('\n');
		out.append("params: ").append(params(first)).append("; rank offset ").append(first.rankOffset()).append("\n\n");
		List<String[]> rows = new ArrayList<>();
		String[] header = new String[reports.size() + 1];
		header[0] = "metric";
		for (int i = 0; i < reports.size(); i++)
			header[i + 1] = reports.get(i).layout();
		rows.add(header);
		for (String metric : METRICS) {
			String[] row = new String[reports.size() + 1];
			row[0] = metric;
			for (int i = 0; i < reports.size(); i++)
				row[i + 1] = metric(reports.get(i), metric);
			rows.add(row);
		}
		appendColumns(out, rows);
		return out.append(assumptions()).toString();
	}

	public static String comparisonCsv(List<CostReport> reports) {
		StringBuilder out = new StringBuilder("layout,metric,value\n");
		for (CostReport r : reports) {
			for (String metric : METRICS)
				out.append(csv(r.layout())).append(',').append(metric).append(',').append(csv(metric(r, metric))).append('\n');
		}
		return out.toString();
	}

	// one row per (value, layout), values in the order given
	public static String sweep(List<SweepPoint> points) {
		return sweep(points, false);
	}

	// offsetColumn: a param and the rank offset are both swept; each row also names its rank offset
	public static String sweep(List<SweepPoint> points, boolean offsetColumn) {
		StringBuilder out = new StringBuilder();
		SweepPoint first = points.get(0);
		out.append("shardml cost: sweep of ").append(offsetColumn ? "rank-offset and " : "").append(first.param()).append(" x ")
				.append(first.report().workload());
		if (!offsetColumn && !first.param().equals("rank-offset"))
			out.append("; rank offset ").append(first.report().rankOffset());
		out.append("\n\n");
		List<String[]> rows = new ArrayList<>();
		List<String> header = new ArrayList<>(offsetColumn ? List.of("rank_offset") : List.of());
		header.addAll(List.of(first.param(), "layout"));
		header.addAll(SWEEP_METRICS);
		rows.add(header.toArray(String[]::new));
		for (SweepPoint p : points) {
			List<String> row = new ArrayList<>(offsetColumn ? List.of(String.valueOf(p.report().rankOffset())) : List.of());
			row.addAll(List.of(p.value(), p.report().layout()));
			for (String metric : SWEEP_METRICS)
				row.add(metric(p.report(), metric));
			rows.add(row.toArray(String[]::new));
		}
		appendColumns(out, rows);
		return out.append(assumptions()).toString();
	}

	public static String sweepCsv(List<SweepPoint> points) {
		return sweepCsv(points, false);
	}

	public static String sweepCsv(List<SweepPoint> points, boolean offsetColumn) {
		StringBuilder out = new StringBuilder(offsetColumn ? "rank_offset," : "").append("param,param_value,layout,metric,value\n");
		for (SweepPoint p : points) {
			for (String metric : METRICS)
				out.append(offsetColumn ? p.report().rankOffset() + "," : "").append(csv(p.param())).append(',').append(csv(p.value())).append(',').append(csv(p.report().layout()))
						.append(',').append(metric).append(',').append(csv(metric(p.report(), metric))).append('\n');
		}
		return out.toString();
	}

	// several reports as one JSON array
	public static String jsonArray(List<CostReport> reports) {
		StringBuilder out = new StringBuilder("[\n");
		for (int i = 0; i < reports.size(); i++) {
			String json = json(reports.get(i)).stripTrailing();
			out.append(json.indent(2).stripTrailing());
			out.append(i + 1 < reports.size() ? ",\n" : "\n");
		}
		return out.append("]\n").toString();
	}

	// the metrics of comparisons and CSV, in this order
	public static final List<String> METRICS = List.of("distributed_2pc", "multi_node", "fan_out_mean", "multi_shard_share",
			"fan_out_max", "data_skew", "data_skew_node", "load_skew", "load_skew_node", "bottleneck", "bottleneck_node",
			"total_touches", "repartition_joins");

	private static final List<String> SWEEP_METRICS = List.of("distributed_2pc", "multi_node", "fan_out_mean", "data_skew",
			"load_skew", "bottleneck", "bottleneck_node", "repartition_joins");

	public static String metric(CostReport r, String metric) {
		return switch (metric) {
			case "distributed_2pc" -> num(r.distributed().twoPhase());
			case "multi_node" -> num(r.distributed().multiNode());
			case "fan_out_mean" -> num(r.fanOut().mean());
			case "multi_shard_share" -> num(r.fanOut().multiShardShare());
			case "fan_out_max" -> String.valueOf(r.fanOut().max());
			case "data_skew" -> num(r.data().maxOverMean());
			case "data_skew_node" -> r.data().maxNode();
			case "load_skew" -> num(r.load().maxOverMean());
			case "load_skew_node" -> r.load().maxNode();
			case "bottleneck" -> num(r.load().bottleneck());
			case "bottleneck_node" -> r.load().maxNode();
			case "total_touches" -> num(r.load().total());
			case "repartition_joins" -> String.valueOf(r.repartitions().size());
			default -> throw new IllegalArgumentException("unknown metric " + metric);
		};
	}

	private static String params(CostReport r) {
		if (r.params().isEmpty())
			return "none";
		List<String> params = new ArrayList<>();
		for (Param p : r.params())
			params.add(p.name() + "=" + plain(p.value()) + (p.overridden() ? " (set)" : ""));
		return String.join(", ", params);
	}

	private static String assumptions() {
		StringBuilder out = new StringBuilder("\nassumptions:\n");
		for (String a : ASSUMPTIONS)
			out.append("  - ").append(a).append('\n');
		return out.toString();
	}

	// left-aligned columns, each as wide as its widest cell, two spaces apart
	private static void appendColumns(StringBuilder out, List<String[]> rows) {
		int[] width = new int[rows.get(0).length];
		for (String[] row : rows) {
			for (int i = 0; i < row.length; i++)
				width[i] = Math.max(width[i], row[i].length());
		}
		for (String[] row : rows) {
			StringBuilder line = new StringBuilder();
			for (int i = 0; i < row.length; i++)
				line.append(String.format(Locale.ROOT, "%-" + width[i] + "s", row[i])).append(i + 1 < row.length ? "  " : "");
			out.append(line.toString().stripTrailing()).append('\n');
		}
	}

	// a CSV field: quoted only when it holds a comma, a quote or a line break
	private static String csv(String field) {
		if (field.indexOf(',') < 0 && field.indexOf(Json.QUOTE) < 0 && field.indexOf('\n') < 0)
			return field;
		String q = String.valueOf(Json.QUOTE);
		return q + field.replace(q, q + q) + q;
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

	// 6 decimals, Locale.ROOT; never a negative zero (-0.000000 for a value that rounds to 0)
	static String num(double value) {
		String text = String.format(Locale.ROOT, "%.6f", value);
		return text.equals("-0.000000") ? "0.000000" : text;
	}

	// a param as written: no trailing zeros beyond what it needs
	private static String plain(double value) {
		return java.math.BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
	}

	// a small JSON writer: two-space indentation, one value per line, commas where they belong (also
	// TouchProfile's)
	static final class Json {
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
