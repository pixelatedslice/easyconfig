package com.pixelatedslice.easyconfig.api.config.node.value.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface ValueNodeBuilderValueStep<T> extends BuilderStep, ValueNodeBuilderSerializerStep<T> {
    @NonNull ValueNodeBuilderValueStep<@NonNull T> value(@Nullable T value);

    @NonNull ValueNodeBuilderValueStep<@NonNull T> defaultValue(@Nullable T defaultValue);
}