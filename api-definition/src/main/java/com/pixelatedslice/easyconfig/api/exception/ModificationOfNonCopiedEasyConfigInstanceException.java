package com.pixelatedslice.easyconfig.api.exception;

public class ModificationOfNonCopiedEasyConfigInstanceException extends RuntimeException {
    public ModificationOfNonCopiedEasyConfigInstanceException() {
        super("You cannot modify a EasyConfig instance that was not copied! (Use EasyConfig::copy)");
    }
}
