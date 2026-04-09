package com.pixelatedslice.easyconfig.api.config.section.descriptor;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.builder.descriptor.*;
import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptorBuilder;
import com.pixelatedslice.easyconfig.api.descriptor.Descriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public interface ConfigSectionDescriptorBuilder
        extends Descriptor.Builder<ConfigSectionDescriptor, ConfigSectionDescriptor>, DescriptorBuilderWithKey,
        DescriptorBuilderWithConfigSectionParent, DescriptorBuilderWithComments, DescriptorBuilderWithNodeChildren,
        DescriptorBuilderWithNestedSections {
    @Override
    default @NonNull ConfigSectionDescriptorBuilder typeToken(
            @NonNull TypeToken<ConfigSectionDescriptor> typeToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NonNull ConfigSectionDescriptorBuilder comments(@NonNull String @NonNull ... comments);

    @Override
    @NonNull ConfigSectionDescriptorBuilder addComment(@NonNull String comment);

    @Override
    @NonNull ConfigSectionDescriptorBuilder parent(@Nullable ConfigSectionDescriptor parent);

    @Override
    @NonNull ConfigSectionDescriptorBuilder key(@NonNull String key);

    @Override
    @NonNull ConfigSectionDescriptorBuilder nodes(@NonNull ConfigNodeDescriptor<?> @NonNull ... nodes);

    @Override
    @NonNull ConfigSectionDescriptorBuilder addNode(@NonNull ConfigNodeDescriptor<?> nodeDescriptor);

    @Override
    @NonNull <T> ConfigSectionDescriptorBuilder addNode(
            @NonNull Consumer<? super @NonNull ConfigNodeDescriptorBuilder<T>> nodeBuilder);

    @Override
    @NonNull ConfigSectionDescriptorBuilder sections(@NonNull ConfigSectionDescriptor @NonNull ... sections);

    @Override
    @NonNull ConfigSectionDescriptorBuilder addSection(@NonNull ConfigSectionDescriptor sectionDescriptor);

    @Override
    @NonNull ConfigSectionDescriptorBuilder addSection(
            @NonNull Consumer<? super @NonNull ConfigSectionDescriptorBuilder> sectionBuilder);
}
