package com.pixelatedslice.easyconfig.api.config;

import com.pixelatedslice.easyconfig.impl.config.ConfigNodeIteratorImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Represents an abstraction for objects that contain child configuration nodes.
 * This interface simplifies hierarchical configuration node management and provides
 * utility methods for node access and manipulation.
 */
public interface WithConfigNodeChildren extends Iterable<ConfigNode> {

    /**
     * Retrieves a list of child configuration nodes associated with this object.
     * Each child node represents a subordinate node in the configuration hierarchy.
     *
     * @return A non-null list containing non-null {@link ConfigNode} instances
     * that are children of this node. The list may be empty if no children exist.
     */
    @NotNull List<@NotNull ConfigNode> children();

    /**
     * Searches for a child configuration node that matches the specified hierarchy of keys.
     * This method navigates through the child nodes using the provided keys to locate
     * a specific node in the configuration hierarchy.
     * <p>
     * Uses {@link ConfigNodeIteratorImpl#findNode(List, String...)} to find the node.
     *
     * @param providedKeys the sequence of keys representing the path to the desired child node;
     *                     each key corresponds to a level in the hierarchy. Must not be null or empty.
     * @return an {@link Optional} containing the {@link ConfigNode} if a node matching the
     * provided keys was found; otherwise, an empty {@link Optional}.
     */
    default @NotNull Optional<@NotNull ConfigNode> childNode(@NotNull String... providedKeys) {
        return ConfigNodeIteratorImpl.findNode(this.children(), providedKeys);
    }

    /**
     * Adds a child node to the current configuration node.
     * The provided child node will be included as part of this node's children.
     *
     * @param child The child node to add. Must not be null.
     *              If null is provided, a validation exception may occur.
     */
    void addChildNode(@NotNull ConfigNode child);

    /**
     * Removes a child node identified by the specified key.
     * If no child node with the given key exists, the method returns {@code false}.
     *
     * @param key the unique key of the child node to remove; must not be null
     * @return {@code true} if a node with the specified key was found and removed, {@code false} otherwise
     */
    boolean removeChildNode(@NotNull String key);

    /**
     * Removes the specified child configuration node from the current node's list of children.
     *
     * @param child The child configuration node to be removed. Must not be null.
     * @return true if the child node was successfully removed, or false if the child node was not found.
     */
    boolean removeChildNode(@NotNull ConfigNode child);

    /**
     * Returns an iterator to traverse over the child configuration nodes in this object.
     *
     * @return A non-null iterator that iterates through the child nodes of this object.
     */
    @Override
    default @NotNull Iterator<@NotNull ConfigNode> iterator() {
        return new ConfigNodeIteratorImpl(this.children());
    }
}
