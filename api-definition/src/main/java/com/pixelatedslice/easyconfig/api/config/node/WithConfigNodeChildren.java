package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.ServiceLoader;

public interface WithConfigNodeChildren {
    @NonNull Collection<@NonNull ConfigNode<?>> nodes();

    <T> @NonNull Optional<@NonNull ConfigNode<T>> node(
            @NonNull TypeToken<@NonNull T> typeToken,
            @NonNull String... providedKeys
    );

    void addNodes(@NonNull ConfigNode<?> @NonNull ... children);

    void removeNodes(@NonNull String @NonNull ... keys);

    void clearNodes();

    default @NonNull ConfigNodeIterator nodeIterator() {
        return ServiceLoader.load(ConfigNodeIterator.class).findFirst().orElseThrow();
    }
}

