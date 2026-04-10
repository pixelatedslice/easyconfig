package com.pixelatedslice.easyconfig.impl.config.node;

import com.pixelatedslice.easyconfig.api.config.node.MutableConfigNodeValueUpdate;
import org.jspecify.annotations.Nullable;

public record MutableConfigNodeValueUpdateImpl<T>(@Nullable T newValue) implements MutableConfigNodeValueUpdate<T> {
}
