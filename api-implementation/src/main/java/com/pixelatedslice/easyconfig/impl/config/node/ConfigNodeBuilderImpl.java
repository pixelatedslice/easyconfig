package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AutoService(ConfigNodeBuilder.class)
public class ConfigNodeBuilderImpl<T> implements ConfigNodeBuilder<T> {
    private final List<String> comments = new ArrayList<>();
    private String key;
    private TypeToken<T> typeToken;
    private T value;
    private T defaultValue;
    private ConfigSection parent;

    public ConfigNodeBuilderImpl(
            @NonNull String key,
            @NonNull TypeToken<T> typeToken,
            @Nullable T value,
            @Nullable T defaultValue,
            @NonNull ConfigSection parent
    ) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(parent);
        Objects.requireNonNull(typeToken);

        this.key = key;
        this.typeToken = typeToken;
        this.value = value;
        this.defaultValue = defaultValue;
        this.parent = parent;
    }

    public ConfigNodeBuilderImpl() {
    }

    @Override
    public @NonNull ConfigNodeBuilder<T> key(@NonNull String key) {
        Objects.requireNonNull(key);
        this.key = key;
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
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public @NonNull ConfigNodeBuilder<T> typeToken(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);
        this.typeToken = typeToken;
        return this;
    }


    @Override
    public @NonNull ConfigNodeBuilder<T> parent(@NonNull ConfigSection parent) {
        Objects.requireNonNull(parent);
        this.parent = parent;
        return this;
    }


    @Override
    public @NonNull ConfigNodeBuilder<T> comments(@NonNull String @NonNull ... comments) {
        Objects.requireNonNull(comments);
        Collections.addAll(this.comments, comments);
        return this;
    }

    @Override
    public @NonNull ConfigNodeBuilder<T> addComment(@NonNull String comment) {
        Objects.requireNonNull(comment);
        this.comments.add(comment);
        return this;
    }


    @Override
    public @NonNull ConfigNode<T> build() {
        return new ConfigNodeImpl<>(
                Objects.requireNonNull(this.key),
                Objects.requireNonNull(this.typeToken),
                this.value,
                this.defaultValue,
                this.parent,
                Objects.requireNonNull(this.comments)
        );
    }
}
