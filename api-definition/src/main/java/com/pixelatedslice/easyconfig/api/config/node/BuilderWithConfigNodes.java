package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public interface BuilderWithConfigNodes {
    <T> @NonNull BuilderWithConfigNodes node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull String key, @NonNull TypeToken<T> typeToken);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull String key, @NonNull Class<T> simpleType);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeToken);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull String key, @NonNull T valueWithSimpleType);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull String key,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> @NonNull BuilderWithConfigNodes node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);
}
