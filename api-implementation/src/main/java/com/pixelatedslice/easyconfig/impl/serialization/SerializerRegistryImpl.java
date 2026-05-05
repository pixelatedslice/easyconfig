package com.pixelatedslice.easyconfig.impl.serialization;

import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.exception.SerializerException;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.serialization.BuiltInSerializer;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.serialization.SerializerRegistry;
import com.pixelatedslice.easyconfig.api.serialization.SerializerType;
import com.pixelatedslice.easyconfig.api.serialization.format.FormatSerializer;
import com.pixelatedslice.easyconfig.api.serialization.format.builtin.BuiltinFormatSerializer;
import com.pixelatedslice.easyconfig.api.serialization.node.NodeSerializer;
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
                    this.serializers.put(loadedSerializer.supportedType(), loadedSerializer);
                } else {
                    this.serializers.putIfAbsent(
                            loadedSerializer.supportedType(),
                            loadedSerializer
                    );
                }
            }
        }
    }

    @Override
    public void register(@NonNull Serializer<?> @NonNull ... serializers) {
        Objects.requireNonNull(serializers);
        for (var serializer : serializers) {
            if (this.serializers.containsKey(serializer.supportedType())) {
                throw new UnsupportedOperationException("Overriding serializers for a type is not allowed!");
            }

            this.serializers.put(serializer.supportedType(), serializer);
        }
    }

    @Override
    public void unregister(@NonNull Serializer<?> @NonNull ... serializers) {
        Objects.requireNonNull(serializers);
        for (var serializer : serializers) {
            if (serializer instanceof BuiltinFormatSerializer<?>) {
                throw new UnsupportedOperationException(
                        "Unregistering built in Format Serializers is not allowed since it can break the system."
                );
            }

            this.serializers.remove(serializer.supportedType(), serializer);
        }
    }

    public <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);

        var serializer = this.serializers.get(typeToken);
        if (serializer == null) {
            return Optional.empty();
        }

        if (!TypeTokenUtils.matches(typeToken, serializer.supportedType())) {
            throw TypeException.TYPES_DO_NOT_MATCH(typeToken, serializer.supportedType());
        }

        @SuppressWarnings("unchecked") var castedSerializer = (Serializer<T>) serializer;
        return Optional.of(castedSerializer);
    }

    @Override
    public @NonNull <T> Optional<@NonNull FormatSerializer<T>> findFormatSerializer(@NonNull TypeToken<T> typeToken) {
        var opt = this.find(typeToken);

        if (opt.isEmpty()) {
            return Optional.empty();
        }

        var found = opt.get();
        if (!(found instanceof FormatSerializer<T>)) {
            throw SerializerException.DID_NOT_EXPECT_SERIALIZER_TYPE(SerializerType.FORMAT, found.serializerType());
        }

        return Optional.of((FormatSerializer<T>) found);
    }

    @Override
    public @NonNull <T> Optional<@NonNull NodeSerializer<T>> findNodeSerializer(@NonNull TypeToken<T> typeToken) {
        var opt = this.find(typeToken);

        if (opt.isEmpty()) {
            return Optional.empty();
        }

        var found = opt.get();
        if (!(found instanceof NodeSerializer<T>)) {
            throw SerializerException.DID_NOT_EXPECT_SERIALIZER_TYPE(SerializerType.NODE, found.serializerType());
        }

        return Optional.of((NodeSerializer<T>) found);
    }
}
