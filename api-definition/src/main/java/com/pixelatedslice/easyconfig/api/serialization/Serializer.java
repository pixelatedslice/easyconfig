package com.pixelatedslice.easyconfig.api.serialization;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.serialization.format.FormatSerializer;
import com.pixelatedslice.easyconfig.api.serialization.node.NodeSerializer;
import org.jspecify.annotations.NonNull;

public sealed interface Serializer<T>
        permits BuiltInSerializer, FormatSerializer, NodeSerializer {
    @NonNull TypeToken<T> supportedType();

    @NonNull SerializerType serializerType();
}
