package com.pixelatedslice.easyconfig.api.config;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Represents an entity that contains a collection of child configuration nodes.
 * This interface provides methods to manage and interact with the child nodes
 * within a hierarchical configuration structure.
 */
public interface WithConfigNodeChildren extends Iterable<ConfigNode<?>> {

    /**
     * Retrieves the list of child configuration nodes associated with this object.
     * The child nodes represent the immediate descendants in the hierarchical
     * configuration structure.
     *
     * @return A non-null list of non-null {@link ConfigNode} objects that are the
     * child nodes of this object.
     */
    @NotNull List<@NotNull ConfigNode<?>> children();

    /**
     * Retrieves a child configuration node that matches the specified sequence of keys.
     * This method searches through the hierarchical structure of child nodes, looking
     * for a configuration node whose path corresponds to the provided sequence of keys.
     * If a matching child configuration node is found, it is returned wrapped in an
     * {@link Optional}. If no match is found, an empty {@link Optional} is returned.
     *
     * @param <T>          the type of the value held by the child configuration node
     * @param providedKeys the sequence of keys representing the path to the desired
     *                     child configuration node; must not be null
     * @return an {@link Optional} containing the matching {@link ConfigNode} if found,
     * or an empty optional if no matching child node exists
     * @throws NullPointerException if the providedKeys argument is null
     */
    default <T> @NotNull Optional<@NotNull ConfigNode<T>> childNode(@NotNull String... providedKeys) {
        return ConfigNodeIterator.findNode(this.children(), providedKeys);
    }

    /**
     * Retrieves a child configuration node that matches the specified key.
     * This method searches through the direct children of the current node
     * for a configuration node with a key that matches the provided key.
     * If a matching child node is found, it is returned wrapped in an {@link Optional}.
     * If no match is found, an empty {@link Optional} is returned.
     *
     * @param <T> the type of the value held by the child configuration node
     * @param key the key identifying the desired child configuration node; must not be null
     * @return an {@link Optional} containing the matching {@link ConfigNode} if found,
     * or an empty optional if no matching child node exists
     * @throws NullPointerException if the {@code key} argument is null
     */
    default <T> @NotNull Optional<@NotNull ConfigNode<T>> childNodeButInTheBukkitAPIStyle(@NotNull String key) {
        return ConfigNodeIterator.findNodeButInTheBukkitAPIStyle(this.children(), key);
    }

    /**
     * Adds a child configuration node to the current node's hierarchy.
     * The provided child node is incorporated as an immediate descendant of
     * the current node in the hierarchical configuration structure.
     *
     * @param child the non-null {@link ConfigNode} object to be added as a child
     *              of the current node; must not be null
     * @throws NullPointerException if the {@code child} argument is null
     */
    void addChildNode(@NotNull ConfigNode<?> child);

    /**
     * Removes a child configuration node associated with the specified key.
     * The method attempts to find a child node with a matching key and removes it
     * from the collection of child nodes maintained by this object.
     *
     * @param key the non-null key identifying the child configuration node to be removed.
     *            The key must refer to an existing child node for successful removal.
     * @return {@code true} if a child node was successfully removed;
     * {@code false} if no matching node was found.
     * @throws NullPointerException if the {@code key} argument is null.
     */
    boolean removeChildNode(@NotNull String key);

    /**
     * Removes the specified child configuration node from the current node's hierarchy.
     * If the child node exists within the current node's children, it will be removed
     * and the operation will return true. If the child node does not exist, no changes
     * will be made and the method will return false.
     *
     * @param child the non-null {@link ConfigNode} object to be removed; must not be null
     * @return true if the child node was successfully removed, false if the child node
     * does not exist within the current node's children
     * @throws NullPointerException if the {@code child} argument is null
     */
    boolean removeChildNode(@NotNull ConfigNode<?> child);

    /**
     * Returns an iterator for iterating over the child configuration nodes of the current object.
     * The iterator allows traversal through the immediate descendants in the hierarchical
     * configuration structure.
     *
     * @return a non-null iterator of non-null {@link ConfigNode} objects that represent the
     * child nodes of this object.
     */
    @Override
    @NotNull Iterator<@NotNull ConfigNode<?>> iterator();
}
