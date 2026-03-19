package com.pixelatedslice.easyconfig.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a configurable value that can be retrieved, modified, or managed within
 * a configuration system. This interface provides methods for accessing the value,
 * setting its value, or working with defaults and exception handling.
 *
 * @param <T> the type of the value managed by this configuration entry
 */
public interface ConfigValue<T> {
    /**
     * Retrieves the value managed by this configuration entry.
     * This method returns an Optional containing the currently stored value if it exists,
     * or an empty Optional if no value is set.
     *
     * @return an Optional containing the value of type T if present, or an empty Optional if no value is set
     */
    Optional<T> get();

    /**
     * Retrieves the value associated with this object or returns the provided default value if no value is present.
     *
     * @param defaultValue the value to return if the optional value is not present, must not be null
     * @return the value if present, otherwise the provided default value
     */
    default T getOrDefault(@NotNull T defaultValue) {
        Objects.requireNonNull(defaultValue);
        return this.get().orElse(defaultValue);
    }

    /**
     * Retrieves the value managed by this configuration entry or throws an exception
     * if the value is not present.
     * <p>
     * This method first attempts to retrieve the value using the {@code get()} method.
     * If a value is present, it is returned; otherwise, an exception is thrown as
     * determined by the {@link Optional#orElseThrow()} mechanism.
     *
     * @return the value managed by this configuration entry if present
     * @throws NoSuchElementException if the value is not present
     */
    default T getOrThrow() {
        return this.get().orElseThrow();
    }

    /**
     * Updates the value represented by this configuration entry.
     *
     * @param value the new value to be set for this configuration entry,
     *              or null to unset the current value
     */
    void set(@Nullable T value);
}
