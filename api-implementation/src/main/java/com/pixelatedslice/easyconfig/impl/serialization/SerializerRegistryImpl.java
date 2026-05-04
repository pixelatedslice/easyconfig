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
        for (var autoLoadIdentifier : autoLoadIdentifiers) {
            this.autoLoadIdentifiers.remove(autoLoadIdentifier);
        }
    }

    @Override
    public void reloadSerializers(boolean override) {
        if (this.autoLoadIdentifiers.isEmpty()) {
            return;
        }

        for (var autoLoadIdentifier : this.autoLoadIdentifiers) {
            var loadedSerializers = ServiceLoader.load(autoLoadIdentifier);

            for (var loadedSerializer : loadedSerializers) {
                if (override) {
                    this.serializers.put(loadedSerializer.forType(), loadedSerializer);
                } else {
                    this.serializers.computeIfAbsent(
                            loadedSerializer.forType(),
                            (@NonNull TypeToken<?> _) -> loadedSerializer
                    );
                }
            }
        }
    }

    @Override
    public void register(@NonNull Serializer<?> @NonNull ... serializers) {
        Objects.requireNonNull(serializers);
        for (var serializer : serializers) {
            if (this.serializers.containsKey(serializer.forType())) {
                throw new UnsupportedOperationException("Overriding serializers for a type is not allowed!");
            }

            this.serializers.put(serializer.forType(), serializer);
        }
    }

    @Override
    public void unregister(@NonNull Serializer<?> @NonNull ... serializers) {
        Objects.requireNonNull(serializers);
        for (var serializer : serializers) {
            this.serializers.remove(serializer.forType(), serializer);
        }
    }

    public <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull Class<T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
        }

        return this.find(typeToken);
    }

    public <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);

        var serializer = this.serializers.get(typeToken);
        if (serializer == null) {
            return Optional.empty();
        }

        if (!TypeTokenUtils.matches(typeToken, serializer.forType())) {
            throw TypeException.TYPES_DO_NOT_MATCH(typeToken, serializer.forType());
        }

        @SuppressWarnings("unchecked") var castedSerializer = (Serializer<T>) serializer;
        return Optional.of(castedSerializer);
    }
}
