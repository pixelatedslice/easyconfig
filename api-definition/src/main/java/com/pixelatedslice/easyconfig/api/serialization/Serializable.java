package com.pixelatedslice.easyconfig.api.serialization;

import org.jspecify.annotations.NonNull;

public interface Serializable<T extends Serializable<T>> extends Serializer<T> {
    @SuppressWarnings("unchecked")
    @Override
    default @NonNull Class<T> forClass() {
        return (Class<T>) this.getClass();
    }
}