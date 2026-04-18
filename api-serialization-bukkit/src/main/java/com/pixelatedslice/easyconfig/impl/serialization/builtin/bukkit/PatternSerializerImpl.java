package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.Pattern;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class PatternSerializerImpl implements BuiltInBukkitSerializer<Pattern> {
    private static volatile PatternSerializerImpl INSTANCE;

    private PatternSerializerImpl() {
    }

    public static PatternSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (PatternSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PatternSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void serialize(@Nullable Pattern value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("color", (value != null) ? value.getColor().toString() : null, String.class);
        sectionBuilder.node("pattern",
                (value != null) ? value.getPattern().getKeyOrThrow().toString() : null,
                String.class);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NonNull Pattern deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var color = section
                .node(String.class, "color")
                .flatMap(ConfigNode::value)
                .map(DyeColor::legacyValueOf)
                .orElseThrow();
        var pattern = section
                .node(String.class, "pattern")
                .flatMap(ConfigNode::value)
                .map(NamespacedKey::fromString)
                .map(Registry.BANNER_PATTERN::get)
                .orElseThrow();

        return new Pattern(color, pattern);
    }
}
