package io.shardml.command;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

import io.shardml.SQLStandaloneSetup;
import io.shardml.ShardMLStandaloneSetup;
import io.shardml.WorkloadStandaloneSetup;
import io.shardml.scoping.ImportedSchema;

/* What the cost and route commands share: loading .shardml and .workload files, each in a resource set
 * of its own (as `shardml validate` reads it), and validating them with their imported .msql, issues
 * printed as file:line: severity: message [code]. */
final class CommandSupport {

	private CommandSupport() {
	}

	static Injector setup() {
		SQLStandaloneSetup.doSetup();
		ShardMLStandaloneSetup.doSetup();
		return new WorkloadStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	static Resource load(Injector injector, File file) {
		XtextResourceSet resources = injector.getInstance(XtextResourceSet.class);
		resources.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		try {
			Resource resource = resources.getResource(URI.createFileURI(file.getCanonicalPath()), true);
			EcoreUtil.resolveAll(resources);
			return resource;
		} catch (java.io.IOException | RuntimeException e) {
			throw new IllegalStateException(file.getPath() + ": " + e.getMessage(), e);
		}
	}

	// validates every file and the .msql it imports (each schema once); prints the issues to err;
	// returns the error count
	static int validate(List<Resource> files, PrintStream err) {
		int errors = 0;
		Set<URI> schemas = new LinkedHashSet<>();
		for (Resource file : files) {
			Resource schema = file.getContents().isEmpty() ? null : ImportedSchema.resource(file.getContents().get(0));
			if (schema instanceof XtextResource && schemas.add(schema.getURI()))
				errors += report(issues(schema), schema, err);
			errors += report(issues(file), file, err);
		}
		return errors;
	}

	private static List<Issue> issues(Resource resource) {
		return new ArrayList<>(((XtextResource) resource).getResourceServiceProvider().getResourceValidator()
				.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl));
	}

	private static int report(List<Issue> issues, Resource resource, PrintStream err) {
		int errors = 0;
		String file = URI.decode(resource.getURI().lastSegment());
		for (Issue issue : issues) {
			if (issue.getSeverity() == Severity.ERROR)
				errors++;
			String code = issue.getCode() == null ? "" : " [" + issue.getCode() + "]";
			err.println(file + ":" + issue.getLineNumber() + ": " + severity(issue.getSeverity()) + ": " + issue.getMessage() + code);
		}
		return errors;
	}

	private static String severity(Severity severity) {
		return switch (severity) {
			case ERROR -> "error";
			case WARNING -> "warning";
			case INFO -> "info";
			case IGNORE -> "ignore";
		};
	}
}
