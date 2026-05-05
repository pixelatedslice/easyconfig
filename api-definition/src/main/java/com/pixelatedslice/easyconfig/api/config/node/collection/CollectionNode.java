package com.pixelatedslice.easyconfig.api.config.node.collection;

import com.google.common.collect.ImmutableCollection;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.collection.builder.CollectionNodeBuilder;
import org.jspecify.annotations.NonNull;

import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface CollectionNode extends Node {
    static @NonNull CollectionNodeBuilder builder() {
        return ServiceLoader.load(CollectionNodeBuilder.class).findFirst().orElseThrow();
    }

    @Override
    default @NonNull NodeType nodeType() {
        return NodeType.COLLECTION_NODE;
    }

    ImmutableCollection<ReturnedNode> nodes();

    Stream<ReturnedNode> stream();

    ReturnedNode atIndex(int index);
}
