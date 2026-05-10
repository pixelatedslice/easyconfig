package com.pixelatedslice.easyconfig.impl.config.node.value.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.config.Config;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractValueNodeBuilder<Self extends AbstractValueNodeBuilder<Self, T>, T> implements NodeBuilder.ValueFinalStep<T>, NodeBuilder.ValueSafeStep<T>, InternalNodeBuilder<Self> {

    @NonNull String key;
    @Nullable T defaultValue;
    @Nullable T value;
    @NonNull TypeToken<T> typeToken;
    @Nullable Validator<T> validator;
    @Nullable Serializer<T> serializer;
    @Nullable Config config;
    @Nullable AbstractNode parent;
    @NonNull Collection<InternalNodeBuilder<?>> children = new CopyOnWriteArrayList<>();

    public AbstractValueNodeBuilder(@NonNull TypeToken<T> token, @NonNull String key) {
        this.key = Objects.requireNonNull(key);
        this.typeToken = Objects.requireNonNull(token);
    }

    public @NonNull String key() {
        return key;
    }

    public @Nullable T defaultValue() {
        return this.defaultValue;
    }

    public @Nullable T value() {
        return this.value;
    }

    public @NonNull TypeToken<T> type() {
        return this.typeToken;
    }

    @Override
    public Self defaultValue(@Nullable T defaultValue) {
        this.defaultValue = defaultValue;
        return (Self) this;
    }

    @Override
    public Self value(@Nullable T value) {
        this.value = value;
        return (Self) this;
    }

    @Override
    public Self validator(@NonNull Validator<T> validator) {
        this.validator = validator;
        return (Self) this;
    }

    public @Nullable Validator<T> validator() {
        return this.validator;
    }

    public @Nullable Serializer<T> serializer() {
        return this.serializer;
    }

    @Override
    public Self serializer(@NonNull Serializer<T> serializer) {
        this.serializer = serializer;
        return (Self) this;
    }

    @Override
    public @NonNull Self parent(@Nullable AbstractNode node) {
        this.parent = node;
        return (Self) this;
    }

    @Override
    public @Nullable AbstractNode parent() {
        return this.parent;
    }

    @Override
    public @NonNull Self config(@Nullable Config config) {
        this.config = config;
        return (Self) this;
    }

    @Override
    public @Nullable Config config() {
        return this.config;
    }

    @Override
    public Collection<InternalNodeBuilder<?>> children() {
        return Collections.unmodifiableCollection(this.children);
    }

    @Override
    public void appendChild(@NonNull InternalNodeBuilder<?> builder) {
        this.children.add(builder);
    }
}
