package com.pixelatedslice.easyconfig.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a configuration node within a hierarchical configuration structure.
 * A configuration node consists of a key, an optional value, and may also have
 * a parent and child nodes. This interface provides methods to manage and access
 * these properties, supporting a tree-like configuration model.
 *
 * @param <T> the type of the value associated with this configuration node
 */
public interface ConfigNode<T> extends WithConfigNodeChildren {
    /**
     * Retrieves the key associated with this configuration node.
     * The key serves as the unique identifier for the node within its hierarchy.
     *
     * @return the non-null key of this configuration node
     */
    @NotNull String key();

    /**
     * Retrieves the optional value associated with this configuration node.
     * A value may or may not be present, depending on whether one has been set
     * for this node.
     *
     * @return an {@link Optional} containing the value if it is present, or an empty {@link Optional} if no value is
     * set
     */
    @NotNull Optional<T> value();

    /**
     * Sets the value associated with this configuration node.
     *
     * @param value the value to be set for this node; can be null to represent the absence of a value
     */
    void setValue(@Nullable T value);

    /**
     * Retrieves the parent configuration node of this node, if present.
     * The parent node represents the next higher level in the hierarchy,
     * providing context for this node within the overall configuration structure.
     *
     * @return an {@link Optional} containing the parent configuration node if it exists, or an empty
     * {@link Optional} if this node has no parent
     */
    @NotNull Optional<@NotNull ConfigNode<?>> parent();

    /**
     * Sets the parent configuration node for this node.
     * The parent node represents the immediate higher level in the hierarchy and
     * helps establish the relationship between this node and its parent within the
     * overall configuration structure.
     *
     * @param parent the {@link ConfigNode} to be set as the parent of this node;
     *               can be null to indicate that this node has no parent
     */
    void setParent(@Nullable ConfigNode<?> parent);
}
