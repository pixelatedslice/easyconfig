package com.pixelatedslice.easyconfig.api.config.node.builder;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;

public interface GenericNodeBuilderHandler<NextAfterKey extends BuilderStep, NextAfterParent extends BuilderStep>
        extends GenericNodeBuilder<NextAfterKey>, GenericNodeBuilderParentStep<NextAfterParent> {
}
