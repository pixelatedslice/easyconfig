package com.pixelatedslice.easyconfig.api.config.node.container;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.editable.Editable;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.Optional;

public interface ContainerNode extends Node, Editable<EditableContainerNode> {

    default @NonNull NodeType nodeType() {
        return NodeType.CONTAINER_NODE;
    }

    @Override
    NodeBuilder.ContainerFinalStep.@NonNull Original toBuilder();

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
        default boolean isRootNode() {
            return true;
        }
    }
}
