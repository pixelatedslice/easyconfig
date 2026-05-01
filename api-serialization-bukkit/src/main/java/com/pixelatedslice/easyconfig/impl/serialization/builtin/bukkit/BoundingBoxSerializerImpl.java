package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.util.BoundingBox;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class BoundingBoxSerializerImpl implements BuiltInBukkitSerializer<BoundingBox> {
    private static final TypeToken<BoundingBox> typeToken = new TypeToken<>() {
    };

    private BoundingBoxSerializerImpl() {
    }

    public static BoundingBoxSerializerImpl instance() {
        return BoundingBoxSerializerImplHolder.INSTANCE;
    }

    @Override
    public @NonNull TypeToken<BoundingBox> forType() {
        return typeToken;
    }

    @Override
    public void serialize(@Nullable BoundingBox value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("minX", (value != null) ? value.getMinX() : null, double.class);
        sectionBuilder.node("minY", (value != null) ? value.getMinY() : null, double.class);
        sectionBuilder.node("minZ", (value != null) ? value.getMinZ() : null, double.class);
        sectionBuilder.node("maxX", (value != null) ? value.getMaxX() : null, double.class);
        sectionBuilder.node("maxY", (value != null) ? value.getMaxY() : null, double.class);
        sectionBuilder.node("maxZ", (value != null) ? value.getMaxZ() : null, double.class);
    }

    @Override
    public @NonNull BoundingBox deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var minX = section.node(double.class, "minX")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var minY = section.node(double.class, "minY")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var minZ = section.node(double.class, "minZ")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var maxX = section.node(double.class, "maxX")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var maxY = section.node(double.class, "maxY")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var maxZ = section.node(double.class, "maxZ")
                .flatMap(ConfigNode::value)
                .orElseThrow();

        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static final class BoundingBoxSerializerImplHolder {
        private static final BoundingBoxSerializerImpl INSTANCE = new BoundingBoxSerializerImpl();

        private BoundingBoxSerializerImplHolder() {
        }
    }
}
