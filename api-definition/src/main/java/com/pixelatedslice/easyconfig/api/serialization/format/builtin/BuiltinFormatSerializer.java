package com.pixelatedslice.easyconfig.api.serialization.format.builtin;

import com.pixelatedslice.easyconfig.api.serialization.BuiltInSerializer;
import com.pixelatedslice.easyconfig.api.serialization.format.FormatSerializer;

public non-sealed interface BuiltinFormatSerializer<T> extends BuiltInSerializer<T>, FormatSerializer<T> {
}
