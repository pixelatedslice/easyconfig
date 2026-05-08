package com.pixelatedslice.easyconfig.api.config.node.env.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface EnvNodeBuilderValidatorStep<T> extends BuilderStep, EnvNodeBuilderFinalStep<T> {
    @NonNull EnvNodeBuilderFinalStep<T> validator(@NonNull Validator<@Nullable T> validator);
}