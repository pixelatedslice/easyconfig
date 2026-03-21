package com.pixelatedslice.easyconfig.api.config;

import com.pixelatedslice.easyconfig.api.exception.BrokenNodeKeyException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * An interface for traversing and searching within a hierarchy of configuration nodes.
 * This iterator supports depth-first iteration and provides utility methods for
 * locating specific nodes based on hierarchical keys.
 */
public interface ConfigNodeIterator extends Iterator<ConfigNode<?>> {
    /**
     * A regular expression pattern used to validate the format of configuration node keys.
     * The key must:
     * - Contain alphanumeric characters, periods (.), underscores (_), or hyphens (-).
     * - End with an alphanumeric character (letters or digits).
     * <p>
     * This pattern ensures that node keys adhere to the expected structure, which is required
     * for proper parsing and navigation within the configuration hierarchy.
     */
    Pattern NODE_KEY_REGEX = Pattern.compile("^[a-zA-Z0-9._-]*[a-zA-Z0-9]$");

    /**
     * Searches for a configuration node within a hierarchy of child nodes by traversing a path
     * defined by a sequence of keys. The method starts with the given list of child nodes and
     * attempts to navigate through the hierarchy using the provided keys in order. If a match
     * is found, the corresponding {@link ConfigNode} is returned wrapped in an {@link Optional}.
     * If no match is found or if any intermediate key is not found, an empty {@link Optional} is returned.
     *
     * @param <T>          the type of the value associated with the configuration node
     * @param childNodes   the list of child nodes to start the search from; must not be null and must not
     *                     contain null elements
     * @param providedKeys the sequence of keys to navigate through the hierarchy; must not be null and
     *                     must contain at least one element
     * @return an {@link Optional} containing the found {@link ConfigNode}, or an empty {@link Optional}
     * if the node is not found
     * @throws NullPointerException if {@code childNodes} or {@code providedKeys} is null
     */
    @SuppressWarnings("unchecked")
    static <T> @NotNull Optional<@NotNull ConfigNode<T>> findNode(
            @NotNull List<@NotNull ConfigNode<?>> childNodes,
            @NotNull String... providedKeys
    ) {
        Objects.requireNonNull(childNodes);
        Objects.requireNonNull(providedKeys);

        if (providedKeys.length == 0) {
            return Optional.empty();
        }

        List<String> keys = List.of(providedKeys);
        List<ConfigNode<?>> currentChildren = childNodes;

        int last = keys.size() - 1;
        for (int i = 0; i <= last; i++) {
            String wanted = keys.get(i);

            ConfigNode<?> next = null;
            for (ConfigNode<?> node : currentChildren) {
                if (node.key().equals(wanted)) {
                    next = node;
                    break;
                }
            }

            if (next == null) {
                return Optional.empty();
            }

            if (i == last) {
                return Optional.of((ConfigNode<T>) next);
            }

            currentChildren = next.children();
        }

        return Optional.empty();
    }

    /**
     * Searches for a configuration node among a list of child nodes using a key that represents a
     * hierarchical path. The specified key may represent a single node or a nested series of nodes.
     * If the node is found, it is returned wrapped in an {@link Optional}. If no node matches the
     * provided key, an empty {@link Optional} is returned. The method enforces the validity of the
     * key format based on a predefined regular expression.
     *
     * @param <T>        the type of the value associated with the configuration node
     * @param childNodes the list of child nodes to search from; must not be null and must not
     *                   contain null elements
     * @param key        the hierarchical key used to locate the target configuration node; must
     *                   not be null and must conform to a valid format
     * @return an {@link Optional} containing the found {@link ConfigNode}, or an empty {@link Optional}
     * if no matching node is found
     * @throws NullPointerException   if {@code childNodes} or {@code key} is null
     * @throws BrokenNodeKeyException if {@code key} does not match the required format
     */
    static <T> @NotNull Optional<@NotNull ConfigNode<T>> findNodeButInTheBukkitAPIStyle(
            @NotNull List<@NotNull ConfigNode<?>> childNodes,
            @NotNull String key
    ) {
        Objects.requireNonNull(childNodes);
        Objects.requireNonNull(key);

        List<String> keys;
        if (!NODE_KEY_REGEX.matcher(key).matches()) {
            throw new BrokenNodeKeyException(key, NODE_KEY_REGEX.pattern());
        }
        keys = List.of(key.split("\\."));

        return findNode(childNodes, keys.toArray(new String[0]));
    }

    /**
     * Checks if there are more elements to iterate over in the configuration node hierarchy.
     *
     * @return {@code true} if there are more elements to iterate, {@code false} otherwise
     */
    @Override
    boolean hasNext();

    /**
     * Retrieves the next configuration node in the iteration sequence.
     * This method progresses the iterator to the next element, if available,
     * and returns it. If there are no more elements to iterate, the method
     * may return {@code null}.
     *
     * @return the next {@link ConfigNode} in the sequence, or {@code null} if no more nodes are available
     */
    @Override
    @Nullable ConfigNode<?> next();
}
