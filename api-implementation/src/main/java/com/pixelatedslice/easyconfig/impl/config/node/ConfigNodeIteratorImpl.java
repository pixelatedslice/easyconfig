package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

/**
 * Implementation of the {@link ConfigNodeIterator} interface that provides
 * functionality to iterate over {@link ConfigNode} instances in a hierarchical
 * configuration structure using a breadth-first traversal approach. The iterator
 * manages both configuration nodes containing values and nested sections, ensuring
 * that all relevant nodes are visited systematically.
 * <p>
 * The iteration starts from a specified root configuration section, from which
 * all child nodes and nested sections are traversed.
 */
public class ConfigNodeIteratorImpl implements ConfigNodeIterator {
    /**
     * A queue to manage {@link ConfigSection} instances during the traversal of configuration
     * structures. This queue supports a breadth-first traversal approach, where each configuration
     * section is processed to extract child nodes and nested sections for further exploration.
     * <p>
     * This queue is populated by methods in the {@code ConfigNodeIteratorImpl} class, specifically
     * to facilitate traversal operations in hierarchical configuration data.
     * <p>
     * The queue ensures proper order of processing for nested sections and provides a mechanism
     * for managing traversal at different levels of the configuration hierarchy.
     */
    private final @NonNull Queue<@NonNull ConfigSection> sectionQueue = new LinkedList<>();
    /**
     * A queue that maintains {@link ConfigNode} instances to be processed in a
     * breadth-first manner. This queue is used internally by the {@code ConfigNodeIteratorImpl}
     * to hold configuration nodes containing values from the current level of the configuration
     * structure. It is dynamically populated and modified during iteration to ensure
     * traversal over all relevant configuration nodes.
     * <p>
     * The {@code nodeQueue} works in conjunction with the section queue to support the
     * iterative traversal of both configuration nodes and nested sections. Nodes that
     * contain values are enqueued here when encountered, allowing their retrieval
     * in breadth-first order. Nested sections are handled via the section queue and
     * processed to populate this queue as needed.
     * <p>
     * This queue operates as a core structure for managing configuration node traversal
     * state within the {@code ConfigNodeIteratorImpl}.
     */
    private final @NonNull Queue<@NonNull ConfigNode<?>> nodeQueue = new LinkedList<>();

    /**
     * Creates an instance of {@code ConfigNodeIteratorImpl} and initializes it
     * with the provided root configuration section. The root section is enqueued
     * for traversal.
     *
     * @param rootContainer the non-null root {@link ConfigSection} from which the iterator
     *                      will begin traversing the configuration nodes
     * @throws NullPointerException if {@code rootContainer} is null
     */
    public ConfigNodeIteratorImpl(@NonNull ConfigSection rootContainer) {
        Objects.requireNonNull(rootContainer);
        this.enqueueSection(rootContainer);
    }

    /**
     * Enqueues the provided {@code ConfigSection} into the section queue for further processing.
     *
     * @param container the non-null {@code ConfigSection} to be added to the section queue
     * @throws NullPointerException if {@code container} is null
     */
    private void enqueueSection(@NonNull ConfigSection container) {
        this.sectionQueue.add(container);
    }

    /**
     * Populates the internal node queue with all child {@code ConfigNode} instances containing
     * values from the current level of configuration sections. If the internal node queue is
     * empty and the section queue has remaining sections, the method processes sections
     * in a breadth-first manner. It also enqueues nested sections for further traversal.
     * <p>
     * The method steps through the current section queue, retrieving child nodes with values
     * and adding them to the node queue. Nested sections are pushed back onto the section queue
     * for further processing.
     * <p>
     * This method ensures that the node queue is populated with valid configuration nodes
     * containing values while preparing for subsequent level traversal.
     */
    private void fillNodeQueueFromCurrentLevel() {
        while (this.nodeQueue.isEmpty() && !this.sectionQueue.isEmpty()) {
            ConfigSection current = this.sectionQueue.poll();

            for (ConfigNode<?> node : current.childNodes()) {
                if (node.value().isPresent()) {
                    this.nodeQueue.add(node);
                }
            }

            for (ConfigSection nested : current.nestedSections()) {
                this.enqueueSection(nested);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method checks if there are any nodes in the internal node queue. If not,
     * it fills the node queue with the nested sections of the current section.
     */
    @Override
    public boolean hasNext() {
        if (this.nodeQueue.isEmpty()) {
            this.fillNodeQueueFromCurrentLevel();
        }
        return !this.nodeQueue.isEmpty();
    }


    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method polls the top node from the internal node queue. If the internal
     * node queue is empty, it fills it with the nested sections of the current section.
     */
    @Override
    public @Nullable ConfigNode<?> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        var node = this.nodeQueue.poll();

        if (this.nodeQueue.isEmpty()) {
            this.fillNodeQueueFromCurrentLevel();
        }

        return node;
    }
}
