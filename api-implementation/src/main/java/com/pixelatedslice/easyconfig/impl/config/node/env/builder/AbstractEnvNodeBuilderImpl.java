package com.pixelatedslice.easyconfig.impl.config.node.env.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.config.Config;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.env.EnvNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.node.value.builder.AbstractValueNodeBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class AbstractEnvNodeBuilderImpl<T, Self extends AbstractEnvNodeBuilderImpl<T, Self>> implements InternalNodeBuilder<Self>, NodeBuilder.EnvAdapterStep<T>, NodeBuilder.EnvFinalStep<T> {

    private final @NonNull String key;
    private final @NonNull String envKey;
    private final @NonNull TypeToken<T> typeToken;
    private @Nullable AbstractNode parent;
    private @Nullable Config config;
    private @Nullable Function<@NonNull String, @Nullable T> adapter;

    public AbstractEnvNodeBuilderImpl(@NonNull AbstractValueNodeBuilder<?, T> builder, @NonNull String envKey) {
        this.key = Objects.requireNonNull(builder.key());
        this.config = builder.config();
        this.parent = builder.parent();
        this.envKey = Objects.requireNonNull(envKey);
        this.typeToken = Objects.requireNonNull(builder.type());
    }

    public AbstractEnvNodeBuilderImpl(@NonNull String key, @NonNull TypeToken<T> typeToken, @NonNull String envKey) {
        this.key = Objects.requireNonNull(key);
        this.envKey = Objects.requireNonNull(envKey);
        this.typeToken = Objects.requireNonNull(typeToken);
    }

    public @Nullable Function<String, T> adapter() {
        return this.adapter;
    }

    public @NonNull TypeToken<T> type() {
        return this.typeToken;
    }

    public @NonNull String envKey() {
        return this.envKey;
    }

    @Override
    public @NonNull Self parent(@Nullable AbstractNode node) {
        this.parent = node;
        //noinspection unchecked
        return (Self) this;
    }

    @Override
    public @Nullable AbstractNode parent() {
        return this.parent;
    }

    @Override
    public @NonNull Self config(@Nullable Config config) {
        this.config = config;
        //noinspection unchecked
        return (Self) this;
    }

    @Override
    public @Nullable Config config() {
        return this.config;
    }

    @Override
    public @Nullable String key() {
        return this.key;
    }

    @Override
    public @NonNull Collection<InternalNodeBuilder<?>> children() {
        return Collections.emptyList();
    }

    @Override
    public void appendChild(@NonNull InternalNodeBuilder<?> builder) {
        throw new IllegalArgumentException("Cannot append to Env");
    }

    @Override
    public @NonNull EnvNodeImpl<T> build() {
        return new EnvNodeImpl<>(this);
    }

    @Override
    public Self adapter(@NonNull Function<String, T> adapter) {
        this.adapter = adapter;
        //noinspection unchecked
        return (Self) this;
    }
}
