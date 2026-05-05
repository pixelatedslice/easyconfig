package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ReturnedNode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

class AbstractNode implements Node {

    private final @Nullable ContainerNode parent;
    private final @NonNull String key;

    AbstractNode(@NonNull String key, @Nullable ContainerNode parent) {
        this.key = Objects.requireNonNull(key);
        this.parent = parent;
    }

    @Override
    public @NonNull String key() {
        return this.key;
    }

    @Override
    public @NonNull Optional<@NonNull ContainerNode> parent() {
        return Optional.ofNullable(this.parent);
    }

    static ReturnedNode travel(@NonNull Node node, String... path){
        return travel(node, 0, path);
    }

    private static ReturnedNode travel(@NonNull Node node, int index, String... path){
        Objects.requireNonNull(node);
        if(path.length == index){
            return new ReturnedNode(node);
        }
        var targetKey = path[index];
        var opChildNode = children(node).filter(n -> n.key().equals(targetKey)).findFirst();
        if(opChildNode.isEmpty()){
            return new ReturnedNode(null);
        }
        return travel(node, index + 1, path);
    }

    private static Stream<Node> children(@NonNull Node node){
        if(node instanceof ContainerNode container){
            return container.children().stream();
        }
        return Stream.empty();
    }
}
