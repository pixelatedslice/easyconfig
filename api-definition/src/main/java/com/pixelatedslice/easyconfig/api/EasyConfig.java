package com.pixelatedslice.easyconfig.api;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public interface EasyConfig {
    Pattern KEY_REGEX = Pattern.compile("^[a-zA-Z0-9._-]*[a-zA-Z0-9]$");

    static boolean isSimpleTypeToken(TypeToken<?> typeToken) {
        return typeToken.getType() instanceof Class<?>;
    }

    @NonNull EasyConfig copy();

    @NonNull Map<@NonNull Class<?>, @NonNull Serializer<?>> serializers();

    <T> @NonNull Optional<@NonNull Serializer<T>> serializer(@NonNull Class<T> clazz);

    void registerSerializers(@NonNull Serializer<?>... serializers);

    void unregisterSerializers(@NonNull Class<?>... classes);

    @NonNull Map<@NonNull Class<? extends FileFormat>, @NonNull FileFormatProvider<?>> providers();

    @NonNull Optional<@NonNull FileFormatProvider<?>> provider(
            @NonNull Class<? extends FileFormat> fileFormatClass
    );

    void registerProviders(@NonNull FileFormatProvider<?>... providers);

    void unregisterProviders(@NonNull FileFormatProvider<?>... providers);
}