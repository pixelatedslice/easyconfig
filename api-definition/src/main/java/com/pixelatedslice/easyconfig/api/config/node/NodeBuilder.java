package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.google.errorprone.annotations.CheckReturnValue;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface NodeBuilder {

    interface BaseOriginal<T> {
        T build();
    }

    interface BaseChild<Previous> {
        Previous complete();
    }

    interface FirstStep extends NodeBuilder {

        ContainerFinalStep.@NonNull Original key(@NonNull String key);
    }

    interface ValueAbstractStep<T> extends NodeBuilder {
        ValueFinalStep<T> defaultValue(@Nullable T defaultValue);

        ValueFinalStep<T> value(@Nullable T value);

        ValueFinalStep<T> validator(@NonNull Validator<T> validator);

        ValueFinalStep<T> serializer(@NonNull Serializer<T> serializer);

        interface Original<T> extends ValueAbstractStep<T>, BaseOriginal<ValueNode<T>> {

            ValueFinalStep.Original<T> defaultValue(@Nullable T defaultValue);

            ValueFinalStep.Original<T> value(@Nullable T value);

            ValueFinalStep.Original<T> validator(@NonNull Validator<T> validator);

            ValueFinalStep.Original<T> serializer(@NonNull Serializer<T> serializer);
        }

        interface Child<T, ParentNode extends NodeBuilder> extends ValueAbstractStep<T>, BaseChild<ParentNode> {

            ValueFinalStep.Child<T, ParentNode> defaultValue(@Nullable T defaultValue);

            ValueFinalStep.Child<T, ParentNode> value(@Nullable T value);

            ValueFinalStep.Child<T, ParentNode> validator(@NonNull Validator<T> validator);

            ValueFinalStep.Child<T, ParentNode> serializer(@NonNull Serializer<T> serializer);
        }

    }

    interface ValueSafeStep<T> extends ValueAbstractStep<T> {

        @NonNull EnvAdapterStep<T> env(@NonNull String env);

        interface Original<T> extends ValueSafeStep<T>, ValueAbstractStep.Original<T> {

            @Override
            EnvAdapterStep.@NonNull Original<T> env(@NonNull String env);
        }

        interface Child<T, ParentNode extends NodeBuilder> extends ValueSafeStep<T>, ValueAbstractStep.Child<T, ParentNode> {

            @Override
            EnvAdapterStep.@NonNull Child<T, ParentNode> env(@NonNull String env);
        }
    }

    interface EnvAdapterStep<T> extends NodeBuilder {

        EnvFinalStep<T> adapter(@NonNull Function<String, T> adapter);

        interface Original<T> extends EnvAdapterStep<T> {

            @Override
            EnvFinalStep.Original<T> adapter(@NonNull Function<String, T> adapter);

        }

        interface Child<T, Previous extends NodeBuilder> extends EnvAdapterStep<T> {

            @Override
            EnvFinalStep.Child<T, Previous> adapter(@NonNull Function<@NonNull String, @Nullable T> adapter);

        }
    }

    interface EnvFinalStep<T> extends NodeBuilder {
        interface Original<T> extends EnvFinalStep<T>, BaseOriginal<EnvNode<T>> {

        }

        interface Child<T, ParentNode extends NodeBuilder> extends EnvFinalStep<T>, BaseChild<ParentNode> {
        }
    }

    interface ValueFinalStep<T> extends ValueAbstractStep<T> {

        interface Original<T> extends ValueFinalStep<T>, ValueAbstractStep.Original<T> {

            @Override
            ValueFinalStep.Original<T> defaultValue(@Nullable T defaultValue);

            @Override
            ValueFinalStep.Original<T> value(@Nullable T value);

            @Override
            ValueFinalStep.Original<T> validator(@NonNull Validator<T> validator);

            @Override
            ValueFinalStep.Original<T> serializer(@NonNull Serializer<T> serializer);
        }

        interface Child<T, ParentBuilder extends NodeBuilder> extends ValueFinalStep<T>, ValueAbstractStep.Child<T, ParentBuilder> {
        }
    }

    interface CollectionStep extends NodeBuilder {

        ContainerSafeStep.Child<? extends CollectionStep> append();

        interface Original extends CollectionStep, BaseOriginal<CollectionNode> {

            @Override
            ContainerSafeStep.Child<CollectionStep.Original> append();
        }

        interface Child<ParentNode extends NodeBuilder> extends CollectionStep, BaseChild<ParentNode> {

            @Override
            ContainerSafeStep.Child<CollectionStep.Child<ParentNode>> append();

        }

    }

    interface ContainerSafeStep extends NodeBuilder {
        @NonNull
        @CheckReturnValue
        CollectionStep collection();

        @CheckReturnValue
        ContainerSafeStep.@NonNull Child<? extends NodeBuilder> append(@NonNull String key);

        interface Original extends ContainerSafeStep, BaseOriginal<ContainerNode> {

            @Override
            CollectionStep.@NonNull Original collection();

            @Override
            ContainerSafeStep.@NonNull Child<? extends ContainerSafeStep.Original> append(@NonNull String key);
        }

        interface Child<Parent extends NodeBuilder> extends ContainerFinalStep, BaseChild<Parent> {
            @Override
            ContainerSafeStep.@NonNull Child<? extends ContainerSafeStep.Child<Parent>> append(@NonNull String key);

            @Override
            CollectionStep.@NonNull Child<Parent> collection();
        }
    }

    interface ContainerFinalStep extends ContainerSafeStep {


        @NonNull
        @CheckReturnValue
        <T> ValueSafeStep<T> of(@NonNull TypeToken<T> token);

        @NonNull
        @CheckReturnValue
        default <T> ValueSafeStep<T> of(@NonNull Class<T> clazz) {
            return of(TypeToken.of(clazz));
        }


        interface Original extends ContainerFinalStep, ContainerSafeStep.Original {

            @Override
            ContainerFinalStep.@NonNull Child<ContainerFinalStep.Original> append(@NonNull String key);

            @Override
            <T> ValueSafeStep.@NonNull Original<T> of(@NonNull TypeToken<T> token);

            @Override
            default <T> ValueSafeStep.@NonNull Original<T> of(@NonNull Class<T> clazz) {
                return of(TypeToken.of(clazz));
            }
        }

        interface Child<Parent extends NodeBuilder> extends ContainerFinalStep, ContainerSafeStep.Child<Parent> {
            @Override
            ContainerFinalStep.@NonNull Child<ContainerFinalStep.Child<Parent>> append(@NonNull String key);

            @Override
            <T> ValueSafeStep.@NonNull Child<T, Parent> of(@NonNull TypeToken<T> token);

            @Override
            default <T> ValueSafeStep.@NonNull Child<T, Parent> of(@NonNull Class<T> clazz) {
                return of(TypeToken.of(clazz));
            }
        }
    }
}
