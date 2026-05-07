package com.pixelatedslice.easyconfig.impl.test.config.node;

import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeOriginalBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContainerNodeBuilderTests {

    private NodeBuilder.FirstStep builder() {
        return new ContainerNodeOriginalBuilder();
    }

    @Test
    public void NodeBuilder_should_build_container_node_with_key() {
        //ARRANGE
        String key = "My Key";

        //ACT
        var node = builder().key(key).build();

        //ASSERT
        Assertions.assertEquals(key, node.key());
    }

    @Test
    public void NodeBuilder_should_throw_exception_with_null_key() {
        //ARRANGE

        //ACT - ASSERT
        //noinspection DataFlowIssue
        Assertions.assertThrows(NullPointerException.class, () ->
                builder().key(null).build());
    }

    @Test
    public void NodeBuilder_should_append_with_provided_key() {
        //ARRANGE
        var key = "My first key";
        var secondKey = "My second key";

        //ACT
        var result = builder().key(key).append(secondKey).complete().build();

        //ASSERT
        Assertions.assertEquals(key, result.key());
        var opNode = result.containerNode(secondKey);
        Assertions.assertTrue(opNode.isPresent());
        Assertions.assertEquals(secondKey, opNode.get().key());
    }
}
