package com.pixelatedslice.easyconfig.impl.config.node.value.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.env.builder.OriginalEnvNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.value.ValueNodeImpl;
import org.jspecify.annotations.NonNull;

public class ValueNodeOriginalBuilder<T> extends AbstractValueNodeBuilder<ValueNodeOriginalBuilder<T>, T> implements NodeBuilder.ValueFinalStep.Original<T>, NodeBuilder.ValueSafeStep.Original<T> {

    public ValueNodeOriginalBuilder(@NonNull TypeToken<T> token, @NonNull String key) {
        super(token, key);
    }

    @Override
    public ValueNodeImpl<T> build() {
        return new ValueNodeImpl<>(this);
    }

    @Override
    public EnvAdapterStep.Original<T> env(@NonNull String env) {
        return new OriginalEnvNodeBuilder<>(this, env);
    }
}
