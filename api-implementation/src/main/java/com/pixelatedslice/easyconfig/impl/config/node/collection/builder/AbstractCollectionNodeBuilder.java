package com.pixelatedslice.easyconfig.impl.config.node.collection.builder;

import com.pixelatedslice.easyconfig.api.config.config.Config;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.collection.CollectionNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeChildBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractCollectionNodeBuilder<Self extends AbstractCollectionNodeBuilder<Self>> implements NodeBuilder.CollectionStep, InternalNodeBuilder<Self> {

    private final List<InternalNodeBuilder<?>> children = new CopyOnWriteArrayList<>();
    private final @NonNull String key;
    private @Nullable Config config;
    private @Nullable AbstractNode parent;

    public AbstractCollectionNodeBuilder(InternalNodeBuilder<?> from) {
        this.key = Objects.requireNonNull(from.key());
        this.parent = from.parent();
        this.config = from.config();
        from.children().forEach(this::appendChild);
    }

    public AbstractCollectionNodeBuilder(@NonNull String key){
        this.key = key;
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

    @Override
    public @Nullable String key() {
        return this.key;
    }

    @Override
    public @NonNull List<InternalNodeBuilder<?>> children() {
        return Collections.unmodifiableList(this.children);
    }

    @Override
    public void appendChild(@NonNull InternalNodeBuilder<?> builder) {
        if (builder instanceof ContainerNodeChildBuilder<?> childBuilder) {
            this.children.add(childBuilder);
            return;
        }
        if(builder instanceof CollectionNodeChildBuilder<?> collectionBuilder){
            this.children.add(collectionBuilder);
return;
        }
        throw new IllegalArgumentException("Cannot append child of " + builder.getClass().getName());
    }

    @Override
    public @NonNull CollectionNodeImpl build() {
        return new CollectionNodeImpl(this);
    }
}
