package com.pixelatedslice.easyconfig.api.config.config;

import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface OnDiskConfig extends Config {

    @NonNull Path filePath();

    default void save() throws IOException {
        try (var writer = Files.newBufferedWriter(this.filePath(),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE)
        ) {
            this.formatInstance().write(this.root(), writer);
        }
    }

    default void load() throws IOException {
        try (var reader = Files.newBufferedReader(this.filePath())) {
            this.formatInstance().parse(this.root(), reader);
        }
    }
}
