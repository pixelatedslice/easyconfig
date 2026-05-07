package com.pixelatedslice.easyconfig.impl.serialization;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.serialization.SerializerRegistry;
import com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInSerializer;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;

import java.util.*;

@AutoService(SerializerRegistry.class)
public class SerializerRegistryImpl implements SerializerRegistry {
    @SuppressWarnings("rawtypes")
    private final @NonNull Collection<@NonNull Class<? extends Serializer>> autoLoadIdentifiers =
            new HashSet<>();
    private final @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers = new HashMap<>();

    public SerializerRegistryImpl() {
        this.autoLoadIdentifiers.add(BuiltInSerializer.class);
        this.reloadSerializers(true);
    }

    public SerializerRegistryImpl(
            @SuppressWarnings("rawtypes")
            @NonNull Collection<@NonNull Class<? extends Serializer>> autoLoadIdentifiers
    ) {
        this.autoLoadIdentifiers.add(BuiltInSerializer.class);
        this.autoLoadIdentifiers.addAll(autoLoadIdentifiers);
        this.reloadSerializers(true);
    }

    @SuppressWarnings({"rawtypes", "FinalMethod"})
    @SafeVarargs
    @Override
    public final void addAutoLoadIdentifiers(
            @NonNull Class<? extends @NonNull Serializer> @NonNull ... autoLoadIdentifiers) {
        Collections.addAll(this.autoLoadIdentifiers, autoLoadIdentifiers);
    }

    @SuppressWarnings({"rawtypes", "FinalMethod"})
    @SafeVarargs
    @Override
    public final void removeAutoLoadIdentifiers(
            @NonNull Class<? extends @NonNull Serializer> @NonNull ... autoLoadIdentifiers) {

    }

    @Override
    public void reloadSerializers(boolean override) {
    }

    @Override
    public void register(@NonNull Serializer<?> @NonNull ... serializers) {
    }

    @Override
    public void unregister(@NonNull Serializer<?> @NonNull ... serializers) {

    }

    public <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull Class<T> simpleType) {
        throw new RuntimeException();
    }

    public <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull TypeToken<T> typeToken) {
        throw new RuntimeException();
    }
}
