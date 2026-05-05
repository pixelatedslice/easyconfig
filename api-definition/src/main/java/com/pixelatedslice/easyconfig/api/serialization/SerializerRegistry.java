package com.pixelatedslice.easyconfig.api.serialization;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.serialization.format.FormatSerializer;
import com.pixelatedslice.easyconfig.api.serialization.node.NodeSerializer;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
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

    default <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull Class<T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
        }

        return this.find(typeToken);
    }

    default <T> @NonNull Optional<@NonNull FormatSerializer<T>> findFormatSerializer(@NonNull Class<T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
        }

        return this.findFormatSerializer(typeToken);
    }

    default <T> @NonNull Optional<@NonNull NodeSerializer<T>> findNodeSerializer(@NonNull Class<T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
        }

        return this.findNodeSerializer(typeToken);
    }

    <T> @NonNull Optional<@NonNull Serializer<T>> find(@NonNull TypeToken<T> typeToken);

    <T> @NonNull Optional<@NonNull FormatSerializer<T>> findFormatSerializer(@NonNull TypeToken<T> typeToken);

    <T> @NonNull Optional<@NonNull NodeSerializer<T>> findNodeSerializer(@NonNull TypeToken<T> typeToken);
}
