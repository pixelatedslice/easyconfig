package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.node.MutableConfigNode;
import org.jspecify.annotations.Nullable;

public class MutableConfigNodeImpl<T> implements MutableConfigNode<T> {
    private final ConfigNodeImpl<? super T> originalNode;
    private T value;

    public MutableConfigNodeImpl(ConfigNodeImpl<? super T> originalNode) {
        this.originalNode = originalNode;
    }

    @Override
    public void setValue(@Nullable T value) {
        this.value = value;
    }

    @Override
    public void apply() {
        this.originalNode.addValueUpdateTask(this.value);
    }
}
