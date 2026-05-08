package com.pixelatedslice.easyconfig.api.config.node.collection.builder;

import com.pixelatedslice.easyconfig.api.config.node.builder.GenericNodeBuilderHandler;

public interface CollectionNodeBuilderHandler extends
        GenericNodeBuilderHandler<CollectionNodeBuilderParentStep, CollectionNodeBuilderChildrenStep>,
        CollectionNodeBuilderParentStep, CollectionNodeBuilderChildrenStep, CollectionNodeBuilderFinalStep {
}