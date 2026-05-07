package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.google.errorprone.annotations.CheckReturnValue;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface NodeBuilder {

    interface FirstStep extends NodeBuilder {

        ContainerFinalStep.@NonNull Original key(@NonNull String key);
    }

    interface ValueFinalStep<T> extends NodeBuilder {
        ValueFinalStep<T> defaultValue(@Nullable T defaultValue);

        ValueFinalStep<T> value(@Nullable T value);

        ValueFinalStep<T> validator(@NonNull Validator<T> validator);

        ValueFinalStep<T> serializer(@NonNull Serializer<T> serializer);

        interface Original<T> extends ValueFinalStep<T> {

            @Override
            ValueFinalStep.Original<T> defaultValue(@Nullable T defaultValue);

            @Override
            ValueFinalStep.Original<T> value(@Nullable T value);

            @Override
            ValueFinalStep.Original<T> validator(@NonNull Validator<T> validator);

            @Override
            ValueFinalStep.Original<T> serializer(@NonNull Serializer<T> serializer);

            ValueNode<T> build();
        }

        interface Child<T, ParentBuilder extends NodeBuilder> extends ValueFinalStep<T> {

            @CheckReturnValue
            @NonNull
            ParentBuilder complete();
        }
    }

    interface CollectionStep extends NodeBuilder {

        ContainerFinalStep.Child<?> append();

        interface Original extends CollectionStep {
            CollectionNode build();
        }

        interface Child<ParentNode extends NodeBuilder> extends CollectionStep {
            @NonNull
            @CheckReturnValue
            ParentNode complete();

        }

    }

    interface ContainerFinalStep extends NodeBuilder {

        @NonNull
        @CheckReturnValue
        <T> ValueFinalStep<T> of(@NonNull TypeToken<T> token);

        @NonNull
        @CheckReturnValue
        default <T> ValueFinalStep<T> of(@NonNull Class<T> clazz) {
            return of(TypeToken.of(clazz));
        }

        @CheckReturnValue
        ContainerFinalStep.@NonNull Child<? extends NodeBuilder> append(@NonNull String key);

        interface Original extends ContainerFinalStep {
            ContainerNode build();

            @Override
            ContainerFinalStep.@NonNull Child<ContainerFinalStep.Original> append(@NonNull String key);

            @Override
            <T> ValueFinalStep.@NonNull Original<T> of(@NonNull TypeToken<T> token);

            @Override
            default <T> ValueFinalStep.@NonNull Original<T> of(@NonNull Class<T> clazz) {
                return of(TypeToken.of(clazz));
            }
        }

        interface Child<Parent extends NodeBuilder> extends ContainerFinalStep {
            @Override
            ContainerFinalStep.@NonNull Child<Child<Parent>> append(@NonNull String key);

            @NonNull
            @CheckReturnValue
            Parent complete();

            @Override
            <T> ValueFinalStep.@NonNull Child<T, Parent> of(@NonNull TypeToken<T> token);

            @Override
            default <T> ValueFinalStep.@NonNull Child<T, Parent> of(@NonNull Class<T> clazz) {
                return of(TypeToken.of(clazz));
            }
        }
    }
}
