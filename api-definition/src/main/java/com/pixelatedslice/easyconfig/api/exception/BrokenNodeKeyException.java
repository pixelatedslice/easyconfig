package com.pixelatedslice.easyconfig.api.exception;

/**
 * Exception thrown when a provided key for a node is invalid and does not match the
 * specified Regex pattern.
 * <p>
 * This exception is typically used to indicate that the input provided as a node key
 * fails to adhere to the expected format or constraints defined by the Regex.
 */
public class BrokenNodeKeyException extends RuntimeException {
    public BrokenNodeKeyException(String providedKey, String regex) {
        super(String.format(
                "The provided key \"%s\" does not match the Regex \"%s\"",
                providedKey,
                regex
        ));
    }
}
