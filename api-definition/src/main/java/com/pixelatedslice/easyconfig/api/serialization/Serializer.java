package com.pixelatedslice.easyconfig.api.serialization;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInSerializer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface Serializer<T> {
    static <T> boolean isBuiltIn(@NonNull Serializer<T> serializer) {
        return serializer instanceof BuiltInSerializer<T>;
    }

    @NonNull Class<@NonNull T> forClass();

    void serialize(@Nullable T value, @NonNull ConfigSectionBuilder sectionBuilder);

    @Nullable T deserialize(@NonNull ConfigSection section);
}