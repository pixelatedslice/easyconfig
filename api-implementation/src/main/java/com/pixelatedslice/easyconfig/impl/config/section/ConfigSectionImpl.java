package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptor;
import com.pixelatedslice.easyconfig.impl.descriptor.section.ConfigSectionDescriptorImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ConfigSectionImpl implements ConfigSection {
    public static final int DRAINED_INITIAL_CAPACITY = 32;
    private final String key;
    private final @NonNull ConfigSectionDescriptor descriptor;
    private final @NonNull List<@NonNull ConfigNode<?>> nodes;
    private final @NonNull List<@NonNull ConfigSection> sections;
    private final @Nullable ConfigSection parent;

    private final @NonNull BlockingQueue<@NonNull Consumer<@NonNull Collection<@NonNull ConfigNode<?>>>>
            nodeUpdateQueue = new LinkedBlockingQueue<>();
    private final @NonNull BlockingQueue<@NonNull Consumer<@NonNull Collection<@NonNull ConfigSection>>>
            sectionUpdateQueue = new LinkedBlockingQueue<>();

    public ConfigSectionImpl(
            @NonNull ConfigSectionDescriptor descriptor,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull ConfigNode<?>> nodes,
            @NonNull List<@NonNull ConfigSection> sections
    ) {
        this.key = descriptor.key();
        this.descriptor = descriptor;
        this.parent = parent;
        this.nodes = nodes;
        this.sections = sections;

        this.startProcessorThreads();
    }

    public ConfigSectionImpl(ConfigSection other) {
        this.key = other.descriptor().key();
        this.descriptor = other.descriptor();
        this.parent = other.parent().orElse(null);
        this.nodes = new ArrayList<>(other.nodes());
        this.sections = new ArrayList<>(other.sections());
    }

    public static ConfigSection newRootSection() {
        return new ConfigSectionImpl(ConfigSectionDescriptorImpl.newRootSectionDescriptor(),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private void startProcessorThreads() {
        Thread.ofVirtual()
                .name(String.format("virtual-thread-%s-node-update-queue-processor", this.key))
                .start(this::processNodeUpdateQueue);
        Thread.ofVirtual()
                .name(String.format("virtual-thread-%s-section-update-queue-processor", this.key))
                .start(this::processSectionUpdateQueue);
    }

    private void processNodeUpdateQueue() {
        while (true) {
            try {
                var consumer = this.nodeUpdateQueue.take();

                synchronized (this.nodes) {
                    consumer.accept(this.nodes);

                    Collection<Consumer<Collection<ConfigNode<?>>>> drained = new ArrayList<>(DRAINED_INITIAL_CAPACITY);
                    this.nodeUpdateQueue.drainTo(drained);

                    for (var drainedConsumer : drained) {
                        drainedConsumer.accept(this.nodes);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("The node update queue processing for section %s interrupted!%n", this.key);
                break;
            }
        }
    }

    private void processSectionUpdateQueue() {
        while (true) {
            try {
                var consumer = this.sectionUpdateQueue.take();
                synchronized (this.sections) {
                    consumer.accept(this.sections);

                    Collection<Consumer<Collection<ConfigSection>>> drained = new ArrayList<>(DRAINED_INITIAL_CAPACITY);
                    this.sectionUpdateQueue.drainTo(drained);

                    for (var drainedConsumer : drained) {
                        drainedConsumer.accept(this.sections);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("The section update queue processing for section %s interrupted!%n", this.key);
                break;
            }
        }
    }

    void pushChangesToQueue(
            @NonNull Iterable<? extends @NonNull Consumer<@NonNull Collection<@NonNull ConfigNode<?>>>> nodeUpdates,
            @NonNull Iterable<? extends @NonNull Consumer<@NonNull Collection<@NonNull ConfigSection>>> sectionUpdates
    ) {
        Thread.ofVirtual().start(() -> {
            try {
                for (var nodeUpdate : nodeUpdates) {
                    this.nodeUpdateQueue.put(nodeUpdate);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread.ofVirtual().start(() -> {
            try {
                for (var sectionUpdate : sectionUpdates) {
                    this.sectionUpdateQueue.put(sectionUpdate);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NonNull List<@NonNull ConfigNode<?>> nodes() {
        return Collections.unmodifiableList(this.nodes);
    }

    @Override
    public @NonNull List<@NonNull ConfigSection> sections() {
        return Collections.unmodifiableList(this.sections);
    }

    @Override
    public @NonNull ConfigSectionDescriptor descriptor() {
        return this.descriptor;
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigSection that)
                && this.descriptor.equals(that.descriptor())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.descriptor, this.parent);
    }
}
