package com.pixelatedslice.easyconfig.api.utils.primitive;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public final class TypeUtils {
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

    private TypeUtils() {
    }

    public static Class<?> primitiveToWrapper(@NonNull Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return PRIMITIVE_TO_WRAPPER.getOrDefault(clazz, clazz);
    }

    public static <T> @NonNull String toString(@Nullable T array) {
        return switch (array) {
            case null -> "null";
            case int[] ints -> Arrays.toString(ints);
            case long[] longs -> Arrays.toString(longs);
            case boolean[] booleans -> Arrays.toString(booleans);
            case byte[] bytes -> Arrays.toString(bytes);
            case char[] chars -> Arrays.toString(chars);
            case float[] floats -> Arrays.toString(floats);
            case double[] doubles -> Arrays.toString(doubles);
            case short[] shorts -> Arrays.toString(shorts);
            case Object[] objects -> Arrays.deepToString(objects);
            default -> String.valueOf(array);
        };
    }
}