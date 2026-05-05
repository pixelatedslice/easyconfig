package com.pixelatedslice.easyconfig.api.config.node.value.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface ValueNodeBuilderFinalStep<T> extends BuilderStep {
    @NonNull ValueNode<@NonNull T> build();
}