package com.pixelatedslice.easyconfig.api.mutability;

@FunctionalInterface
public interface WithMutableVariant<M extends MutableVariant> {
    M mutable();
}
