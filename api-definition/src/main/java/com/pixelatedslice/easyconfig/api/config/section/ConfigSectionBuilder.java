package com.pixelatedslice.easyconfig.api.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

/**
 * An interface for building configuration sections in a structured configuration system.
 * This builder allows defining hierarchical sections, adding comments, and creating
 * nested or flat configuration nodes with detailed customization.
 */
public interface ConfigSectionBuilder {
    /**
     * Sets the key for the current configuration section.
     * The key uniquely identifies this section within its parent context.
     *
     * @param key the non-null key to set for the configuration section
     * @return this {@code ConfigSectionBuilder} instance, to allow for method chaining
     * @throws NullPointerException if {@code key} is null
     */
    ConfigSectionBuilder key(@NonNull String key);

    /**
     * Sets the parent configuration section for this builder.
     * The parent represents the immediate higher-level configuration section in the hierarchy,
     * providing context for this builder's configuration structure.
     *
     * @param parent the non-null {@link ConfigSection} to be set as the parent for this builder
     * @return this {@link ConfigSectionBuilder} instance, to allow for method chaining
     * @throws NullPointerException if the {@code parent} is null
     */
    ConfigSectionBuilder parent(@NonNull ConfigSection parent);

    /**
     * Creates and configures a configuration node within the current section. The
     * node is identified by the provided key and is associated with a specific
     * type, as defined by the {@code typeToken}. This method also takes a
     * {@code nodeBuilder} consumer to customize the properties of the node being
     * built.
     *
     * @param <T>         the type of the value associated with the configuration
     *                    node
     * @param key         the non-null key to associate with the node; must not be
     *                    empty or null
     * @param typeToken   the non-null {@link TypeToken} that specifies the type of
     *                    the configuration node
     * @param nodeBuilder a non-null {@link Consumer} to configure the properties
     *                    of the node being built
     * @return this {@code ConfigSectionBuilder} instance, allowing for method chaining
     * @throws NullPointerException if {@code key}, {@code typeToken}, or {@code nodeBuilder} is null
     */
    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder);

    /**
     * Adds a configuration node to the current section using the specified key and type token.
     * The node will be identified by the provided key and constrained to the type defined by the type token.
     *
     * @param <T>       the type of the value associated with the node
     * @param key       the non-null key identifying the configuration node
     * @param typeToken the non-null type token specifying the type of the configuration node
     * @return the current {@code ConfigSectionBuilder} instance for method chaining
     * @throws NullPointerException if {@code key} or {@code typeToken} is null
     */
    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull Class<T> simpleType);

    /**
     * Adds a configuration node to the current section with the specified key, value, and type.
     *
     * @param <T>       the type of the value for the configuration node
     * @param key       the non-null key for the configuration node
     * @param value     the non-null value for the configuration node
     * @param typeToken the non-null type token representing the type of the value
     * @return this {@code ConfigSectionBuilder} instance, to allow for method chaining
     * @throws NullPointerException if {@code key}, {@code value}, or {@code typeToken} is null
     */
    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeToken);

    <T> ConfigSectionBuilder node(@NonNull String key, @NonNull T valueWithSimpleType);

    /**
     * Creates or updates a configuration node with the specified key and applies
     * the given node builder for customization.
     *
     * @param <T>         the type of the value associated with the configuration node
     * @param key         the non-null key to identify the configuration node
     * @param nodeBuilder a non-null consumer that configures the properties of the node
     * @return the current {@code ConfigSectionBuilder} instance to allow method chaining
     * @throws NullPointerException if {@code key} or {@code nodeBuilder} is null
     */
    <T> ConfigSectionBuilder node(@NonNull String key,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    /**
     * Creates and configures a configuration node within the current configuration section.
     *
     * @param <T>         the type of the value to be associated with the configuration node
     * @param typeToken   the non-null type token representing the type of the value for the node
     * @param nodeBuilder a non-null {@link Consumer} used to define the properties of the configuration node
     * @return this {@code ConfigSectionBuilder} instance, to allow for method chaining
     * @throws NullPointerException if {@code typeToken} or {@code nodeBuilder} is null
     */
    <T> ConfigSectionBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    /**
     * Adds a configuration node to the current section using the specified configuration node builder.
     * The node's properties are configured by the provided {@code nodeBuilder} consumer.
     *
     * @param <T>         the type of the value associated with the configuration node
     * @param nodeBuilder a non-null {@link Consumer} that configures the {@link ConfigNodeBuilder}
     *                    for the node being added
     * @return the current {@link ConfigSectionBuilder} instance for method chaining
     * @throws NullPointerException if {@code nodeBuilder} is null
     */
    <T> ConfigSectionBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    /**
     * Adds a comment or series of comments to the current configuration section.
     * Each comment line will be added sequentially as part of the section's comments.
     *
     * @param comment the non-null array of strings representing the comment lines to add
     * @return the current {@code ConfigSectionBuilder} instance to allow method chaining
     * @throws NullPointerException if {@code comment} is null
     */
    ConfigSectionBuilder comment(@NonNull String... comment);

    /**
     * Creates a nested configuration section associated with the given key and allows
     * configuring it using the provided {@code nestedSectionBuilder}.
     *
     * @param key                  the non-null key to associate with the nested section
     * @param nestedSectionBuilder a non-null {@link Consumer} used to define the properties of the nested section
     * @return the current {@link ConfigSectionBuilder} instance for method chaining
     * @throws NullPointerException if {@code key} or {@code nestedSectionBuilder} is null
     */
    ConfigSectionBuilder nestedSection(
            @NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder
    );

    /**
     * Adds a nested configuration section to the current section by using the provided
     * {@link ConfigSectionBuilder} consumer to define its properties.
     *
     * @param nestedSectionBuilder a non-null consumer that configures the nested {@link ConfigSectionBuilder}
     * @return the current {@link ConfigSectionBuilder} to allow for method chaining
     * @throws NullPointerException if {@code nestedSectionBuilder} is null
     */
    ConfigSectionBuilder nestedSection(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    /**
     * Builds and returns a {@link ConfigSection} based on the properties and nodes
     * configured in this {@link ConfigSectionBuilder}.
     *
     * @return a non-null {@link ConfigSection} instance representing the final
     * configuration structure
     * @throws NullPointerException if any required argument annotated with {@code @NonNull}
     *                              was not properly provided during the builder's configuration
     */
    @NonNull ConfigSection build();
}
