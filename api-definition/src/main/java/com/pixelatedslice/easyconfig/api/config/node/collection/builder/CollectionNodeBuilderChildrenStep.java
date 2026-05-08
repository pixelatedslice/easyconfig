package com.pixelatedslice.easyconfig.api.config.node.collection.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.container.builder.ContainerNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.env.builder.EnvNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.config.node.value.builder.ValueNodeBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public interface CollectionNodeBuilderChildrenStep extends BuilderStep, CollectionNodeBuilderFinalStep {
    <T> @NonNull CollectionNodeBuilder valueNode(@NonNull Consumer<? super ValueNodeBuilder<T>> valueNodeBuilder);

    <T> @NonNull CollectionNodeBuilder valueNode(@NonNull ValueNode<T> valueNode);

    /**
     * Call {@link CollectionNodeBuilderChildrenStep#valueNode(ValueNode)} to add the node
     *
     * @param <T> The type
     * @return A new {@link ValueNodeBuilder}
     */
    <T> @NonNull ValueNodeBuilder<T> node();

    default <T> @NonNull CollectionNodeBuilder valueNode(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @Nullable T value) {
        return this.valueNode((ValueNodeBuilder<T> b) -> {
            b.key(key).type(typeToken).value(value);
        });
    }

    default <T> @NonNull CollectionNodeBuilder valueNode(@NonNull String key, @NonNull Class<T> simpleType,
            @Nullable T value) {
        return this.valueNode((ValueNodeBuilder<T> b) -> {
            b.key(key).type(simpleType).value(value);
        });
    }

    <T> @NonNull CollectionNodeBuilder envNode(@NonNull Consumer<? super EnvNodeBuilder<T>> envNodeBuilder);

    <T> @NonNull CollectionNodeBuilder envNode(@NonNull EnvNode<T> envNode);

    /**
     * Call {@link CollectionNodeBuilderChildrenStep#envNode(EnvNode)} to add the node
     *
     * @param <T> The type
     * @return A new {@link EnvNodeBuilder}
     */
    <T> @NonNull EnvNodeBuilder<T> envNode();

    default <T> @NonNull CollectionNodeBuilder envNode(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull String environmentVariable) {
        return this.envNode((EnvNodeBuilder<T> b) -> {
            b.key(key).type(typeToken).environmentVariable(environmentVariable);
        });
    }

    default <T> @NonNull CollectionNodeBuilder envNode(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull String environmentVariable) {
        return this.envNode((EnvNodeBuilder<T> b) -> {
            b.key(key).type(simpleType).environmentVariable(environmentVariable);
        });
    }

    @NonNull CollectionNodeBuilder containerNode(@NonNull Consumer<? super ContainerNodeBuilder> containerNodeBuilder);

    @NonNull CollectionNodeBuilder containerNode(@NonNull ContainerNode containerNodeBuilder);

    /**
     * Call {@link CollectionNodeBuilderChildrenStep#containerNode(ContainerNode)} to add the ContainerNode
     *
     * @return A new {@link ContainerNodeBuilder}
     */
    @NonNull ContainerNodeBuilder containerNode();

    @NonNull CollectionNodeBuilder collectionNode(
            @NonNull Consumer<? super CollectionNodeBuilder> containerNodeBuilder);

    @NonNull CollectionNodeBuilder collectionNode(@NonNull CollectionNode containerNodeBuilder);

    /**
     * Call {@link CollectionNodeBuilderChildrenStep#collectionNode(CollectionNode)} to add the ContainerNode
     *
     * @return A new {@link ContainerNodeBuilder}
     */
    @NonNull CollectionNodeBuilder collectionNode();
}
