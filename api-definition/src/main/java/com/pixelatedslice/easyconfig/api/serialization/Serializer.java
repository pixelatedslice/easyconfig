package com.pixelatedslice.easyconfig.api.serialization;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInSerializer;
import org.jspecify.annotations.NonNull;

public interface Serializer<T> {
    static <T> boolean isBuiltIn(@NonNull Serializer<T> serializer) {
        return serializer instanceof BuiltInSerializer<T>;
    }

    @NonNull Class<@NonNull T> forClass();

    @NonNull ConfigSection serialize(@NonNull T value);

    @NonNull T deserialize(@NonNull ConfigSection section);
}