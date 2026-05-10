package com.pixelatedslice.easyconfig.impl.test.config.node.specific;

import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.impl.config.node.InternalNodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.collection.builder.CollectionNodeOriginalBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.ContainerNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeOriginalBuilder;
import com.pixelatedslice.easyconfig.impl.test.testUtils.MockitoHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ContainerNodeTests {

    @Test
    public void ContainerNode_fails_when_creating_without_key() {
        //ARRANGE
        var internalBuilder = new ContainerNodeOriginalBuilder();

        //ACT - ASSERT
        Assertions.assertThrows(NullPointerException.class, () -> new ContainerNodeImpl(internalBuilder));
    }

    @Test
    public void ContainerNode_can_maintain_values() {
        //ARRANGE
        var key = "key";
        var internalBuilder = new ContainerNodeOriginalBuilder().key(key);

        //ACT
        var result = new ContainerNodeImpl(internalBuilder);

        //ASSERT
        Assertions.assertEquals(key, result.key());
        Assertions.assertTrue(result.isRootNode(), "is not root node");
    }

    @Test
    public void ContainerNode_to_builder() {
        //ARRANGE
        var key = "key";
        var internalBuilder = new ContainerNodeOriginalBuilder().key(key);

        ContainerNode node = new ContainerNodeImpl(internalBuilder);

        //ACT
        var result = node.toBuilder();

        //ASSERT
        Assertions.assertInstanceOf(ContainerNodeOriginalBuilder.class, result);
        var castResult = (ContainerNodeOriginalBuilder) result;
        Assertions.assertEquals(key, castResult.key());
    }
}
