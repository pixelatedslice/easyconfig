package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.serialization.SerializedElement;
import com.pixelatedslice.easyconfig.api.serialization.builtin.bukkit.LocationSerializer;
import com.pixelatedslice.easyconfig.impl.serialization.SerializedElementImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public final class LocationSerializerImpl implements LocationSerializer {
    private static volatile LocationSerializerImpl INSTANCE;

    private LocationSerializerImpl() {
    }

    public static LocationSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (LocationSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocationSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public @NotNull Map<@NotNull Integer, @NotNull SerializedElement<?>> serialize(@NotNull Location value) {
        var elements = new HashMap<Integer, SerializedElement<?>>(6);

        if (value.getWorld() != null) {
            elements.put(0, new SerializedElementImpl<>("world", value.getWorld().getName()));
        }

        elements.put(1, new SerializedElementImpl<>("x", value.getX()));
        elements.put(2, new SerializedElementImpl<>("y", value.getY()));
        elements.put(3, new SerializedElementImpl<>("z", value.getZ()));

        elements.put(4, new SerializedElementImpl<>("yaw", value.getYaw()));
        elements.put(5, new SerializedElementImpl<>("pitch", value.getPitch()));

        return elements;
    }

    @Override
    public @NonNull Location deserialize(
            @NotNull Map<@NotNull Integer, ? extends @NotNull SerializedElement<?>> elements
    ) {
        @Nullable var worldNameElement = elements.get(0);
        @Nullable World world = null;
        if (worldNameElement != null) {
            world = worldNameElement.<String>cast().value().map(Bukkit::getWorld).orElse(null);
        }

        var x = elements.get(1).<Double>cast().value().orElse(0.0);
        var y = elements.get(2).<Double>cast().value().orElse(0.0);
        var z = elements.get(3).<Double>cast().value().orElse(0.0);

        var yaw = elements.get(4).<Float>cast().value().orElse(0f);
        var pitch = elements.get(5).<Float>cast().value().orElse(0f);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
