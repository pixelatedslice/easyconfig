package com.pixelatedslice.easyconfig.api.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.comments.Commentable;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.node.WithConfigNodeChildren;
import com.pixelatedslice.easyconfig.api.mutability.mutable.WithMutableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

public interface ConfigSection
        extends Commentable, WithConfigNodeChildren, WithNestedConfigSection, WithMutableVariant<MutableConfigSection> {
    static @NonNull ConfigSectionBuilder builder() {
        return ServiceLoader.load(ConfigSectionBuilder.class).findFirst().orElseThrow();
    }

    @NonNull String key();

    @NonNull Optional<@NonNull ConfigSection> parent();

    @Override
    default @NonNull <T> Optional<ConfigNode<T>> node(
            @NonNull TypeToken<T> typeToken,
            @NonNull String... providedKeys
    ) {
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(providedKeys);
        if (providedKeys.length == 0) {
            return Optional.empty();
        }

        return ((providedKeys.length == 1) && providedKeys[0].contains("."))
                ? ConfigNodeIterator.findButInTheBukkitAPIStyle(this, typeToken, providedKeys[0])
                : ConfigNodeIterator.find(this, typeToken, providedKeys);
    }

    default Optional<TypeToken<ConfigSection>> typeToken() {
        return Optional.of(TypeToken.of(ConfigSection.class));
    }
}