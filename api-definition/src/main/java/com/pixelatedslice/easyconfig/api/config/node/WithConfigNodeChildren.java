package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.ServiceLoader;

public interface WithConfigNodeChildren {
    <T> @NonNull Optional<ConfigNode<T>> node(
            @NonNull TypeToken<@NonNull T> typeToken,
            @NonNull String... providedKeys
    );

    @NonNull Collection<@NonNull ConfigNode<?>> nodes();

    @NonNull
    default ConfigNodeIterator nodeIterator() {
        return ServiceLoader.load(ConfigNodeIterator.class).findFirst().orElseThrow();
    }

}

