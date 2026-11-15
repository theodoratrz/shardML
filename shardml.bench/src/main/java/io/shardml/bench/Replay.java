package io.shardml.bench;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import io.shardml.cost.Layout;
import io.shardml.cost.RankMapping;
import io.shardml.cost.Sampler;
import io.shardml.cost.StatementSql;
import io.shardml.cost.Workload;
import io.shardml.cost.Workload.Kind;
import io.shardml.cost.Workload.Transaction;

/* A workload under one layout, ready to replay (Plan-phase4 2.2): the template mix (weight / total),
 * the model's Sampler for the bindings, and each statement's SQL from StatementSql (the SQL `shardml
 * route` checks against Citus). One transaction run consumes from the client's draw stream: one
 * uniform for the template, then the Sampler's draws; the layout only chooses the SQL text, so the
 * sequence of templates and values is the same under every layout. Deletes are refused: a replayed
 * delete removes its rows for the rest of the run, so later statements would not see the snapshot the
 * model describes (none of the examples has one). */
final class Replay {

	// one statement execution: the statement's index in its transaction and its parameters
	record Execution(int statement, long[] parameters) {
	}

	// one transaction run: the template (transaction) index and its executions in order
	record Run(int template, List<Execution> executions) {
	}

	private final Workload workload;
	private final List<Transaction> transactions;
	private final Mix mix;
	private final Sampler sampler;
	private final List<List<StatementSql.Template>> templates = new ArrayList<>();

	Replay(Workload workload, Layout layout, RankMapping mapping) {
		this.workload = workload;
		this.transactions = workload.transactions();
		this.mix = new Mix(transactions.stream().mapToDouble(Transaction::weight).toArray());
		this.sampler = Sampler.of(workload, mapping);
		for (Transaction t : transactions) {
			List<StatementSql.Template> list = StatementSql.templates(t, layout, mapping);
			for (StatementSql.Template template : list) {
				if (template.statement().kind() == Kind.DELETE)
					throw new IllegalArgumentException("Transaction '" + t.name() + "' has a delete on table '"
							+ template.statement().table() + "': the driver cannot replay a delete exactly (it removes rows "
							+ "from the snapshot the model describes).");
			}
			templates.add(list);
		}
	}

	Workload workload() {
		return workload;
	}

	List<Transaction> transactions() {
		return transactions;
	}

	Mix mix() {
		return mix;
	}

	Sampler sampler() {
		return sampler;
	}

	List<StatementSql.Template> templates(int transaction) {
		return templates.get(transaction);
	}

	Run next(RandomGenerator random) {
		int t = mix.draw(random);
		List<StatementSql.Template> list = templates.get(t);
		List<Execution> executions = new ArrayList<>();
		sampler.run(transactions.get(t).name(), random, new Sampler.Visitor() {
			@Override
			public void statement(int index, long[] values) {
				executions.add(new Execution(index, list.get(index).parameters(values)));
			}
		});
		return new Run(t, executions);
	}
}
