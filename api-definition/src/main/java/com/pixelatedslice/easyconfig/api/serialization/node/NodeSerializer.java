package com.pixelatedslice.easyconfig.api.serialization.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.container.builder.ContainerNodeBuilderChildrenStep;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.serialization.SerializerType;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public non-sealed interface NodeSerializer<T> extends Serializer<T> {
    @Override
    default @NonNull SerializerType serializerType() {
        return SerializerType.NODE;
    }

    void serialize(@Nullable T value, @NonNull ContainerNodeBuilderChildrenStep builder);

    @Nullable T deserialize(@NonNull Node node);

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
                    @NonNull BiConsumer<@NonNull T, @NonNull ContainerNodeBuilderChildrenStep> serialize
            );

        }

        @FunctionalInterface
        interface DeserializeMethodStep<T> {
            @NonNull FinalStep<T> deserialize(
                    @NonNull Function<@NonNull Node, @NonNull T> deserialize
            );
        }

        @FunctionalInterface
        interface FinalStep<T> {
            @NonNull Serializer<T> build();
        }
    }
}
