package io.shardml.cost;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.xtext.EcoreUtil2;

import io.shardml.cost.Workload.ColumnMapping;
import io.shardml.cost.Workload.ColumnRef;
import io.shardml.cost.Workload.Condition;
import io.shardml.cost.Workload.Connective;
import io.shardml.cost.Workload.Dist;
import io.shardml.cost.Workload.Join;
import io.shardml.cost.Workload.JoinOn;
import io.shardml.cost.Workload.Kind;
import io.shardml.cost.Workload.Op;
import io.shardml.cost.Workload.Rows;
import io.shardml.cost.Workload.Step;
import io.shardml.cost.Workload.VarRef;
import io.shardml.schema.SchemaFacts;
import io.shardml.schema.SqlNames;
import io.shardml.scoping.ImportedSchema;
import io.shardml.sql.Column;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.workload.Distribution;
import io.shardml.workload.Domain;
import io.shardml.workload.Let;
import io.shardml.workload.MappedColumn;
import io.shardml.workload.Num;
import io.shardml.workload.OverRows;
import io.shardml.workload.Param;
import io.shardml.workload.PerRows;
import io.shardml.workload.Repeat;
import io.shardml.workload.Statement;
import io.shardml.workload.TableData;
import io.shardml.workload.Transaction;
import io.shardml.workload.WorkloadModel;
import io.shardml.workload.Zipf;

/* Builds a Workload from a parsed .workload file, with params overridden by name (--set
 * name=value). Expects a file without validation errors. Every number a param can feed (zipf
 * exponents, weights, `else with` probabilities) is resolved after the overrides, and the
 * validators' rules are applied to the resolved value, so an override can produce nothing the file
 * could not: zipf s >= 0, weight > 0, probability in [0, 1]. Equal inputs give equal records,
 * whatever the order of the overrides. */
public final class Workloads {

	// the largest domain LargestRemainder and the per-value arrays index
	static final long MAX_DOMAIN = Integer.MAX_VALUE;

	private Workloads() {
	}

	public static Workload of(WorkloadModel model, Map<String, String> overrides) {
		return new Builder(model.getWorkload(), overrides).build(model);
	}

	private static final class Builder {
		private final io.shardml.workload.Workload workload;
		private final Map<String, BigDecimal> values = new LinkedHashMap<>();
		private final Map<String, String> overridden = new TreeMap<>();
		private final Map<Let, Integer> ids = new IdentityHashMap<>();
		private int nextId;

		Builder(io.shardml.workload.Workload workload, Map<String, String> overrides) {
			this.workload = workload;
			for (Param param : workload.getParams())
				values.put(param.getName(), param.getValue());
			// sorted, so the first error does not depend on the map's order
			for (Map.Entry<String, String> override : new TreeMap<>(overrides).entrySet()) {
				String name = override.getKey();
				if (!values.containsKey(name))
					throw new IllegalArgumentException("--set " + name + ": workload '" + workload.getName()
							+ "' has no param '" + name + "' (params: "
							+ (values.isEmpty() ? "none" : String.join(", ", values.keySet())) + ").");
				BigDecimal value;
				try {
					value = new BigDecimal(override.getValue().strip());
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("--set " + name + "=" + override.getValue() + ": not a number.");
				}
				values.put(name, value);
				overridden.put(name, override.getValue());
			}
		}

		Workload build(WorkloadModel model) {
			List<Workload.Param> params = new ArrayList<>();
			for (Map.Entry<String, BigDecimal> param : values.entrySet())
				params.add(new Workload.Param(param.getKey(), param.getValue().doubleValue(), overridden.containsKey(param.getKey())));
			List<Workload.Domain> domains = new ArrayList<>();
			for (Domain domain : workload.getDomains()) {
				if (domain.getSize() > MAX_DOMAIN)
					throw new IllegalArgumentException("Domain '" + domain.getName() + "' has " + domain.getSize()
							+ " values; the cost model handles at most " + MAX_DOMAIN + " per domain.");
				Workload.Within within = domain.getParent() == null ? null
						: new Workload.Within(domain.getParent().getName(),
								dist(domain.getChildren(), "domain '" + domain.getName() + "'"));
				domains.add(new Workload.Domain(domain.getName(), domain.getSize(), within));
			}
			List<Workload.TableData> tables = new ArrayList<>();
			for (TableData data : workload.getTables())
				tables.add(table(data));
			List<Workload.Transaction> transactions = new ArrayList<>();
			for (Transaction transaction : workload.getTransactions()) {
				BigDecimal weight = number(transaction.getWeight());
				if (weight.signum() <= 0)
					throw new IllegalArgumentException(origin(transaction.getWeight()) + " makes the weight of transaction '"
							+ transaction.getName() + "' " + weight.toPlainString() + "; it must be greater than 0.");
				nextId = 0;
				transactions.add(new Workload.Transaction(transaction.getName(), weight.doubleValue(),
						steps(transaction.getSteps(), transaction.getName())));
			}
			Schema schema = ImportedSchema.of(model);
			if (schema == null)
				throw new IllegalStateException("Workload '" + workload.getName() + "' has no readable .msql import");
			return new Workload(workload.getName(), params, domains, tables, transactions,
					SchemaView.of(SchemaFacts.of(schema)));
		}

