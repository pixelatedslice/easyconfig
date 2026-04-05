package com.pixelatedslice.easyconfig.impl.descriptor.node;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptorBuilder;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AutoService(ConfigNodeDescriptorBuilder.class)
public class ConfigNodeDescriptorBuilderImpl<T> implements ConfigNodeDescriptorBuilder<T> {
    private final List<String> comments = new ArrayList<>();
    private TypeToken<T> typeToken;
    private String key;
    private T defaultValue;
    private ConfigSectionDescriptor parent;

    @Override
    public @NonNull ConfigNodeDescriptorBuilder<T> key(@NonNull String key) {
        this.key = key;
        return this;
    }

    @Override
    public @NonNull ConfigNodeDescriptorBuilder<T> defaultValue(@Nullable T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public @NonNull ConfigNodeDescriptorBuilder<T> parent(@Nullable ConfigSectionDescriptor parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public @NonNull ConfigNodeDescriptorBuilder<T> typeToken(@NonNull TypeToken<T> typeToken) {
        this.typeToken = typeToken;
        return this;
    }

    @Override
    public @NonNull ConfigNodeDescriptorBuilder<T> comments(@NonNull String @NonNull ... comments) {
        Collections.addAll(this.comments, comments);
        return this;
    }

    @Override
    public @NonNull ConfigNodeDescriptorBuilder<T> addComment(@NonNull String comment) {
        this.comments.add(comment);
        return this;
    }

    @Override
    public @NonNull ConfigNodeDescriptor<T> build() {
        return new ConfigNodeDescriptorImpl<>(
                Objects.requireNonNull(this.typeToken),
                Objects.requireNonNull(this.key),
                this.defaultValue,
                this.parent,
                Objects.requireNonNull(this.comments)
        );
    }
}
