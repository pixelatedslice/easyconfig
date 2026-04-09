package com.pixelatedslice.easyconfig.api.config.node.descriptor;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.descriptor.DescriptorBuilderWithComments;
import com.pixelatedslice.easyconfig.api.builder.descriptor.DescriptorBuilderWithConfigSectionParent;
import com.pixelatedslice.easyconfig.api.builder.descriptor.DescriptorBuilderWithKey;
import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptor;
import com.pixelatedslice.easyconfig.api.descriptor.Descriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface ConfigNodeDescriptorBuilder<T> extends Descriptor.Builder<T, ConfigNodeDescriptor<T>>,
        DescriptorBuilderWithKey, DescriptorBuilderWithConfigSectionParent, DescriptorBuilderWithComments {
    @NonNull ConfigNodeDescriptorBuilder<T> defaultValue(@Nullable T defaultValue);

    @Override
    @NonNull ConfigNodeDescriptorBuilder<T> typeToken(@NonNull TypeToken<T> typeToken);

    @Override
    @NonNull ConfigNodeDescriptorBuilder<T> comments(@NonNull String @NonNull ... comments);

    @Override
    @NonNull ConfigNodeDescriptorBuilder<T> addComment(@NonNull String comment);

    @Override
    @NonNull ConfigNodeDescriptorBuilder<T> parent(@Nullable ConfigSectionDescriptor parent);

    @Override
    @NonNull ConfigNodeDescriptorBuilder<T> key(@NonNull String key);
}
