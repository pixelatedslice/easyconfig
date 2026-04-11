package com.pixelatedslice.easyconfig.impl.config.section;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.MutableConfigSection;
import com.pixelatedslice.easyconfig.impl.comments.AbstractCommentable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ConfigSectionImpl extends AbstractCommentable implements ConfigSection {
    private final @NonNull String key;
    private final @NonNull List<@NonNull ConfigNode<?>> nodes;
    private final @NonNull List<@NonNull ConfigSection> sections;
    private final @Nullable ConfigSection parent;
    private final int hashCode;

    public ConfigSectionImpl(
            @NonNull String key,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull ConfigNode<?>> nodes,
            @NonNull List<@NonNull ConfigSection> sections,
            @NonNull List<@NonNull String> comments
    ) {
        super(comments);

        this.key = key;
        this.parent = parent;
        this.nodes = nodes;
        this.sections = sections;

        this.hashCode = Objects.hash(this.key, this.parent);
    }

    public static ConfigSection newRootSection() {
        return new ConfigSectionImpl("root",
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    void pushChangesToQueue(
            @NonNull Collection<? extends @NonNull Consumer<@NonNull Collection<@NonNull ConfigNode<?>>>> nodeUpdates,
            @NonNull Collection<? extends @NonNull Consumer<@NonNull Collection<@NonNull ConfigSection>>> sectionUpdates,
            @NonNull Collection<? extends @NonNull Consumer<@NonNull Collection<@NonNull String>>> commentUpdates
    ) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            if (!nodeUpdates.isEmpty()) {
                executor.submit(() -> {
                    synchronized (this.nodes) {
                        for (var consumer : nodeUpdates) {
                            consumer.accept(this.nodes);
                        }
                    }
                });
            }

            if (!sectionUpdates.isEmpty()) {
                executor.submit(() -> {
                    synchronized (this.sections) {
                        for (var consumer : sectionUpdates) {
                            consumer.accept(this.sections);
                        }
                    }
                });
            }

            if (!commentUpdates.isEmpty()) {
                executor.submit(() -> this.updateComments(commentUpdates));
            }
        }
    }

    @Override
    public @NonNull List<@NonNull ConfigNode<?>> nodes() {
        synchronized (this.nodes) {
            return List.copyOf(this.nodes);
        }
    }

    @Override
    public @NonNull List<@NonNull ConfigSection> sections() {
        synchronized (this.sections) {
            return List.copyOf(this.sections);
        }
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
        return this.hashCode;
    }

    @Override
    public MutableConfigSection mutable() {
        return new MutableConfigSectionImpl(this);
    }
}
