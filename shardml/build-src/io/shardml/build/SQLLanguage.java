package io.shardml.build;

import org.eclipse.xtext.XtextRuntimeModule;
import org.eclipse.xtext.XtextStandaloneSetup;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;
import org.eclipse.xtext.xtext.generator.StandardLanguage;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

/* Build-time only. The SQL language of GenerateSQLMaven.mwe2: a StandardLanguage whose grammar is
 * loaded with SqlEcorePostProcessor bound.
 *
 * Xtext 2.42 has no setting for this. XtextLinker takes an optional IXtext2EcorePostProcessor
 * from the injector of the Xtext language itself, and XtextRuntimeModule binds none. That injector
 * is the one registered for *.xtext by XtextStandaloneSetup, which XtextGenerator runs when it is
 * constructed, before it initializes the languages. So this language registers *.xtext again,
 * from an injector with the binding, just before the grammar is loaded by super.initialize. */
public class SQLLanguage extends StandardLanguage {

	@Override
	@SuppressWarnings("deprecation") // IXtext2EcorePostProcessor, see SqlEcorePostProcessor
	public void initialize(Injector injector) {
		new XtextStandaloneSetup() {
			@Override
			public Injector createInjector() {
				return Guice.createInjector(Modules.override(new XtextRuntimeModule())
						.with(binder -> binder.bind(IXtext2EcorePostProcessor.class).to(SqlEcorePostProcessor.class)));
			}
		}.createInjectorAndDoEMFRegistration();
		super.initialize(injector);
	}
}
