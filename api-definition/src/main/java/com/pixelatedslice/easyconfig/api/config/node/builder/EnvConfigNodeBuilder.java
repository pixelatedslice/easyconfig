package com.pixelatedslice.easyconfig.api.config.node.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.EnvConfigNode;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface EnvConfigNodeBuilder<T>
        extends BaseConfigNodeBuilder<T, EnvConfigNode<T>> {
    @Override
    @NonNull TypeStep<T> key(@NonNull String key);

    @FunctionalInterface
    interface TypeStep<T> extends BuilderStep, BaseConfigNodeBuilder.TypeStep<T, EnvConfigNode<T>> {
        @Override
        @NonNull EnvironmentVariableStep<T> type(@NonNull TypeToken<T> typeToken);

        @Override
        @NonNull
        default EnvironmentVariableStep<T> type(@NonNull Class<T> simpleType) {
            return (EnvironmentVariableStep<T>) BaseConfigNodeBuilder.TypeStep.super.type(simpleType);
        }
    }

    interface EnvironmentVariableStep<T> extends BuilderStep, ParentStep<T, EnvConfigNode<T>> {
        ParentStep<T, EnvConfigNode<T>> environmentVariable(@NonNull String variable);
    }

    interface Handler<T> extends EnvConfigNodeBuilder<T>,
            BaseConfigNodeBuilder.Handler<T, EnvConfigNode<T>>, EnvironmentVariableStep<T>, TypeStep<T> {
    }
}
