package com.pixelatedslice.easyconfig.api.config.node.container.builder;

import com.pixelatedslice.easyconfig.api.config.node.builder.GenericNodeBuilderHandler;

public interface ContainerNodeBuilderHandler extends
        GenericNodeBuilderHandler<ContainerNodeBuilderParentStep, ContainerNodeBuilderChildrenStep>,
        ContainerNodeBuilder,
        ContainerNodeBuilderParentStep, ContainerNodeBuilderChildrenStep, ContainerNodeBuilderFinalStep {
}