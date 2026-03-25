package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import org.jspecify.annotations.NonNull;

import java.util.*;

/**
 * <b>Implementation Details:</b> Uses a stack to implement depth-first traversal of the configuration tree.
 *
 * @see ConfigNodeIterator ConfigNodeIterator for contract details.
 */
public class ConfigSectionIteratorImpl implements ConfigSectionIterator {
    private final Deque<ConfigSection> nodeStack;

    /**
     * Constructs a new {@code ConfigSectionIteratorImpl} for depth-first traversal
     * of the configuration tree starting from the specified root section.
     *
     * @param rootSection the non-null root configuration section from which traversal begins
     * @throws NullPointerException if {@code rootSection} is null
     */
    public ConfigSectionIteratorImpl(@NonNull ConfigSection rootSection) {
        Objects.requireNonNull(rootSection);
        this.nodeStack = new ArrayDeque<>(rootSection.nestedSections());
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
    public @NonNull ConfigSection next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        ConfigSection current = this.nodeStack.pop();
        List<@NonNull ConfigSection> children = current.nestedSections();

        if (!children.isEmpty()) {
            for (int i = children.size() - 1; i >= 0; i--) {
                this.nodeStack.push(children.get(i));
            }
        }

        return current;
    }
}
