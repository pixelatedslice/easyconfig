package com.pixelatedslice.easyconfig.api.serialization;

import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Defines a contract for serialization and deserialization of objects of type {@code T}.
 * Implementations of this interface are responsible for converting objects to and from
 * a serialized representation composed of {@link SerializedElement} instances.
 *
 * @param <T> the type of the objects handled by this serializer
 */
public interface Serializer<T> {
    /**
     * Determines if the specified {@link Serializer} instance is a built-in serializer.
     *
     * @param serializer the {@link Serializer} instance to check; must not be {@code null}.
     * @return {@code true} if the provided serializer is an instance of {@link BuiltInSerializer}, {@code false}
     * otherwise.
     * @throws NullPointerException if the provided {@code serializer} is {@code null}.
     */
    static <T> boolean isBuiltIn(@NotNull Serializer<T> serializer) {
        return serializer instanceof BuiltInSerializer<T>;
    }

    /**
     * Retrieves the {@link Class} instance representing the type {@code T} that this serializer is designed to handle.
     *
     * @return the {@link Class} object corresponding to the type {@code T}; never {@code null}.
     */
    @NotNull Class<@NotNull T> forClass();

    /**
     * Serializes the provided value into a map where each entry represents a serialized component
     * of the value. The key of each entry is an integer identifier, and the value is the corresponding
     * {@link SerializedElement} instance that contains the serialized representation.
     *
     * @param value the object of type {@code T} to serialize; must not be {@code null}.
     * @return a non-null map containing the serialized representation of the input object, where each
     * key is a non-null integer identifier and each value is a non-null {@link SerializedElement}.
     * @throws NullPointerException if the {@code value} parameter is {@code null}.
     */
    @NotNull Map<@NotNull Integer, @NotNull SerializedElement<?>> serialize(@NotNull T value);

    /**
     * Deserializes a map of serialized elements back into an object of type {@code T}.
     *
     * @param elements a non-null map where each key represents an identifier and each value
     *                 is a non-null {@link SerializedElement} containing the data to be deserialized.
     *                 The map must not contain null keys or values.
     * @return the object of type {@code T} reconstructed from the serialized elements.
     * @throws NullPointerException if the {@code elements} parameter is {@code null}.
     */
    @NotNull T deserialize(@NotNull Map<@NotNull Integer, ? extends @NotNull SerializedElement<?>> elements);
}