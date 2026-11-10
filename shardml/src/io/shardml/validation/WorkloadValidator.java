package io.shardml.validation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;

import io.shardml.cost.LargestRemainder;
import io.shardml.scoping.WorkloadScopeProvider;
import io.shardml.sql.Column;
import io.shardml.sql.Schema;
import io.shardml.sql.Table;
import io.shardml.workload.ColumnMap;
import io.shardml.workload.ColumnRef;
import io.shardml.workload.Condition;
import io.shardml.workload.Distribution;
import io.shardml.workload.Domain;
import io.shardml.workload.Draw;
import io.shardml.workload.Import;
import io.shardml.workload.Join;
import io.shardml.workload.KeyPath;
import io.shardml.workload.Let;
import io.shardml.workload.MappedColumn;
import io.shardml.workload.Num;
import io.shardml.workload.Operator;
import io.shardml.workload.OverRows;
import io.shardml.workload.PerRows;
import io.shardml.workload.Repeat;
import io.shardml.workload.Rows;
import io.shardml.workload.Statement;
import io.shardml.workload.Step;
import io.shardml.workload.TableData;
import io.shardml.workload.Transaction;
import io.shardml.workload.UniqueColumn;
import io.shardml.workload.VarRef;
import io.shardml.workload.Workload;
import io.shardml.workload.WorkloadModel;
import io.shardml.workload.WorkloadPackage.Literals;
import io.shardml.workload.Zipf;

/* Checks on a workload beyond linking (SHML_043 onward). Unknown names are linking errors,
 * see WorkloadLinkingDiagnosticMessageProvider. A check skips whatever did not link, so an
 * unknown name is reported once, by linking, and gets no follow-on error here.
 *
 * Numbers in the grammar are unsigned (INT, INT.INT), so a negative weight, exponent,
 * probability or count is a syntax error and needs no check: the plan's SHML_044 (negative
 * zipf exponent) cannot fire and is not implemented; the code stays unused. */
public class WorkloadValidator extends AbstractWorkloadValidator {

	public static final String WEIGHT_NOT_POSITIVE = "SHML_043";
	public static final String PROBABILITY_OUT_OF_RANGE = "SHML_045";
	public static final String COUNT_BELOW_ONE = "SHML_046";
	public static final String DOMAIN_MISMATCH = "SHML_047";
	public static final String NO_TABLE_BLOCK = "SHML_048";
	public static final String UNMAPPED_COLUMN = "SHML_049";
	public static final String DUPLICATE_NAME = "SHML_050";
	public static final String BAD_DEPENDENCY_DOMAIN = "SHML_051";
	public static final String BAD_DEPENDENCY_BASE = "SHML_052";
	public static final String MAPPING_NOT_FROM_ROW_DOMAIN = "SHML_054";
	public static final String UNUSED_VARIABLE = "SHML_055";
	public static final String CHILDLESS_PARENTS = "SHML_056";
	public static final String IMPORT_NOT_A_SCHEMA = "SHML_057";
	public static final String AMBIGUOUS_COLUMN = "SHML_058";
	public static final String DOMAIN_CYCLE = "SHML_059";
	public static final String EXTRA_IMPORT = "SHML_060";

	// above this many parents SHML_056 is not computed (one double per parent)
	static final long CHILDLESS_CHECK_LIMIT = 10_000_000L;

	// --- SHML_043, SHML_045, SHML_046: numbers ---

	@Check
	public void checkWeight(Transaction transaction) {
		BigDecimal weight = value(transaction.getWeight());
		if (weight != null && weight.signum() <= 0)
			error("The weight of '" + transaction.getName() + "' is " + describe(transaction.getWeight())
					+ "; a transaction's weight must be greater than 0.", transaction, Literals.TRANSACTION__WEIGHT,
					WEIGHT_NOT_POSITIVE);
	}

	@Check
	public void checkRemoteProbability(Let let) {
		if (let.getBase() == null)
			return;
		BigDecimal p = value(let.getRemote());
		if (p != null && p.compareTo(BigDecimal.ONE) > 0)
			error("The probability of `else with` is " + describe(let.getRemote()) + "; it must be between 0 and 1.", let,
					Literals.LET__REMOTE, PROBABILITY_OUT_OF_RANGE);
	}

	@Check
	public void checkDomainSize(Domain domain) {
		if (domain.getSize() < 1)
			error("The size of domain '" + domain.getName() + "' is " + domain.getSize() + "; it must be at least 1.", domain,
					Literals.DOMAIN__SIZE, COUNT_BELOW_ONE);
	}

