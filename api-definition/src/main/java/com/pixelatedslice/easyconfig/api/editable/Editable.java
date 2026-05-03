package com.pixelatedslice.easyconfig.api.editable;

@FunctionalInterface
public interface Editable<E extends EditableVariant> {
    E editable();
}
