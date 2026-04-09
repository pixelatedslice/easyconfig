package com.pixelatedslice.easyconfig.api.mutability.immutable;

import com.pixelatedslice.easyconfig.api.mutability.mutable.MutableVariant;

@FunctionalInterface
public interface WithImmutableVariant<I extends ImmutableVariant> extends MutableVariant {
}
