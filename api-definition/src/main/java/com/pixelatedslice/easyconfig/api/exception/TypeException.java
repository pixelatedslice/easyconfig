package com.pixelatedslice.easyconfig.api.exception;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class TypeException extends RuntimeException {
    public TypeException(@NonNull String message) {
        Objects.requireNonNull(message);

        super(message);
    }

    public TypeException(@NonNull String message, @NonNull Object @NonNull ... args) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(args);

        super(String.format(message, args));
    }

    public static TypeException CLASS_USED_IN_PLACE_OF_TYPETOKEN(
            @NonNull TypeToken<?> complex,
            @NonNull Class<?> simple
    ) {
        Objects.requireNonNull(complex);
        Objects.requireNonNull(simple);

        return new TypeException(
                "Type safety violation: Cannot use simple Class (%s) to evaluate complex TypeToken (%s).",
                simple.getSimpleName(), complex.toString()
        );
    }

    public static TypeException CLASS_USED_IN_PLACE_OF_TYPETOKEN(@NonNull Class<?> simple) {
        Objects.requireNonNull(simple);

        return new TypeException(
                "Type safety violation: Simple Class (%s) cannot be used where a complex TypeToken is required.",
                simple.getSimpleName()
        );
    }

    public static TypeException TYPES_DO_NOT_MATCH(@NonNull TypeToken<?> expected, @NonNull TypeToken<?> received) {
        Objects.requireNonNull(expected);
        Objects.requireNonNull(received);

        return new TypeException(
                "Type safety violation: Incompatible types. Expected %s but received %s.",
                expected.toString(), received.toString()
        );
    }
}
