package com.pixelatedslice.easyconfig.api.config.node.mutable;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.mutability.MutableVariant;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface MutableConfigNode<T>
        extends ConfigNode<T>, MutableVariant {
    @NonNull Optional<@NonNull T> value();

    void setValue(@Nullable T value);
}
