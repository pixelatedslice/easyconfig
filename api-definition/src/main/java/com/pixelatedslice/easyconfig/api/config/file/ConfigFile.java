package com.pixelatedslice.easyconfig.api.config.file;

import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

public interface ConfigFile extends ConfigSection {
    @NonNull Path filePathWithoutExtension();

    @NonNull Class<? extends FileFormat> fileFormatClass();

    void save() throws IOException, ParseException;

    ConfigFile reload() throws IOException, ParseException;
}