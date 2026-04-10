package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.comments.Commentable;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.mutability.mutable.WithMutableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.ServiceLoader;

public interface ConfigNode<T> extends Commentable, WithMutableVariant<MutableConfigNode<T>> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull ConfigNodeBuilder<T> builder() {
        final var builderLoader = ServiceLoader.load(ConfigNodeBuilder.class);
        return (ConfigNodeBuilder<T>) builderLoader.findFirst().orElseThrow();
    }

    @NonNull String key();

    @NonNull Optional<@NonNull T> value();

    @NonNull Optional<@NonNull T> defaultValue();

    @NonNull Optional<? extends @NonNull ConfigSection> parent();

    @NonNull TypeToken<T> typeToken();
}