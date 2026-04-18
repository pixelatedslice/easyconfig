package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.Color;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class ColorSerializerImpl implements BuiltInBukkitSerializer<Color> {
    private static volatile ColorSerializerImpl INSTANCE;

    private ColorSerializerImpl() {
    }

    public static ColorSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (ColorSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ColorSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void serialize(@Nullable Color value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("alpha", (value != null) ? value.getAlpha() : null, int.class);
        sectionBuilder.node("red", (value != null) ? value.getRed() : null, int.class);
        sectionBuilder.node("blue", (value != null) ? value.getBlue() : null, int.class);
        sectionBuilder.node("green", (value != null) ? value.getGreen() : null, int.class);
    }

    @Override
    public @NonNull Color deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var alpha = section
                .node(int.class, "alpha")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var red = section.node(int.class, "red")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var blue = section
                .node(int.class, "blue")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var green = section.node(int.class, "green")
                .flatMap(ConfigNode::value)
                .orElseThrow();

        return Color.fromARGB(alpha, red, green, blue);
    }
}
