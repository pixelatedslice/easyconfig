package com.pixelatedslice.easyconfig.api.serialization;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInSerializer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface Serializer<T> {
    static <T> boolean isBuiltIn(@NonNull Serializer<T> serializer) {
        return serializer instanceof BuiltInSerializer<T>;
    }

    @NonNull
    TypeToken<@NonNull T> forType();

    void serialize(@Nullable T value, ConfigSectionBuilder.@NonNull NestedSectionStep sectionBuilder);

    @Nullable T deserialize(@NonNull ConfigSection section);
}