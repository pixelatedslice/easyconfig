package com.pixelatedslice.easyconfig.api.config.node.value;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.GenericNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.editable.Editable;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.function.Function;

public non-sealed interface ValueNode<T> extends Node, Editable<EditableValueNode<T>> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull Builder<T> builder() {
        return (Builder<T>) ServiceLoader.load(Builder.class).findFirst().orElseThrow();
    }

    default @NonNull NodeType nodeType() {
        return NodeType.VALUE_NODE;
    }

    @NonNull Optional<@NonNull T> value();

    @NonNull Optional<@NonNull T> defaultValue();

    default @NonNull Optional<@NonNull T> valueOrDefault() {
        return this.value().or(this::defaultValue);
    }

    @NonNull Optional<@NonNull Serializer<@NonNull T>> serializer();

    @NonNull Validator<T> validator();

    @NonNull TypeToken<T> typeToken();

    @FunctionalInterface
    interface Builder<T> extends GenericNodeBuilder<Builder.ParentStep<T>> {
        interface ParentStep<T> extends GenericNodeBuilder.ParentStep<TypeStep<T>>, TypeStep<T> {
        }

        @FunctionalInterface
        interface TypeStep<T> extends BuilderStep {

            @NonNull ValueStep<@NonNull T> type(@NonNull TypeToken<@NonNull T> typeToken);

            default @NonNull ValueStep<@NonNull T> type(@NonNull Class<@NonNull T> simpleType) {
                Objects.requireNonNull(simpleType);

                var typeToken = TypeToken.of(simpleType);

                if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
                    throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
                }

                return this.type(typeToken);
            }

        }

        interface ValueStep<T> extends BuilderStep, SerializerStep<T> {

            @NonNull ValueStep<@NonNull T> value(@Nullable T value);

            @NonNull ValueStep<@NonNull T> defaultValue(@Nullable T defaultValue);
        }

        interface SerializerStep<T> extends BuilderStep, ValidatorStep<T> {
            @NonNull ValidatorStep<T> serializer(@NonNull Serializer<@NonNull T> serializer);

            @NonNull ValidatorStep<T> serializer(
                    @NonNull BiConsumer<@NonNull T, ContainerNode.Builder.@NonNull ChildrenStep> serialize,
                    @NonNull Function<@NonNull Node, @NonNull T> deserialize
            );

            @NonNull EndWithDeserializeStep<T> serialize(
                    @NonNull BiConsumer<@NonNull T, ContainerNode.Builder.@NonNull ChildrenStep> serialize
            );

            @NonNull EndWithSerializeStep<T> deserialize(
                    @NonNull Function<@NonNull Node, @NonNull T> deserialize
            );

            @FunctionalInterface
            interface EndWithSerializeStep<T> {
                @NonNull ValidatorStep<T> serialize(
                        @NonNull BiConsumer<@NonNull T, ContainerNode.Builder.@NonNull ChildrenStep> serialize
                );
            }

            @FunctionalInterface
            interface EndWithDeserializeStep<T> {
                @NonNull ValidatorStep<T> deserialize(
                        @NonNull Function<@NonNull Node, @NonNull T> deserialize
                );
            }
        }

        interface ValidatorStep<T> extends BuilderStep, FinalStep<T> {
            @NonNull FinalStep<T> validator(@NonNull Validator<@Nullable T> validator);
        }

        @FunctionalInterface
        interface FinalStep<T> extends BuilderStep {
            @NonNull ValueNode<@NonNull T> build();
        }

        interface Handler<T> extends GenericNodeBuilder.Handler<ParentStep<T>, TypeStep<T>>,
                ParentStep<T>, TypeStep<T>, ValueStep<T>, SerializerStep<T>, ValidatorStep<T>, FinalStep<T> {
        }
    }
}
