package io.shardml.build;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

/* Build-time only (generate-sources; never in the runtime jar). Adjusts the Ecore that Xtext
 * infers from SQL.xtext before EMF generates code from it: the attributes of a column's type become
 * unsettable (Plan-sql 11b, option (a)). Xtext cannot declare this in the grammar.
 *
 * An attribute that is not unsettable cannot tell "the default was parsed" from "nothing was
 * parsed": Column.simpleType reads INT, its first literal, and a size reads 0. Unsettable, the
 * parser's assignment sets a flag, so isSetSimpleType() is false for a column whose type did not
 * parse, and isSetSize() for a VARCHAR whose length did not.
 *
 * Fails the build if an attribute is missing, rather than generating silently without the flag.
 *
 * IXtext2EcorePostProcessor is deprecated in Xtext 2.42 but still called by XtextLinker, and is
 * the only hook into the inferred Ecore. If an Xtext upgrade removes it, the build fails here at
 * compile time; the fallback is Plan-sql 11b option (b), an UNSPECIFIED first enum literal. */
@SuppressWarnings("deprecation")
public class SqlEcorePostProcessor implements IXtext2EcorePostProcessor {

	@Override
	public void process(GeneratedMetamodel metamodel) {
		EPackage ePackage = metamodel.getEPackage();
		if (ePackage == null || !"http://shardml.io/SQL".equals(ePackage.getNsURI()))
			return;
		unsettable(ePackage, "Column", "simpleType");
		unsettable(ePackage, "Varchar", "size");
		unsettable(ePackage, "Char", "size");
		unsettable(ePackage, "Numeric", "precision");
		unsettable(ePackage, "Numeric", "scale");
	}

	private static void unsettable(EPackage ePackage, String className, String attributeName) {
		if (!(ePackage.getEClassifier(className) instanceof EClass eClass))
			throw new IllegalStateException("SQL.xtext infers no EClass " + className);
		if (!(eClass.getEStructuralFeature(attributeName) instanceof EAttribute attribute))
			throw new IllegalStateException("SQL.xtext infers no attribute " + className + "." + attributeName);
		attribute.setUnsettable(true);
	}
}
