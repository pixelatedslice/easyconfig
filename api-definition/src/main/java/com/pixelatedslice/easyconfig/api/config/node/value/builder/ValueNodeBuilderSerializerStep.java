package com.pixelatedslice.easyconfig.api.config.node.value.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.container.builder.ContainerNodeBuilderChildrenStep;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ValueNodeBuilderSerializerStep<T> extends BuilderStep, ValueNodeBuilderValidatorStep<T> {
    @NonNull ValueNodeBuilderValidatorStep<T> serializer(@NonNull Serializer<@NonNull T> serializer);

    @NonNull ValueNodeBuilderValidatorStep<T> serializer(
            @NonNull BiConsumer<@NonNull T, @NonNull ContainerNodeBuilderChildrenStep> serialize,
            @NonNull Function<@NonNull Node, @NonNull T> deserialize
    );

    @NonNull EndWithDeserializeStep<T> serialize(
            @NonNull BiConsumer<@NonNull T, @NonNull ContainerNodeBuilderChildrenStep> serialize
    );

    @NonNull EndWithSerializeStep<T> deserialize(
            @NonNull Function<@NonNull Node, @NonNull T> deserialize
    );

    @FunctionalInterface
    interface EndWithSerializeStep<T> {
        @NonNull ValueNodeBuilderValidatorStep<T> serialize(
                @NonNull BiConsumer<@NonNull T, @NonNull ContainerNodeBuilderChildrenStep> serialize
        );
    }

    @FunctionalInterface
    interface EndWithDeserializeStep<T> {
        @NonNull ValueNodeBuilderValidatorStep<T> deserialize(
                @NonNull Function<@NonNull Node, @NonNull T> deserialize
        );
    }
}