package com.pixelatedslice.easyconfig.impl.test.config.node;

import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeOriginalBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollectionNodeBuilderTests {

    @Test
    public void NodeBuilder_collection_builds_empty(){
        //ARRANGE
        String key = "First key";

        //ACT
        var result = builder().key(key).collection().build();

        //ASSERT
        Assertions.assertEquals(key, result.key());
    }

    @Test
    public void NodeBuilder_collection_builds_with_single(){
        //ARRANGE
        String key = "First key";

        //ACT
        var result = builder().key(key).collection().append().complete().build();

        //ASSERT
        Assertions.assertEquals(key, result.key());
        var opChildNode = result.atIndex(0).plainNode();
        Assertions.assertTrue(opChildNode.isPresent());
        Assertions.assertEquals("index_0", opChildNode.get().key());
    }

    @Test
    public void NodeBuilder_collection_builds_with_single_with_container(){
        //ARRANGE
        String key = "First key";
        String thirdKey = "Third key";

        //ACT
        var result = builder().key(key).collection().append().append(thirdKey).complete().complete().build();

        //ASSERT
        Assertions.assertEquals(key, result.key());
        var opChildNode = result.atIndex(0).container();
        Assertions.assertTrue(opChildNode.isPresent());
        Assertions.assertEquals("index_0", opChildNode.get().key());
        var opSecondChildNode = opChildNode.get().containerNode(thirdKey);
        Assertions.assertTrue(opSecondChildNode.isPresent());
        Assertions.assertEquals(thirdKey, opSecondChildNode.get().key());
    }

    @Test
    public void NodeBuilder_collection_builds_with_single_with_collection_with_container(){
        //ARRANGE
        String key = "First key";
        String fourKey = "Four key";

        //ACT
        var result = builder().key(key).collection().append().collection().append().append(fourKey).complete().complete().complete().build();

        //ASSERT
        Assertions.assertEquals(key, result.key());
        var opSecondCollection = result.atIndex(0).collectionNode();
        Assertions.assertTrue(opSecondCollection.isPresent());
        Assertions.assertEquals("index_0", opSecondCollection.get().key());
        var opThirdChildNode = opSecondCollection.get().atIndex(0).container();
        Assertions.assertTrue(opThirdChildNode.isPresent()); //index container
        var opFourChildNode = opThirdChildNode.get().containerNode(fourKey);
        Assertions.assertTrue(opFourChildNode.isPresent());
    }

    @Test
    public void NodeBuilder_collection_builds_throws_exception_when_invalid_child_appends(){
        //ARRANGE
        String key = "First key";

        //ACT - ASSERT
        Assertions.assertThrows(RuntimeException.class, () -> {
            var collectionBuilder = (NodeBuilder.ContainerFinalStep.Child<NodeBuilder.CollectionStep.Original>)builder().key(key).collection().append();
            collectionBuilder.of(String.class).complete();
        });
    }

    private NodeBuilder.FirstStep builder() {
        return new ContainerNodeOriginalBuilder();
    }
}
