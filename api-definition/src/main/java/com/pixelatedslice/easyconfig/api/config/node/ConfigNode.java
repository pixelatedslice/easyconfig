package com.pixelatedslice.easyconfig.api.config.node;

import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.config.node.mutable.MutableConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.descriptor.WithDescriptor;
import com.pixelatedslice.easyconfig.api.mutability.WithMutableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.ServiceLoader;

public interface ConfigNode<T>
        extends WithDescriptor<ConfigNodeDescriptor<T>>, WithMutableVariant<MutableConfigNode<T>> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull ConfigNodeBuilder<T> builder() {
        final var builderLoader = ServiceLoader.load(ConfigNodeBuilder.class);
        return (ConfigNodeBuilder<T>) builderLoader.findFirst().orElseThrow();
    }

    @NonNull Optional<@NonNull ConfigSection> parent();
}