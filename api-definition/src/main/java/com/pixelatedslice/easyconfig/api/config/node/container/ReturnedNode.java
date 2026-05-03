package com.pixelatedslice.easyconfig.api.config.node.container;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.exception.NodeException;
import com.pixelatedslice.easyconfig.api.utils.typetoken.TypeTokenUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public record ReturnedNode(@Nullable Node node) {
    public @NonNull Optional<@NonNull ContainerNode> container() {
        if (this.node == null) {
            return Optional.empty();
        }

        if (!(this.node instanceof ContainerNode containerNode)) {
            throw NodeException.EXPECTED_CONTAINER_NODE_GOT_VALUE(this.node.key());
        }

        return Optional.of(containerNode);
    }

    public <T> @NonNull Optional<@NonNull ValueNode<T>> value(@NonNull Class<T> simpleType) {
        Objects.requireNonNull(simpleType);

        var typeToken = TypeToken.of(simpleType);

        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw NodeException.CLASS_USED_IN_PLACE_OF_TYPETOKEN(typeToken, simpleType);
        }

        return this.value(typeToken);
    }

    public <T> @NonNull Optional<@NonNull ValueNode<T>> value(@NonNull TypeToken<T> typeToken) {
        Objects.requireNonNull(typeToken);

        if (this.node == null) {
            return Optional.empty();
        }

        if (!(this.node instanceof ValueNode<?> valueNode)) {
            throw NodeException.EXPECTED_VALUE_NODE_GOT_CONTAINER(this.node.key());
        }

        var nodeTypeToken = valueNode.typeToken();

        if (!TypeTokenUtils.matches(typeToken, nodeTypeToken)) {
            throw NodeException.NODE_HAS_WRONG_VALUE_TYPE(this.node.key(), nodeTypeToken, typeToken.toString());
        }

        @SuppressWarnings("unchecked") var castedNode = (ValueNode<T>) valueNode;
        return Optional.of(castedNode);
    }

    public @NonNull Optional<@NonNull ValueNode<?>> unsafeValue() {
        return (this.node instanceof ValueNode<?> valueNode)
                ? Optional.of(valueNode)
                : Optional.empty();
    }
}
