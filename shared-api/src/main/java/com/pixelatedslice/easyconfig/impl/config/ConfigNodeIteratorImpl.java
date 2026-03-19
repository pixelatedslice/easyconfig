package com.pixelatedslice.easyconfig.impl.config;

import com.pixelatedslice.easyconfig.api.config.ConfigNode;
import com.pixelatedslice.easyconfig.impl.exception.BrokenNodeKeyException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class ConfigNodeIteratorImpl implements Iterator<ConfigNode> {
    /**
     * A regular expression pattern used to validate the format of node keys
     * in the configuration node hierarchy.
     * <p>
     * The keys must adhere to the following format:
     * - Allowed characters: alphanumeric characters (a-z, A-Z, 0-9),
     * dots (.), underscores (_), and hyphens (-).
     * - Keys may contain any combination of the allowed characters.
     * - A key cannot end with a dot (.) or be empty.
     * <p>
     * This pattern ensures that every key has at least one alphanumeric
     * character and avoids invalid trailing dots.
     */
    private static final Pattern NODE_KEY_REGEX = Pattern.compile("^[a-zA-Z0-9._-]*[a-zA-Z0-9]$");
    private final Deque<ConfigNode> nodeStack;

    public ConfigNodeIteratorImpl(@NotNull List<@NotNull ConfigNode> initialNodes) {
        this.nodeStack = new ArrayDeque<>(initialNodes);
    }

    public ConfigNodeIteratorImpl(@NotNull ConfigNode rootNode) {
        this.nodeStack = new ArrayDeque<>(Collections.singleton(rootNode));
    }

    /**
     * Navigates through the hierarchy of configuration nodes to find a child node
     * based on the provided keys.
     * <p>
     * If the provided keys contain a single string with dot-separated segments,
     * the string is split into individual keys. Each key is matched against
     * child nodes recursively to locate the desired node. If any key is invalid
     * (i.e., does not conform to the required regex pattern), a
     * {@link BrokenNodeKeyException} is thrown. If no matching child node can
     * be found at any level, an empty {@link Optional} is returned.
     *
     * @param providedKeys The keys used to traverse the node hierarchy. Multiple keys
     *                     can be provided as separate arguments, or a single key
     *                     can be provided in a dot-separated format. At least one
     *                     valid key is required.
     * @return An {@link Optional} containing the matching {@link ConfigNode} if found;
     * otherwise, an empty {@link Optional}.
     * @throws BrokenNodeKeyException If any provided key does not match the key
     *                                validation regex.
     */
    public static @NotNull Optional<@NotNull ConfigNode> findNode(
            @NotNull List<@NotNull ConfigNode> childNodes,
            @NotNull String... providedKeys
    ) {
        if (providedKeys.length == 0) {
            return Optional.empty();
        }

        List<String> keys;
        if (providedKeys.length == 1) {
            String key = providedKeys[0];
            if (!NODE_KEY_REGEX.matcher(key).matches()) {
                throw new BrokenNodeKeyException(key, NODE_KEY_REGEX.pattern());
            }
            keys = List.of(key.split("\\."));
        } else {
            keys = List.of(providedKeys);
        }

        List<ConfigNode> currentChildren = childNodes;

        int last = keys.size() - 1;
        for (int i = 0; i <= last; i++) {
            String wanted = keys.get(i);

            ConfigNode next = null;
            for (ConfigNode node : currentChildren) {
                if (node.key().equals(wanted)) {
                    next = node;
                    break;
                }
            }

            if (next == null) {
                return Optional.empty();
            }

            if (i == last) {
                return Optional.of(next);
            }

            currentChildren = next.children();
        }

        return Optional.empty();
    }

    @Override
    public boolean hasNext() {
        return !this.nodeStack.isEmpty();
    }

    @Override
    public ConfigNode next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        @NotNull ConfigNode current = this.nodeStack.pop();
        List<@NotNull ConfigNode> children = current.children();

        if (!children.isEmpty()) {
            for (int i = children.size() - 1; i >= 0; i--) {
                this.nodeStack.push(children.get(i));
            }
        }

        return current;
    }
}
