package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.Commentable;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface ConfigNode<T> extends Commentable {
    @NonNull String key();

    @NonNull Optional<@NonNull T> value();

    ConfigNode<T> setValue(@Nullable T value);

    @NonNull Optional<@NonNull T> defaultValue();

    ConfigNode<T> setDefaultValue(@Nullable T defaultValue);

    default @NonNull TypeToken<T> typeToken() {
        return new TypeToken<>(this.getClass()) {
        };
    }

    @NonNull Optional<@NonNull ConfigSection> parent();

    @NonNull ConfigNode<T> setParent(@Nullable ConfigSection parent);
}