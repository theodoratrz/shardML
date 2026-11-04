package io.shardml.scoping;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;

import io.shardml.shardML.Model;
import io.shardml.sql.Schema;
import io.shardml.workload.WorkloadModel;

/* Loads the .msql schema a ShardML model or a workload imports. */
public final class ImportedSchema {

	private ImportedSchema() {
	}

	// manual resolve URI, because Shardml uses a custom import rather than Xtext's built-in.
	// null if there is no import, or the first import is not a schema (e.g. a .shardml)
	public static Schema of(EObject obj) {
		String importURI = firstImport(EcoreUtil2.getRootContainer(obj));
		if (importURI == null)
			return null;
		URI baseURI = obj.eResource().getURI();
		URI uri = URI.createURI(importURI).resolve(baseURI);
		Resource importedSource = obj.eResource().getResourceSet().getResource(uri, true);
		if (importedSource == null || importedSource.getContents().isEmpty())
			return null;
		return importedSource.getContents().get(0) instanceof Schema schema ? schema : null;
	}

	private static String firstImport(EObject root) {
		if (root instanceof Model model && !model.getImports().isEmpty())
			return model.getImports().get(0).getImportURI();
		if (root instanceof WorkloadModel model && !model.getImports().isEmpty())
			return model.getImports().get(0).getImportURI();
		return null;
	}
}
