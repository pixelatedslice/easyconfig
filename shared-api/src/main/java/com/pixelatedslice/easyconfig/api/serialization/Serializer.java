package com.pixelatedslice.easyconfig.api.serialization;

import com.pixelatedslice.easyconfig.api.encoding.Decoder;
import com.pixelatedslice.easyconfig.api.encoding.Encoder;
import org.jetbrains.annotations.NotNull;

public interface Serializer<T> {
    void serialize(Encoder encoder, @NotNull T value);

    T deserialize(Decoder decoder);
}