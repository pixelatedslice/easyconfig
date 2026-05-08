package com.pixelatedslice.easyconfig.api.serialization;

import com.pixelatedslice.easyconfig.api.serialization.format.builtin.BuiltinFormatSerializer;
import com.pixelatedslice.easyconfig.api.serialization.node.builtin.BuiltinNodeSerializer;

public sealed interface BuiltInSerializer<T> extends Serializer<T>
        permits BuiltinFormatSerializer, BuiltinNodeSerializer {
}
