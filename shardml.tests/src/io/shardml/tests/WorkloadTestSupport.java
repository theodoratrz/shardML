package io.shardml.tests;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Assertions;

import io.shardml.SQLStandaloneSetup;
import io.shardml.workload.WorkloadModel;

/* Parses workloads against in-memory .msql schemas, and finds issues by their offset in the
 * workload text, as ShardMLPlacementValidationTest does for models. */
final class WorkloadTestSupport {

	private final ParseHelper<WorkloadModel> parseHelper;
	private final XtextResourceSet resourceSet;
	private final IResourceValidator validator;
	String text;

	WorkloadTestSupport(ParseHelper<WorkloadModel> parseHelper, XtextResourceSet resourceSet, IResourceValidator validator) {
		this.parseHelper = parseHelper;
		this.resourceSet = resourceSet;
		this.validator = validator;
		SQLStandaloneSetup.doSetup();
	}

	// adds a schema at the given URI to the resource set the workloads are parsed in
	void schema(String uri, String msql) throws Exception {
		Resource sql = resourceSet.createResource(URI.createURI(uri));
		sql.load(new ByteArrayInputStream(msql.getBytes()), null);
	}

	WorkloadModel parse(String uri, String workload) throws Exception {
		text = workload;
		return parseHelper.parse(workload, URI.createURI(uri), resourceSet);
	}

	List<Issue> issues(WorkloadModel model) {
		return validator.validate(model.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
	}

	void assertNoIssues(WorkloadModel model) {
		List<Issue> issues = issues(model);
		Assertions.assertTrue(issues.isEmpty(), issues.stream().map(Object::toString).collect(Collectors.joining("\n")));
	}

	// offset of the n-th (1-based) occurrence of snippet in the workload text, plus a shift into it
	int at(String snippet, int occurrence, int shift) {
		int offset = -1;
		for (int i = 0; i < occurrence; i++) {
			offset = text.indexOf(snippet, offset + 1);
			Assertions.assertTrue(offset >= 0, "'" + snippet + "' occurs fewer than " + occurrence + " times");
		}
		return offset + shift;
	}

	int at(String snippet) {
		return at(snippet, 1, 0);
	}
}
