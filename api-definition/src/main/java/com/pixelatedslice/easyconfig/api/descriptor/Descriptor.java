package com.pixelatedslice.easyconfig.api.descriptor;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

@FunctionalInterface
public interface Descriptor<T> {
    Optional<TypeToken<T>> typeToken();

    interface Builder<T, R> {
        @NonNull Builder<T, R> typeToken(@NonNull TypeToken<T> typeToken);

        @NonNull R build();
    }
}
