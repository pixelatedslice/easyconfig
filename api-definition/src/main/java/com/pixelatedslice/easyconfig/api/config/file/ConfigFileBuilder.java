package com.pixelatedslice.easyconfig.api.config.file;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.function.Consumer;

public interface ConfigFileBuilder {
    ConfigFileBuilder filePath(@NonNull Path filePathWithoutExtension);

    ConfigFileBuilder fileFormat(@NonNull Class<? extends FileFormat> fileFormatClass);

    <T> ConfigFileBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigFileBuilder node(@NonNull String key, @NonNull Class<T> simpleType,
            @NonNull Consumer<ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigFileBuilder node(@NonNull String key, @NonNull TypeToken<T> typeToken);

    <T> ConfigFileBuilder node(@NonNull String key, @NonNull Class<T> simpleType);

    <T> ConfigFileBuilder node(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeToken);

    <T> ConfigFileBuilder node(@NonNull String key, @NonNull T valueWithSimpleType);

    <T> ConfigFileBuilder node(@NonNull String key, @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigFileBuilder node(@NonNull TypeToken<T> typeToken,
            @NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    <T> ConfigFileBuilder node(@NonNull Consumer<? super ConfigNodeBuilder<T>> nodeBuilder);

    ConfigFileBuilder comment(@NonNull String... comment);

    ConfigFileBuilder section(@NonNull String key,
            @NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    ConfigFileBuilder section(@NonNull Consumer<? super ConfigSectionBuilder> nestedSectionBuilder);

    ConfigFile build();
}