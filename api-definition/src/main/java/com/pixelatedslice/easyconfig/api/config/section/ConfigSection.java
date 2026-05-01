package com.pixelatedslice.easyconfig.api.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.comments.Commentable;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.node.WithConfigNodeChildren;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.exception.ComplexInsteadOfSimpleTypeUsedException;
import com.pixelatedslice.easyconfig.api.mutability.mutable.WithMutableVariant;
import com.pixelatedslice.easyconfig.api.utils.type_token.TypeTokenUtils;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

public interface ConfigSection
        extends Commentable, WithConfigNodeChildren, WithNestedConfigSection, WithMutableVariant<MutableConfigSection> {
    static @NonNull ConfigSectionBuilder builder() {
        return ServiceLoader.load(ConfigSectionBuilder.class).findFirst().orElseThrow();
    }

    default ConfigSectionBuilder.@NonNull NestedSectionStep builderForNested(@NonNull String key) {
        Objects.requireNonNull(key);
        return builder().key(key).parent(this);
    }

    @NonNull String key();

    @NonNull Optional<@NonNull ConfigSection> parent();

    @Override
    default @NonNull <T> Optional<ConfigNode<T>> node(
            @NonNull TypeToken<T> typeToken,
            @NonNull String @NonNull ... providedKeys
    ) {
        Objects.requireNonNull(typeToken);
        Objects.requireNonNull(providedKeys);

        return (providedKeys.length == 0) ? Optional.empty() : ConfigNodeIterator.find(this, typeToken, providedKeys);
    }

    @Override
    default @NonNull <T> Optional<ConfigNode<T>> node(
            @NonNull Class<@NonNull T> simpleType,
            @NonNull String @NonNull ... providedKeys
    ) {
        Objects.requireNonNull(simpleType);
        Objects.requireNonNull(providedKeys);

        var typeToken = TypeToken.of(simpleType);
        if (!TypeTokenUtils.isSimpleTypeToken(typeToken)) {
            throw new ComplexInsteadOfSimpleTypeUsedException();
        }

        return this.node(typeToken, providedKeys);
    }

    @Override
    @NonNull
    default Optional<TypeToken<?>> nodeTypeToken(@NonNull String @NonNull ... providedKeys) {
        Objects.requireNonNull(providedKeys);
        return (providedKeys.length == 0) ? Optional.empty() : ConfigNodeIterator.findTypeToken(this, providedKeys);
    }

    default Optional<TypeToken<ConfigSection>> typeToken() {
        return Optional.of(TypeToken.of(ConfigSection.class));
    }
}