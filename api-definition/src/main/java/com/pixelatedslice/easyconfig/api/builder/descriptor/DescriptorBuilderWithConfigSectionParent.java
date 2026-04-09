package com.pixelatedslice.easyconfig.api.builder.descriptor;

import com.pixelatedslice.easyconfig.api.config.section.descriptor.ConfigSectionDescriptor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface DescriptorBuilderWithConfigSectionParent {
    @NonNull DescriptorBuilderWithConfigSectionParent parent(@Nullable ConfigSectionDescriptor parent);
}
