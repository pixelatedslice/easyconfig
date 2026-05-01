package com.pixelatedslice.easyconfig.api.config.file;

import com.pixelatedslice.easyconfig.api.builder.BuilderStep;
import com.pixelatedslice.easyconfig.api.builder.config.ConfigNodeChildrenStep;
import com.pixelatedslice.easyconfig.api.builder.config.NestedConfigSectionStep;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

@FunctionalInterface
public interface ConfigFileBuilder {
    @NonNull ConfigFileBuilder filePath(@NonNull Path filePathWithoutExtension);

    interface ChildNodeStep extends BuilderStep, ConfigNodeChildrenStep<ChildNodeStep>, NestedSectionStep {
    }

    interface NestedSectionStep extends BuilderStep, NestedConfigSectionStep<NestedSectionStep>, FinalStep {
    }

    @FunctionalInterface
    interface FinalStep extends BuilderStep {
        @NonNull ConfigFile build();
    }

    interface Handler extends ConfigFileBuilder, ChildNodeStep, NestedSectionStep, FinalStep {
    }
}