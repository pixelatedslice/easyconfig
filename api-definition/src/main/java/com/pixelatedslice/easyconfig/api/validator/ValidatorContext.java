package com.pixelatedslice.easyconfig.api.validator;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface ValidatorContext {
    void error(@NonNull String message, @Nullable Object @NonNull ... variables);

    void throwIfErrorsExist();
}
