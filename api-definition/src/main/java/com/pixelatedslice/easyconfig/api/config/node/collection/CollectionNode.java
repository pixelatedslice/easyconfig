package com.pixelatedslice.easyconfig.api.config.node.collection;

import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CheckReturnValue;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

public interface CollectionNode extends Node {
    @Override
    default @NonNull NodeType nodeType() {
        return NodeType.COLLECTION_NODE;
    }

    @NonNull
    @CheckReturnValue
    ImmutableCollection<ReturnedNode> nodes();

    @NonNull
    @CheckReturnValue
    Stream<ReturnedNode> stream();

    @NonNull
    @CheckReturnValue
    ReturnedNode atIndex(int index);


}
