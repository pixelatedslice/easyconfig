package com.pixelatedslice.easyconfig.api.config.node.descriptor;

import com.pixelatedslice.easyconfig.api.comments.Commentable;
import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.Descriptor;
import com.pixelatedslice.easyconfig.api.descriptor.DescriptorWithKey;
import com.pixelatedslice.easyconfig.api.descriptor.DescriptorWithParent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.ServiceLoader;

public interface ConfigNodeDescriptor<T>
        extends Descriptor<T>, DescriptorWithKey, DescriptorWithParent<ConfigSectionDescriptor>,
        Commentable {
    @SuppressWarnings("unchecked")
    static <T> @NonNull ConfigNodeDescriptorBuilder<T> builder() {
        return (ConfigNodeDescriptorBuilder<T>) ServiceLoader
                .load(ConfigNodeDescriptorBuilder.class)
                .findFirst()
                .orElseThrow();
    }

    @NonNull Optional<T> defaultValue();

    @Override
    default void setKey(@NonNull String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void setParent(@Nullable ConfigSectionDescriptor parent) {
        throw new UnsupportedOperationException();
    }
}
