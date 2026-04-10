package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.MutableConfigSection;
import com.pixelatedslice.easyconfig.impl.comments.AbstractCommentable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ConfigSectionImpl extends AbstractCommentable implements ConfigSection {
    private final @NonNull String key;
    private final @NonNull List<@NonNull ConfigNode<?>> nodes;
    private final @NonNull List<@NonNull ConfigSection> sections;
    private final @Nullable ConfigSection parent;

    private final @NonNull BlockingQueue<@NonNull Consumer<@NonNull Collection<@NonNull ConfigNode<?>>>>
            nodeUpdateQueue = new LinkedBlockingQueue<>();
    private final @NonNull BlockingQueue<@NonNull Consumer<@NonNull Collection<@NonNull ConfigSection>>>
            sectionUpdateQueue = new LinkedBlockingQueue<>();

    public ConfigSectionImpl(
            @NonNull String key,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull ConfigNode<?>> nodes,
            @NonNull List<@NonNull ConfigSection> sections,
            @NonNull List<@NonNull String> comments
    ) {
        super(key, comments);

        this.key = key;
        this.parent = parent;
        this.nodes = nodes;
        this.sections = sections;

        this.startProcessorThreads();
    }

    public static ConfigSection newRootSection() {
        return new ConfigSectionImpl("root",
                null,
                new ArrayList<>(),
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

                    Collection<Consumer<Collection<ConfigNode<?>>>> drained =
                            new ArrayList<>(EasyConfig.QUEUE_DRAINED_INITIAL_CAPACITY);
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

                    Collection<Consumer<Collection<ConfigSection>>> drained =
                            new ArrayList<>(EasyConfig.QUEUE_DRAINED_INITIAL_CAPACITY);
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
            @NonNull Iterable<? extends @NonNull Consumer<@NonNull Collection<@NonNull ConfigSection>>> sectionUpdates,
            @NonNull Iterable<? extends @NonNull Consumer<@NonNull Collection<@NonNull String>>> commentUpdates
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
        Thread.ofVirtual().start(() -> {
            try {
                super.pushChangesToQueue(commentUpdates);
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
    public @NonNull String key() {
        return this.key;
    }

    @Override
    public @NonNull Optional<@NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigSection that)
                && this.key.equals(that.key())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.parent);
    }

    @Override
    public MutableConfigSection mutable() {
        return new MutableConfigSectionImpl(this);
    }
}
