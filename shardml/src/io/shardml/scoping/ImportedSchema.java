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
	// null if there is no import, the first import is not a schema (e.g. a .shardml), or it
	// cannot be loaded (a missing file)
	public static Schema of(EObject obj) {
		Resource importedSource = resource(obj);
		if (importedSource == null || importedSource.getContents().isEmpty())
			return null;
		return importedSource.getContents().get(0) instanceof Schema schema ? schema : null;
	}

	// the resource the first import loads, whatever it holds; null if there is no import or it
	// cannot be loaded
	public static Resource resource(EObject obj) {
		String importURI = firstImport(EcoreUtil2.getRootContainer(obj));
		if (importURI == null)
			return null;
		URI baseURI = obj.eResource().getURI();
		URI uri = URI.createURI(importURI).resolve(baseURI);
		try {
			return obj.eResource().getResourceSet().getResource(uri, true);
		} catch (RuntimeException e) {
			return null;
		}
	}

	private static String firstImport(EObject root) {
		if (root instanceof Model model && !model.getImports().isEmpty())
			return model.getImports().get(0).getImportURI();
		if (root instanceof WorkloadModel model && !model.getImports().isEmpty())
			return model.getImports().get(0).getImportURI();
		return null;
	}
}