	@Check
	public void checkRowCount(Rows rows) {
		// `rows: per D` has no count, and an absent count reads as 0, so look at the text
		if (rows.getCount() < 1 && !NodeModelUtils.findNodesForFeature(rows, Literals.ROWS__COUNT).isEmpty())
			error("The row count is " + rows.getCount() + "; it must be at least 1.", rows, Literals.ROWS__COUNT,
					COUNT_BELOW_ONE);
	}

	@Check
	public void checkRepeatCount(Repeat repeat) {
		if (repeat.getCount() < 1)
			error("The repeat count is " + repeat.getCount() + "; it must be at least 1.", repeat, Literals.REPEAT__COUNT,
					COUNT_BELOW_ONE);
	}

	// --- SHML_047, SHML_048, SHML_049: statements against the table blocks ---

	@Check
	public void checkStatementTables(Statement statement) {
		Table table = statement.getTable();
		if (linked(table) && tableData(statement, table) == null)
			error(noBlock(table), statement, Literals.STATEMENT__TABLE, NO_TABLE_BLOCK);
		for (Join join : statement.getJoins()) {
			if (linked(join.getTable()) && tableData(statement, join.getTable()) == null)
				error(noBlock(join.getTable()), join, Literals.JOIN__TABLE, NO_TABLE_BLOCK);
		}
	}

	private static String noBlock(Table table) {
		return "'" + table.getName() + "' has no table block, so the workload says nothing about its rows. Add `table "
				+ table.getName() + " { rows: ... }`.";
	}

	@Check
	public void checkBinding(Condition condition) {
		VarRef value = condition.getValue();
		Column column = condition.getColumn() == null ? null : condition.getColumn().getColumn();
		if (value == null || !linked(column))
			return;
		Domain varDomain = domainOf(value);
		Table table = (Table) column.eContainer();
		TableData data = tableData(condition, table);
		if (varDomain == null || data == null)
			return; // an unknown name, or SHML_048
		ColumnMap map = data.getColumns().stream().filter(m -> m.getColumn() == column).findFirst().orElse(null);
		String where = "'" + column.getName() + "' of '" + table.getName() + "'";
		if (map == null) {
			if (condition.getOp() == Operator.EQ)
				error(where + " is bound to " + text(value) + ", but the table block of '" + table.getName()
						+ "' does not map it. Add `" + column.getName() + ": " + varDomain.getName() + "` there.", condition,
						Literals.CONDITION__COLUMN, UNMAPPED_COLUMN);
			return;
		}
		if (map instanceof UniqueColumn) {
			error(where + " is unique (a fresh value per row), so it has no domain a variable could bind: " + text(value)
					+ " is a value of '" + varDomain.getName() + "'.", condition, Literals.CONDITION__VALUE, DOMAIN_MISMATCH);
			return;
		}
		Domain columnDomain = domainOf(((MappedColumn) map).getPath());
		if (columnDomain == null || columnDomain == varDomain)
			return;
		String hint = ancestors(varDomain).contains(columnDomain)
				? " Write $" + value.getVariable().getName() + "." + columnDomain.getName() + "."
				: "";
		error(text(value) + " is a value of '" + varDomain.getName() + "', but " + where + " maps to '"
				+ columnDomain.getName() + "'." + hint, condition, Literals.CONDITION__VALUE, DOMAIN_MISMATCH);
	}

	// --- SHML_050: duplicate names ---

	@Check
	public void checkDuplicates(Workload workload) {
		duplicates(workload.getParams(), p -> p.getName(), "param", Literals.PARAM__NAME);
		duplicates(workload.getDomains(), d -> d.getName(), "domain", Literals.DOMAIN__NAME);
		duplicates(workload.getTables().stream().filter(t -> linked(t.getTable())).toList(),
				t -> t.getTable().getName(), "table block for", Literals.TABLE_DATA__TABLE);
		duplicates(workload.getTransactions(), t -> t.getName(), "transaction", Literals.TRANSACTION__NAME);
	}

	@Check
	public void checkDuplicateMappings(TableData data) {
		duplicates(data.getColumns().stream().filter(m -> linked(m.getColumn())).toList(), m -> m.getColumn().getName(),
				"mapping of column", Literals.COLUMN_MAP__COLUMN);
	}

	// two lets of one name in the same block; a let in a repeat may shadow one outside it
	@Check
	public void checkDuplicateVariables(Transaction transaction) {
		duplicateLets(transaction.getSteps());
	}

	@Check
	public void checkDuplicateVariables(Repeat repeat) {
		duplicateLets(repeat.getSteps());
	}

