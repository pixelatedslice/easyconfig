package com.pixelatedslice.easyconfig.impl;

import com.pixelatedslice.easyconfig.api.CopiedEasyConfig;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public final class CopiedEasyConfigImpl extends EasyConfigImpl implements CopiedEasyConfig {

    CopiedEasyConfigImpl(
            @NonNull Map<@NonNull Class<? extends FileFormat>, @NonNull FileFormatProvider<?>> providers,
            @NonNull Map<@NonNull Class<?>, @NonNull Serializer<?>> serializers) {
        super(providers, serializers);
    }

}
