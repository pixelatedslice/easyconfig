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

/**
 * Implementation of the {@link ConfigNodeBuilder} interface for constructing instances
 * of {@link ConfigNode}. This class enables the configuration of various properties
 * for a {@link ConfigNode}, including its key, value, parent, and comments.
 *
 * @param <T> the type associated with the value of the configuration node
 */
public class ConfigNodeBuilderImpl<T> implements ConfigNodeBuilder<T> {
    /**
     * Stores the comments associated with this configuration node builder.
     * These comments are typically used to provide additional context or
     * documentation for the configuration node and can be written along
     * with the configuration node when serialized.
     */
    private final List<String> comments = new ArrayList<>();
    /**
     * Represents the unique identifier or name for a configuration node within a configuration structure.
     * <p>
     * This variable serves as the key used to access or reference the node in the configuration hierarchy.
     * It must be unique within its parent configuration section and is integral to distinguishing configuration
     * entries.
     */
    private String key;
    /**
     * Represents the value associated with the configuration node being built.
     * The value can be of any type specified by the generic type parameter {@code T}.
     * It may be {@code null} to indicate that no specific value is assigned to the node.
     */
    private T value;
    private TypeToken<T> typeToken;
    /**
     * Represents the parent configuration section associated with the current configuration node.
     * The parent section serves as the immediate higher level in the configuration hierarchy,
     * providing context and facilitating the organization of nested configuration structures.
     * <p>
     * This field can be used to access and traverse the hierarchy of configuration sections,
     * enabling the retrieval of parent-specific details or context for the current node.
     */
    private ConfigSection parent;

    /**
     * Sets the key for the configuration node being built.
     * The key serves as the unique identifier for the node within its hierarchy.
     *
     * @param key the non-null key to set for the configuration node
     * @return the current instance of {@code ConfigNodeBuilder}, allowing method chaining
     * @throws NullPointerException if the provided {@code key} is null
     */
    @Override
    public ConfigNodeBuilder<T> key(@NonNull String key) {
        Objects.requireNonNull(key);
        this.key = key;
        return this;
    }

    /**
     * Sets the value associated with the configuration node being built.
     *
     * @param value the value to associate with the node; may be null to indicate no value
     * @return the current {@code ConfigNodeBuilder} instance for method chaining
     */
    @Override
    public ConfigNodeBuilder<T> value(@Nullable T value) {
        Objects.requireNonNull(value);
        this.value = value;
        return this;
    }

    @Override
    public ConfigNodeBuilder<T> typeToken(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);
        this.typeToken = typeToken;
        return this;
    }

    /**
     * Sets the parent {@link ConfigSection} for this configuration node builder.
     * The parent configuration section establishes the hierarchical structure for
     * the node being built.
     *
     * @param parent the non-null {@link ConfigSection} to set as the parent for this node builder
     * @return the current instance of {@code ConfigNodeBuilder}, allowing method chaining
     * @throws NullPointerException if {@code parent} is null
     */
    @Override
    public ConfigNodeBuilder<T> parent(@NonNull ConfigSection parent) {
        Objects.requireNonNull(parent);
        this.parent = parent;
        return this;
    }

    /**
     * Sets comments for the configuration node being built. Comments provide additional
     * context or documentation within the configuration file.
     *
     * @param comment the comments to associate with the configuration node; must not be null
     * @return the current {@code ConfigNodeBuilder} instance for method chaining
     * @throws NullPointerException if the {@code comment} argument is null
     */
    @Override
    public ConfigNodeBuilder<T> comment(@NonNull String... comment) {
        Objects.requireNonNull(comment);
        Collections.addAll(this.comments, comment);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method constructs a new instance of {@code ConfigNodeImpl} with the
     * specified properties.
     */
    @Override
    public @NonNull ConfigNode<T> build() {
        return new ConfigNodeImpl<>(
                Objects.requireNonNull(this.key),
                this.value,
                Objects.requireNonNull(this.typeToken),
                this.parent,
                this.comments
        );
    }
}
