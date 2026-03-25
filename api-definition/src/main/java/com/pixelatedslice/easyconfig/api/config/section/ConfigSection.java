package com.pixelatedslice.easyconfig.api.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.Commentable;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.WithConfigNodeChildren;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface ConfigSection extends WithConfigNodeChildren, WithNestedConfigSection, Commentable {
    @NonNull String key();

    @NonNull Optional<@NonNull ConfigSection> parent();

    ConfigSection setParent(@Nullable ConfigSection parent);

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> node(@NonNull TypeToken<@NonNull T> typeToken,
            @NonNull String... providedKeys) {
        return this.node(this, typeToken, providedKeys);
    }

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> node(@NonNull Class<@NonNull T> simpleType,
            @NonNull String... providedKeys) {
        var typeToken = TypeToken.of(simpleType);
        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }
        return this.node(this, typeToken, providedKeys);
    }

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> nodeButInTheBukkitAPIStyle(
            @NonNull TypeToken<@NonNull T> typeToken, @NonNull String key) {
        return this.node(this, typeToken, key);
    }

    default <T> @NonNull Optional<@NonNull ConfigNode<T>> nodeButInTheBukkitAPIStyle(
            @NonNull Class<@NonNull T> simpleType, @NonNull String key) {
        var typeToken = TypeToken.of(simpleType);
        if (!EasyConfig.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }
        return this.node(this, typeToken, key);
    }
}