package io.shardml.validation;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

import io.shardml.scoping.ImportedSchema;

/* Validates the .msql schema a model or workload imports. Validating a .shardml or .workload
 * resource checks that resource only: a syntax error in the schema it imports would otherwise
 * surface, at best, as unknown tables, and a column whose type did not parse would be read with
 * no error at all. Callers (the CLI) report these issues with the schema file's own location. */
public final class ImportedSchemaValidation {

	private ImportedSchemaValidation() {
	}

	// the issues of the schema the resource's model imports; empty if it imports no schema (a
	// missing or non-schema import is the importing language's own error, e.g. SHML_057)
	public static List<Issue> issues(Resource modelResource) {
		if (modelResource.getContents().isEmpty())
			return List.of();
		Resource schema = ImportedSchema.resource(modelResource.getContents().get(0));
		// by extension, not by content: a file that fails to parse may have no Schema root at all,
		// and its syntax errors are exactly what must be reported
		if (!(schema instanceof XtextResource xtext) || !"msql".equals(schema.getURI().fileExtension()))
			return List.of();
		return xtext.getResourceServiceProvider().getResourceValidator().validate(schema, CheckMode.ALL,
				CancelIndicator.NullImpl);
	}
}
