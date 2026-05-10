package com.pixelatedslice.easyconfig.impl.test.config.node.specific;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvAdapter;
import com.pixelatedslice.easyconfig.impl.config.node.env.EnvNodeImpl;
import com.pixelatedslice.easyconfig.impl.config.node.env.builder.OriginalEnvNodeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnvNodeTests {

    @Test
    public void EnvNode_fails_when_creating_without_env_adapter() {
        //ARRANGE
        var key = "key";
        var token = TypeToken.of(String.class);
        String envKey = "LANG";
        var internalBuilder = new OriginalEnvNodeBuilder<>(key, token, envKey);

        //ACT - ASSERT
        Assertions.assertThrows(NullPointerException.class, () -> new EnvNodeImpl<>(internalBuilder));
    }

    @Test
    public void EnvNode_valid_when_creating() {
        //ARRANGE
        String key = "key";
        var token = TypeToken.of(String.class);
        var envKey = "LANG";
        EnvAdapter<String> adapter = t -> t;
        var internalBuilder = new OriginalEnvNodeBuilder<>(key, token, envKey).adapter(adapter);

        //ACT
        var result = new EnvNodeImpl<>(internalBuilder);

        //ASSERT
        Assertions.assertEquals(key, result.key());
        Assertions.assertEquals(envKey, result.envKey());
        Assertions.assertEquals(token, result.typeToken());
        Assertions.assertEquals(System.getenv(envKey), result.value().orElse(""));
    }

    @Test
    public void EnvNode_to_builder() {
        //ARRANGE
        String key = "key";
        var token = TypeToken.of(String.class);
        var envKey = "LANG";
        EnvAdapter<String> adapter = t -> t;
        var internalBuilder = new OriginalEnvNodeBuilder<>(key, token, envKey).adapter(adapter);

        var node = new EnvNodeImpl<>(internalBuilder);

        //ACT
        var result = node.toBuilder();

        //ASSERT
        Assertions.assertInstanceOf(OriginalEnvNodeBuilder.class, result);
        var castResult = (OriginalEnvNodeBuilder<?>) result;
        Assertions.assertEquals(key, castResult.key());
        Assertions.assertEquals(envKey, castResult.envKey());
        Assertions.assertEquals(token, castResult.type());
        Assertions.assertEquals(adapter, castResult.adapter());
    }
}
