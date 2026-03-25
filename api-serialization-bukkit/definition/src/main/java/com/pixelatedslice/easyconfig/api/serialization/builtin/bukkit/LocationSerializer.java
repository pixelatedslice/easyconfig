package com.pixelatedslice.easyconfig.api.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.Location;
import org.jspecify.annotations.NonNull;


public interface LocationSerializer extends BuiltInBukkitSerializer<Location> {

    @Override
    default @NonNull Class<Location> forClass() {
        return Location.class;
    }
}
