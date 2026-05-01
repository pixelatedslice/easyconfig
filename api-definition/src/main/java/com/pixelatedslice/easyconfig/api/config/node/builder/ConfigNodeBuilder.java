package com.pixelatedslice.easyconfig.api.config.node.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface ConfigNodeBuilder<T> extends BaseConfigNodeBuilder<T, ConfigNode<T>> {
    @Override
    @NonNull TypeStep<T> key(@NonNull String key);

    @FunctionalInterface
    interface TypeStep<T> extends BuilderStep, BaseConfigNodeBuilder.TypeStep<T, ConfigNode<T>> {
        @Override
        @NonNull ParentStep<T> type(@NonNull TypeToken<T> typeToken);

        @Override
        default @NonNull ParentStep<T> type(@NonNull Class<T> simpleType) {
            return (ParentStep<T>) BaseConfigNodeBuilder.TypeStep.super.type(simpleType);
        }
    }

    interface ParentStep<T> extends BuilderStep, ValueStep<T>, BaseConfigNodeBuilder.ParentStep<T, ConfigNode<T>> {
        @NonNull ValueStep<T> parent(@NonNull ConfigSection parent);
    }

    interface ValueStep<T>
            extends BuilderStep, ValidatorStep<T, ConfigNode<T>>, BaseConfigNodeBuilder.ValueStep<T, ConfigNode<T>> {
        @NonNull ValueStep<T> value(@Nullable T value);

        @Override
        @NonNull ValueStep<T> defaultValue(@Nullable T defaultValue);
    }

    interface Handler<T> extends ConfigNodeBuilder<T>,
            BaseConfigNodeBuilder.Handler<T, ConfigNode<T>>, TypeStep<T>, ParentStep<T>, ValueStep<T> {
    }
}