package com.pixelatedslice.easyconfig.api.mutability.mutable;

@FunctionalInterface
public interface WithMutableVariant<M extends MutableVariant> {
    M asMutable();
}
