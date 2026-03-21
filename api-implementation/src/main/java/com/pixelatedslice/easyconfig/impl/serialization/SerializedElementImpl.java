package com.pixelatedslice.easyconfig.impl.serialization;

import com.pixelatedslice.easyconfig.api.serialization.SerializedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * A concrete implementation of the {@link SerializedElement} interface, representing
 * an individual serialized element with metadata about a value being serialized
 * or deserialized.
 *
 * @param <T> the type of the value held by this serialized element
 */
public record SerializedElementImpl<T>(@NotNull String key, @Nullable T nullableValue, Class<T> type)
        implements SerializedElement<T> {

    @SuppressWarnings("unchecked")
    public SerializedElementImpl(@NotNull String key, @NotNull T value) {
        this(Objects.requireNonNull(key), Objects.requireNonNull(value), (Class<T>) value.getClass());
    }

    @Override
    public @NotNull Optional<T> value() {
        return Optional.ofNullable(this.nullableValue);
    }
}
