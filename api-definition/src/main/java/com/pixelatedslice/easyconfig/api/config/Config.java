package com.pixelatedslice.easyconfig.api.config;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.format.Format;
import org.jspecify.annotations.NonNull;

public interface Config {
    @NonNull Format formatInstance();

    ContainerNode.@NonNull Root root();

    @FunctionalInterface
    interface Builder<Next extends BuilderStep> {
        @NonNull Next format(@NonNull Format formatInstance);
    }
}
