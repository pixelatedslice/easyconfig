package com.pixelatedslice.easyconfig.api.config.node;

import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface MutableConfigNodeValueUpdate<T> {
    @Nullable T newValue();
}
