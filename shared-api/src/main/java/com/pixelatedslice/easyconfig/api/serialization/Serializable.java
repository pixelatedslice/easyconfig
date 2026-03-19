package com.pixelatedslice.easyconfig.api.serialization;

public interface Serializable<T extends Serializable<T>> {
    Serializer<T> serializer();
}
