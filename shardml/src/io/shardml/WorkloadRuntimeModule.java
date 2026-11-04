package io.shardml;

public class WorkloadRuntimeModule extends AbstractWorkloadRuntimeModule {

    // unresolved references get ShardML issue codes (SHML_040-042, SHML_053)
    public Class<? extends org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider.Extended> bindILinkingDiagnosticMessageProvider$Extended() {
        return io.shardml.validation.WorkloadLinkingDiagnosticMessageProvider.class;
    }
}
