package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ConfigNodeImpl<T> implements ConfigNode<T> {
    /**
     * Holds the comments associated with this configuration node.
     * These comments typically provide additional context or metadata
     * for the corresponding configuration entry.
     */
    private final List<String> comments = new ArrayList<>();

    /**
     * Represents the unique key used to identify the configuration node within its parent hierarchy.
     * This key serves as an immutable identifier for accessing or referencing the node.
     * It is guaranteed to be non-null when the {@link ConfigNodeImpl} instance is constructed.
     */
    private final String key;
    /**
     * Represents the type of the value contained within the configuration node.
     * This variable is used for type-safe serialization and deserialization of the node's value.
     */
    private final TypeToken<@NonNull T> typeToken;
    /**
     * Represents the value associated with this configuration node.
     * <p>
     * This value can be of the generic type {@code T} and may be {@code null}
     * if no specific value has been assigned to the node. It is used to store
     * the data associated with a configuration entry.
     */
    private T value;
    /**
     * The parent configuration section of this node.
     * It represents the immediate higher-level context within the configuration hierarchy.
     * This field may be null, indicating that this node has no parent and stands as a root node.
     */
    private ConfigSection parent;

    /**
     * Constructs a new instance of {@code ConfigNodeImpl}.
     *
     * @param key       the non-null key identifying this configuration node
     * @param value     the value associated with this configuration node, or null if none
     * @param typeToken the non-null type token representing the type of the value
     * @param parent    the parent configuration section of this node, or null if it has no parent
     * @param comments  the non-null list of comments associated with this configuration node
     * @throws NullPointerException if {@code key}, {@code typeToken}, or {@code comments} is null
     */
    public ConfigNodeImpl(
            @NonNull String key,
            @Nullable T value,
            @NonNull TypeToken<@NonNull T> typeToken,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(comments);

        this.key = key;
        this.value = value;
        this.typeToken = typeToken;
        this.parent = parent;
        this.comments.addAll(comments);
    }

    /**
     * Constructs a new instance of {@code ConfigNodeImpl} with the specified key, value, parent section, and comments.
     *
     * @param key                 the non-null key of the configuration node
     * @param valueWithSimpleType the non-null simple type value associated with the configuration node. A simple
     *                            type has no generics, e.g., {@code String.class} or similar.
     * @param parent              the parent configuration section of this node, may be null
     * @param comments            the non-null list of comments associated with this configuration node
     * @throws NullPointerException if {@code key}, {@code valueWithSimpleType}, or {@code comments} is null
     */
    @SuppressWarnings("unchecked")
    public ConfigNodeImpl(
            @NonNull String key,
            @NonNull T valueWithSimpleType,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        this(
                Objects.requireNonNull(key),
                Objects.requireNonNull(valueWithSimpleType),
                (TypeToken<T>) TypeToken.of(valueWithSimpleType.getClass()),
                parent,
                comments
        );
    }

    /**
     * Constructs a new instance of {@code ConfigNodeImpl}.
     *
     * @param key       the non-null key associated with this configuration node; it serves as a unique identifier
     *                  within the configuration hierarchy
     * @param typeToken the non-null type token representing the expected type of the value associated with this node
     * @param parent    the parent configuration section within which this node resides; can be null if this node has
     *                  no parent
     * @param comments  a non-null list of comments associated with this configuration node
     * @throws NullPointerException if {@code key}, {@code typeToken}, or {@code comments} is null
     */
    public ConfigNodeImpl(
            @NonNull String key,
            @NonNull TypeToken<@NonNull T> typeToken,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        this(Objects.requireNonNull(key), null, Objects.requireNonNull(typeToken), parent, comments);
    }

    /**
     * Constructs a new instance of {@code ConfigNodeImpl} with the specified key, type, parent, and comments.
     *
     * @param key        the non-null key associated with the configuration node
     * @param simpleType the non-null class representing the simple type of the node's value. A simple type has no
     *                   generics, e.g., {@code String.class} or similar.
     * @param parent     the optional parent configuration section of the node; may be null
     * @param comments   the non-null list of comments associated with this node
     * @throws NullPointerException if {@code key}, {@code simpleType}, or {@code comments} is null
     */
    public ConfigNodeImpl(
            @NonNull String key,
            @NonNull Class<@NonNull T> simpleType,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        this(
                Objects.requireNonNull(key),
                null,
                Objects.requireNonNull(TypeToken.of(simpleType)),
                parent,
                comments
        );
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns an unmodifiable collection of comments associated with this
     * node.
     */
    @Override
    public @NonNull Collection<String> comments() {
        return Collections.unmodifiableCollection(this.comments);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method adds a comment to the list of comments associated with this node.
     */
    @Override
    public void addComment(@NonNull String comment) {
        this.comments.add(comment);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes a comment from the list of comments associated with this node.
     */
    @Override
    public void removeComment(@NonNull String comment) {
        this.comments.remove(comment);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method removes a comment at a specific index from the list of comments
     * associated with this node.
     */
    @Override
    public void removeComment(int index) {
        this.comments.remove(index);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method clears all comments associated with this node.
     */
    @Override
    public void clearComments() {
        this.comments.clear();
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the key associated with this node.
     */
    @Override
    public @NonNull String key() {
        return this.key;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the value associated with this node.
     */
    @Override
    public @NonNull Optional<@NonNull T> value() {
        return Optional.ofNullable(this.value);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method sets the value associated with this node.
     */
    @Override
    public void setValue(@Nullable T value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the type token associated with this node.
     */
    @Override
    public @NonNull TypeToken<@NonNull T> typeToken() {
        return this.typeToken;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the parent section of this node, if any.
     */
    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method sets the parent section of this node.
     */
    @Override
    public void setParent(@Nullable ConfigSection parent) {
        this.parent = parent;
    }
}
