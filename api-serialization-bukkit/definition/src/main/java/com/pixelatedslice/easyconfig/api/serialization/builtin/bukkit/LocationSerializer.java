package com.pixelatedslice.easyconfig.api.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.serialization.SerializedElement;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * A specialized serializer interface for handling the serialization and deserialization of
 * {@link Location} objects in the context of Bukkit applications. This interface extends
 * {@link BuiltInBukkitSerializer} and provides the required type information for
 * {@link Location}.
 * <p>
 * The implementation of this interface is responsible for converting {@link Location}
 * objects to and from their serialized representation using {@link SerializedElement} instances.
 */
public interface LocationSerializer extends BuiltInBukkitSerializer<Location> {
    /**
     * Retrieves the class object associated with the {@link Location} type
     * that this serializer handles.
     *
     * @return the {@link Location} class object.
     * @throws NullPointerException if this method returns a null value,
     *                              which should not occur because the implementation guarantees non-nullity.
     */
    @Override
    default @NotNull Class<Location> forClass() {
        return Location.class;
    }
}
