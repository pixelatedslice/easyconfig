package com.pixelatedslice.easyconfig.impl.config.node.container.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.ContainerNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.node.value.builder.ValueNodeOriginalBuilder;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class ContainerNodeOriginalBuilder extends AbstractContainerNodeBuilder<ContainerNodeOriginalBuilder> implements NodeBuilder.ContainerFinalStep.Original, NodeBuilder.FirstStep {
    @Override
    public ContainerNodeImpl build() {
        var built = new ContainerNodeImpl(this);
        this.buildChildren(built);
        return built;
    }

    @Override
    public @NonNull Child<Original> append(@NonNull String key) {
        //noinspection RedundantCast,unchecked
        return (Child<Original>) (Object) new ContainerNodeChildBuilder<>(this, key);
    }

    @Override
    public <T> ValueFinalStep.@NonNull Original<T> of(@NonNull TypeToken<T> token) {
        return new ValueNodeOriginalBuilder<T>(token, Objects.requireNonNull(key)).config(config).parent(parent);
    }

    @Override
    public @NonNull Original key(@NonNull String key) {
        this.key = key;
        return this;
    }
}
