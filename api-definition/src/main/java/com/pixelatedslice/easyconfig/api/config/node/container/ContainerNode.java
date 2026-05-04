package com.pixelatedslice.easyconfig.api.config.node.container;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.GenericNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.editable.Editable;
import com.pixelatedslice.easyconfig.api.exception.NodeException;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Consumer;

public non-sealed interface ContainerNode extends Node, Editable<EditableContainerNode> {
    static ContainerNode.@NonNull Builder builder() {
        return ServiceLoader.load(Builder.class).findFirst().orElseThrow();
    }

    default Builder.@NonNull ChildrenStep childContainerBuilder(@NonNull String key) {
        return builder().key(key).parent(this);
    }

    default @NonNull NodeType nodeType() {
        return NodeType.CONTAINER_NODE;
    }

    ImmutableList<Node> children();

    default boolean isRootNode() {
        return false;
    }

    @NonNull ReturnedNode node(@NonNull String @NonNull ... path);

    default @NonNull Optional<@NonNull ContainerNode> containerNode(@NonNull String @NonNull ... path) {
        return this.node(path).container();
    }

    default <T> @NonNull Optional<@NonNull ValueNode<T>> valueNode(
            @NonNull Class<T> simpleType,
            @NonNull String @NonNull ... path
    ) {
        Objects.requireNonNull(simpleType);
        Objects.requireNonNull(path);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
        }

        return this.valueNode(typeToken, path);
    }

    default <T> @NonNull Optional<@NonNull ValueNode<T>> valueNode(
            @NonNull TypeToken<T> typeToken,
            @NonNull String @NonNull ... path
    ) {
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(path);

        return this.node(path).value(typeToken);
    }

    default <T> @NonNull Optional<@NonNull EnvNode<T>> envNode(
            @NonNull Class<T> simpleType,
            @NonNull String @NonNull ... path
    ) {
        Objects.requireNonNull(simpleType);
        Objects.requireNonNull(path);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(simpleType);
        }

        return this.envNode(typeToken, path);
    }

    default <T> @NonNull Optional<@NonNull EnvNode<T>> envNode(
            @NonNull TypeToken<T> typeToken,
            @NonNull String @NonNull ... path
    ) {
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(path);

        return this.node(path).env(typeToken);
    }

    interface Root extends ContainerNode {
        @Override
        default @NonNull String key() {
            return "root";
        }

        @Override
        @NonNull
        default String[] fullPath() {
            return new String[]{"root"};
        }

        @Override
        default @NonNull ContainerNode parent() {
            throw NodeException.ROOT_NODE_HAS_NO_PARENT();
        }

        @Override
        default boolean isRootNode() {
            return true;
        }
    }

    @FunctionalInterface
    interface Builder extends GenericNodeBuilder<Builder.ParentStep> {
        interface ParentStep extends GenericNodeBuilder.ParentStep<ChildrenStep>, ChildrenStep {
        }

        interface ChildrenStep extends BuilderStep, FinalStep {
            <T> @NonNull ChildrenStep valueNode(@NonNull Consumer<? super ValueNode.Builder<T>> valueNodeBuilder);

            <T> @NonNull ChildrenStep valueNode(@NonNull ValueNode<T> valueNode);

            /**
             * Call {@link ContainerNode.Builder.ChildrenStep#valueNode(ValueNode)} to add the node.
             *
             * @param <T> The type
             * @return A new {@link ValueNode.Builder}
             */
            <T> ValueNode.@NonNull Builder<T> node();

            default <T> @NonNull ChildrenStep valueNode(@NonNull String key, @NonNull TypeToken<T> typeToken,
                    @Nullable T value) {
                return this.valueNode((ValueNode.Builder<T> b) -> {
                    b.key(key).type(typeToken).value(value);
                });
            }

            default <T> @NonNull ChildrenStep valueNode(@NonNull String key, @NonNull Class<T> simpleType,
                    @Nullable T value) {
                return this.valueNode((ValueNode.Builder<T> b) -> {
                    b.key(key).type(simpleType).value(value);
                });
            }

            <T> @NonNull ChildrenStep envNode(@NonNull Consumer<? super EnvNode.Builder<T>> envNodeBuilder);

            <T> @NonNull ChildrenStep envNode(@NonNull EnvNode<T> envNode);

            /**
             * Call {@link ContainerNode.Builder.ChildrenStep#envNode(EnvNode)} to add the node.
             *
             * @param <T> The type
             * @return A new {@link EnvNode.Builder}
             */
            <T> EnvNode.@NonNull Builder<T> envNode();

            default <T> @NonNull ChildrenStep envNode(@NonNull String key, @NonNull TypeToken<T> typeToken,
                    @NonNull String environmentVariable) {
                return this.envNode((EnvNode.Builder<T> b) -> {
                    b.key(key).type(typeToken).environmentVariable(environmentVariable);
                });
            }

            default <T> @NonNull ChildrenStep envNode(@NonNull String key, @NonNull Class<T> simpleType,
                    @NonNull String environmentVariable) {
                return this.envNode((EnvNode.Builder<T> b) -> {
                    b.key(key).type(simpleType).environmentVariable(environmentVariable);
                });
            }

            @NonNull ChildrenStep containerNode(@NonNull Consumer<? super Builder> containerNodeBuilder);

            @NonNull ChildrenStep containerNode(@NonNull ContainerNode containerNodeBuilder);

            /**
             * Call {@link ChildrenStep#containerNode(ContainerNode)} to add the ContainerNode.
             *
             * @return A new {@link ContainerNode.Builder}
             */
            ContainerNode.@NonNull Builder containerNode();
        }

        @FunctionalInterface
        interface FinalStep extends BuilderStep {
            @NonNull ContainerNode build();
        }

        interface Handler extends GenericNodeBuilder.Handler<ParentStep, ChildrenStep>,
                ParentStep, ChildrenStep, FinalStep {
        }
    }
}
