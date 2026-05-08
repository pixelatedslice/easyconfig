package com.pixelatedslice.easyconfig.api.config.node.env.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface EnvNodeBuilderFinalStep<T> extends BuilderStep {
    @NonNull EnvNode<@NonNull T> build();
}
