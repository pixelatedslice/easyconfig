package com.pixelatedslice.easyconfig.api.config.file;

import com.pixelatedslice.easyconfig.api.builder.config.BuilderWithConfigNodes;
import com.pixelatedslice.easyconfig.api.builder.config.BuilderWithConfigSections;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

public interface ConfigFileBuilder extends BuilderWithConfigNodes, BuilderWithConfigSections {
    @NonNull ConfigFileBuilder filePath(@NonNull Path filePathWithoutExtension);

    @NonNull ConfigFile build();
}