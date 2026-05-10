package com.pixelatedslice.easyconfig.impl.config.node.env;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.env.builder.AbstractEnvNodeBuilderImpl;
import com.pixelatedslice.easyconfig.impl.config.node.env.builder.OriginalEnvNodeBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class EnvNodeImpl<T> extends AbstractNode implements EnvNode<T> {

    private final @NonNull String envKey;
    private final @NonNull Function<@NonNull String, @Nullable T> adapter;
    private final @NonNull TypeToken<T> type;

    public EnvNodeImpl(@NonNull AbstractEnvNodeBuilderImpl<T, ?> builder) {
        super(builder);
        this.envKey = Objects.requireNonNull(builder.envKey());
        this.adapter = Objects.requireNonNull(builder.adapter());
        this.type = Objects.requireNonNull(builder.type());
    }

    @Override
    public @NonNull String envKey() {
        return this.envKey;
    }

    @Override
    public Optional<T> value() {
        var envValue = System.getenv(this.envKey);
        if (envValue == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.adapter.apply(envValue));
    }

    @Override
    public @NonNull Function<String, T> adapter() {
        return this.adapter;
    }

    @Override
    public @NonNull TypeToken<T> typeToken() {
        return this.type;
    }

    @Override
    protected void internalAppendChild(@NonNull AbstractNode node) {
        throw new IllegalArgumentException("child nodes are not supported on EnvNode");
    }

    @Override
    public @NonNull OriginalEnvNodeBuilder<T> toBuilder() {
        return new OriginalEnvNodeBuilder<>(this.key(), this.typeToken(), this.envKey()).config(this.attached).parent(this.parent).adapter(this.adapter);
    }
}
