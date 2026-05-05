package com.pixelatedslice.easyconfig.api.serialization.format;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.format.FormatReader;
import com.pixelatedslice.easyconfig.api.format.FormatWriter;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.serialization.SerializerType;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public non-sealed interface FormatSerializer<T> extends Serializer<T> {
    @Override
    default @NonNull SerializerType serializerType() {
        return SerializerType.FORMAT;
    }

    void serialize(@Nullable T value, @NonNull FormatWriter writer);

    @Nullable T deserialize(@NonNull FormatReader reader);

    @FunctionalInterface
    interface Builder<T> {
        default @NonNull SerializeMethodStep<T> forType(@NonNull Class<@NonNull T> simpleType) {
            Objects.requireNonNull(simpleType);

            var typeToken = TypeToken.of(simpleType);
            if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
                throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
            }

            return this.forType(typeToken);
        }

        @NonNull SerializeMethodStep<T> forType(@NonNull TypeToken<@NonNull T> typeToken);

        @FunctionalInterface
        interface SerializeMethodStep<T> {
            @NonNull DeserializeMethodStep<T> serialize(
                    @NonNull BiConsumer<@NonNull T, @NonNull FormatWriter> serialize
            );

        }

        @FunctionalInterface
        interface DeserializeMethodStep<T> {
            @NonNull FinalStep<T> deserialize(
                    @NonNull Function<@NonNull FormatReader, @NonNull T> deserialize
            );
        }

        @FunctionalInterface
        interface FinalStep<T> {
            @NonNull Serializer<T> build();
        }
    }
}
