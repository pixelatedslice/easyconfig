package com.pixelatedslice.easyconfig.api.config.builder;

import com.pixelatedslice.easyconfig.api.config.ConfigFile;

import java.nio.file.Path;

public interface ConfigFileBuilder {
    ConfigFileBuilder path(Path path);
    

    ConfigFile build();
}
