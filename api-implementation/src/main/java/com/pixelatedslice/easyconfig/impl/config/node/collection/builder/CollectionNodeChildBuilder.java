package com.pixelatedslice.easyconfig.impl.config.node.collection.builder;

import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeChildBuilder;
import org.jspecify.annotations.NonNull;

public class CollectionNodeChildBuilder<Previous extends InternalNodeBuilder<?>> extends AbstractCollectionNodeBuilder<CollectionNodeChildBuilder<Previous>> implements NodeBuilder.CollectionStep.Child<Previous> {

    private final @NonNull Previous previous;

    public CollectionNodeChildBuilder(InternalNodeBuilder<?> from, @NonNull Previous previous) {
        super(from);
        this.previous = previous;
    }

    @Override
    public ContainerSafeStep.Child<Child<Previous>> append() {
        ContainerNodeChildBuilder<CollectionNodeChildBuilder<Previous>> builder = new ContainerNodeChildBuilder<>(this, "index_" + children().size());
        //noinspection unchecked
        return (ContainerSafeStep.Child<Child<Previous>>) (Object) builder;

    }

    @Override
    public @NonNull Previous complete() {
        this.previous.appendChild(this);
        return this.previous;
    }
}
