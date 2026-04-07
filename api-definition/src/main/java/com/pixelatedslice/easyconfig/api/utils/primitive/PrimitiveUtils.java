package com.pixelatedslice.easyconfig.api.utils.primitive;

import java.util.Map;

public final class PrimitiveUtils {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = Map.of(
            boolean.class, Boolean.class,
            byte.class, Byte.class,
            char.class, Character.class,
            double.class, Double.class,
            float.class, Float.class,
            int.class, Integer.class,
            long.class, Long.class,
            short.class, Short.class,
            void.class, Void.class
    );

    private PrimitiveUtils() {
    }

    public static Class<?> wrap(Class<?> clazz) {
        return PRIMITIVE_TO_WRAPPER.getOrDefault(clazz, clazz);
    }
}
