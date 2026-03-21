package com.pixelatedslice.easyconfig.impl.config;

import com.pixelatedslice.easyconfig.api.config.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.ConfigNodeIterator;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * <b>Implementation Details:</b> Uses a stack to implement depth-first traversal of the configuration tree.
 *
 * @see ConfigNodeIterator ConfigNodeIterator for contract details.
 */
public class ConfigNodeIteratorImpl implements ConfigNodeIterator {
    private final Deque<ConfigNode<?>> nodeStack;

    /**
     * Constructs an instance of {@code ConfigNodeIteratorImpl} with an initial set of configuration nodes.
     * The iterator starts traversing nodes from the provided list of root configuration nodes in a depth-first manner.
     *
     * @param initialNodes the non-null list of root configuration nodes to initialize the iterator with,
     *                     where traversal begins; each node in the list must also be non-null
     */
    public ConfigNodeIteratorImpl(@NotNull List<@NotNull ConfigNode<?>> initialNodes) {
        Objects.requireNonNull(initialNodes);
        this.nodeStack = new ArrayDeque<>(initialNodes);
    }

    /**
     * Constructs an instance of {@code ConfigNodeIteratorImpl} with the given root configuration node.
     * This iterator starts from the provided root node and allows traversing its subtree in a depth-first manner.
     *
     * @param rootNode the non-null root configuration node from which iteration begins
     */
    public ConfigNodeIteratorImpl(@NotNull ConfigNode<?> rootNode) {
        Objects.requireNonNull(rootNode);
        this.nodeStack = new ArrayDeque<>(Collections.singleton(rootNode));
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method checks if the internal stack is empty, indicating that no more nodes.
     */
    @Override
    public boolean hasNext() {
        return !this.nodeStack.isEmpty();
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method pops the top node from the internal stack and adds its children in
     * reverse order to the stack.
     */
    @Override
    public ConfigNode<?> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        @NotNull ConfigNode<?> current = this.nodeStack.pop();
        List<@NotNull ConfigNode<?>> children = current.children();

        if (!children.isEmpty()) {
            for (int i = children.size() - 1; i >= 0; i--) {
                this.nodeStack.push(children.get(i));
            }
        }

        return current;
    }
}
