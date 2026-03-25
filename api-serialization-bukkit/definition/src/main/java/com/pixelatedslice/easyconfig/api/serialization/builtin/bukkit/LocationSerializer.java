package com.pixelatedslice.easyconfig.api.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.Location;
import org.jspecify.annotations.NonNull;


/**
 * An interface for serializing and deserializing {@link Location} objects into and from
 * {@link ConfigSection}s. This serializer provides mechanisms to handle the conversion
 * of {@link Location} data to a structured configuration format and vice versa.
 * <p>
 * It is a specialized serializer that extends the {@link BuiltInBukkitSerializer} interface,
 * specifically targeting the {@link Location} type.
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
    default @NonNull Class<Location> forClass() {
        return Location.class;
    }
}
