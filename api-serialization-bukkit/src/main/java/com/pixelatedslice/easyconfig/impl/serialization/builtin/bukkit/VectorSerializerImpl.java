package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.util.Vector;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class VectorSerializerImpl implements BuiltInBukkitSerializer<Vector> {
    private static volatile VectorSerializerImpl INSTANCE;

    private VectorSerializerImpl() {
    }

    public static VectorSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (VectorSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VectorSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void serialize(@Nullable Vector value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("x", (value != null) ? value.getX() : null, double.class);
        sectionBuilder.node("y", (value != null) ? value.getY() : null, double.class);
        sectionBuilder.node("z", (value != null) ? value.getZ() : null, double.class);
    }

    @Override
    public @NonNull Vector deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var x = section.node(double.class, "x")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var y = section.node(double.class, "y")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var z = section.node(double.class, "z")
                .flatMap(ConfigNode::value)
                .orElseThrow();

        return new Vector(x, y, z);
    }
}
