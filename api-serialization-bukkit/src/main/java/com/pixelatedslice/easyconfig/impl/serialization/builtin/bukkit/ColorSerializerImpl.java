package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.Color;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class ColorSerializerImpl implements BuiltInBukkitSerializer<Color> {
    private static final TypeToken<Color> typeToken = new TypeToken<>() {
    };

    private ColorSerializerImpl() {
    }

    public static ColorSerializerImpl instance() {
        return ColorSerializerImplHolder.INSTANCE;
    }

    @Override
    public @NonNull TypeToken<Color> forType() {
        return typeToken;
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

    private static final class ColorSerializerImplHolder {
        private static final ColorSerializerImpl INSTANCE = new ColorSerializerImpl();
    }
}
