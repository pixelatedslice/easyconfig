package com.pixelatedslice.easyconfig.api.config.node.env;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.GenericNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.function.Function;

public interface EnvNode<T> extends ValueNode<T> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull Builder<T> builder() {
        return (Builder<T>) ServiceLoader.load(Builder.class).findFirst().orElseThrow();
    }

    default @NonNull NodeType nodeType() {
        return NodeType.ENV_NODE;
    }

    @NonNull String envKey();

    @FunctionalInterface
    interface Builder<T> extends GenericNodeBuilder<Builder.TypeStep<T>> {
        interface ParentStep<T> extends GenericNodeBuilder.ParentStep<TypeStep<T>>, TypeStep<T> {
        }

        @FunctionalInterface
        interface TypeStep<T> extends BuilderStep {
            @NonNull EnvStep<@NonNull T> type(@NonNull TypeToken<@NonNull T> typeToken);

            default @NonNull EnvStep<@NonNull T> type(@NonNull Class<@NonNull T> simpleType) {
                Objects.requireNonNull(simpleType);

                var typeToken = TypeToken.of(simpleType);

                if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
                    throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
                }

                return this.type(typeToken);
            }
        }

        @FunctionalInterface
        interface EnvStep<T> extends BuilderStep {
            @NonNull SerializerStep<@NonNull T> environmentVariable(@Nullable String environmentVariable);
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
            @NonNull EnvNode<@NonNull T> build();
        }

        interface Handler<T> extends GenericNodeBuilder.Handler<ParentStep<T>, TypeStep<T>>,
                Builder.ParentStep<T>, Builder.TypeStep<T>, Builder.EnvStep<T>, Builder.SerializerStep<T>,
                Builder.ValidatorStep<T>, Builder.FinalStep<T> {
        }
    }
}
