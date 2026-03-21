package com.pixelatedslice.easyconfig.api.serialization.builtin;

import com.pixelatedslice.easyconfig.api.serialization.Serializer;

public sealed interface BuiltInSerializer<T> extends Serializer<T> permits BuiltInBukkitSerializer {
}
