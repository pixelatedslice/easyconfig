package com.pixelatedslice.easyconfig.api.serialization;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.ServiceLoader;

public interface SerializerRegistry {
    static SerializerRegistry create() {
        return ServiceLoader.load(SerializerRegistry.class).findFirst().orElseThrow();
    }

    @SuppressWarnings("rawtypes")
    void addAutoLoadIdentifiers(@NonNull Class<? extends @NonNull Serializer> @NonNull ... autoLoadIdentifiers);

    @SuppressWarnings("rawtypes")
    void removeAutoLoadIdentifiers(@NonNull Class<? extends @NonNull Serializer> @NonNull ... autoLoadIdentifiers);

    default void reloadSerializers() {
        this.reloadSerializers(false);
    }

    void reloadSerializers(boolean override);

    void register(@NonNull Serializer<?> @NonNull ... serializer);

    void unregister(@NonNull Serializer<?> @NonNull ... serializer);

    <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull Class<T> simpleType);

    <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull TypeToken<T> typeToken);
}
