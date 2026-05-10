package com.pixelatedslice.easyconfig.impl.config.node.collection;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ReturnedNode;
import com.pixelatedslice.easyconfig.api.config.node.collection.CollectionNode;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.ReturnKnownNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.node.collection.builder.CollectionNodeOriginalBuilder;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class CollectionNodeImpl extends AbstractNode implements CollectionNode {

    private final List<AbstractNode> children = new CopyOnWriteArrayList<>();

    public CollectionNodeImpl(@NonNull InternalNodeBuilder<?> builder) {
        super(builder);
    }

    @Override
    public ImmutableCollection<ReturnedNode> nodes() {
        return ImmutableList.copyOf(this.children.stream().map(ReturnKnownNodeImpl::new).toList());
    }

    @Override
    public @NonNull Stream<ReturnedNode> stream() {
        return this.children.stream().map(ReturnKnownNodeImpl::new);
    }

    @Override
    public ReturnedNode atIndex(int index) {
        return new ReturnKnownNodeImpl(this.children.get(index));
    }

    @Override
    protected void internalAppendChild(@NonNull AbstractNode node) {
        this.children.add(node);
    }

    @Override
    public @NonNull InternalNodeBuilder<?> toBuilder() {
        var builder = new CollectionNodeOriginalBuilder(this.key()).parent(this.parent).config(this.attached);
        this.children.stream().map(AbstractNode::toBuilder).forEach(builder::appendChild);
        return builder;
    }
}
