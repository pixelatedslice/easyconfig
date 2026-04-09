package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.impl.descriptor.node.ConfigNodeDescriptorImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ConfigNodeImpl<T> implements ConfigNode<T> {
    private final @NonNull ConfigNodeDescriptor<T> descriptor;
    private final @Nullable ConfigSection parent;
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

    public static <T> ConfigNode<T> of(@NonNull String key, @NonNull TypeToken<T> typeToken, ConfigSection parent) {
        var descriptor = new ConfigNodeDescriptorImpl<>(typeToken, key, null, parent.descriptor(), new ArrayList<>());
        return new ConfigNodeImpl<>(descriptor, null, parent);
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
    public @NonNull ConfigNodeDescriptor<T> descriptor() {
        return this.descriptor;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigNodeImpl<?> that)
                && this.descriptor.equals(that.descriptor)
                && Objects.equals(this.parent, that.parent)
        );

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.descriptor, this.parent);
    }
}
