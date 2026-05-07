package com.pixelatedslice.easyconfig.api.config.node.value;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.editable.Editable;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface ValueNode<T> extends Node, Editable<EditableValueNode<T>> {

    @Override
    NodeBuilder.ValueFinalStep.@NonNull Original<T> toBuilder();

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

}