package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class ReturnKnownNodeImpl implements ReturnedNode {

    private final @Nullable Node node;

    public ReturnKnownNodeImpl(@Nullable Node node) {
        this.node = node;
    }

    @Override
    public @NonNull Optional<@NonNull Node> plainNode() {
        return Optional.ofNullable(this.node);
    }

    private <N extends Node> @NonNull Optional<@NonNull N> node(Class<N> instanceOf) {
        return plainNode().filter(instanceOf::isInstance).map(node -> (N) node);
    }

    @Override
    public @NonNull Optional<@NonNull ContainerNode> container() {
        return node(ContainerNode.class);
    }

    @Override
    public @NonNull Optional<@NonNull CollectionNode> collectionNode() {
        return node(CollectionNode.class);
    }

    @Override
    public @NonNull <T> Optional<@NonNull ValueNode<T>> value(@NonNull Class<T> simpleType) {
        return value(TypeToken.of(simpleType));
    }

    @Override
    public @NonNull <T> Optional<@NonNull ValueNode<T>> value(@NonNull TypeToken<T> typeToken) {
        //noinspection unchecked
        return node(ValueNode.class)
                .filter(valueNode -> valueNode.typeToken().equals(typeToken))
                .map(valueNode -> (ValueNode<T>) valueNode);

    }

    @Override
    public @NonNull Optional<@NonNull ValueNode<?>> unsafeValue() {
        return node(ValueNode.class).map(valueNode -> (ValueNode<?>) valueNode);
    }

    @Override
    public @NonNull <T> Optional<@NonNull EnvNode<T>> env(@NonNull Class<T> simpleType) {
        return env(TypeToken.of(simpleType));
    }

    @Override
    public @NonNull <T> Optional<@NonNull EnvNode<T>> env(@NonNull TypeToken<T> typeToken) {
        //noinspection unchecked
        return node(EnvNode.class)
                .filter(envNode -> envNode.typeToken().equals(typeToken))
                .map(envNode -> (EnvNode<T>) envNode);
    }

    @Override
    public @NonNull Optional<@NonNull ValueNode<?>> unsafeEnv() {
        return node(EnvNode.class).map(node -> (EnvNode<?>)node);
    }
}
