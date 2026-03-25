package com.pixelatedslice.easyconfig.api.exception;

import org.jspecify.annotations.NonNull;

import java.util.Collection;

public class BuiltInSerializerUnregisterException extends RuntimeException {
    public BuiltInSerializerUnregisterException(@NonNull Collection<@NonNull Class<?>> classes) {
        super(message(classes));
    }

    private static String message(@NonNull Collection<@NonNull Class<?>> classes) {
        return "The built-in serializers cannot be unregistered!\n" + "Tried unregistering the serializers for the " +
                "classes: " + String.join(
                "\", \"",
                classes.stream().map(Class::getName).toList());
    }
}