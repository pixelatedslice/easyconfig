package com.pixelatedslice.easyconfig.api.config.file;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface ConfigFile<S extends ConfigSection<S, ?>> {
    static @NonNull ConfigFileBuilder builder() {
        return ServiceLoader.load(ConfigFileBuilder.class).findFirst().orElseThrow();
    }

    @NonNull S rootSection();

    @NonNull Path filePathWithoutExtension();
}