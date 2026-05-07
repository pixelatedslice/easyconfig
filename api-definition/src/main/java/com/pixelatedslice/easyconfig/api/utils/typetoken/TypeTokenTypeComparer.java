package com.pixelatedslice.easyconfig.api.utils.typetoken;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("SequencedCollectionMethodCanBeUsed")
final class TypeTokenTypeComparer {
    private TypeTokenTypeComparer() {
    }

    static <T> boolean hasCorrectType(@NonNull T value, @NonNull TypeToken<?> typeToken) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(typeToken);

        var baseType = typeToken.getRawType();

        if (!baseType.isInstance(value)) {
            return false;
        }

        var valueClass = value.getClass();

        if (TypeTokenUtils.matchingClass(Collection.class, baseType, valueClass)) {
            return iterable(value, typeToken);
        } else if (valueClass.isArray() && baseType.equals(valueClass)) {
            return iterable(value, typeToken);
        } else if (TypeTokenUtils.matchingClass(Map.class, baseType, valueClass)) {
            return map((Map<?, ?>) value, typeToken);
        }

        return typeToken.getRawType().isInstance(value);
    }

    private static boolean iterable(@NonNull Object container, @NonNull TypeToken<?> typeToken) {
        throw  new RuntimeException();
    }

    private static boolean map(@NonNull Map<?, ?> map, @NonNull TypeToken<?> typeToken) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(typeToken);

        if (map.isEmpty()) {
            return true;
        }

        var generics = TypeTokenUtils.generics(typeToken);
        var keyGeneric = generics.get(0);
        var keyGenericClass = keyGeneric.getRawType();
        var valueGeneric = generics.get(1);
        var valueGenericClass = valueGeneric.getRawType();

        for (var entry : map.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            if (!keyGenericClass.isInstance(key)
                    || !valueGenericClass.isInstance(value)
                    || !hasCorrectType(key, keyGeneric)
                    || !hasCorrectType(value, valueGeneric)
            ) {
                return false;
            }
        }

        return true;
    }
}
