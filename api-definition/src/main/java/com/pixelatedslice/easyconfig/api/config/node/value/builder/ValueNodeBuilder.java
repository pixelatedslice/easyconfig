package com.pixelatedslice.easyconfig.api.config.node.value.builder;

import com.pixelatedslice.easyconfig.api.config.node.builder.GenericNodeBuilder;

@FunctionalInterface
public interface ValueNodeBuilder<T> extends GenericNodeBuilder<ValueNodeBuilderParentStep<T>> {
}