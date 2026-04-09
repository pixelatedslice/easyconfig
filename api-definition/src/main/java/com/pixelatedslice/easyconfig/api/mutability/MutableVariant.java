package com.pixelatedslice.easyconfig.api.mutability;

@FunctionalInterface
public interface MutableVariant extends AutoCloseable {
    void apply();

    @Override
    default void close() {
        this.apply();
    }
}