	private void duplicateLets(List<Step> steps) {
		duplicates(steps.stream().filter(Let.class::isInstance).map(Let.class::cast).toList(), Let::getName,
				"variable", Literals.LET__NAME);
	}

	private <T extends EObject> void duplicates(List<T> items, Function<T, String> name, String kind,
			EStructuralFeature feature) {
		Set<String> seen = new HashSet<>();
		for (T item : items) {
			String n = name.apply(item);
			if (n != null && !seen.add(n))
				error("Duplicate " + kind + " '" + n + "'.", item, feature, DUPLICATE_NAME);
		}
	}

	// --- SHML_051, SHML_052: distinct from and else with ---

	@Check
	public void checkDependency(Let let) {
		Draw draw = let.getDraw();
		Domain domain = draw == null ? null : draw.getDomain();
		if (!linked(domain))
			return;
		if (let.getBase() != null && let.getDraw().getDistinctFrom() != null)
			error("A variable depends on one other variable: `else with` already excludes "
					+ text(let.getBase()) + ", so it cannot also be `distinct from` " + text(draw.getDistinctFrom()) + ".",
					draw, Literals.DRAW__DISTINCT_FROM, BAD_DEPENDENCY_BASE);
		else {
			dependency(let.getBase(), "else with", domain, let, Literals.LET__BASE);
			dependency(draw.getDistinctFrom(), "distinct from", domain, draw, Literals.DRAW__DISTINCT_FROM);
		}
		if ((let.getBase() != null || draw.getDistinctFrom() != null) && domain.getSize() == 1)
			error("Domain '" + domain.getName() + "' has one value, so a draw that excludes "
					+ text(let.getBase() != null ? let.getBase() : draw.getDistinctFrom()) + " has nothing to draw from.",
					draw, Literals.DRAW__DOMAIN, BAD_DEPENDENCY_DOMAIN);
	}

	// the variable a dependent draw refers to: of the draw's domain (SHML_051), and an
	// independent draw declared in the transaction itself, not in a repeat (SHML_052)
	private void dependency(VarRef ref, String form, Domain domain, EObject owner, EStructuralFeature feature) {
		if (ref == null || !linked(ref.getVariable()))
			return;
		Domain refDomain = domainOf(ref);
		if (refDomain != null && refDomain != domain)
			error(text(ref) + " is a value of '" + refDomain.getName() + "', but the draw is from '" + domain.getName()
					+ "'; `" + form + "` compares values of one domain.", owner, feature, BAD_DEPENDENCY_DOMAIN);
		Let base = ref.getVariable();
		boolean dependent = base.getBase() != null || (base.getDraw() != null && base.getDraw().getDistinctFrom() != null);
		if (dependent)
			error(text(ref) + " depends on another variable itself; `" + form
					+ "` needs an independent draw (`let " + base.getName() + " = D dist`).", owner, feature,
					BAD_DEPENDENCY_BASE);
		else if (base.eContainer() instanceof Repeat)
			error(text(ref) + " is declared in a repeat block; `" + form
					+ "` needs a variable declared in the transaction itself, drawn once.", owner, feature,
					BAD_DEPENDENCY_BASE);
	}

	// --- SHML_054: column mappings start at a row domain ---

	@Check
	public void checkMapping(MappedColumn map) {
		KeyPath path = map.getPath();
		TableData data = (TableData) map.eContainer();
		if (path == null || !linked(path.getDomain()) || !linked(map.getColumn()))
			return;
		List<Domain> rowDomains = new ArrayList<>();
		if (data.getRows() instanceof PerRows per)
			per.getDomains().stream().filter(WorkloadValidator::linked).forEach(rowDomains::add);
		else if (data.getRows() instanceof OverRows over && linked(over.getDomain()))
			rowDomains.add(over.getDomain());
		if (rowDomains.isEmpty() || rowDomains.contains(path.getDomain()))
			return;
		String names = rowDomains.stream().map(d -> "'" + d.getName() + "'").collect(Collectors.joining(", "));
		String hint = rowDomains.stream().filter(d -> ancestors(d).contains(path.getDomain())).findFirst()
				.map(d -> " Write `" + map.getColumn().getName() + ": " + d.getName() + "." + pathText(path) + "`.")
				.orElse("");
		error("'" + map.getColumn().getName() + "' maps to '" + path.getDomain().getName()
				+ "', but the rows are per " + names + ": a key path starts at a domain of the rows." + hint, map,
				Literals.MAPPED_COLUMN__PATH, MAPPING_NOT_FROM_ROW_DOMAIN);
	}

	private static String pathText(KeyPath path) {
		StringBuilder sb = new StringBuilder(path.getDomain().getName());
		for (Domain parent : path.getParents())
			sb.append('.').append(linked(parent) ? parent.getName() : "?");
		return sb.toString();
	}

