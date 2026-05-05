package com.pixelatedslice.easyconfig.api.config.node.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.container.builder.ContainerNodeBuilderChildrenStep;
import com.pixelatedslice.easyconfig.api.config.node.value.builder.ValueNodeBuilderTypeStep;
import org.jspecify.annotations.NonNull;

public interface NodeBuilderToNodeBuilderStep extends BuilderStep {
    <T> @NonNull ValueNodeBuilderTypeStep<T> valueNode();

    @NonNull ContainerNodeBuilderChildrenStep containerNode();
}
