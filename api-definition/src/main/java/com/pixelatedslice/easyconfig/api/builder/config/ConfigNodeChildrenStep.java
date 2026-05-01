package com.pixelatedslice.easyconfig.api.builder.config;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.builder.EnvConfigNodeBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public interface ConfigNodeChildrenStep<Self extends ConfigNodeChildrenStep<Self>> extends BuilderStep {
    <T> @NonNull Self node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> @NonNull Self node(ConfigNodeBuilder.@NonNull Handler<T> nodeBuilder);

    /**
     * Call {@link ConfigNodeChildrenStep#node(ConfigNodeBuilder.Handler)} to add the node.
     *
     * @param <T> The type
     * @return A new {@link ConfigNodeBuilder}
     */
    <T> @NonNull ConfigNodeBuilder<T> node();

    default <T> @NonNull Self node(@NonNull String key, @NonNull TypeToken<T> typeToken, @Nullable T value) {
        return this.node((ConfigNodeBuilder<T> b) -> {
            b.key(key).type(typeToken).value(value);
        });
    }

    default <T> @NonNull Self node(@NonNull String key, @NonNull Class<T> simpleType, @Nullable T value) {
        return this.node((ConfigNodeBuilder<T> b) -> {
            b.key(key).type(simpleType).value(value);
        });
    }

    <T> @NonNull Self env(@NonNull Consumer<? super EnvConfigNodeBuilder<T>> nodeBuilder);

    <T> @NonNull Self env(EnvConfigNodeBuilder.@NonNull Handler<T> nodeBuilder);

    /**
     * Call {@link ConfigNodeChildrenStep#env(EnvConfigNodeBuilder.Handler)} to add the node.
     *
     * @param <T> The type
     * @return A new {@link EnvConfigNodeBuilder}
     */
    <T> @NonNull EnvConfigNodeBuilder<T> env();

    default <T> @NonNull Self env(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull String environmentVariable) {
        return this.env((EnvConfigNodeBuilder<T> b) -> {
            b.key(key).type(typeToken).environmentVariable(environmentVariable);
        });
    }

    default <T> @NonNull Self env(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull String environmentVariable) {
        return this.env((EnvConfigNodeBuilder<T> b) -> {
            b.key(key).type(simpleType).environmentVariable(environmentVariable);
        });
    }
}
