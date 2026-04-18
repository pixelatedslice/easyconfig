package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class FireworkEffectSerializerImpl implements BuiltInBukkitSerializer<FireworkEffect> {
    private static volatile FireworkEffectSerializerImpl INSTANCE;

    private FireworkEffectSerializerImpl() {
    }

    public static FireworkEffectSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (FireworkEffectSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FireworkEffectSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void serialize(@Nullable FireworkEffect value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("flicker", (value != null) ? value.hasFlicker() : null, boolean.class);
        sectionBuilder.node("trail", (value != null) ? value.hasTrail() : null, boolean.class);
        sectionBuilder.node("colors", (value != null) ? value.getColors() : null, new TypeToken<>() {
        });
        sectionBuilder.node("fadeColors", (value != null) ? value.getFadeColors() : null, new TypeToken<>() {
        });
        sectionBuilder.node("type", (value != null) ? value.getType().name() : null, String.class);
    }

    @Override
    public @NonNull FireworkEffect deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var flicker = section
                .node(boolean.class, "flicker")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var trail = section.node(boolean.class, "trail")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var colors = section.node(new TypeToken<List<Color>>() {
                }, "colors")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var fadeColors = section.node(new TypeToken<List<Color>>() {
                }, "fadeColors")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var type = section.node(String.class, "type")
                .flatMap(ConfigNode::value)
                .map(FireworkEffect.Type::valueOf)
                .orElseThrow();

        return FireworkEffect.builder()
                .flicker(flicker)
                .trail(trail)
                .withColor(colors)
                .withFade(fadeColors)
                .with(type)
                .build();
    }
}
