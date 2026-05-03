package com.pixelatedslice.easyconfig.api.config.config;

import org.jspecify.annotations.NonNull;

import java.io.StringReader;
import java.io.StringWriter;

public interface InMemoryConfig extends Config {
    default void write(@NonNull StringWriter writer) {
        this.formatInstance().write(this.root(), writer);
    }

    default void parseFromString(@NonNull String content) {
        try (var reader = new StringReader(content)) {
            this.formatInstance().parse(this.root(), reader);
        }
    }
}
