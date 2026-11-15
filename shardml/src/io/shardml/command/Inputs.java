package io.shardml.command;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;

import io.shardml.cost.CostInputs;
import io.shardml.cost.Layout;
import io.shardml.cost.Layouts;
import io.shardml.cost.Workload;
import io.shardml.cost.Workloads;
import io.shardml.shardML.Model;
import io.shardml.workload.WorkloadModel;

/* A workload and its layouts as EMF-free records, read and validated as `shardml cost` and `shardml
 * route` read them (each file in a resource set of its own, issues printed to err), every layout
 * checked against the workload (CostInputs.check). For the Phase 4 driver, which must replay exactly
 * the inputs the model evaluated. */
public final class Inputs {

	private Inputs() {
	}

	public record Loaded(Workload workload, List<Layout> layouts) {

		public Loaded {
			layouts = List.copyOf(layouts);
		}
	}

	// throws IllegalArgumentException when a file cannot be read, has errors (printed to err), or a
	// layout does not fit the workload
	public static Loaded load(File workload, List<File> models, Map<String, String> sets, PrintStream err) {
		com.google.inject.Injector injector = CommandSupport.setup();
		List<Resource> files = new ArrayList<>();
		try {
			files.add(CommandSupport.load(injector, workload));
			for (File model : models)
				files.add(CommandSupport.load(injector, model));
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("cannot read " + e.getMessage(), e);
		}
		int errors = CommandSupport.validate(files, err);
		if (errors > 0)
			throw new IllegalArgumentException(errors + (errors == 1 ? " error" : " errors") + " in the inputs");
		Workload w = Workloads.of((WorkloadModel) files.get(0).getContents().get(0), sets);
		List<Layout> layouts = new ArrayList<>();
		for (Resource file : files.subList(1, files.size())) {
			Layout layout = Layouts.of(((Model) file.getContents().get(0)).getDb());
			CostInputs.check(layout, w);
			layouts.add(layout);
		}
		return new Loaded(w, layouts);
	}
}
