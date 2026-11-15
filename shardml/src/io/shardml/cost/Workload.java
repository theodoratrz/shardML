package io.shardml.cost;

import java.util.List;

/* A workload as the cost model reads it, free of EMF (built by Workloads.of): params after
 * overrides, domains, table blocks and transactions, every number resolved to its value. The
 * records keep what the file declares, nothing computed per value: child counts, ranks and
 * rows come from RankMapping and RowCounts, so toString stays short and deterministic.
 *
 * Nested, because io.shardml.workload holds EMF classes of the same names. */
public record Workload(String name, List<Param> params, List<Domain> domains, List<TableData> tables,
		List<Transaction> transactions, SchemaView schema) {

	public Workload {
		params = List.copyOf(params);
		domains = List.copyOf(domains);
		tables = List.copyOf(tables);
		transactions = List.copyOf(transactions);
	}

	public Domain domain(String name) {
		return domains.stream().filter(d -> d.name().equals(name)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Workload '" + this.name + "' has no domain '" + name + "'."));
	}

	public java.util.Optional<TableData> table(String name) {
		return tables.stream().filter(t -> t.table().equals(name)).findFirst();
	}

	// value after --set; overridden says whether --set changed it
	public record Param(String name, double value, boolean overridden) {
	}

	// values 1..size; within is null for a top-level domain
	public record Domain(String name, long size, Within within) {
	}

	// a child domain: size * pmf(parent rank) children per parent rank, by largest remainder
	public record Within(String parent, Dist children) {
	}

	public sealed interface Dist permits Uniform, Zipf {
	}

	public record Uniform() implements Dist {
	}

	// pmf(r) = r^-s / sum; s >= 0 (0 is uniform)
	public record Zipf(double s) implements Dist {
	}

	public record TableData(String table, Rows rows, List<ColumnMapping> columns) {

		public TableData {
			columns = List.copyOf(columns);
		}

		public java.util.Optional<ColumnMapping> column(String name) {
			return columns.stream().filter(c -> c.column().equals(name)).findFirst();
		}
	}

	public sealed interface Rows permits PerRows, OverRows {
	}

	// count rows per combination of the domains' values (count 1 when the file writes none)
	public record PerRows(long count, List<String> domains) implements Rows {

		public PerRows {
			domains = List.copyOf(domains);
		}
	}

	// count rows in all, spread over domain's ranks by dist (largest remainder)
	public record OverRows(long count, String domain, Dist dist) implements Rows {
	}

	public sealed interface ColumnMapping permits Mapped, Unique {
		String column();
	}

	// the column holds the value of path: a row domain, then ancestors (account.customer)
	public record Mapped(String column, KeyPath path) implements ColumnMapping {
	}

	// a fresh value per row: 1..rows
	public record Unique(String column) implements ColumnMapping {
	}

	public record KeyPath(String domain, List<String> parents) {

		public KeyPath {
			parents = List.copyOf(parents);
		}

		// the domain whose values the column holds: the last of the path
		public String target() {
			return parents.isEmpty() ? domain : parents.get(parents.size() - 1);
		}
	}

	public record Transaction(String name, double weight, List<Step> steps) {

		public Transaction {
			steps = List.copyOf(steps);
		}
	}

	public sealed interface Step permits Let, Repeat, Statement {
	}

	// id: the let's number in its transaction, in declaration order, depth-first through repeats;
	// a VarRef names it, so a name shadowed in a repeat is never ambiguous
	public sealed interface Let extends Step permits Draw, ElseWith {
		int id();

		String name();

		String domain();
	}

	// `let x = D dist [distinct from $y]`; distinctFrom null when not written
	public record Draw(int id, String name, String domain, Dist dist, VarRef distinctFrom) implements Let {
	}

	// `let x = $base, else with p: D dist`: base's value, except with probability p a draw from D
	// that excludes it
	public record ElseWith(int id, String name, VarRef base, double p, String domain, Dist dist) implements Let {
	}

	// the steps run count times; the lets in them are drawn again each time
	public record Repeat(int count, List<Step> steps) implements Step {

		public Repeat {
			steps = List.copyOf(steps);
		}
	}

	// conditions joined by connectives (one fewer than conditions); no where: both empty
	public record Statement(Kind kind, String table, List<Join> joins, List<Condition> conditions,
			List<Connective> connectives) implements Step {

		public Statement {
			joins = List.copyOf(joins);
			conditions = List.copyOf(conditions);
			connectives = List.copyOf(connectives);
		}
	}

	public enum Kind {
		READ, WRITE, DELETE
	}

	public enum Connective {
		AND, OR
	}

	public record Join(String table, List<JoinOn> on) {

		public Join {
			on = List.copyOf(on);
		}
	}

	public record JoinOn(ColumnRef left, ColumnRef right) {
	}

	// table: always the column's own table, whether or not the file qualified it
	public record ColumnRef(String table, String column) {
	}

	// value null for `?` (bound to nothing)
	public record Condition(ColumnRef column, Op op, VarRef value) {
	}

	public enum Op {
		EQ, NEQ, LT, LE, GT, GE
	}

	// $name[.ancestor]*: let is the Let's id; path the ancestor domains projected to, in order
	public record VarRef(int let, String name, List<String> path) {

		public VarRef {
			path = List.copyOf(path);
		}
	}
}
