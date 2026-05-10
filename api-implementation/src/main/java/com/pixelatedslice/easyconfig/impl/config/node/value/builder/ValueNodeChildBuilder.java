package com.pixelatedslice.easyconfig.impl.config.node.value.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.AbstractNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.env.builder.ChildEnvNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.value.ValueNodeImpl;
import org.jspecify.annotations.NonNull;

public class ValueNodeChildBuilder<T, Previous extends InternalNodeBuilder<?>> extends AbstractValueNodeBuilder<ValueNodeChildBuilder<T, Previous>, T> implements NodeBuilder.ValueFinalStep.Child<T, Previous>, NodeBuilder.ValueSafeStep.Child<T, Previous> {

    private final Previous previous;

    public ValueNodeChildBuilder(@NonNull TypeToken<T> token, @NonNull String key, Previous previous) {
        super(token, key);
        this.previous = previous;
    }

    @Override
    public @NonNull AbstractNode build() {
        return new ValueNodeImpl<>(this);
    }

    @Override
    public @NonNull Previous complete() {
        this.previous.appendChild(this);
        return this.previous;
    }

    @Override
    public EnvAdapterStep.Child<T, Previous> env(@NonNull String env) {
        return new ChildEnvNodeBuilder<>(this, this.previous, env);
    }
}
