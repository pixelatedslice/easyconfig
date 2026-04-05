package com.pixelatedslice.easyconfig.impl.descriptor.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.section.ConfigSectionDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ConfigNodeDescriptorImpl<T> implements ConfigNodeDescriptor<T> {
    private final @NonNull TypeToken<T> typeToken;
    private final @NonNull String key;
    private final @NonNull List<@NonNull String> comments;
    private @Nullable T defaultValue;
    private @Nullable ConfigSectionDescriptor parent;

    ConfigNodeDescriptorImpl(
            @NonNull TypeToken<T> typeToken,
            @NonNull String key,
            @Nullable T defaultValue,
            @Nullable ConfigSectionDescriptor parent,
            @NonNull List<@NonNull String> comments
    ) {
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
        this.comments.add(comment);
    }

    @Override
    public void removeComment(@NonNull String comment) {
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
    public void setKey(@NonNull String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSectionDescriptor> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public void setParent(@Nullable ConfigSectionDescriptor parent) {
        this.parent = parent;
    }
}