package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.impl.descriptor.node.ConfigNodeDescriptorBuilderImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AutoService(ConfigNodeBuilder.class)
public class ConfigNodeBuilderImpl<T> implements ConfigNodeBuilder<T> {
    private final ConfigNodeDescriptorBuilderImpl<T> descriptorBuilder = new ConfigNodeDescriptorBuilderImpl<>();
    private final List<String> comments = new ArrayList<>();
    private T value;
    private ConfigSection parent;

    @Override
    public @NonNull ConfigNodeBuilder<T> key(@NonNull String key) {
        Objects.requireNonNull(key);
        this.descriptorBuilder.key(key);
        return this;
    }

    @Override
    public @NonNull ConfigNodeBuilder<T> value(@Nullable T value) {
        Objects.requireNonNull(value);
        this.value = value;
        return this;
    }

    @Override
    public @NonNull ConfigNodeBuilder<T> defaultValue(@Nullable T defaultValue) {
        this.descriptorBuilder.defaultValue(defaultValue);
        return this;
    }

    @Override
    public @NonNull ConfigNodeBuilder<T> typeToken(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);
        this.descriptorBuilder.typeToken(typeToken);
        return this;
    }


    @Override
    public @NonNull ConfigNodeBuilder<T> parent(@NonNull ConfigSection parent) {
        Objects.requireNonNull(parent);
        this.parent = parent;
        this.descriptorBuilder.parent(parent.descriptor());
        return this;
    }


    @Override
    public @NonNull ConfigNodeBuilder<T> comments(@NonNull String @NonNull ... comment) {
        Objects.requireNonNull(comment);
        this.descriptorBuilder.comments(comment);
        return this;
    }


    @Override
    public @NonNull ConfigNode<T> build() {
        return new ConfigNodeImpl<>(
                this.descriptorBuilder.build(),
                this.value,
                this.parent
        );
    }
}
