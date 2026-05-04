package com.pixelatedslice.easyconfig.api.config.node;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Optional;

public interface Node {
    default @NonNull NodeType nodeType() {
        return NodeType.PLAIN_NODE;
    }

    @NonNull String key();

    @NonNull Optional<@NonNull ContainerNode> parent();

    default @NonNull String[] fullPath() {
        var list = new ArrayList<String>();
        Node current = this;

        while (true) {
            list.add(current.key());

            if (current.parent().isEmpty()) {
                break;
            }

            current = current.parent().get();
        }

        return list.reversed().toArray(String[]::new);
    }

    @FunctionalInterface
    interface Builder extends GenericNodeBuilder<Builder.ParentStep> {
        interface ParentStep extends GenericNodeBuilder.ParentStep<ToNodeBuilderStep>, ToNodeBuilderStep {
        }

        interface ToNodeBuilderStep extends BuilderStep {
            <T> ValueNode.Builder.@NonNull TypeStep<T> valueNode();

            ContainerNode.Builder.@NonNull ChildrenStep containerNode();
        }
    }
}
