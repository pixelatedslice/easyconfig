package com.pixelatedslice.easyconfig.api.mutability.mutable;

import com.pixelatedslice.easyconfig.api.mutability.immutable.ImmutableVariant;

@FunctionalInterface
public interface WithMutableVariant<M extends MutableVariant> extends ImmutableVariant {
    M mutable();
}
