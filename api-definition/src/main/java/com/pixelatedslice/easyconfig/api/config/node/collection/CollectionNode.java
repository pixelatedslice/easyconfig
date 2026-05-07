package com.pixelatedslice.easyconfig.api.config.node.collection;

import com.google.common.collect.ImmutableCollection;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import org.jspecify.annotations.NonNull;

public interface CollectionNode extends Node {
    @Override
    default @NonNull NodeType nodeType() {
        return NodeType.COLLECTION_NODE;
    }

    ImmutableCollection<Node> nodes();

    ContainerNode atIndex(int index);

   
}
