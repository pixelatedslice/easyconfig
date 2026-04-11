package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.MutableConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.impl.comments.AbstractCommentable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ConfigNodeImpl<T> extends AbstractCommentable implements ConfigNode<T> {
    private final @NonNull String key;
    private final @NonNull TypeToken<T> typeToken;
    private final @Nullable ConfigSection parent;
    private final @Nullable T defaultValue;
    private final @NonNull AtomicReference<@Nullable T> value;
    private final int hashCode;

    public ConfigNodeImpl(
            @NonNull String key,
            @NonNull TypeToken<T> typeToken,
            @Nullable T value,
            @Nullable T defaultValue,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        super(comments);

        this.key = key;
        this.typeToken = typeToken;
        this.value = new AtomicReference<>(value);
        this.defaultValue = defaultValue;
        this.parent = parent;

        this.hashCode = Objects.hash(this.key, this.parent, this.typeToken);
    }

    void setValueAndComments(
            @Nullable T newValue,
            @NonNull Collection<? extends @NonNull Consumer<@NonNull Collection<@NonNull String>>> commentUpdates
    ) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> this.value.set(newValue));
            if (!commentUpdates.isEmpty()) {
                executor.submit(() -> this.updateComments(commentUpdates));
            }
        }
    }

    @Override
    public @NonNull String key() {
        return this.key;
    }

    @Override
    public @NonNull Optional<@NonNull T> value() {
        return Optional.ofNullable(this.value.get());
    }

    @Override
    public @NonNull Optional<@NonNull T> defaultValue() {
        return Optional.ofNullable(this.defaultValue);
    }

    @Override
    public @NonNull Optional<@NonNull T> valueOrDefault() {
        return this.value().or(this::defaultValue);
    }

    @Override
    public @NonNull Optional<? extends @NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public @NonNull TypeToken<T> typeToken() {
        return this.typeToken;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigNode<?> that)
                && this.key.equals(that.key())
                && Objects.equals(this.parent, that.parent().orElse(null))
        );

    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public MutableConfigNode<T> mutable() {
        return new MutableConfigNodeImpl<>(this);
    }
}
