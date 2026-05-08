package com.pixelatedslice.easyconfig.api.config.node.value;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.value.builder.ValueNodeBuilder;
import com.pixelatedslice.easyconfig.api.editable.Editable;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import com.pixelatedslice.easyconfig.api.validator.Validator;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.ServiceLoader;

public interface ValueNode<T> extends Node, Editable<EditableValueNode<T>> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull ValueNodeBuilder<T> builder() {
        return (ValueNodeBuilder<T>) ServiceLoader.load(ValueNodeBuilder.class).findFirst().orElseThrow();
    }

    @Override
    @NonNull ValueNodeBuilder<T> toBuilder();

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
