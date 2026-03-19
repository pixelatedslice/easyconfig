package com.pixelatedslice.easyconfig.impl.exception;

public class DefaultSerializerOverrideException extends RuntimeException {
    public DefaultSerializerOverrideException(Class<?> clazz, Class<?> serializer) {
        super(String.format(
                "The Default Serializer for the Class \"%s\" cannot be changed! (Tried changing it to: \"%s\")",
                clazz.getName(),
                serializer.getName()
        ));
    }
}