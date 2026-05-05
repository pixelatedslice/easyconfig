package com.pixelatedslice.easyconfig.api.config.node.env;

import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.env.builder.EnvNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;

import java.util.ServiceLoader;

public interface EnvNode<T> extends ValueNode<T> {
    @SuppressWarnings("unchecked")
    static <T> @NonNull EnvNodeBuilder<T> builder() {
        return (EnvNodeBuilder<T>) ServiceLoader.load(EnvNodeBuilder.class).findFirst().orElseThrow();
    }

    default @NonNull NodeType nodeType() {
        return NodeType.ENV_NODE;
    }

    @NonNull String envKey();
}
