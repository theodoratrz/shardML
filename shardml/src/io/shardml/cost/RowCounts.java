package io.shardml.cost;

import io.shardml.cost.Workload.ColumnMapping;
import io.shardml.cost.Workload.Mapped;
import io.shardml.cost.Workload.OverRows;
import io.shardml.cost.Workload.PerRows;
import io.shardml.cost.Workload.TableData;

/* The rows of a table block per value of one of its columns: what data skew sums per bucket.
 *  - `rows: k per D1, .., Dn`: each value of Di carries k * product of the other sizes rows;
 *  - `rows: N over D dist`: N rows split over D's ranks by largest remainder, rank r being value
 *    RankMapping.value(D, r);
 *  - a mapped column takes the row domain's value through its key path (account.customer);
 *  - a `unique` column holds 1..total, one row each. */
public final class RowCounts {

	@FunctionalInterface
	public interface ValueRows {
		void accept(long value, long rows);
	}

	private final Workload workload;
	private final RankMapping mapping;
	private final TableData data;

	private RowCounts(Workload workload, RankMapping mapping, TableData data) {
		this.workload = workload;
		this.mapping = mapping;
		this.data = data;
	}

	public static RowCounts of(Workload workload, RankMapping mapping, String table) {
		TableData data = workload.table(table).orElseThrow(
				() -> new IllegalArgumentException("Workload '" + workload.name() + "' has no table block for '" + table + "'."));
		return new RowCounts(workload, mapping, data);
	}

	public long total() {
		return total(workload, data);
	}

	static long total(Workload workload, TableData data) {
		if (data.rows() instanceof OverRows over)
			return over.count();
		PerRows per = (PerRows) data.rows();
		long total = per.count();
		for (String domain : per.domains())
			total = Math.multiplyExact(total, workload.domain(domain).size());
		return total;
	}

	// (value, rows) for every value of the column holding at least one row, ascending
	public void forEachKeyValue(String column, ValueRows consumer) {
		ColumnMapping mapping = data.column(column).orElseThrow(() -> new IllegalArgumentException(
				"The table block of '" + data.table() + "' does not map column " + column + "."));
		if (!(mapping instanceof Mapped mapped)) {
			long total = total();
			for (long v = 1; v <= total; v++)
				consumer.accept(v, 1);
			return;
		}
		String target = mapped.path().target();
		long[] rows = new long[(int) this.mapping.size(target) + 1];
		String rowDomain = mapped.path().domain();
		if (data.rows() instanceof PerRows per) {
			long each = per.count();
			for (String domain : per.domains()) {
				if (!domain.equals(rowDomain))
					each = Math.multiplyExact(each, workload.domain(domain).size());
			}
			long size = this.mapping.size(rowDomain);
			java.util.function.LongUnaryOperator projector = this.mapping.projector(mapped.path());
			for (long v = 1; v <= size; v++)
				rows[(int) projector.applyAsLong(v)] += each;
		} else {
			OverRows over = (OverRows) data.rows();
			long[] counts = LargestRemainder.apportion(over.count(),
					RankMapping.weights((int) this.mapping.size(over.domain()), over.dist()));
			java.util.function.LongUnaryOperator projector = this.mapping.projector(mapped.path());
			for (int r = 1; r <= counts.length; r++) {
				long value = this.mapping.value(over.domain(), r);
				rows[(int) projector.applyAsLong(value)] += counts[r - 1];
			}
		}
		for (int v = 1; v < rows.length; v++) {
			if (rows[v] > 0)
				consumer.accept(v, rows[v]);
		}
	}
}
