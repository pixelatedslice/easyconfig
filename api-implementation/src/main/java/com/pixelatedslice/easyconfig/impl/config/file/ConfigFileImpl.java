package com.pixelatedslice.easyconfig.impl.config.file;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionImpl;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.Objects;

public class ConfigFileImpl implements ConfigFile {
    private final ConfigSection rootSection;
    private final Path filePathWithoutExtension;

    public ConfigFileImpl(@NonNull Path filePathWithoutExtension) {
        Objects.requireNonNull(filePathWithoutExtension);
        this.rootSection = ConfigSectionImpl.newRootSection();
        this.filePathWithoutExtension = filePathWithoutExtension;
    }

    ConfigFileImpl(@NonNull ConfigSection rootSection, @NonNull Path filePathWithoutExtension) {
        Objects.requireNonNull(rootSection);
        Objects.requireNonNull(filePathWithoutExtension);

        this.rootSection = rootSection;
        this.filePathWithoutExtension = filePathWithoutExtension;
    }

    @Override
    public @NonNull ConfigSection rootSection() {
        return this.rootSection;
    }

    @Override
    public @NonNull Path filePathWithoutExtension() {
        return this.filePathWithoutExtension;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ConfigFileImpl that)
                && Objects.equals(this.rootSection, that.rootSection)
                && Objects.equals(this.filePathWithoutExtension, that.filePathWithoutExtension)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rootSection, this.filePathWithoutExtension);
    }
}
