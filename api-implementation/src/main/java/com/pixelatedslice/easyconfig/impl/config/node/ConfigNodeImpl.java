package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.MutableConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.MutableConfigNodeValueUpdate;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.impl.comments.AbstractCommentable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ConfigNodeImpl<T> extends AbstractCommentable implements ConfigNode<T> {
    private final @NonNull String key;
    private final @NonNull TypeToken<T> typeToken;
    private final @Nullable ConfigSection parent;
    private final @NonNull BlockingQueue<@Nullable MutableConfigNodeValueUpdate<T>> valueUpdateQueue =
            new LinkedBlockingQueue<>();
    private volatile @Nullable T value;

    public ConfigNodeImpl(
            @NonNull String key,
            @NonNull TypeToken<T> typeToken,
            @Nullable T value,
            @Nullable T defaultValue,
            @Nullable ConfigSection parent,
            @NonNull List<@NonNull String> comments
    ) {
        super(key, comments);

        this.key = key;
        this.typeToken = typeToken;
        this.value = value;
        this.parent = parent;

        this.startProcessorThread();
    }

    private void startProcessorThread() {
        Thread.ofVirtual()
                .name(String.format("virtual-thread-%s-newValue-queue-processor", this.key))
                .start(this::processValueUpdateQueue);
    }

    void pushChangesToQueue(
            @NonNull MutableConfigNodeValueUpdate<T> newValue,
            @NonNull Iterable<? extends @NonNull Consumer<@NonNull Collection<@NonNull String>>> commentUpdates
    ) {
        Thread.ofVirtual().start(() -> {
            try {
                this.valueUpdateQueue.put(newValue);
            } catch (InterruptedException e) {
                System.out.printf("Got interrupted while trying to add a update for the newValue of %s", this.key);
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

    private void processValueUpdateQueue() {
        while (true) {
            try {
                this.value = this.valueUpdateQueue.take().newValue();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("%s interrupted!%n", Thread.currentThread().getName());
                break;
            }
        }
    }

    @Override
    public @NonNull String key() {
        return this.key;
    }

    @Override
    public @NonNull Optional<T> value() {
        return Optional.ofNullable(this.value);
    }

    @Override
    public @NonNull Optional<T> defaultValue() {
        return Optional.empty();
    }

    @Override
    public @NonNull Optional<? extends @NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public @NonNull TypeToken<T> typeToken() {
        return this.typeToken;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigNode<?> that)
                && this.key.equals(that.key())
                && Objects.equals(this.parent, that.parent().orElse(null))
        );

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.parent, this.typeToken);
    }

    @Override
    public MutableConfigNode<T> mutable() {
        return new MutableConfigNodeImpl<>(this);
    }
}
