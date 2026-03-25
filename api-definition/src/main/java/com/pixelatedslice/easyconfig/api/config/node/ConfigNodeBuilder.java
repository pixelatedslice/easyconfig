package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface ConfigNodeBuilder<T> {
    /**
     * Sets the key for the configuration node being built.
     * The key serves as the unique identifier for the node within its hierarchy.
     *
     * @param key the non-null key to set for the configuration node
     * @return the current instance of {@code ConfigNodeBuilder}, allowing method chaining
     * @throws NullPointerException if the provided {@code key} is null
     */
    ConfigNodeBuilder<T> key(@NonNull String key);

    /**
     * Sets the value associated with the configuration node being built.
     *
     * @param value the value to be associated with the node; may be null to indicate no value
     * @return the current {@code ConfigNodeBuilder} instance for method chaining
     */
    ConfigNodeBuilder<T> value(@Nullable T value);

    /**
     * Sets the type token associated with the configuration node being built.
     * The type token defines the type of the value stored in the configuration node,
     * enabling the use of generic types.
     *
     * @param typeToken the type token representing the type of the value; must not be null
     * @return the current {@code ConfigNodeBuilder} instance for method chaining
     * @throws NullPointerException if the {@code typeToken} argument is null
     */
    ConfigNodeBuilder<T> typeToken(@NonNull TypeToken<T> typeToken);

    /**
     * Sets the parent {@link ConfigSection} for this configuration node builder.
     * The parent configuration section establishes the hierarchical structure for
     * the node being built.
     *
     * @param parent the non-null {@link ConfigSection} to set as the parent for this node builder
     * @return the current instance of {@code ConfigNodeBuilder} for method chaining
     * @throws NullPointerException if {@code parent} is null
     */
    ConfigNodeBuilder<T> parent(@NonNull ConfigSection parent);

    /**
     * Sets comments for the configuration node being built. Comments are meant to provide
     * additional context or documentation within the configuration file.
     *
     * @param comment the comments to associate with the configuration node; must not be null
     * @return the current {@link ConfigNodeBuilder} instance for method chaining
     * @throws NullPointerException if the {@code comment} argument is null
     */
    ConfigNodeBuilder<T> comment(@NonNull String... comment);

    /**
     * Builds and returns a configured {@link ConfigNode} instance based on the properties
     * set in the builder. This method finalizes the construction process and ensures all
     * necessary configurations are applied to the returned node.
     *
     * @return a non-null configured {@link ConfigNode} instance
     * @throws NullPointerException if any required properties annotated with {@link NonNull}
     *                              were omitted during construction
     */
    @NonNull ConfigNode<T> build();
}
