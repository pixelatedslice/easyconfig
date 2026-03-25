package com.pixelatedslice.easyconfig.api.config.node;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public interface WithConfigNodeChildren {
    @NonNull List<@NonNull ConfigNode<?>> nodes();

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> node(@NonNull ConfigSection rootSection,
            @NonNull TypeToken<@NonNull T> typeToken, @NonNull String... providedKeys) {
        return ConfigNodeIterator.findNode(rootSection, typeToken, providedKeys);
    }

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> nodeButInTheBukkitAPIStyle(
            @NonNull ConfigSection rootSection, @NonNull TypeToken<@NonNull T> typeToken, @NonNull String key) {
        return ConfigNodeIterator.findNodeButInTheBukkitAPIStyle(rootSection, typeToken, key);
    }

    WithConfigNodeChildren addNode(@NonNull ConfigNode<?> child);

    WithConfigNodeChildren removeNode(@NonNull String key);

    @NonNull ConfigNodeIterator nodeIterator();
}