package io.shardml.command;

import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;

import io.shardml.cost.CostModel;
import io.shardml.cost.CostReport;
import io.shardml.cost.CostReportFormat;
import io.shardml.cost.Layout;
import io.shardml.cost.Layouts;
import io.shardml.cost.SweepPoint;
import io.shardml.cost.Workload;
import io.shardml.cost.Workloads;
import io.shardml.shardML.Model;
import io.shardml.workload.Param;
import io.shardml.workload.WorkloadModel;

/* `shardml cost [options] <file>...` (Plan-phase3 4.5, 4.5a): the cost of a workload under one layout,
 * a side-by-side comparison of several, or a sweep of one param. Exactly one .workload and at least one
 * .shardml. Exit codes: 0 ok; 1 an error in a file (validation, printed file:line: severity: message
 * [code]) or between files (`shardml cost: error: <message>`); 2 usage. Lives in the core module so the
 * tests can run it end to end; ShardMLCli delegates the `cost` command here. */
public final class CostCommand {

	static final int OK = 0;
	static final int ERROR = 1;
	static final int USAGE = 2;

	public static final String USAGE_TEXT = String.join(System.lineSeparator(),
			"Usage: shardml cost [options] <workload.workload> <model.shardml>...",
			"  one model: its report; two or more: a side-by-side comparison (no ranking)",
			"  --json                  print JSON (an array for comparisons and sweeps)",
			"  --csv                   long-format CSV (comparisons and sweeps only)",
			"  --set name=value        override a workload param (repeatable)",
			"  --rank-offset k         rotate the top-level domains' ranks by k (k >= 0, default 0)",
			"  --sweep name=v1,v2,...  evaluate every value of one param",
			"  --sweep rank-offset=k1,k2,...  evaluate every rank offset (alone, or with one param sweep)",
			"  --compare               the same as giving two or more models; an error with fewer");

	private CostCommand() {
	}

	// a usage error: message, then the usage text, exit 2
	private static final class UsageError extends Exception {
		UsageError(String message) {
			super(message);
		}
	}

	// offsets: the swept rank offsets (`--sweep rank-offset=..`), empty when not swept
	private record Options(File workload, List<File> models, boolean json, boolean csv, Map<String, String> sets,
			long rankOffset, String sweepParam, List<String> sweepValues, List<Long> offsets) {
	}

	// the reserved sweep name for the rank offset (not a workload param)
	static final String RANK_OFFSET = "rank-offset";

	public static int run(List<String> args, PrintStream out, PrintStream err) {
		Options options;
		try {
			options = parse(args);
		} catch (UsageError e) {
			err.println("shardml cost: " + e.getMessage());
			err.println(USAGE_TEXT);
			return USAGE;
		}
		return execute(options, out, err);
	}

