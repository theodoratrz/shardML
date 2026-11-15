package io.shardml.cost;

import java.util.List;

import io.shardml.cost.SkewAndLoad.DataSkew;
import io.shardml.cost.SkewAndLoad.Load;
import io.shardml.cost.SkewAndLoad.RepartitionJoin;
import io.shardml.cost.Workload.Param;

/* The cost of a workload under a layout (Plan-phase3 4.3, 4.4): M1 distributed transactions, M2
 * fan-out, M3 data skew, M4 load skew and bottleneck load, the repartition joins (a flag, excluded
 * from M1, M2 and M4), per template, and the params used (after --set). No composite score. */
public record CostReport(String layout, String workload, List<String> nodes, long rankOffset, List<Param> params,
		Distributed distributed, FanOut fanOut, DataSkew data, Load load, List<RepartitionJoin> repartitions,
		List<TransactionCost> transactions) {

	public CostReport {
		nodes = List.copyOf(nodes);
		params = List.copyOf(params);
		repartitions = List.copyOf(repartitions);
		transactions = List.copyOf(transactions);
	}

	// twoPhase: P[the writes touch more than one node]; multiNode: P[all statements do]
	public record Distributed(double twoPhase, double multiNode) {
	}

	// mean tasks per executed statement, the share of them with more than one task, the largest count
	public record FanOut(double mean, double multiShardShare, int max) {
	}

	// probability: weight / total weight; touches: expected shard relations per execution
	public record TransactionCost(String name, double probability, double twoPhase, double multiNode, double fanOutMean,
			int maxFanOut, double touches) {
	}
}
