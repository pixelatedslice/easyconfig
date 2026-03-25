package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ConfigNodeImpl<T> implements ConfigNode<T> {
    private final List<String> comments = new ArrayList<>();
    private final String key;
    private final TypeToken<@NonNull T> typeToken;
    private final T defaultValue;

    private T value;

    private ConfigSection parent;


    public ConfigNodeImpl(
            @NonNull String key,
            @Nullable T value,
            @Nullable T defaultValue,
            @NonNull TypeToken<@NonNull T> typeToken,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(comments);

        this.key = key;
        this.value = value;
        this.defaultValue = defaultValue;
        this.typeToken = typeToken;
        this.parent = parent;
        this.comments.addAll(comments);
    }


    @SuppressWarnings("unchecked")
    public ConfigNodeImpl(
            @NonNull String key,
            @NonNull T valueWithSimpleType,
            @Nullable T defaultValueWithSimpleType,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        this(
                Objects.requireNonNull(key),
                Objects.requireNonNull(valueWithSimpleType),
                defaultValueWithSimpleType,
                (TypeToken<T>) TypeToken.of(valueWithSimpleType.getClass()),
                parent,
                comments
        );
    }


    public ConfigNodeImpl(
            @NonNull String key,
            @Nullable T defaultValue,
            @NonNull TypeToken<@NonNull T> typeToken,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        this(Objects.requireNonNull(key), null, defaultValue, Objects.requireNonNull(typeToken), parent, comments);
    }


    public ConfigNodeImpl(
            @NonNull String key,
            @Nullable T defaultValueWithSimpleType,
            @NonNull Class<@NonNull T> simpleType,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        this(
                Objects.requireNonNull(key),
                defaultValueWithSimpleType,
                null,
                Objects.requireNonNull(TypeToken.of(simpleType)),
                parent,
                comments
        );
    }


    @Override
    public @NonNull Collection<String> comments() {
        return Collections.unmodifiableCollection(this.comments);
    }


    @Override
    public ConfigNode<T> addComment(@NonNull String comment) {
        this.comments.add(comment);
        return this;
    }


    @Override
    public ConfigNode<T> removeComment(@NonNull String comment) {
        this.comments.remove(comment);
        return this;
    }


    @Override
    public ConfigNode<T> removeComment(int index) {
        this.comments.remove(index);
        return this;
    }


    @Override
    public ConfigNode<T> clearComments() {
        this.comments.clear();
        return this;
    }


    @Override
    public @NonNull String key() {
        return this.key;
    }


    @Override
    public @NonNull Optional<@NonNull T> value() {
        return Optional.ofNullable(this.value);
    }


    @Override
    public ConfigNode<T> setValue(@Nullable T value) {
        this.value = value;
        return this;
    }

    @Override
    public @NonNull Optional<T> defaultValue() {
        return Optional.ofNullable(this.defaultValue);
    }

    @Override
    public ConfigNode<T> setDefaultValue(@Nullable T defaultValue) {
        this.value = defaultValue;
        return this;
    }


    @Override
    public @NonNull TypeToken<@NonNull T> typeToken() {
        return this.typeToken;
    }


    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }


    @Override
    public @NonNull ConfigNode<T> setParent(@Nullable ConfigSection parent) {
        this.parent = parent;
        return this;
    }
}