	private static Options parse(List<String> args) throws UsageError {
		List<File> workloads = new ArrayList<>();
		List<File> models = new ArrayList<>();
		boolean json = false;
		boolean csv = false;
		boolean compare = false;
		Map<String, String> sets = new LinkedHashMap<>();
		long rankOffset = 0;
		String sweepParam = null;
		List<String> sweepValues = List.of();
		List<Long> offsets = List.of();
		boolean rankOffsetGiven = false;
		for (int i = 0; i < args.size(); i++) {
			String arg = args.get(i);
			switch (arg) {
				case "--json" -> json = true;
				case "--csv" -> csv = true;
				case "--compare" -> compare = true;
				case "--set" -> {
					String value = next(args, ++i, arg);
					int eq = value.indexOf('=');
					if (eq <= 0)
						throw new UsageError("--set needs name=value, got '" + value + "'");
					String name = value.substring(0, eq);
					if (sets.put(name, value.substring(eq + 1)) != null)
						throw new UsageError("--set " + name + " given twice");
				}
				case "--rank-offset" -> {
					String value = next(args, ++i, arg);
					try {
						rankOffset = Long.parseLong(value);
					} catch (NumberFormatException e) {
						throw new UsageError("--rank-offset needs a whole number, got '" + value + "'");
					}
					if (rankOffset < 0)
						throw new UsageError("--rank-offset must be at least 0, got " + rankOffset);
					rankOffsetGiven = true;
				}
				case "--sweep" -> {
					String value = next(args, ++i, arg);
					int eq = value.indexOf('=');
					if (eq <= 0 || eq == value.length() - 1)
						throw new UsageError("--sweep needs name=v1,v2,..., got '" + value + "'");
					String name = value.substring(0, eq);
					List<String> values = new ArrayList<>();
					for (String v : value.substring(eq + 1).split(",", -1)) {
						if (v.isBlank())
							throw new UsageError("--sweep " + name + " has an empty value");
						values.add(v.strip());
					}
					if (name.equals(RANK_OFFSET)) {
						if (!offsets.isEmpty())
							throw new UsageError("--sweep " + RANK_OFFSET + " given twice");
						List<Long> ks = new ArrayList<>();
						for (String v : values) {
							long k;
							try {
								k = Long.parseLong(v);
							} catch (NumberFormatException e) {
								throw new UsageError("--sweep " + RANK_OFFSET + " needs whole numbers, got '" + v + "'");
							}
							if (k < 0)
								throw new UsageError("--sweep " + RANK_OFFSET + " values must be at least 0, got " + k);
							ks.add(k);
						}
						offsets = List.copyOf(ks);
					} else {
						if (sweepParam != null)
							throw new UsageError("--sweep given twice; one param per sweep (and optionally " + RANK_OFFSET + ")");
						sweepParam = name;
						sweepValues = List.copyOf(values);
					}
				}
				default -> {
					if (arg.startsWith("-"))
						throw new UsageError("unknown option '" + arg + "'");
					File file = new File(arg);
					if (arg.endsWith(".workload"))
						workloads.add(file);
					else if (arg.endsWith(".shardml"))
						models.add(file);
					else
						throw new UsageError("expected .workload or .shardml files, got '" + arg + "'");
					if (!file.isFile())
						throw new UsageError("no such file: " + arg);
				}
			}
		}
		if (workloads.size() != 1)
			throw new UsageError("needs exactly one .workload file, got " + workloads.size());
		if (models.isEmpty())
			throw new UsageError("needs at least one .shardml model");
		if (compare && models.size() < 2)
			throw new UsageError("--compare needs two or more models, got " + models.size());
		if (json && csv)
			throw new UsageError("--json and --csv are exclusive");
		if (rankOffsetGiven && !offsets.isEmpty())
			throw new UsageError("--rank-offset and --sweep " + RANK_OFFSET + " together; give one");
		if (csv && models.size() < 2 && sweepParam == null && offsets.isEmpty())
			throw new UsageError("--csv is for comparisons and sweeps; one model without --sweep prints a table or --json");
		if (sweepParam != null && sets.containsKey(sweepParam))
			throw new UsageError("--sweep " + sweepParam + " and --set " + sweepParam + " together; give one");
		return new Options(workloads.get(0), List.copyOf(models), json, csv, Map.copyOf(sets), rankOffset, sweepParam, sweepValues,
				offsets);
	}

	private static String next(List<String> args, int i, String option) throws UsageError {
		if (i >= args.size())
			throw new UsageError(option + " needs a value");
		return args.get(i);
	}

