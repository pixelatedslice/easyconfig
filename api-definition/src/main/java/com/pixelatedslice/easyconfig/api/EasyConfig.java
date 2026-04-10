package com.pixelatedslice.easyconfig.api;

import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.HoconFileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.JsonFileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.TomlFileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.YamlFileFormat;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public interface EasyConfig {
    Pattern KEY_REGEX = Pattern.compile("^[a-zA-Z0-9._-]*[a-zA-Z0-9]$");
    int QUEUE_DRAINED_INITIAL_CAPACITY = 32;

    @NonNull EasyConfig copy();

    @NonNull Map<@NonNull Class<?>, @NonNull Serializer<?>> serializers();

    <T> @NonNull Optional<@NonNull Serializer<T>> serializer(@NonNull Class<T> clazz);

    void registerSerializers(@NonNull Serializer<?> @NonNull ... serializers);

    void unregisterSerializers(@NonNull Class<?> @NonNull ... classes);

    @NonNull Map<@NonNull Class<? extends FileFormat>, @NonNull FileFormatProvider<?>> providers();

    <T extends FileFormat> @NonNull Optional<@NonNull FileFormatProvider<T>> provider(
            @NonNull Class<T> fileFormatClass
    );

    void registerProviders(@NonNull FileFormatProvider<?> @NonNull ... providers);

    void unregisterProviders(@NonNull FileFormatProvider<?> @NonNull ... providers);

    @NonNull CommonFormatProviders commonFormatProviders();

    interface CommonFormatProviders {
        @NonNull Optional<@NonNull FileFormatProvider<HoconFileFormat>> hocon();

        @NonNull Optional<@NonNull FileFormatProvider<JsonFileFormat>> json();

        @NonNull Optional<@NonNull FileFormatProvider<TomlFileFormat>> toml();

        @NonNull Optional<@NonNull FileFormatProvider<YamlFileFormat>> yaml();

        void reloadFormatProviderInstances();
    }
}