	// --- SHML_055: unused variables ---

	@Check
	public void checkUsed(Transaction transaction) {
		Set<Let> used = new HashSet<>();
		for (VarRef ref : EcoreUtil2.getAllContentsOfType(transaction, VarRef.class)) {
			if (linked(ref.getVariable()))
				used.add(ref.getVariable());
		}
		for (Let let : EcoreUtil2.getAllContentsOfType(transaction, Let.class)) {
			if (!used.contains(let) && !isDuplicate(let))
				warning("Variable '" + let.getName() + "' is never used.", let, Literals.LET__NAME, UNUSED_VARIABLE);
		}
	}

	// a later let of a name already declared in its block: SHML_050 reports it, and references
	// link to the first one, so it is not also reported as unused
	private static boolean isDuplicate(Let let) {
		List<?> steps = (List<?>) let.eContainer().eGet(let.eContainingFeature());
		return steps.subList(0, steps.indexOf(let)).stream()
				.anyMatch(s -> s instanceof Let other && let.getName() != null && let.getName().equals(other.getName()));
	}

	// --- SHML_059: a domain that is its own ancestor ---

	@Check
	public void checkCycle(Domain domain) {
		List<Domain> cycle = cycle(domain);
		if (cycle != null)
			error("Domain '" + domain.getName() + "' is its own ancestor: "
					+ cycle.stream().map(Domain::getName).collect(Collectors.joining(" within ")) + ".", domain,
					Literals.DOMAIN__PARENT, DOMAIN_CYCLE);
	}

	// the chain domain, parent, ..., domain if the domain's parents lead back to it; null
	// otherwise (also when they lead into a cycle the domain is not on). At most one step per
	// domain of the workload, so it ends on any input
	private static List<Domain> cycle(Domain domain) {
		List<Domain> chain = new ArrayList<>(List.of(domain));
		Set<Domain> seen = new HashSet<>(chain);
		Domain current = domain.getParent();
		while (linked(current)) {
			chain.add(current);
			if (current == domain)
				return chain;
			if (!seen.add(current))
				return null;
			current = current.getParent();
		}
		return null;
	}

	// --- SHML_056: parents without children ---

	@Check
	public void checkChildless(Domain domain) {
		Domain parent = domain.getParent();
		if (!linked(parent) || domain.getSize() < 1 || parent.getSize() < 1 || parent.getSize() > CHILDLESS_CHECK_LIMIT
				|| cycle(domain) != null || cycle(parent) != null)
			return; // SHML_059 for a cycle
		Double s = exponent(domain.getChildren());
		if (s == null)
			return;
		long[] counts = LargestRemainder.apportion(domain.getSize(), LargestRemainder.zipfWeights((int) parent.getSize(), s));
		long childless = 0;
		for (long c : counts) {
			if (c == 0)
				childless++;
		}
		if (childless * 100 > parent.getSize()) // more than 1%
			warning(String.format(Locale.ROOT,
					"%s%% of '%s' values (%d of %d) get no '%s': largest-remainder rounding of %d over %s leaves them none. "
							+ "A larger size or a smaller exponent keeps every parent.",
					BigDecimal.valueOf(childless * 100).divide(BigDecimal.valueOf(parent.getSize()), 1, RoundingMode.HALF_UP),
					parent.getName(), childless, parent.getSize(), domain.getName(), domain.getSize(),
					describe(domain.getChildren())), domain, Literals.DOMAIN__CHILDREN, CHILDLESS_PARENTS);
	}

	// 0 for uniform, s for zipf(s); null if s does not resolve
	private static Double exponent(Distribution distribution) {
		if (distribution instanceof Zipf zipf) {
			BigDecimal s = value(zipf.getS());
			return s == null ? null : s.doubleValue();
		}
		return distribution == null ? null : 0.0;
	}

	private static String describe(Distribution distribution) {
		if (distribution instanceof Zipf zipf)
			return "zipf(" + describe(zipf.getS()) + ")";
		return "uniform";
	}

	// --- SHML_057: the import is a schema ---

