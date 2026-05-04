package com.pixelatedslice.easyconfig.api.config.node.value;

import com.pixelatedslice.easyconfig.api.editable.EditableVariant;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface EditableValueNode<T> extends EditableVariant {
    @NonNull EditableValueNode<T> setValue(@Nullable T value);
}
