package com.pixelatedslice.easyconfig.impl.config.node.container.builder;

import com.pixelatedslice.easyconfig.api.config.config.Config;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractContainerNodeBuilder<Self extends AbstractContainerNodeBuilder<Self>> implements NodeBuilder.ContainerFinalStep, InternalNodeBuilder<Self> {

    @Nullable String key;
    @Nullable AbstractNode parent;
    @Nullable Config config;
    @NonNull Collection<InternalNodeBuilder<?>> children = new CopyOnWriteArrayList<>();

    @Override
    public Collection<InternalNodeBuilder<?>> children() {
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public void appendChild(@NonNull InternalNodeBuilder<?> builder) {
        this.children.add(builder);
    }

    @Override
    public @NonNull Self parent(@Nullable AbstractNode node) {
        this.parent = node;
        //noinspection unchecked
        return (Self) this;
    }

    @Override
    public @Nullable AbstractNode parent() {
        return this.parent;
    }

    @Override
    public @NonNull Self config(@Nullable Config config) {
        this.config = config;
        //noinspection unchecked
        return (Self) this;
    }

    @Override
    public @Nullable Config config() {
        return this.config;
    }

    public @Nullable String key() {
        return key;
    }
}
