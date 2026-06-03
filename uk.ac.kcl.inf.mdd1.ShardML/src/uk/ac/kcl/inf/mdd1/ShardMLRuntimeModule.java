package uk.ac.kcl.inf.mdd1;

public class ShardMLRuntimeModule extends AbstractShardMLRuntimeModule {
    @Override
    public Class<? extends org.eclipse.xtext.scoping.IScopeProvider> bindIScopeProvider() {
        return uk.ac.kcl.inf.mdd1.scoping.ShardMLScopeProvider.class;
    }
}