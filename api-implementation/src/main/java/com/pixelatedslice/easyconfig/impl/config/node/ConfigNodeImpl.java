package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class ConfigNodeImpl<T> implements ConfigNode<T> {
    private final @NonNull ConfigNodeDescriptor<T> descriptor;
    private @Nullable ConfigSection parent;
    private @Nullable T value;

    public ConfigNodeImpl(
            @NonNull ConfigNodeDescriptor<T> descriptor,
            @Nullable T value,
            @Nullable ConfigSection parent
    ) {
        this.descriptor = descriptor;
        this.value = value;
        this.parent = parent;
    }

    @Override
    public @NonNull Optional<T> value() {
        return Optional.ofNullable(this.value);
    }

    @Override
    public void setValue(@Nullable T value) {
        this.value = value;
    }

    @Override
    public @NonNull Optional<T> defaultValue() {
        return this.descriptor.defaultValue();
    }

    @Override
    public void setDefaultValue(@Nullable T defaultValue) {
        this.descriptor.setDefaultValue(defaultValue);
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public void setParent(@Nullable ConfigSection parent) {
        this.parent = parent;
        this.descriptor.setParent((parent == null) ? null : parent.descriptor());
    }

    @Override
    public @NonNull ConfigNodeDescriptor<T> descriptor() {
        return this.descriptor;
    }
}
