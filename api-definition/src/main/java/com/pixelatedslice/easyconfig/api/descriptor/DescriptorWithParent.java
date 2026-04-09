package com.pixelatedslice.easyconfig.api.descriptor;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface DescriptorWithParent<T> {
    @NonNull Optional<@NonNull T> parent();

    void setParent(@Nullable T parent);
}
