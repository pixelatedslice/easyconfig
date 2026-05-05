package com.pixelatedslice.easyconfig.api.config.node.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface GenericNodeBuilder<Next extends BuilderStep> {
    @NonNull Next key(@NonNull String key);
}
