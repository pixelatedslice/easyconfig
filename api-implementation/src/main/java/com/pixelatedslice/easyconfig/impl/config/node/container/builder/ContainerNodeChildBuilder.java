package com.pixelatedslice.easyconfig.impl.config.node.container.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.ContainerNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.node.value.builder.ValueNodeChildBuilder;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class ContainerNodeChildBuilder<Previous extends InternalNodeBuilder<?>> extends AbstractContainerNodeBuilder<ContainerNodeChildBuilder<Previous>> implements NodeBuilder.ContainerFinalStep.Child<Previous> {

    private final @NonNull Previous previous;
    private final @NonNull Collection<InternalNodeBuilder<?>> children = new LinkedList<>();

    public ContainerNodeChildBuilder(@NonNull Previous previous, @NonNull String key) {
        this.key = key;
        this.previous = previous;
    }

    @Override
    public @NonNull Child<Child<Previous>> append(@NonNull String key) {
        ContainerNodeChildBuilder<ContainerNodeChildBuilder<Previous>> builder = new ContainerNodeChildBuilder<>(this, key);
        //noinspection unchecked
        return (Child<Child<Previous>>) (Object) builder;
    }

    @Override
    public @NonNull Previous complete() {
        this.previous.appendChild(this);
        return this.previous;
    }

    @Override
    public <T> ValueFinalStep.@NonNull Child<T, Previous> of(@NonNull TypeToken<T> token) {
        return new ValueNodeChildBuilder<>(token, Objects.requireNonNull(this.key()), this.previous);
    }

    @Override
    public @NonNull Collection<InternalNodeBuilder<?>> children() {
        return Collections.unmodifiableCollection(this.children);
    }

    @Override
    public void appendChild(@NonNull InternalNodeBuilder<?> builder) {
        this.children.add(builder);
    }

    @Override
    public @NonNull AbstractNode build() {
        return new ContainerNodeImpl(this);
    }
}
