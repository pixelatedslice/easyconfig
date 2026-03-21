package com.pixelatedslice.easyconfig.api.serialization;

import org.jetbrains.annotations.NotNull;

/**
 * Defines a contract for objects that can be serialized and deserialized while also
 * acting as their own {@link Serializer} implementation.
 * <br>
 * Classes implementing this interface must extend from this interface to ensure that
 * they can handle their own serialization and deserialization through methods defined in
 * the {@link Serializer} interface.
 * <br>
 * It is recommended to create a specialized class for each Serializable type, since you can create an
 * instance to serialize and deserialize the object more efficiently.
 *
 * @param <T> the type of the object implementing this interface, allowing the implementation
 *            to act as a serializer specifically for its own type.
 */
public interface Serializable<T extends Serializable<T>> extends Serializer<T> {

    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the class of the implementing object.
     */
    @SuppressWarnings("unchecked")
    @Override
    default @NotNull Class<T> forClass() {
        return (Class<T>) this.getClass();
    }
}