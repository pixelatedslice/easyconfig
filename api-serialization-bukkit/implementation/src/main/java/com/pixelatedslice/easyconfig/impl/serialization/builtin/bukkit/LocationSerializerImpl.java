package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.bukkit.LocationSerializer;
import com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionBuilderImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jspecify.annotations.NonNull;


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
    public @NonNull ConfigSection serialize(@NonNull Location value) {
        ConfigSectionBuilder sectionBuilder = new ConfigSectionBuilderImpl();

        if (value.getWorld() != null) {
            sectionBuilder.node("world", value.getWorld().getName(), TypeToken.of(String.class));
        }

        sectionBuilder.node("x", value.getX());
        sectionBuilder.node("y", value.getY());
        sectionBuilder.node("z", value.getZ());
        sectionBuilder.node("yaw", value.getYaw());
        sectionBuilder.node("pitch", value.getPitch());

        return sectionBuilder.build();
    }

    @Override
    public @NonNull Location deserialize(@NonNull ConfigSection section) {
        var world = section
                .childNode(String.class, "world")
                .flatMap(ConfigNode::value)
                .map(Bukkit::getWorld)
                .orElse(null);
        var x = section.childNode(Double.class, "x")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var y = section.childNode(Double.class, "y")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var z = section.childNode(Double.class, "z")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var yaw = section.childNode(Float.class, "yaw")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var pitch = section.childNode(Float.class, "pitch")
                .flatMap(ConfigNode::value)
                .orElseThrow();

        return new Location(
                world,
                x, y, z,
                yaw, pitch
        );
    }
}
