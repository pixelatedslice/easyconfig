package com.pixelatedslice.easyconfig.api.exception;

import org.jspecify.annotations.NonNull;

public class BrokenNodeKeyException extends RuntimeException {
    public BrokenNodeKeyException(@NonNull String providedKey, @NonNull String regex) {
        super(String.format("The provided key \"%s\" does not match the Regex \"%s\"", providedKey, regex));
    }
}