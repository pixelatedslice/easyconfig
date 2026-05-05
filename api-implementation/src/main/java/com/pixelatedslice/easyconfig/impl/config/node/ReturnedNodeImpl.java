package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.exception.NodeException;
import com.pixelatedslice.easyconfig.api.exception.TypeException;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ReturnedNodeImpl implements ReturnedNode {
    private final @Nullable Node plainNode;

    public ReturnedNodeImpl(@Nullable Node plainNode) {
        this.plainNode = plainNode;
    }

    @Override
    public @NonNull Optional<@NonNull Node> plainNode() {
        return Optional.ofNullable(this.plainNode);
    }

    @Override
    public @NonNull Optional<@NonNull ContainerNode> container() {
        if (this.plainNode == null) {
            return Optional.empty();
        }

        if (!(this.plainNode instanceof ContainerNode containerNode)) {
            throw NodeException.DID_NOT_EXPECT_NODE_TYPE(
                    this.plainNode.key(),
                    NodeType.CONTAINER_NODE,
                    this.plainNode.nodeType()
            );
        }

        return Optional.of(containerNode);
    }

    @Override
    public @NonNull Optional<@NonNull CollectionNode> collectionNode() {
        if (this.plainNode == null) {
            return Optional.empty();
        }

        if (!(this.plainNode instanceof CollectionNode containerNode)) {
            throw NodeException.DID_NOT_EXPECT_NODE_TYPE(
                    this.plainNode.key(),
                    NodeType.COLLECTION_NODE,
                    this.plainNode.nodeType()
            );
        }

        return Optional.of(containerNode);
    }

    @Override
    public <T> @NonNull Optional<@NonNull ValueNode<T>> value(@NonNull Class<T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(typeToken, simpleType);
        }

        return this.value(typeToken);
    }

    @Override
    public <T> @NonNull Optional<@NonNull ValueNode<T>> value(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);

        if (this.plainNode == null) {
            return Optional.empty();
        }

        if (!(this.plainNode instanceof ValueNode<?> valueNode)) {
            throw NodeException.DID_NOT_EXPECT_NODE_TYPE(
                    this.plainNode.key(),
                    NodeType.VALUE_NODE,
                    this.plainNode.nodeType()
            );
        }

        var nodeTypeToken = valueNode.typeToken();

        if (!TypeTokenUtils.matches(typeToken, nodeTypeToken)) {
            throw NodeException.NODE_HAS_WRONG_VALUE_TYPE(this.plainNode.key(), nodeTypeToken, typeToken.toString());
        }

        @SuppressWarnings("unchecked") var castedNode = (ValueNode<T>) valueNode;
        return Optional.of(castedNode);
    }

    @Override
    public @NonNull Optional<@NonNull ValueNode<?>> unsafeValue() {
        return (this.plainNode instanceof ValueNode<?> valueNode)
                ? Optional.of(valueNode)
                : Optional.empty();
    }

    @Override
    public <T> @NonNull Optional<@NonNull EnvNode<T>> env(@NonNull Class<T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw TypeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(typeToken, simpleType);
        }

        return this.env(typeToken);
    }

    @Override
    public <T> @NonNull Optional<@NonNull EnvNode<T>> env(@NonNull TypeToken<T> typeToken) {
        var opt = this.value(typeToken);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        var node = opt.get();
        if (!(node instanceof EnvNode<T> envNode)) {
            throw NodeException.DID_NOT_EXPECT_NODE_TYPE(node.key(), NodeType.ENV_NODE, node.nodeType());
        }
        return Optional.of(envNode);
    }

    @Override
    public @NonNull Optional<@NonNull ValueNode<?>> unsafeEnv() {
        return (this.plainNode instanceof EnvNode<?> valueNode)
                ? Optional.of(valueNode)
                : Optional.empty();
    }
}
