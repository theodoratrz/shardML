package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Assertions;

import io.shardml.SQLStandaloneSetup;
import io.shardml.ShardMLStandaloneSetup;
import io.shardml.WorkloadStandaloneSetup;
import io.shardml.cost.KeyHasher;
import io.shardml.cost.Layout;
import io.shardml.cost.Layouts;
import io.shardml.cost.Workload;
import io.shardml.cost.Workloads;
import io.shardml.shardML.Model;
import io.shardml.workload.WorkloadModel;

/* The three languages in one resource set: .msql schemas, .shardml layouts and .workload files
 * loaded from text, for the cost model's tests. Each load is checked for errors unless the test
 * is about a broken input (loadUnchecked). Plan-phase3 6.1's hand-computable fixtures live here. */
final class CostFixtures {

	// bucket(v) = (v - 1) mod B: hand-computable (Plan-phase3 6.1)
	static final KeyHasher MOD = (key, buckets) -> (int) Math.floorMod(key - 1, (long) buckets);

	private final XtextResourceSet resources;

	CostFixtures() {
		SQLStandaloneSetup.doSetup();
		ShardMLStandaloneSetup.doSetup();
		resources = new WorkloadStandaloneSetup().createInjectorAndDoEMFRegistration().getInstance(XtextResourceSet.class);
	}

	Resource load(String name, String text) {
		Resource r = loadUnchecked(name, text);
		List<String> errors = errors(r);
		Assertions.assertEquals(List.of(), errors, name);
		return r;
	}

	Resource loadUnchecked(String name, String text) {
		try {
			Resource r = resources.createResource(URI.createURI("dummy:/" + name));
			r.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), resources.getLoadOptions());
			EcoreUtil.resolveAll(resources);
			return r;
		} catch (java.io.IOException e) {
			throw new IllegalStateException(e);
		}
	}

	static List<String> errors(Resource r) {
		List<Issue> issues = ((XtextResource) r).getResourceServiceProvider().getResourceValidator().validate(r, CheckMode.ALL,
				CancelIndicator.NullImpl);
		return issues.stream().filter(i -> i.getSeverity() == Severity.ERROR).map(i -> i.getCode() + ": " + i.getMessage())
				.toList();
	}

	Layout layout(String name, String text) {
		return Layouts.of(((Model) load(name, text).getContents().get(0)).getDb());
	}

	Workload workload(String name, String text, Map<String, String> overrides) {
		return Workloads.of((WorkloadModel) load(name, text).getContents().get(0), overrides);
	}

	// a fresh schema and workload in this fixture's resource set
	Workload workloadWith(String schema, String workload, Map<String, String> overrides) {
		load("s.msql", schema);
		return workload("w.workload", workload, overrides);
	}

	Workload workload(String name, String text) {
		return workload(name, text, Map.of());
	}

	// --- the fixtures of Plan-phase3 6.1: 2 nodes, B = 4, buckets 0, 2 on n1 and 1, 3 on n2 ---

	static final String SCHEMA = """
			CREATE TABLE customers (id BIGINT PRIMARY KEY)
			CREATE TABLE accounts (id BIGINT, customer_id BIGINT, PRIMARY KEY (customer_id, id))
			""";

	static final String NODES = """
			    node n1 { host: "h1" port: 5432 }
			    node n2 { host: "h2" port: 5432 }
			""";

	// layout C: everything by customer
	static final String LAYOUT_C = "import \"s.msql\"\ndatabase c {\n    type: postgres\n" + NODES + """
			    shard customers { strategy: hash key: id buckets: 4 }
			    shard accounts { strategy: hash key: customer_id buckets: 4 colocate_with: customers }
			    accounts belongs_to customers via customer_id
			}
			""";

	// layout A: accounts by their own id
	static final String LAYOUT_A = "import \"s.msql\"\ndatabase a {\n    type: postgres\n" + NODES + """
			    shard customers { strategy: hash key: id buckets: 4 }
			    shard accounts { strategy: hash key: id buckets: 4 }
			}
			""";

	// T1: 8 accounts over 4 customers by zipf(1)
	static final String WORKLOAD = """
			import "s.msql"
			workload t {
			    domain customer { size: 4 }
			    domain account { size: 8 within customer zipf(1) }
			    table customers { rows: per customer id: customer }
			    table accounts { rows: per account id: account customer_id: account.customer }
			    transaction read weight 1 {
			        let c = customer zipf(1)
			        read accounts where customer_id = $c
			    }
			}
			""";
}
