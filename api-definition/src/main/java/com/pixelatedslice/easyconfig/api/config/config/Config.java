package com.pixelatedslice.easyconfig.api.config.config;

import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.format.Format;
import org.jspecify.annotations.NonNull;

public interface Config {
    @NonNull Format formatInstance();

    ContainerNode.@NonNull Root root();
}
