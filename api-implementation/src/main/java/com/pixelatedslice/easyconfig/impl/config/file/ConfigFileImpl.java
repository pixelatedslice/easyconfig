package com.pixelatedslice.easyconfig.impl.config.file;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;

import java.nio.file.Path;

public record ConfigFileImpl(ConfigSection rootSection, Path filePathWithoutExtension) implements ConfigFile {
}
