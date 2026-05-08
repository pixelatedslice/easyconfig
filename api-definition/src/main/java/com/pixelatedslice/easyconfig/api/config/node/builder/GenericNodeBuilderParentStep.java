package com.pixelatedslice.easyconfig.api.config.node.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface GenericNodeBuilderParentStep<Next extends BuilderStep> extends BuilderStep {
    @NonNull Next parent(@NonNull ContainerNode parent);
}