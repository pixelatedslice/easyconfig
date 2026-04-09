package com.pixelatedslice.easyconfig.impl.descriptor.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConfigNodeDescriptorImpl<T> implements ConfigNodeDescriptor<T> {
    private final @NonNull TypeToken<T> typeToken;
    private final @NonNull String key;
    private final @NonNull List<@NonNull String> comments;
    private final @NonNull ConfigSectionDescriptor parent;
    private @Nullable T defaultValue;

    public ConfigNodeDescriptorImpl(
            @NonNull TypeToken<T> typeToken,
            @NonNull String key,
            @Nullable T defaultValue,
            @NonNull ConfigSectionDescriptor parent,
            @NonNull List<@NonNull String> comments
    ) {
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(key);
        Objects.requireNonNull(parent);
        Objects.requireNonNull(comments);

        this.typeToken = typeToken;
        this.key = key;
        this.defaultValue = defaultValue;
        this.parent = parent;
        this.comments = comments;
    }

    @Override
    public @NonNull Optional<T> defaultValue() {
        return Optional.ofNullable(this.defaultValue);
    }

    @Override
    public void setDefaultValue(@Nullable T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public @NonNull List<String> comments() {
        return Collections.unmodifiableList(this.comments);
    }

    @Override
    public void addComment(@NonNull String comment) {
        Objects.requireNonNull(comment);
        this.comments.add(comment);
    }

    @Override
    public void removeComment(@NonNull String comment) {
        Objects.requireNonNull(comment);
        this.comments.remove(comment);
    }

    @Override
    public void removeComment(int index) {
        this.comments.remove(index);
    }

    @Override
    public void clearComments() {
        this.comments.clear();
    }

    @Override
    public Optional<TypeToken<T>> typeToken() {
        return Optional.of(this.typeToken);
    }

    @Override
    public @NonNull String key() {
        return this.key;
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSectionDescriptor> parent() {
        return Optional.of(this.parent);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigNodeDescriptorImpl<?> that)
                && this.key.equals(that.key())
                && this.typeToken().orElseThrow().equals(that.typeToken().orElseThrow())
                && Objects.equals(this.defaultValue, that.defaultValue().orElse(null))
                && this.parent.equals(that.parent().orElse(null))
                && (this.comments.size() == that.comments().size())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.parent, this.typeToken);
    }
}