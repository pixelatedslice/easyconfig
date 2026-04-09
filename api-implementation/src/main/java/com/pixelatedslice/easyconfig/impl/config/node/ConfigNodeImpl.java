package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.MutableConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConfigNodeImpl<T> implements ConfigNode<T> {
    protected final String key;
    protected final @NonNull ConfigNodeDescriptor<T> descriptor;
    protected final @Nullable ConfigSection parent;
    private final BlockingQueue<T> valueUpdateQueue = new LinkedBlockingQueue<>();
    protected volatile @Nullable T value;

    public ConfigNodeImpl(
            @NonNull ConfigNodeDescriptor<T> descriptor,
            @Nullable T value,
            @Nullable ConfigSection parent
    ) {
        this.key = descriptor.key();
        this.descriptor = descriptor;
        this.value = value;
        this.parent = parent;

        this.startProcessorThread();
    }

    public ConfigNodeImpl(ConfigNodeImpl<T> other) {
        this.key = other.descriptor.key();
        this.descriptor = other.descriptor;
        this.value = other.value;
        this.parent = other.parent;
    }

    private void startProcessorThread() {
        Thread.ofVirtual()
                .name(String.format("virtual-thread-%s-value-queue-processor", this.key))
                .start(this::processValueUpdateQueue);
    }

    void addValueUpdateTask(T newValue) {
        try {
            this.valueUpdateQueue.put(newValue);
        } catch (InterruptedException e) {
            System.out.printf("Got interrupted while trying to add a update for the value of %s", this.key);
        }
    }

    private void processValueUpdateQueue() {
        while (true) {
            try {
                this.value = this.valueUpdateQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("%s interrupted!%n", Thread.currentThread().getName());
                break;
            }
        }
    }

    @Override
    public @NonNull Optional<T> value() {
        return Optional.ofNullable(this.value);
    }

    @Override
    public @NonNull Optional<? extends @NonNull ConfigSection> parent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public @NonNull ConfigNodeDescriptor<T> descriptor() {
        return this.descriptor;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigNodeImpl<?> that)
                && this.descriptor.equals(that.descriptor)
                && Objects.equals(this.parent, that.parent)
        );

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.descriptor, this.parent);
    }

    @Override
    public MutableConfigNode<T> mutable() {
        return new MutableConfigNodeImpl<>(this);
    }
}
