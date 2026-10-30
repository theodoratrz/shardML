package io.shardml;

public class ShardMLRuntimeModule extends AbstractShardMLRuntimeModule {
    @Override
    public Class<? extends org.eclipse.xtext.scoping.IScopeProvider> bindIScopeProvider() {
        return io.shardml.scoping.ShardMLScopeProvider.class;
    }

    // unknown node references get their own issue code (SHML_031)
    public Class<? extends org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider.Extended> bindILinkingDiagnosticMessageProvider$Extended() {
        return io.shardml.validation.ShardMLLinkingDiagnosticMessageProvider.class;
    }
}