	@Check
	public void checkImport(Import imp) {
		if (!(imp.eContainer() instanceof WorkloadModel model))
			return;
		if (model.getImports().indexOf(imp) > 0) {
			// only the first import is read (ImportedSchema), so a later one is not checked further
			warning("Only the first import is read; this one is ignored. A workload's tables come from one schema.", imp,
					Literals.IMPORT__IMPORT_URI, EXTRA_IMPORT);
			return;
		}
		String uri = imp.getImportURI();
		if (uri == null)
			return;
		if (!uri.endsWith(".msql")) {
			error("'" + uri + "' is not a .msql schema. A workload imports the schema its tables come from, never a "
					+ ".shardml layout, so one workload can be evaluated against every layout of that schema.", imp,
					Literals.IMPORT__IMPORT_URI, IMPORT_NOT_A_SCHEMA);
			return;
		}
		if (!(load(imp, uri) instanceof Schema))
			error("'" + uri + "' could not be loaded as a schema: the file is missing, empty or not valid SQL.", imp,
					Literals.IMPORT__IMPORT_URI, IMPORT_NOT_A_SCHEMA);
	}

	private static EObject load(Import imp, String importURI) {
		try {
			URI uri = URI.createURI(importURI).resolve(imp.eResource().getURI());
			Resource resource = imp.eResource().getResourceSet().getResource(uri, true);
			return resource == null || resource.getContents().isEmpty() ? null : resource.getContents().get(0);
		} catch (RuntimeException e) {
			return null;
		}
	}

	// --- SHML_058: an unqualified column several tables of a join have ---

	@Check
	public void checkAmbiguousColumn(ColumnRef ref) {
		Statement statement = EcoreUtil2.getContainerOfType(ref, Statement.class);
		Column column = ref.getColumn();
		if (ref.getTable() != null || statement == null || statement.getJoins().isEmpty() || !linked(column))
			return;
		List<Table> tables = new ArrayList<>();
		if (linked(statement.getTable()))
			tables.add(statement.getTable());
		for (Join join : statement.getJoins()) {
			if (linked(join.getTable()))
				tables.add(join.getTable());
		}
		List<Table> having = tables.stream()
				.filter(t -> t.getColumns().stream().anyMatch(c -> column.getName().equals(c.getName()))).toList();
		if (having.size() < 2)
			return;
		Table linkedTo = (Table) column.eContainer();
		String which = linkedTo == statement.getTable() ? "the statement's own table" : "the first join that has it";
		String names = having.stream().map(t -> "'" + t.getName() + "'").distinct().collect(Collectors.joining(", "));
		String qualified = having.stream().filter(t -> t != linkedTo).findFirst().orElse(linkedTo).getName() + "."
				+ column.getName();
		warning("'" + column.getName() + "' is a column of " + (having.stream().distinct().count() < having.size()
				? names + " more than once (a self-join)" : names) + "; unqualified, it links to '" + linkedTo.getName()
				+ "', " + which + ". Qualify it (e.g. " + qualified + ") to say which.", ref, Literals.COLUMN_REF__COLUMN,
				AMBIGUOUS_COLUMN);
	}

	// --- helpers ---

	private static boolean linked(EObject object) {
		return object != null && !object.eIsProxy();
	}

	// a literal, or a param's value; null if the param does not resolve
	private static BigDecimal value(Num num) {
		if (num == null)
			return null;
		if (num.getParam() == null)
			return num.getValue();
		return linked(num.getParam()) ? num.getParam().getValue() : null;
	}

	private static String describe(Num num) {
		BigDecimal v = value(num);
		String plain = v == null ? "?" : v.toPlainString();
		return num.getParam() == null ? plain : "$" + num.getParam().getName() + " = " + plain;
	}

	private static String text(VarRef ref) {
		return NodeModelUtils.getTokenText(NodeModelUtils.getNode(ref));
	}

	// the table block for a table, or null
	private static TableData tableData(EObject context, Table table) {
		Workload workload = EcoreUtil2.getContainerOfType(context, Workload.class);
		return workload.getTables().stream().filter(t -> t.getTable() == table).findFirst().orElse(null);
	}

	// the domain of a variable's value, after its projections; null if anything is unresolved
	private static Domain domainOf(VarRef ref) {
		Let let = ref.getVariable();
		if (!linked(let) || let.getDraw() == null)
			return null;
		Domain domain = ref.getParents().isEmpty() ? let.getDraw().getDomain() : ref.getParents().get(ref.getParents().size() - 1);
		return linked(domain) && ref.getParents().stream().allMatch(WorkloadValidator::linked) ? domain : null;
	}

	private static Domain domainOf(KeyPath path) {
		Domain domain = path.getParents().isEmpty() ? path.getDomain() : path.getParents().get(path.getParents().size() - 1);
		return linked(domain) && linked(path.getDomain()) && path.getParents().stream().allMatch(WorkloadValidator::linked)
				? domain
				: null;
	}

	private static List<Domain> ancestors(Domain domain) {
		return WorkloadScopeProvider.ancestors(domain);
	}
}
