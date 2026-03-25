package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface ConfigNodeBuilder<T> {
    ConfigNodeBuilder<T> key(@NonNull String key);

    ConfigNodeBuilder<T> value(@Nullable T value);

    ConfigNodeBuilder<T> defaultValue(@Nullable T defaultValue);

    ConfigNodeBuilder<T> typeToken(@NonNull TypeToken<T> typeToken);

    ConfigNodeBuilder<T> parent(@NonNull ConfigSection parent);

    ConfigNodeBuilder<T> comment(@NonNull String... comment);

    @NonNull ConfigNode<T> build();
}