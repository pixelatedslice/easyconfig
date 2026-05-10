package com.pixelatedslice.easyconfig.impl.config.node.container;

import com.google.common.collect.ImmutableList;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.container.EditableContainerNode;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeOriginalBuilder;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class ContainerNodeImpl extends AbstractNode implements ContainerNode {

    private final Collection<Node> immediateChildren = new LinkedBlockingQueue<>();

    public ContainerNodeImpl(InternalNodeBuilder<?> builder) {
        super(builder);
    }

    @Override
    protected synchronized void internalAppendChild(@NonNull AbstractNode node) {
        this.immediateChildren.add(node);
    }

    protected synchronized void internalAppendChildren(@NonNull Collection<AbstractNode> node) {
        this.immediateChildren.addAll(node);
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


    synchronized void removeNodes(@NonNull Collection<Node> nodes) {
        this.immediateChildren.removeAll(nodes);
    }

    @Override
    public EditableContainerNode editable() {
        return new ContainerNodeEditableImpl(this);
    }

    @Override
    public @NonNull ContainerNodeOriginalBuilder  toBuilder() {
        return new ContainerNodeOriginalBuilder().parent(this.parent).config(this.attached).key(this.key());
    }
}
