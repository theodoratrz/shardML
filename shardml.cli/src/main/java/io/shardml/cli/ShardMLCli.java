package io.shardml.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

import io.shardml.SQLStandaloneSetup;
import io.shardml.ShardMLStandaloneSetup;

/**
 * Command line front end for ShardML.
 *
 * <pre>
 *   shardml validate &lt;model.shardml&gt;
 *   shardml generate &lt;model.shardml&gt; [-o &lt;output-dir&gt;]
 * </pre>
 *
 * This is a thin driver: validation runs the normal {@code IResourceValidator}
 * and generation calls the normal {@code ShardMLGenerator} through Xtext's
 * {@code GeneratorDelegate}, so output is identical to what any other Xtext host
 * produces on save.
 */
public final class ShardMLCli {

	private static final String USAGE = String.join(System.lineSeparator(),
			"Usage:",
			"  shardml validate <model.shardml>",
			"  shardml generate <model.shardml> [-o <output-dir>]",
			"",
			"generate writes <output-dir>/<database>-sharding.json and, for postgres",
			"targets, <output-dir>/<database>-schema.sql (apply first) and",
			"<output-dir>/<database>-distribution.sql. The default output",
			"directory is src-gen/ next to the model file.");

	/** Exit codes: 0 ok, 1 model error, 2 bad invocation or I/O failure. */
	private static final int EXIT_OK = 0;
	private static final int EXIT_MODEL_ERROR = 1;
	private static final int EXIT_USAGE = 2;

	private ShardMLCli() {
	}

	public static void main(String[] args) {
		System.exit(run(args));
	}

	static int run(String[] args) {
		if (args.length == 0 || isHelp(args[0])) {
			System.out.println(USAGE);
			return args.length == 0 ? EXIT_USAGE : EXIT_OK;
		}

		String command = args[0];
		if (!"validate".equals(command) && !"generate".equals(command)) {
			System.err.println("shardml: unknown command '" + command + "'");
			System.err.println(USAGE);
			return EXIT_USAGE;
		}
		if (args.length < 2) {
			System.err.println("shardml: " + command + " needs a model file");
			System.err.println(USAGE);
			return EXIT_USAGE;
		}

		File input = new File(args[1]);
		String outputOption = null;
		for (int i = 2; i < args.length; i++) {
			if ("-o".equals(args[i]) || "--output".equals(args[i])) {
				if (i + 1 >= args.length) {
					System.err.println("shardml: " + args[i] + " needs a directory");
					return EXIT_USAGE;
				}
				outputOption = args[++i];
			} else {
				System.err.println("shardml: unexpected argument '" + args[i] + "'");
				System.err.println(USAGE);
				return EXIT_USAGE;
			}
		}
		if ("validate".equals(command) && outputOption != null) {
			System.err.println("shardml: -o is only valid for generate");
			return EXIT_USAGE;
		}

		if (!input.isFile()) {
			System.err.println("shardml: no such file: " + input.getPath());
			return EXIT_USAGE;
		}
		if (!input.getName().endsWith(".shardml")) {
			System.err.println("shardml: expected a .shardml model, got: " + input.getName());
			return EXIT_USAGE;
		}

		try {
			return execute(command, input.getCanonicalFile(), outputOption);
		} catch (IOException e) {
			System.err.println("shardml: " + e.getMessage());
			return EXIT_USAGE;
		}
	}

	private static int execute(String command, File input, String outputOption) throws IOException {
		// Both languages must be registered: the model's `import "x.msql"` is
		// resolved by ShardMLScopeProvider against the same ResourceSet.
		SQLStandaloneSetup.doSetup();
		Injector injector = new ShardMLStandaloneSetup().createInjectorAndDoEMFRegistration();

		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = resourceSet.getResource(URI.createFileURI(input.getPath()), true);

		List<Issue> issues = injector.getInstance(IResourceValidator.class)
				.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		int errors = report(issues, input);

		if (errors > 0) {
			System.err.println("shardml: " + input.getName() + " has " + errors
					+ (errors == 1 ? " error" : " errors"));
			return EXIT_MODEL_ERROR;
		}
		if ("validate".equals(command)) {
			System.out.println("shardml: " + input.getName() + " is valid");
			return EXIT_OK;
		}

		File outputDir = outputOption != null
				? new File(outputOption)
				: new File(input.getParentFile(), "src-gen");
		if (!outputDir.isDirectory() && !outputDir.mkdirs()) {
			System.err.println("shardml: cannot create output directory: " + outputDir.getPath());
			return EXIT_USAGE;
		}

		JavaIoFileSystemAccess fsa = injector.getInstance(JavaIoFileSystemAccess.class);
		fsa.setOutputPath(outputDir.getCanonicalPath());
		injector.getInstance(GeneratorDelegate.class).generate(resource, fsa, new GeneratorContext());

		System.out.println("shardml: generated into " + outputDir.getPath());
		return EXIT_OK;
	}

	/** Prints every issue as {@code file:line: severity: message [code]}; returns the error count. */
	private static int report(List<Issue> issues, File input) {
		int errors = 0;
		for (Issue issue : issues) {
			if (issue.getSeverity() == Severity.ERROR) {
				errors++;
			}
			String location = input.getName() + ":" + issue.getLineNumber();
			String code = issue.getCode() == null ? "" : " [" + issue.getCode() + "]";
			String line = location + ": " + issue.getSeverity().name().toLowerCase() + ": "
					+ issue.getMessage() + code;
			if (issue.getSeverity() == Severity.ERROR) {
				System.err.println(line);
			} else {
				System.out.println(line);
			}
		}
		return errors;
	}

	private static boolean isHelp(String arg) {
		return "-h".equals(arg) || "--help".equals(arg) || "help".equals(arg);
	}
}
