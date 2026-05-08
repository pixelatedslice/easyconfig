package com.pixelatedslice.easyconfig.api.editable;

@FunctionalInterface
public interface EditableVariant extends AutoCloseable {
    @Override
    void close();
}
