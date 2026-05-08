package com.pixelatedslice.easyconfig.api.config.node.env.builder;

import com.pixelatedslice.easyconfig.api.config.node.builder.GenericNodeBuilder;

@FunctionalInterface
public interface EnvNodeBuilder<T> extends GenericNodeBuilder<EnvNodeBuilderTypeStep<T>> {
}
