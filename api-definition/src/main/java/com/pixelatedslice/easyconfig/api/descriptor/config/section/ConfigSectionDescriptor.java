package com.pixelatedslice.easyconfig.api.descriptor.config.section;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.comments.Commentable;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.descriptor.Descriptor;
import com.pixelatedslice.easyconfig.api.descriptor.config.DescriptorWithKey;
import com.pixelatedslice.easyconfig.api.descriptor.config.DescriptorWithParent;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface ConfigSectionDescriptor
        extends Descriptor<ConfigSection>, DescriptorWithKey, DescriptorWithParent<ConfigSectionDescriptor>,
        Commentable {
    @NonNull Collection<@NonNull ConfigSectionDescriptor> sections();

    void addSections(@NonNull ConfigSectionDescriptor @NonNull ... sectionDescriptors);

    void removeSections(@NonNull String @NonNull ... keys);

    void clearSections();

    @NonNull Collection<@NonNull ConfigNodeDescriptor<?>> nodes();

    void addNodes(@NonNull ConfigNodeDescriptor<?> @NonNull ... nodeDescriptors);

    void removeNodes(@NonNull String @NonNull ... keys);

    void clearNodes();

    @Override
    default void setKey(@NonNull String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void setParent(@Nullable ConfigSectionDescriptor parent) {
        throw new UnsupportedOperationException();
    }

    @Override
    default Optional<TypeToken<ConfigSection>> typeToken() {
        return Optional.of(TypeToken.of(ConfigSection.class));
    }
}
