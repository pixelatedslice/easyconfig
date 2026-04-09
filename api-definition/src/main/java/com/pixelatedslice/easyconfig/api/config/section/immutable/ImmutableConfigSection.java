package com.pixelatedslice.easyconfig.api.config.section.immutable;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.immutable.ImmutableConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.mutable.MutableConfigSection;
import com.pixelatedslice.easyconfig.api.mutability.ImmutableVariant;
import com.pixelatedslice.easyconfig.api.mutability.WithMutableVariant;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface ImmutableConfigSection extends ConfigSection<ImmutableConfigSection, ImmutableConfigNode<?>>,
        ImmutableVariant, WithMutableVariant<MutableConfigSection> {
    @SuppressWarnings("unchecked")
    @Override
    default @NonNull <T> Optional<ImmutableConfigNode<T>> node(@NonNull TypeToken<T> typeToken,
            @NonNull String... providedKeys) {
        return (Optional<ImmutableConfigNode<T>>) ConfigSection.super.node(typeToken, providedKeys);
    }
}
