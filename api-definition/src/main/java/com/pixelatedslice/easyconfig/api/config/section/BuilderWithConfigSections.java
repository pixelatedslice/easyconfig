package com.pixelatedslice.easyconfig.api.config.section;

import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public interface BuilderWithConfigSections {
    @NonNull BuilderWithConfigSections section(@NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    @NonNull BuilderWithConfigSections section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);
}
