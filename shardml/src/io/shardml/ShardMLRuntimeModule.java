package io.shardml;

public class ShardMLRuntimeModule extends AbstractShardMLRuntimeModule {
    @Override
    public Class<? extends org.eclipse.xtext.scoping.IScopeProvider> bindIScopeProvider() {
        return io.shardml.scoping.ShardMLScopeProvider.class;
    }
}