package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.block.spawner.SpawnRule;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class SpawnRuleSerializerImpl implements BuiltInBukkitSerializer<SpawnRule> {
    private static volatile SpawnRuleSerializerImpl INSTANCE;

    private SpawnRuleSerializerImpl() {
    }

    public static SpawnRuleSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (SpawnRuleSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpawnRuleSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void serialize(@Nullable SpawnRule value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        sectionBuilder.node("minBlockLight", (value != null) ? value.getMinBlockLight() : null, int.class);
        sectionBuilder.node("maxBlockLight", (value != null) ? value.getMaxBlockLight() : null, int.class);
        sectionBuilder.node("minSkyLight", (value != null) ? value.getMinSkyLight() : null, int.class);
        sectionBuilder.node("maxSkyLight", (value != null) ? value.getMaxSkyLight() : null, int.class);
    }

    @Override
    public @NonNull SpawnRule deserialize(@NonNull ConfigSection section) {
        Objects.requireNonNull(section);

        var minBlockLight = section
                .node(int.class, "minBlockLight")
                .flatMap(ConfigNode::value)
                .orElse(0);
        var maxBlockLight = section
                .node(int.class, "maxBlockLight")
                .flatMap(ConfigNode::value)
                .orElse(0);
        var minSkyLight = section
                .node(int.class, "minBlockLight")
                .flatMap(ConfigNode::value)
                .orElse(0);
        var maxSkyLight = section
                .node(int.class, "maxBlockLight")
                .flatMap(ConfigNode::value)
                .orElse(0);

        return new SpawnRule(minBlockLight, maxBlockLight, minSkyLight, maxSkyLight);
    }
}
