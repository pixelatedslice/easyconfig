package com.pixelatedslice.easyconfig.api.builder.descriptor;

import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface DescriptorBuilderWithKey {
    @NonNull DescriptorBuilderWithKey key(@NonNull String key);
}
