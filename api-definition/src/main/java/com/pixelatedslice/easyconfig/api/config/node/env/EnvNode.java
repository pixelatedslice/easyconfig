package com.pixelatedslice.easyconfig.api.config.node.env;

import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;

public interface EnvNode<T> extends ValueNode<T> {

    default @NonNull NodeType nodeType() {
        return NodeType.ENV_NODE;
    }

    @NonNull String envKey();
}
