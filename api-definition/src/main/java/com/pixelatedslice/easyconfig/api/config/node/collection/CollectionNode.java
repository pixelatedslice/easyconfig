package com.pixelatedslice.easyconfig.api.config.node.collection;

import com.google.common.collect.ImmutableCollection;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.GenericNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface CollectionNode extends Node {
    @Override
    default @NonNull NodeType nodeType() {
        return NodeType.COLLECTION_NODE;
    }

    ImmutableCollection<ReturnedNode> nodes();

    Stream<ReturnedNode> stream();

    ReturnedNode atIndex(int index);

    @FunctionalInterface
    interface Builder extends GenericNodeBuilder<Builder.ParentStep> {
        interface ParentStep extends GenericNodeBuilder.ParentStep<ChildrenStep>,
                ChildrenStep {
        }

        interface ChildrenStep extends BuilderStep, FinalStep {
            <T> @NonNull ChildrenStep valueNode(@NonNull Consumer<? super ValueNode.Builder<T>> valueNodeBuilder);

            <T> @NonNull ChildrenStep valueNode(@NonNull ValueNode<T> valueNode);

            /**
             * Call {@link ChildrenStep#valueNode(ValueNode)} to add the node.
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
             * Call {@link ChildrenStep#envNode(EnvNode)} to add the node.
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

            @NonNull ChildrenStep containerNode(@NonNull Consumer<? super ContainerNode.Builder> containerNodeBuilder);

            @NonNull ChildrenStep containerNode(@NonNull ContainerNode containerNodeBuilder);

            /**
             * Call {@link ChildrenStep#containerNode(ContainerNode)} to add the ContainerNode.
             *
             * @return A new {@link ContainerNode.Builder}
             */
            ContainerNode.@NonNull Builder containerNode();

            @NonNull ChildrenStep collectionNode(@NonNull Consumer<? super Builder> containerNodeBuilder);

            @NonNull ChildrenStep collectionNode(@NonNull CollectionNode containerNodeBuilder);

            /**
             * Call {@link ChildrenStep#collectionNode(CollectionNode)} to add the ContainerNode.
             *
             * @return A new {@link ContainerNode.Builder}
             */
            @NonNull Builder collectionNode();
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
