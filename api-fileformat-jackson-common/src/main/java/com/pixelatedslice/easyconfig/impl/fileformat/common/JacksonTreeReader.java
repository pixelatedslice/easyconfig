package com.pixelatedslice.easyconfig.impl.fileformat.common;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;
import tools.jackson.core.JsonParser;

import java.util.Map;

public class JacksonTreeReader {
    private final @NonNull JsonParser parser;
    private final @NonNull Map<@NonNull TypeToken<?>,
            @NonNull Serializer<?>> serializers;

    public JacksonTreeReader(@NonNull JsonParser parser,
                             @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers) {
        this.parser = parser;
        this.serializers = serializers;
    }
}
