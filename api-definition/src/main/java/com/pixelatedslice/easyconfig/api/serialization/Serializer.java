package com.pixelatedslice.easyconfig.api.serialization;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface Serializer<T, N extends Node> {
    @NonNull TypeToken<T> forType();

    void serialize(@Nullable T value, @NonNull ContainerNodeBuilder);
    @Nullable T deserialize(@NonNull N node);
}
