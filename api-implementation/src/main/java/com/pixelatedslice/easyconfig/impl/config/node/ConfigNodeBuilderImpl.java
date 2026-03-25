package com.pixelatedslice.easyconfig.impl.config.node;

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


public class ConfigNodeBuilderImpl<T> implements ConfigNodeBuilder<T> {
    private final List<String> comments = new ArrayList<>();

    private String key;

    private T value;
    private T defaultValue;
    private TypeToken<T> typeToken;

    private ConfigSection parent;


    @Override
    public ConfigNodeBuilder<T> key(@NonNull String key) {
        Objects.requireNonNull(key);
        this.key = key;
        return this;
    }


    @Override
    public ConfigNodeBuilder<T> value(@Nullable T value) {
        Objects.requireNonNull(value);
        this.value = value;
        return this;
    }

    @Override
    public ConfigNodeBuilder<T> defaultValue(@Nullable T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public ConfigNodeBuilder<T> typeToken(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);
        this.typeToken = typeToken;
        return this;
    }


    @Override
    public ConfigNodeBuilder<T> parent(@NonNull ConfigSection parent) {
        Objects.requireNonNull(parent);
        this.parent = parent;
        return this;
    }


    @Override
    public ConfigNodeBuilder<T> comment(@NonNull String... comment) {
        Objects.requireNonNull(comment);
        Collections.addAll(this.comments, comment);
        return this;
    }


    @Override
    public @NonNull ConfigNode<T> build() {
        return new ConfigNodeImpl<>(
                Objects.requireNonNull(this.key),
                this.value,
                this.defaultValue,
                Objects.requireNonNull(this.typeToken),
                this.parent,
                this.comments
        );
    }
}
