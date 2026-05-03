package com.pixelatedslice.easyconfig.api.config.node;

import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public sealed interface Node permits ContainerNode, ValueNode {
    @NonNull String key();

    @NonNull ContainerNode parent();

    default @NonNull String[] fullPath() {
        var list = new ArrayList<String>();
        Node current = this;

        while (true) {
            list.add(current.key());

            if ((current instanceof ContainerNode cn) && cn.isRootNode()) {
                break;
            }

            current = current.parent();
        }

        return list.reversed().toArray(String[]::new);
    }
}