		private Workload.TableData table(TableData data) {
			String table = SqlNames.stored(data.getTable());
			Rows rows;
			if (data.getRows() instanceof PerRows per) {
				// no count written reads as 0 (validated >= 1 when written)
				long count = per.getCount() == 0 ? 1 : per.getCount();
				rows = new Workload.PerRows(count, per.getDomains().stream().map(Domain::getName).toList());
			} else {
				OverRows over = (OverRows) data.getRows();
				rows = new Workload.OverRows(over.getCount(), over.getDomain().getName(),
						dist(over.getDistribution(), "table '" + table + "' (rows over " + over.getDomain().getName() + ")"));
			}
			List<ColumnMapping> columns = new ArrayList<>();
			for (io.shardml.workload.ColumnMap map : data.getColumns()) {
				String column = SqlNames.stored(map.getColumn());
				if (map instanceof MappedColumn mapped)
					columns.add(new Workload.Mapped(column, new Workload.KeyPath(mapped.getPath().getDomain().getName(),
							mapped.getPath().getParents().stream().map(Domain::getName).toList())));
				else
					columns.add(new Workload.Unique(column));
			}
			return new Workload.TableData(table, rows, columns);
		}

		private List<Step> steps(List<io.shardml.workload.Step> steps, String transaction) {
			List<Step> out = new ArrayList<>();
			for (io.shardml.workload.Step step : steps) {
				if (step instanceof Let let) {
					int id = nextId++;
					ids.put(let, id);
					String domain = let.getDraw().getDomain().getName();
					String where = "let " + let.getName() + " in '" + transaction + "'";
					Dist dist = dist(let.getDraw().getDistribution(), where);
					if (let.getBase() != null) {
						BigDecimal p = number(let.getRemote());
						if (p.signum() < 0 || p.compareTo(BigDecimal.ONE) > 0)
							throw new IllegalArgumentException(origin(let.getRemote()) + " makes the probability of 'else with' in '"
									+ transaction + "' (let " + let.getName() + ") " + p.toPlainString()
									+ "; it must be between 0 and 1.");
						out.add(new Workload.ElseWith(id, let.getName(), ref(let.getBase()), p.doubleValue(), domain, dist));
					} else {
						VarRef from = let.getDraw().getDistinctFrom() == null ? null : ref(let.getDraw().getDistinctFrom());
						out.add(new Workload.Draw(id, let.getName(), domain, dist, from));
					}
				} else if (step instanceof Repeat repeat) {
					out.add(new Workload.Repeat(repeat.getCount(), steps(repeat.getSteps(), transaction)));
				} else {
					out.add(statement((Statement) step));
				}
			}
			return out;
		}

		private Workload.Statement statement(Statement statement) {
			List<Join> joins = new ArrayList<>();
			for (io.shardml.workload.Join join : statement.getJoins()) {
				List<JoinOn> on = new ArrayList<>();
				for (io.shardml.workload.JoinOn j : join.getOn())
					on.add(new JoinOn(column(j.getLeft().getColumn()), column(j.getRight().getColumn())));
				joins.add(new Join(SqlNames.stored(join.getTable()), on));
			}
			List<Condition> conditions = new ArrayList<>();
			if (statement.getFirst() != null) {
				conditions.add(condition(statement.getFirst()));
				for (io.shardml.workload.Condition c : statement.getRest())
					conditions.add(condition(c));
			}
			List<Connective> connectives = statement.getLinks().stream()
					.map(l -> l == io.shardml.workload.Link.AND ? Connective.AND : Connective.OR).toList();
			Kind kind = switch (statement.getType()) {
				case READ -> Kind.READ;
				case WRITE -> Kind.WRITE;
				case DELETE -> Kind.DELETE;
			};
			return new Workload.Statement(kind, SqlNames.stored(statement.getTable()), joins, conditions, connectives);
		}

		private Condition condition(io.shardml.workload.Condition c) {
			// the grammar's names: GE is '>', LE is '<', GEQ '>=', LEQ '<='
			Op op = switch (c.getOp()) {
				case EQ -> Op.EQ;
				case NEQ -> Op.NEQ;
				case GE -> Op.GT;
				case LE -> Op.LT;
				case GEQ -> Op.GE;
				case LEQ -> Op.LE;
			};
			return new Condition(column(c.getColumn().getColumn()), op, c.isUnbound() ? null : ref(c.getValue()));
		}

		// a column with its own table, whether or not the file qualified it
		private static ColumnRef column(Column column) {
			return new ColumnRef(SqlNames.stored(EcoreUtil2.getContainerOfType(column, Table.class)), SqlNames.stored(column));
		}

		private VarRef ref(io.shardml.workload.VarRef ref) {
			Integer id = ids.get(ref.getVariable());
			if (id == null)
				throw new IllegalStateException("$" + ref.getVariable().getName() + " refers to a let not declared before it");
			return new VarRef(id, ref.getVariable().getName(), ref.getParents().stream().map(Domain::getName).toList());
		}

		private Dist dist(Distribution distribution, String where) {
			if (distribution instanceof Zipf zipf) {
				BigDecimal s = number(zipf.getS());
				if (s.signum() < 0)
					throw new IllegalArgumentException(origin(zipf.getS()) + " makes the zipf exponent of " + where + " "
							+ s.toPlainString() + "; it must be at least 0.");
				return new Workload.Zipf(s.doubleValue());
			}
			return new Workload.Uniform();
		}

		private BigDecimal number(Num num) {
			return num.getParam() == null ? num.getValue() : values.get(num.getParam().getName());
		}

		// where a resolved number came from, for a message
		private String origin(Num num) {
			if (num.getParam() == null)
				return "The literal " + num.getValue().toPlainString();
			String name = num.getParam().getName();
			if (overridden.containsKey(name))
				return "--set " + name + "=" + overridden.get(name);
			return "Param '" + name + "' = " + values.get(name).toPlainString();
		}
	}
}
