package com.pixelatedslice.easyconfig.impl.fileformat.common;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;
import tools.jackson.core.JsonGenerator;

import java.util.Map;

public class JacksonTreeWriter {
    private final @NonNull JsonGenerator generator;
    private final @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers;

    public JacksonTreeWriter(@NonNull JsonGenerator generator,
                             @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers) {
        this.generator = generator;
        this.serializers = serializers;
    }


}
