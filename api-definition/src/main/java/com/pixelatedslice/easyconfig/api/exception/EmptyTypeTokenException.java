package com.pixelatedslice.easyconfig.api.exception;

public final class EmptyTypeTokenException extends RuntimeException {
    private EmptyTypeTokenException(String message) {
        super(message);
    }

    public static EmptyTypeTokenException emptyNodeTypeToken() {
        return new EmptyTypeTokenException("A ConfigNode cannot have an empty TypeToken!");
    }

    public static EmptyTypeTokenException emptySectionTypeToken() {
        return new EmptyTypeTokenException("A ConfigSection cannot have an empty TypeToken!");
    }
}
