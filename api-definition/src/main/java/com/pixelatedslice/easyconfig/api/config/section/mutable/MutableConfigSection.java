package com.pixelatedslice.easyconfig.api.config.section.mutable;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.mutable.MutableConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.immutable.ImmutableConfigSection;
import com.pixelatedslice.easyconfig.api.mutability.MutableVariant;
import com.pixelatedslice.easyconfig.api.mutability.WithImmutableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface MutableConfigSection
        extends ConfigSection<MutableConfigSection, MutableConfigNode<?>>,
        MutableVariant, WithImmutableVariant<ImmutableConfigSection> {

    @SuppressWarnings("unchecked")
    @Override
    default @NonNull <T> Optional<MutableConfigNode<T>> node(@NonNull TypeToken<T> typeToken,
            @NonNull String... providedKeys) {
        return (Optional<MutableConfigNode<T>>) ConfigSection.super.node(typeToken, providedKeys);
    }
}