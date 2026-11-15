package io.shardml.bench;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.shardml.command.Inputs;

// the example workloads and layouts, loaded as the driver loads them (warnings discarded)
final class Examples {

	static final String COST = "../examples/cost/";

	private Examples() {
	}

	static Inputs.Loaded load(String workload, String... models) {
		return load(Map.of(), workload, models);
	}

	static Inputs.Loaded load(Map<String, String> sets, String workload, String... models) {
		return Inputs.load(new File(workload), Arrays.stream(models).map(File::new).toList(), sets,
				new PrintStream(OutputStream.nullOutputStream()));
	}

	static Inputs.Loaded banking() {
		return load(COST + "banking.workload", COST + "banking-by-customer.shardml", COST + "banking-by-account.shardml");
	}

	static Inputs.Loaded tpcc() {
		return load(COST + "tpcc.workload", COST + "tpcc-by-warehouse.shardml", COST + "tpcc-stock-by-item.shardml");
	}

	static List<String> args(String... a) {
		return List.of(a);
	}
}
