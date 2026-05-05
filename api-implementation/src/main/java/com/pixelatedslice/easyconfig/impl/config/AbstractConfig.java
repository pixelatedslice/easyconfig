package com.pixelatedslice.easyconfig.impl.config;

import com.pixelatedslice.easyconfig.api.config.config.Config;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.format.Format;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

class AbstractConfig implements Config {

    private @NonNull Format format;
    private ContainerNode.@NonNull Root root;

    AbstractConfig(@NonNull Format format){
        this.format = Objects.requireNonNull(format);

    }

    @Override
    public @NonNull Format formatInstance() {
        return this.format;
    }

    @Override
    public ContainerNode.@NonNull Root root() {
        return this.root;
    }
}
