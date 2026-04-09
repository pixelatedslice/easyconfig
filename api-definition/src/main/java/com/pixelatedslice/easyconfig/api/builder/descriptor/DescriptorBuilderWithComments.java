package com.pixelatedslice.easyconfig.api.builder.descriptor;

import org.jspecify.annotations.NonNull;

public interface DescriptorBuilderWithComments {
    @NonNull DescriptorBuilderWithComments comments(@NonNull String @NonNull ... comments);

    @NonNull DescriptorBuilderWithComments addComment(@NonNull String comment);
}
