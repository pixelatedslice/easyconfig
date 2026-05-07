package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.container.EditableContainerNode;
import com.pixelatedslice.easyconfig.impl.utils.DistinctByGatherer;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Function;
import java.util.stream.Stream;

public class ContainerNodeEditableImpl implements EditableContainerNode {

    private final @NonNull ContainerNodeImpl target;
    private final @NonNull LinkedTransferQueue<Node> removingNode = new LinkedTransferQueue<>();
    private final @NonNull LinkedTransferQueue<Node> addingNodes = new LinkedTransferQueue<>();

    ContainerNodeEditableImpl(@NonNull ContainerNodeImpl target) {
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public @NonNull EditableContainerNode addNodes(@NonNull Collection<? extends @NonNull Node> nodes) {
        this.addingNodes.addAll(nodes);
        return this;
    }

    @Override
    public @NonNull EditableContainerNode setNodes(@NonNull Collection<? extends @NonNull Node> nodes) {
        this.clearNodes();
        return this.addNodes(nodes.toArray(Node[]::new));
    }

    @Override
    public @NonNull EditableContainerNode removeNodes(@NonNull Node @NonNull ... nodes) {
        return removeNodes(Stream.of(nodes).map(Node::key), t -> t);
    }

    @Override
    public @NonNull EditableContainerNode removeNodes(@NonNull Collection<? extends @NonNull Node> nodes) {
        return removeNodes(nodes.stream().map(Node::key), t -> t);
    }

    @Override
    public @NonNull EditableContainerNode removeNodes(@NonNull String @NonNull ... keys) {
        return removeNodes(Stream.of(keys), t -> t);
    }

    @Override
    public @NonNull EditableContainerNode clearNodes() {
        this.removingNode.addAll(this.target.children());
        return this;
    }

    private @NonNull EditableContainerNode removeNodes(Stream<String> keys, Function<Stream<Node>, Stream<Node>> function) {
        var targetChildren = this.target.children().stream().toList();
        var removingFilter = keys.map(key -> targetChildren.stream().filter(n -> n.key().equals(key)).findFirst()).filter(Optional::isPresent).map(Optional::orElseThrow);
        function.apply(removingFilter);
        this.removingNode.addAll(removingFilter.toList());
        return this;
    }

    @Override
    public void close() {
        var distinctToRemove = this.removingNode.stream().gather(new DistinctByGatherer<>(Node::key)).toList();
        this.target.removeNodes(distinctToRemove);

        //TODO to builder -> swap parent to this
        var distinctToAdd = this.addingNodes.stream().gather(new DistinctByGatherer<>(Node::key)).toList();
        this.target.addNodes(distinctToAdd);
    }
}
