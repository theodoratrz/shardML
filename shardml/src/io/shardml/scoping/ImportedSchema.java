package io.shardml.scoping;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;

import io.shardml.shardML.Model;
import io.shardml.sql.Schema;

/* Loads the .msql schema a ShardML model imports. */
public final class ImportedSchema {

	private ImportedSchema() {
	}

	// manual resolve URI, because Shardml uses a custom import rather than Xtext's built-in
	public static Schema of(EObject obj) {
		EObject root = EcoreUtil2.getRootContainer(obj);
		if (!(root instanceof Model model))
			return null;
		if (model.getImports().isEmpty())
			return null;
		String importURI = model.getImports().get(0).getImportURI();
		URI baseURI = obj.eResource().getURI();
		URI uri = URI.createURI(importURI).resolve(baseURI);
		Resource importedSource = obj.eResource().getResourceSet().getResource(uri, true);
		if (importedSource == null || importedSource.getContents().isEmpty())
			return null;
		return (Schema) importedSource.getContents().get(0);
	}
}
