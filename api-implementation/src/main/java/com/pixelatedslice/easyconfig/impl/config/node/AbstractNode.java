package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.config.Config;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractNode implements Node {

    protected final @Nullable AbstractNode parent;
    protected final @Nullable Config attached;
    private final @NonNull String key;

    public AbstractNode(@NonNull InternalNodeBuilder<?> builder) {
        this.key = Objects.requireNonNull(builder.key());
        this.parent = builder.parent();
        this.attached = builder.config();
    }

    public static ReturnedNode travel(@NonNull Node node, String... path) {
        return travel(node, 0, path);
    }

    private static ReturnedNode travel(@NonNull Node node, int index, String... path) {
        Objects.requireNonNull(node);
        if (path.length == index) {
            return new ReturnKnownNodeImpl(node);
        }
        var targetKey = path[index];
        var opChildNode = children(node).filter(n -> n.key().equals(targetKey)).findFirst();
        return opChildNode.map(value -> travel(value, index + 1, path)).orElseGet(() -> new ReturnKnownNodeImpl(null));
    }

    private static Stream<Node> children(@NonNull Node node) {
        if (node instanceof ContainerNode container) {
            return container.children().stream();
        }
        if (node instanceof CollectionNode collection) {
            return collection.nodes().stream();
        }
        return Stream.empty();
    }

    protected abstract void internalAppendChild(@NonNull AbstractNode node);

    @Override
    public @NonNull String key() {
        return this.key;
    }

    @Override
    public @NonNull ReturnedNode parent() {
        return new ReturnKnownNodeImpl(this.parent);
    }

    public @Nullable Config config() {
        if (this.attached != null) {
            return this.attached;
        }
        if (this.parent == null) {
            return null;
        }
        return this.parent.config();
    }
}
