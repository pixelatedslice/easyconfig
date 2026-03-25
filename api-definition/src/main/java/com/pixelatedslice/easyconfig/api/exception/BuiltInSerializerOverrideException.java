package com.pixelatedslice.easyconfig.api.exception;

import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class BuiltInSerializerOverrideException extends RuntimeException {
    private static final String FORMAT = "- %s: %s -> %s\n";

    public BuiltInSerializerOverrideException(
            @NonNull Map<? extends @NonNull Serializer<?>, /* Built-in Serializer*/            ?
                    extends @NonNull Serializer<?> /* Overriding Serializer */> serializers) {
        super(message(serializers));
    }

    private static String message(
            @NonNull Map<? extends @NonNull Serializer<?>, /* Built-in Serializer*/            ?
                    extends @NonNull Serializer<?> /* Overriding Serializer */> serializers) {
        var builder = new StringBuilder();
        builder.append("The built-in serializers cannot be overridden!\n");
        builder.append("Tried overriding the serializers for the classes:\n");
        builder.append("- Format: Class: Built-in Serializer -> Overriding Serializer\n");
        serializers.forEach((builtinSerializer, overridingSerializer) -> builder.append(String.format(FORMAT,
                builtinSerializer.forClass().getName(),
                builtinSerializer.getClass().getSimpleName(),
                overridingSerializer.getClass().getSimpleName())));
        return builder.toString();
    }
}