package com.pixelatedslice.easyconfig.impl.config.node;

import com.google.common.collect.ImmutableList;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.container.EditableContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ReturnedNode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ContainerNodeImpl extends AbstractNode implements ContainerNode {

    private final Collection<Node> immediateChildren = new LinkedBlockingQueue<>();

    public ContainerNodeImpl(@NonNull String key, @Nullable ContainerNode parent) {
        super(key, parent);
    }

    @Override
    public ImmutableList<Node> children() {
        return ImmutableList.<Node>builder()
                .addAll(this.immediateChildren).build();
    }

    @Override
    public @NonNull ReturnedNode node(@NonNull String @NonNull ... path) {
        return travel(this, path);
    }

    void addNodes(@NonNull Collection<Node> nodes) {
        this.immediateChildren.addAll(nodes);
    }

    void removeNodes(@NonNull Collection<Node> nodes) {
        this.immediateChildren.removeAll(nodes);
    }

    @Override
    public EditableContainerNode editable() {
        return null;
    }
}
