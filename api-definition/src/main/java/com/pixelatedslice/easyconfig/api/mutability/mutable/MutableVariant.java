package com.pixelatedslice.easyconfig.api.mutability.mutable;

@FunctionalInterface
public interface MutableVariant extends AutoCloseable {
    void apply();

    @Override
    default void close() {
        this.apply();
    }
}
