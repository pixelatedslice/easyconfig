package com.pixelatedslice.easyconfig.api;

import com.pixelatedslice.easyconfig.api.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

public class EasyConfig {
    private final Map<String, FileFormatProvider> providers = new HashMap<>();
    private final Map<Class<?>, Serializer<?>> serializers = new HashMap<>();


}