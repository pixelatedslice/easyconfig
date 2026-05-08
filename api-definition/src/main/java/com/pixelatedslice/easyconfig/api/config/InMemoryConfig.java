package com.pixelatedslice.easyconfig.api.config;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import org.jspecify.annotations.NonNull;

import java.io.StringReader;
import java.io.StringWriter;

public interface InMemoryConfig extends Config {
    default void write(@NonNull StringWriter writer) {
        this.formatInstance().write(this.root(), writer);
    }

    default void parse(@NonNull String content) {
        try (var reader = new StringReader(content)) {
            this.formatInstance().parse(this.root(), reader);
        }
    }

    @FunctionalInterface
    interface Builder extends Config.Builder<Builder.FinalStep> {
        @FunctionalInterface
        interface FinalStep extends BuilderStep {
            @NonNull InMemoryConfig build();
        }
    }
}
