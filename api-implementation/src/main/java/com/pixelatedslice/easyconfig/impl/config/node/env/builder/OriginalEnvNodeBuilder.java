package com.pixelatedslice.easyconfig.impl.config.node.env.builder;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.value.builder.AbstractValueNodeBuilder;
import org.jspecify.annotations.NonNull;

public class OriginalEnvNodeBuilder<T> extends AbstractEnvNodeBuilderImpl<T, OriginalEnvNodeBuilder<T>> implements NodeBuilder.EnvAdapterStep.Original<T>, NodeBuilder.EnvFinalStep.Original<T> {

    public OriginalEnvNodeBuilder(@NonNull AbstractValueNodeBuilder<?, T> builder, @NonNull String envKey) {
        super(builder, envKey);
    }

    public OriginalEnvNodeBuilder(@NonNull String key, @NonNull TypeToken<T> type, @NonNull String envKey){
        super(key, type, envKey);
    }
}
