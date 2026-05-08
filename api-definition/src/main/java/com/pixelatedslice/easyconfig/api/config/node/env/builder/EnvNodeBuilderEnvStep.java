package com.pixelatedslice.easyconfig.api.config.node.env.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface EnvNodeBuilderEnvStep<T> extends BuilderStep {
    @NonNull EnvNodeBuilderSerializerStep<@NonNull T> environmentVariable(@Nullable String environmentVariable);
}
