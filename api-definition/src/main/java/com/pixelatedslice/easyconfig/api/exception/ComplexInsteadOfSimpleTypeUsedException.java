package com.pixelatedslice.easyconfig.api.exception;

public class ComplexInsteadOfSimpleTypeUsedException extends RuntimeException {
    public ComplexInsteadOfSimpleTypeUsedException() {
        super("""
                    A complex type was used instead of a simple type!
                    This is not allowed, as it would result in a loss of information.
                    A simple type has no generics or similar, e.g. Strings, Integers, Custom classes without generics, \
                    etc.
                """.trim()
        );
    }
}
