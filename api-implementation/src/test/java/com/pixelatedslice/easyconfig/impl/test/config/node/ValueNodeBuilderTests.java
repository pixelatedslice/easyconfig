package com.pixelatedslice.easyconfig.impl.test.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeOriginalBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ValueNodeBuilderTests {

    private NodeBuilder.FirstStep builder() {
        return new ContainerNodeOriginalBuilder();
    }

    @Test
    public void NodeBuilder_should_build_value_node_with_expected() {
        //ARRANGE
        String key = "My Key";
        TypeToken<String> type = TypeToken.of(String.class);
        var defaultValue = "default value";
        var value = "value";

        //ACT
        var node = builder()
                .key(key)
                .of(String.class)
                .defaultValue(defaultValue)
                .value(value)
                .build();

        //ASSERT
        Assertions.assertEquals(key, node.key());
        Assertions.assertEquals(type, node.typeToken());
        Assertions.assertEquals(defaultValue, node.defaultValue().orElse(null));
        Assertions.assertEquals(value, node.value().orElse(null));
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
    public void NodeBuilder_should_throw_exception_with_null_typetoken() {
        //ARRANGE
        var key = "My Key";

        System.getenv("");

        //ACT - ASSERT
        //noinspection DataFlowIssue
        Assertions.assertThrows(NullPointerException.class, () ->
                builder()
                        .key(key)
                        .of((TypeToken<?>) null)
                        .build());
    }

    @Test
    public void NodeBuilder_should_append_value_with_provided_key() {
        //ARRANGE
        var key = "My first key";
        var secondKey = "My second key";
        var type = TypeToken.of(String.class);

        //ACT
        var builder = builder().key(key).append(secondKey).of(String.class);
        NodeBuilder.ContainerFinalStep.Original originalBuilder = builder.complete();
        ContainerNode result = originalBuilder.build();

        //ASSERT
        Assertions.assertEquals(key, result.key());

        Optional<@NonNull ValueNode<String>> opNode = result.valueNode(String.class, secondKey);
        Assertions.assertTrue(opNode.isPresent());
        Assertions.assertEquals(secondKey, opNode.get().key());
        Assertions.assertInstanceOf(ValueNode.class, opNode.get());
        Assertions.assertEquals(type, opNode.get().typeToken());
    }
}
