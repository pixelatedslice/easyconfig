package com.pixelatedslice.easyconfig.api.config.node.container.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface ContainerNodeBuilderFinalStep extends BuilderStep {
    @NonNull ContainerNode build();
}