package com.pixelatedslice.easyconfig.api.serialization;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInSerializer;
import org.jspecify.annotations.NonNull;

/**
 * Represents a contract for serializing and deserializing objects of a specific type.
 * Implementations of this interface provide mechanisms to convert objects of type {@code T}
 * to and from a {@link ConfigSection}, which serves as a serialized representation.
 *
 * @param <T> the type of objects that this serializer is capable of handling
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
    static <T> boolean isBuiltIn(@NonNull Serializer<T> serializer) {
        return serializer instanceof BuiltInSerializer<T>;
    }

    /**
     * Retrieves the {@link Class} instance representing the type {@code T} that this serializer is designed to handle.
     *
     * @return the {@link Class} object corresponding to the type {@code T}; never {@code null}.
     */
    @NonNull Class<@NonNull T> forClass();

    /**
     * Serializes the provided value into a {@link ConfigSection}.
     *
     * @param value the non-null value to be serialized
     * @return the non-null {@link ConfigSection} representation of the serialized value
     * @throws NullPointerException if the provided {@code value} is {@code null}
     */
    @NonNull ConfigSection serialize(@NonNull T value);

    /**
     * Deserializes the provided configuration section into an object of type {@code T}.
     *
     * @param section the non-null {@link ConfigSection} representing the serialized data
     *                to be converted into an object of type {@code T}.
     * @return the deserialized object of type {@code T}.
     * @throws NullPointerException if the provided {@code section} is {@code null}.
     */
    @NonNull T deserialize(@NonNull ConfigSection section);
}