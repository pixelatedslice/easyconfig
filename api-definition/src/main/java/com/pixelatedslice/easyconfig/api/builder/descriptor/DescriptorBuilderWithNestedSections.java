package com.pixelatedslice.easyconfig.api.builder.descriptor;

import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptor;
import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptorBuilder;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public interface DescriptorBuilderWithNestedSections {

    @NonNull ConfigSectionDescriptorBuilder sections(@NonNull ConfigSectionDescriptor @NonNull ... sections);

    @NonNull ConfigSectionDescriptorBuilder addSection(@NonNull ConfigSectionDescriptor sectionDescriptor);

    @NonNull ConfigSectionDescriptorBuilder addSection(
            @NonNull Consumer<? super @NonNull ConfigSectionDescriptorBuilder> sectionBuilder
    );
}
