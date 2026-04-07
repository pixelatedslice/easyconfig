package com.pixelatedslice.easyconfig.api.config.node;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.descriptor.WithDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.ServiceLoader;

public interface ConfigNode<T> extends WithDescriptor<ConfigNodeDescriptor<T>> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull ConfigNodeBuilder<T> builder() {
        final var builderLoader = ServiceLoader.load(ConfigNodeBuilder.class);
        return (ConfigNodeBuilder<T>) builderLoader.findFirst().orElseThrow();
    }

    @NonNull Optional<@NonNull T> value();

    void setValue(@Nullable T value);

    @NonNull Optional<@NonNull T> defaultValue();

    void setDefaultValue(@Nullable T defaultValue);

    @NonNull Optional<@NonNull ConfigSection> parent();
}