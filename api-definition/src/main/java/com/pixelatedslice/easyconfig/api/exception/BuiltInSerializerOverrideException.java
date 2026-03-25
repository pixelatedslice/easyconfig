package com.pixelatedslice.easyconfig.api.exception;

import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.Map;

/**
 * Exception thrown when an attempt is made to override built-in serializers with custom implementations.
 * Built-in serializers are predefined and cannot be replaced by user-defined serializers.
 * This restriction ensures the stability and consistency of serialization behavior.
 * <p>
 * The exception provides detailed information about the offending serializers that triggered
 * the override attempt. It includes the class name, the built-in serializer, and the overriding serializer.
 */
public class BuiltInSerializerOverrideException extends RuntimeException {
    private static final String FORMAT = "- %s: %s -> %s\n";

    public BuiltInSerializerOverrideException(@NonNull Map<
            ? extends @NonNull Serializer<?>, /* Built-in Serializer*/
            ? extends @NonNull Serializer<?> /* Overriding Serializer */
            > serializers
    ) {
        super(message(serializers));
    }

    private static String message(@NonNull Map<
            ? extends @NonNull Serializer<?>, /* Built-in Serializer*/
            ? extends @NonNull Serializer<?> /* Overriding Serializer */
            > serializers
    ) {
        var builder = new StringBuilder();
        builder.append("The built-in serializers cannot be overridden!\n");
        builder.append("Tried overriding the serializers for the classes:\n");
        builder.append("- Format: Class: Built-in Serializer -> Overriding Serializer\n");

        serializers.forEach((
                builtinSerializer,
                overridingSerializer
        ) -> builder.append(
                String.format(FORMAT,
                        builtinSerializer.forClass().getName(),
                        builtinSerializer.getClass().getSimpleName(),
                        overridingSerializer.getClass().getSimpleName()
                )
        ));

        return builder.toString();
    }
}