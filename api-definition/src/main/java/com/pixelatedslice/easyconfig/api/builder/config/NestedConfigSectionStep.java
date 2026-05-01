package com.pixelatedslice.easyconfig.api.builder.config;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public interface NestedConfigSectionStep<Self extends NestedConfigSectionStep<Self>> extends BuilderStep {
    @NonNull Self section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    @NonNull Self section(ConfigSectionBuilder.@NonNull Handler nestedSectionBuilder);

    /**
     * Call {@link NestedConfigSectionStep#section(ConfigSectionBuilder.Handler)} to add the section.
     *
     * @return A new {@link ConfigSectionBuilder}
     */
    @NonNull ConfigSectionBuilder section();
}
