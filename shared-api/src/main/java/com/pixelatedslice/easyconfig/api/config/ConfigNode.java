package com.pixelatedslice.easyconfig.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a node in a configuration structure.
 * A ConfigNode is identified by a unique key and may optionally have an associated
 * value and a parent in the configuration hierarchy. It can also be part of a
 * hierarchical configuration tree with parent-child relationships.
 */
public interface ConfigNode extends WithConfigNodeChildren {
    /**
     * Retrieves the unique key that identifies this configuration node.
     *
     * @return the non-null unique key of this configuration node
     */
    @NotNull String key();

    /**
     * Retrieves the value associated with this configuration node, if present.
     * The value is wrapped in an Optional, allowing the presence of a value to be
     * explicitly checked.
     *
     * @param <T> The type of the value associated with this configuration node.
     * @return An Optional containing the value associated with this node, or an empty
     * Optional if no value is present.
     */
    <T> @NotNull Optional<ConfigValue<T>> value();

    /**
     * Retrieves the parent of this configuration node, if one exists. The parent node
     * represents the direct hierarchical ancestor of this node within the configuration
     * structure. If this node is a top-level node, the result will be an empty optional.
     *
     * @return an {@link Optional} containing the parent {@link ConfigNode} of this node,
     * or an empty optional if this node does not have a parent.
     */
    @NotNull Optional<ConfigNode> parent();

    /**
     * Sets the parent node for this configuration node.
     * The parent represents the immediate higher node in the configuration hierarchy.
     * Setting the parent to null indicates that this node is a top-level node
     * with no parent in the hierarchy.
     *
     * @param parent the parent {@link ConfigNode} to associate with this node,
     *               or null if this node should not have a parent.
     */
    void setParent(@Nullable ConfigNode parent);
}
