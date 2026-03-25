package com.pixelatedslice.easyconfig.api.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public interface ConfigSectionBuilder {
    ConfigSectionBuilder key(@NonNull String key);

    ConfigSectionBuilder parent(@NonNull ConfigSection parent);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeToken);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T valueWithSimpleType);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigSectionBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigSectionBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    ConfigSectionBuilder comment(@NonNull String... comment);

    ConfigSectionBuilder section(@NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    ConfigSectionBuilder section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    @NonNull ConfigSection build();
}
