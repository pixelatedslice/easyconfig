package com.pixelatedslice.easyconfig.api.descriptor;

import org.jspecify.annotations.NonNull;

public interface DescriptorWithKey {
    @NonNull String key();

    void setKey(@NonNull String key);
}


