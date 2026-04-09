package com.pixelatedslice.easyconfig.api.config.node;

import com.pixelatedslice.easyconfig.api.mutability.immutable.WithImmutableVariant;
import com.pixelatedslice.easyconfig.api.mutability.mutable.MutableVariant;
import org.jspecify.annotations.Nullable;

public interface MutableConfigNode<T>
        extends MutableVariant, WithImmutableVariant<ConfigNode<T>> {
    void setValue(@Nullable T value);
}
