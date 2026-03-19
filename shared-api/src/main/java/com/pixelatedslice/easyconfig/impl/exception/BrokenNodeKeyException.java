package com.pixelatedslice.easyconfig.impl.exception;

public class BrokenNodeKeyException extends RuntimeException {
    public BrokenNodeKeyException(String providedKey, String regex) {
        super(String.format(
                "The provided key \"%s\" does not match the Regex \"%s\"",
                providedKey,
                regex
        ));
    }
}
