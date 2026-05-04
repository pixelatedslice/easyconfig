package com.pixelatedslice.easyconfig.api.config.config;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
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

    @FunctionalInterface
    interface Builder extends Config.Builder<Builder.PathStep> {
        @FunctionalInterface
        interface PathStep extends BuilderStep {
            @NonNull FinalStep path(@NonNull Path pathWithoutExtension);
        }

        @FunctionalInterface
        interface FinalStep extends BuilderStep {
            @NonNull OnDiskConfig build();
        }
    }
}
