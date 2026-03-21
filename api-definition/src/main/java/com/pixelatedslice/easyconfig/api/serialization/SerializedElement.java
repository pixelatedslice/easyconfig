package com.pixelatedslice.easyconfig.api.serialization;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents an individual serialized element that holds metadata about a value being serialized
 * or deserialized in the context of the serialization process.
 *
 * @param <T> the type of the value held by this serialized element
 */
public interface SerializedElement<T> {
    /**
     * Casts the current instance of {@code SerializedElement} to a different generic type {@code N}.
     * This method facilitates type-safe casting for serialized elements where the desired
     * generic type is explicitly known at runtime.
     *
     * @param <N> the target generic type to which this {@code SerializedElement} is cast
     * @return the current {@code SerializedElement} instance cast to the specified type {@code N};
     * never {@code null}.
     * @throws NullPointerException if the current instance is {@code null}.
     */
    @SuppressWarnings("unchecked")
    default <N> @NotNull SerializedElement<N> cast() {
        return (SerializedElement<N>) this;
    }

    /**
     * Retrieves the {@link Class} instance associated with the generic type {@code T}
     * of the serialized element. This type represents the Java class of the value
     * held by the serialized element, enforcing non-null constraints for type safety.
     *
     * @return the {@link Class} object representing the type {@code T} of the serialized element;
     * never {@code null}.
     */
    @NotNull Class<@NotNull T> type();

    /**
     * Retrieves the unique identifier associated with this serialized element.
     * The key is used to reference or differentiate this element during serialization
     * or deserialization processes.
     *
     * @return a non-null string representing the unique key of this serialized element
     */
    @NotNull String key();

    /**
     * Retrieves the value held by this serialized element, if available.
     *
     * @return the value of the serialized element, or {@code null} if no value is present
     */
    @NotNull Optional<@NotNull T> value();
}
