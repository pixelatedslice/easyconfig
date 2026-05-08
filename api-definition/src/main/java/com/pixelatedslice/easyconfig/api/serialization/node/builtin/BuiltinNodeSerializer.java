package com.pixelatedslice.easyconfig.api.serialization.node.builtin;

import com.pixelatedslice.easyconfig.api.serialization.BuiltInSerializer;
import com.pixelatedslice.easyconfig.api.serialization.node.NodeSerializer;

public non-sealed interface BuiltinNodeSerializer<T> extends BuiltInSerializer<T>, NodeSerializer<T> {
}
