package com.pixelatedslice.easyconfig.api.builder.descriptor;

import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptor;
import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptorBuilder;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public interface DescriptorBuilderWithNodeChildren {

    @NonNull DescriptorBuilderWithNodeChildren nodes(@NonNull ConfigNodeDescriptor<?> @NonNull ... nodes);

    @NonNull DescriptorBuilderWithNodeChildren addNode(@NonNull ConfigNodeDescriptor<?> nodeDescriptor);

    <T> @NonNull DescriptorBuilderWithNodeChildren addNode(
            @NonNull Consumer<? super @NonNull ConfigNodeDescriptorBuilder<T>> nodeBuilder
    );
}
