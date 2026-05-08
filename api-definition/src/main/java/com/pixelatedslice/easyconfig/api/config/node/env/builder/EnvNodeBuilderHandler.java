package com.pixelatedslice.easyconfig.api.config.node.env.builder;

import com.pixelatedslice.easyconfig.api.config.node.builder.GenericNodeBuilderHandler;

public interface EnvNodeBuilderHandler<T> extends
        GenericNodeBuilderHandler<EnvNodeBuilderParentStep<T>, EnvNodeBuilderTypeStep<T>>,
        EnvNodeBuilderParentStep<T>, EnvNodeBuilderTypeStep<T>, EnvNodeBuilderEnvStep<T>,
        EnvNodeBuilderSerializerStep<T>,
        EnvNodeBuilderValidatorStep<T>, EnvNodeBuilderFinalStep<T> {
}