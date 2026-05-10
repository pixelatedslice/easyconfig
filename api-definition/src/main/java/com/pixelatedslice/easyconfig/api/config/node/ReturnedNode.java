package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface ReturnedNode {
    @NonNull Optional<@NonNull Node> plainNode();

    @NonNull Optional<@NonNull ContainerNode> container();

    @NonNull Optional<@NonNull CollectionNode> collectionNode();

    <T> @NonNull Optional<@NonNull ValueNode<T>> value(@NonNull Class<T> simpleType);

    <T> @NonNull Optional<@NonNull ValueNode<T>> value(@NonNull TypeToken<T> typeToken);

    @NonNull Optional<@NonNull ValueNode<?>> unsafeValue();

    <T> @NonNull Optional<@NonNull EnvNode<T>> env(@NonNull Class<T> simpleType);

    <T> @NonNull Optional<@NonNull EnvNode<T>> env(@NonNull TypeToken<T> typeToken);

    @NonNull Optional<@NonNull EnvNode<?>> unsafeEnv();
}
