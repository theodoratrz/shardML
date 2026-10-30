package io.shardml.validation;

import java.util.stream.Collectors;

import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

import io.shardml.shardML.Database;
import io.shardml.shardML.Node;
import io.shardml.shardML.PlaceDecl;
import io.shardml.shardML.ShardMLPackage;

/* Gives unresolved node references in a place block their own issue code (SHML_031),
 * so they are reported like every other ShardML problem. */
public class ShardMLLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {

	public static final String UNKNOWN_NODE = "SHML_031";

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(ILinkingDiagnosticContext context) {
		if (context.getReference() != ShardMLPackage.Literals.PLACE_DECL__NODES)
			return super.getUnresolvedProxyMessage(context);
		Database db = (Database) ((PlaceDecl) context.getContext()).eContainer();
		String declared = db.getNodes().stream().map(Node::getName).collect(Collectors.joining(", "));
		String message = "Unknown node '" + context.getLinkText() + "'. "
				+ (declared.isEmpty() ? "No nodes are declared." : "Declared nodes: " + declared + ".");
		return new DiagnosticMessage(message, Severity.ERROR, UNKNOWN_NODE);
	}
}
