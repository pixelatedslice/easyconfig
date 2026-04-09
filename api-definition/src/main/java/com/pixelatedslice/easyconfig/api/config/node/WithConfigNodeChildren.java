package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.ServiceLoader;

public interface WithConfigNodeChildren<N extends ConfigNode<?>> {
    <T> @NonNull Optional<? extends ConfigNode<T>> node(
            @NonNull TypeToken<@NonNull T> typeToken,
            @NonNull String... providedKeys
    );

    @NonNull Collection<@NonNull N> nodes();

    void addNodes(@NonNull ConfigNode<?> @NonNull ... children);

    void removeNodes(@NonNull String @NonNull ... keys);

    void clearNodes();

    @NonNull
    default ConfigNodeIterator nodeIterator() {
        return ServiceLoader.load(ConfigNodeIterator.class).findFirst().orElseThrow();
    }

}

