package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class PotionEffectSerializerImpl implements BuiltInBukkitSerializer<PotionEffect> {
    private static volatile PotionEffectSerializerImpl INSTANCE;

    private PotionEffectSerializerImpl() {
    }

    public static PotionEffectSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (PotionEffectSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PotionEffectSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void serialize(@Nullable PotionEffect value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("type",
                (value != null) ? Objects.toString(value.getType().getKeyOrNull()) : null,
                String.class);
        sectionBuilder.node("duration", (value != null) ? value.getDuration() : null, int.class);
        sectionBuilder.node("amplifier", (value != null) ? value.getAmplifier() : null, int.class);
        sectionBuilder.node("ambient", (value != null) ? value.isAmbient() : null, boolean.class);
        sectionBuilder.node("particles", (value != null) ? value.hasParticles() : null, boolean.class);
        sectionBuilder.node("icon", (value != null) ? value.hasIcon() : null, boolean.class);
    }

    @Override
    public @NonNull PotionEffect deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var type = section
                .node(String.class, "type")
                .flatMap(ConfigNode::value)
                .map(NamespacedKey::fromString)
                .map(Registry.EFFECT::get)
                .orElseThrow();
        var duration = section.node(int.class, "duration")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var amplifier = section.node(int.class, "amplifier")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var ambient = section.node(boolean.class, "ambient")
                .flatMap(ConfigNode::value)
                .orElse(false);
        var icon = section.node(boolean.class, "icon")
                .flatMap(ConfigNode::value)
                .orElse(true);
        var particles = section.node(boolean.class, "particles")
                .flatMap(ConfigNode::value)
                .orElse(icon);

        return new PotionEffect(
                type,
                duration,
                amplifier,
                ambient,
                particles,
                icon
        );

    }
}