	private static int execute(Options options, PrintStream out, PrintStream err) {
		com.google.inject.Injector injector = CommandSupport.setup();
		// each file in a resource set of its own, as `shardml validate` reads it: the records are EMF-free and
		// the cross-input checks compare names and types, so nothing needs a shared resource set (and two
		// schemas with the same table names in one set confuse ShardML's relationship checks, Plan-phase3 4.5a)
		List<Resource> files = new ArrayList<>();
		try {
			files.add(CommandSupport.load(injector, options.workload()));
			for (File model : options.models())
				files.add(CommandSupport.load(injector, model));
		} catch (RuntimeException e) {
			err.println("shardml cost: error: cannot read " + e.getMessage());
			return ERROR;
		}
		int errors = CommandSupport.validate(files, err);
		if (errors > 0) {
			err.println("shardml cost: " + errors + (errors == 1 ? " error" : " errors"));
			return ERROR;
		}

		try {
			WorkloadModel workloadModel = (WorkloadModel) files.get(0).getContents().get(0);
			List<Layout> layouts = new ArrayList<>();
			for (Resource model : files.subList(1, files.size()))
				layouts.add(Layouts.of(((Model) model.getContents().get(0)).getDb()));
			CostModel costModel = new CostModel();
			if (options.sweepParam() != null || !options.offsets().isEmpty())
				return sweep(options, workloadModel, layouts, costModel, out);
			Workload workload = Workloads.of(workloadModel, options.sets());
			List<CostReport> reports = new ArrayList<>();
			for (Layout layout : layouts)
				reports.add(costModel.evaluate(layout, workload, options.rankOffset()));
			if (reports.size() == 1)
				out.print(options.json() ? CostReportFormat.json(reports.get(0)) : CostReportFormat.report(reports.get(0)));
			else if (options.json())
				out.print(CostReportFormat.jsonArray(reports));
			else
				out.print(options.csv() ? CostReportFormat.comparisonCsv(reports) : CostReportFormat.comparison(reports));
			return OK;
		} catch (IllegalArgumentException e) {
			err.println("shardml cost: error: " + e.getMessage());
			return ERROR;
		}
	}

	// a sweep of one param, of the rank offset, or of both (offsets outermost, then values, then layouts)
	private static int sweep(Options options, WorkloadModel model, List<Layout> layouts, CostModel costModel, PrintStream out) {
		String name = options.sweepParam();
		if (name != null) {
			List<String> params = model.getWorkload().getParams().stream().map(Param::getName).toList();
			if (!params.contains(name))
				throw new IllegalArgumentException("--sweep " + name + ": workload '" + model.getWorkload().getName()
						+ "' has no param '" + name + "' (params: " + (params.isEmpty() ? "none" : String.join(", ", params)) + ").");
		}
		List<Long> offsets = options.offsets().isEmpty() ? List.of(options.rankOffset()) : options.offsets();
		List<SweepPoint> points = new ArrayList<>();
		for (long offset : offsets) {
			if (name == null) {
				Workload workload = Workloads.of(model, options.sets());
				for (Layout layout : layouts)
					points.add(new SweepPoint(RANK_OFFSET, String.valueOf(offset), costModel.evaluate(layout, workload, offset)));
				continue;
			}
			for (String value : options.sweepValues()) {
				try {
					new BigDecimal(value);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("--sweep " + name + "=" + value + ": not a number.");
				}
				Map<String, String> overrides = new LinkedHashMap<>(options.sets());
				overrides.put(name, value);
				Workload workload;
				try {
					workload = Workloads.of(model, overrides);
				} catch (IllegalArgumentException e) {
					// the value came from --sweep, not --set
					throw new IllegalArgumentException(e.getMessage().replace("--set " + name + "=", "--sweep " + name + "="));
				}
				for (Layout layout : layouts)
					points.add(new SweepPoint(name, value, costModel.evaluate(layout, workload, offset)));
			}
		}
		// a rank_offset column only when a param and the offset are both swept
		boolean offsetColumn = name != null && !options.offsets().isEmpty();
		if (options.json())
			out.print(CostReportFormat.jsonArray(points.stream().map(SweepPoint::report).toList()));
		else
			out.print(options.csv() ? CostReportFormat.sweepCsv(points, offsetColumn) : CostReportFormat.sweep(points, offsetColumn));
		return OK;
	}
}
