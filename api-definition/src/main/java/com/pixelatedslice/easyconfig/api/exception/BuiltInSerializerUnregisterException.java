package com.pixelatedslice.easyconfig.api.exception;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Exception thrown when an attempt is made to unregister built-in serializers.
 * Built-in serializers are predefined and cannot be removed from the configuration.
 * This restriction ensures the stability and integrity of the serialization system.
 * <p>
 * The exception provides detailed information about the offending classes for which
 * the unregistering attempt was made.
 */
public class BuiltInSerializerUnregisterException extends RuntimeException {
    public BuiltInSerializerUnregisterException(@NotNull Collection<@NotNull Class<?>> classes) {
        super(message(classes));
    }

    private static String message(@NotNull Collection<@NotNull Class<?>> classes) {
        return "The built-in serializers cannot be unregistered!\n" +
                "Tried unregistering the serializers for the classes: " +
                String.join("\", \"", classes.stream().map(Class::getName).toList());
    }
}