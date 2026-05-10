package com.pixelatedslice.easyconfig.impl.test.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeBuilder;
import com.pixelatedslice.easyconfig.impl.config.node.container.builder.ContainerNodeOriginalBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class EnvBuilderTests {

    private NodeBuilder.FirstStep builder(){
        return new ContainerNodeOriginalBuilder();
    }

    @Test
    public void NodeBuilder_can_build_node(){
        //ARRANGE
        var key = "First key";
        var typeToken = TypeToken.of(String.class);
        var envKey = "envKey";
        Function<String, String> adapter = t -> t;

        //ACT
        var result = builder().key(key).of(String.class).env(envKey).adapter(adapter).build();

        //ASSERT
        Assertions.assertEquals(key, result.key());
        Assertions.assertEquals(typeToken, result.typeToken());
        Assertions.assertEquals(envKey, result.envKey());
        Assertions.assertEquals(adapter, result.adapter());
    }
}
