package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInBukkitSerializer;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class BlockVectorSerializerImpl implements BuiltInBukkitSerializer<BlockVector> {
    private static final TypeToken<BlockVector> typeToken = new TypeToken<>() {
    };

    private BlockVectorSerializerImpl() {
    }

    public static BlockVectorSerializerImpl instance() {
        return BlockVectorSerializerImplHolder.INSTANCE;
    }

    @Override
    public @NonNull TypeToken<BlockVector> forType() {
        return typeToken;
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

    public static final class BlockVectorSerializerImplHolder {
        private static final BlockVectorSerializerImpl INSTANCE = new BlockVectorSerializerImpl();

        private BlockVectorSerializerImplHolder() {
        }
    }
}
