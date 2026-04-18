package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class BlockVectorSerializerImpl implements BuiltInBukkitSerializer<BlockVector> {
    private static volatile BlockVectorSerializerImpl INSTANCE;

    private BlockVectorSerializerImpl() {
    }

    public static BlockVectorSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (BlockVectorSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BlockVectorSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void serialize(@Nullable BlockVector value, @NonNull ConfigSectionBuilder sectionBuilder) {
        Objects.requireNonNull(sectionBuilder);

        VectorSerializerImpl.instance().serialize(value, sectionBuilder);
    }

    @Override
    public @NonNull BlockVector deserialize(@NonNull ConfigSection section) {
        return (BlockVector) VectorSerializerImpl.instance().deserialize(section);
    }
}
