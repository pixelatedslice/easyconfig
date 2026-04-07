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

public class ConfigNodeIteratorImpl implements ConfigNodeIterator {

    private final @NonNull Queue<@NonNull ConfigSection> sectionQueue = new LinkedList<>();

    private final @NonNull Queue<@NonNull ConfigNode<?>> nodeQueue = new LinkedList<>();


    public ConfigNodeIteratorImpl(@NonNull ConfigSection rootContainer) {
        Objects.requireNonNull(rootContainer);
        this.enqueueSection(rootContainer);
    }


    private void enqueueSection(@NonNull ConfigSection container) {
        this.sectionQueue.add(container);
    }


    private void fillNodeQueueFromCurrentLevel() {
        while (this.nodeQueue.isEmpty() && !this.sectionQueue.isEmpty()) {
            ConfigSection current = this.sectionQueue.poll();

            this.nodeQueue.addAll(current.nodes());
            
            for (ConfigSection nested : current.sections()) {
                this.enqueueSection(nested);
            }
        }
    }


    @Override
    public boolean hasNext() {
        if (this.nodeQueue.isEmpty()) {
            this.fillNodeQueueFromCurrentLevel();
        }
        return !this.nodeQueue.isEmpty();
    }


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
