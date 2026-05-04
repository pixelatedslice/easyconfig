package com.pixelatedslice.easyconfig.api.config.node;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface GenericNodeBuilder<Next extends BuilderStep> {
    @NonNull Next key(@NonNull String key);

    @FunctionalInterface
    interface ParentStep<Next extends BuilderStep> extends BuilderStep {
        @NonNull Next parent(@NonNull ContainerNode parent);
    }

    interface Handler<NextAfterKey extends BuilderStep, NextAfterParent extends BuilderStep>
            extends GenericNodeBuilder<NextAfterKey>, ParentStep<NextAfterParent> {
    }
}
