package com.pixelatedslice.easyconfig.api.config.node.value.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface ValueNodeBuilderValidatorStep<T> extends BuilderStep, ValueNodeBuilderFinalStep<T> {
    @NonNull ValueNodeBuilderFinalStep<T> validator(@NonNull Validator<@Nullable T> validator);
}