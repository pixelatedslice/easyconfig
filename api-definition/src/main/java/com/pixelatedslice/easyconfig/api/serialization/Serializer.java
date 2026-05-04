package com.pixelatedslice.easyconfig.api.serialization;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.Node;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface Serializer<T> {
    @NonNull TypeToken<T> forType();

    void serialize(@Nullable T value, ContainerNode.Builder.@NonNull ChildrenStep builder);

    @Nullable T deserialize(@NonNull Node node);
}
