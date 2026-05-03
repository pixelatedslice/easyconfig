package com.pixelatedslice.easyconfig.api.utils.typetoken;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted"})
public final class TypeTokenUtils {
    private TypeTokenUtils() {
    }

    public static boolean isSimpleTypeToken(@NonNull TypeToken<?> typeToken) {
        Objects.requireNonNull(typeToken);

        Type type = typeToken.getType();

        if (!(type instanceof Class<?> clazz)) {
            return false;
        }

        Class<?> leafType = clazz;
        while (leafType.isArray()) {
            leafType = leafType.getComponentType();
        }

        return leafType.getTypeParameters().length == 0;
    }

    public static <T> boolean hasCorrectType(@NonNull T value, @NonNull TypeToken<?> typeToken) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(typeToken);

        return TypeTokenTypeComparer.hasCorrectType(value, typeToken);
    }

    public static boolean matches(@NonNull TypeToken<?> typeTokenOne, @NonNull TypeToken<?> typeTokenTwo) {
        Objects.requireNonNull(typeTokenOne);
        Objects.requireNonNull(typeTokenTwo);

        return typeTokenOne.equals(typeTokenTwo);
    }

    public static <T> boolean matchingClass(
            @NonNull Class<T> targetType,
            @NonNull TypeToken<?> typeToken,
            @NonNull Class<?> type
    ) {
        Objects.requireNonNull(targetType);
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(type);

        return matchingClass(targetType, typeToken.getRawType(), type);
    }

    public static <T> boolean matchingClass(
            @NonNull Class<T> targetType,
            @NonNull Class<?> typeTokenRawType,
            @NonNull Class<?> type
    ) {
        Objects.requireNonNull(targetType);
        Objects.requireNonNull(typeTokenRawType);
        Objects.requireNonNull(type);

        return typeTokenRawType.isAssignableFrom(type)
                && targetType.isAssignableFrom(type)
                && targetType.isAssignableFrom(typeTokenRawType);
    }

    public static @NonNull List<@NonNull TypeToken<?>> generics(@NonNull TypeToken<?> typeToken) {
        Objects.requireNonNull(typeToken);

        List<TypeToken<?>> generics = new ArrayList<>();
        for (var typeParameter : typeToken.getRawType().getTypeParameters()) {
            generics.add(typeToken.resolveType(typeParameter));
        }
        return Collections.unmodifiableList(generics);
    }
}
