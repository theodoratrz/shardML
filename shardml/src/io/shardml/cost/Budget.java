package io.shardml.cost;

/* The enumeration budget (Plan-phase3 4.3a, 4.4c 5): steps = values visited, counted exactly from the
 * workload's structure before any array is built, so a workload over the limit is an error before it
 * costs anything, and never sampled or approximated. Per template (a transaction, or a table's data
 * skew) and per evaluation. */
public final class Budget {

	public static final long STEPS_PER_TEMPLATE = 200_000_000L;
	public static final long STEPS_PER_EVALUATION = 1_000_000_000L;

	private final long perTemplate;
	private final long perEvaluation;
	private long total;

	public Budget(long perTemplate, long perEvaluation) {
		this.perTemplate = perTemplate;
		this.perEvaluation = perEvaluation;
	}

	// what: "transaction 'x'" or "table 'y' (data skew)"
	public void charge(String what, long steps) {
		if (steps > perTemplate)
			throw new IllegalArgumentException("The cost of " + what + " needs " + steps + " steps; the limit is " + perTemplate
					+ " per template.");
		total += steps;
		if (total > perEvaluation)
			throw new IllegalArgumentException("The evaluation needs " + total + " steps (" + what + " brings it over); the "
					+ "limit is " + perEvaluation + " per evaluation.");
	}

	public long total() {
		return total;
	}
}
