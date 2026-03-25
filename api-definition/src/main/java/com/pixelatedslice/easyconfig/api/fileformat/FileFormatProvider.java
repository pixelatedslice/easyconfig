package com.pixelatedslice.easyconfig.api.fileformat;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

public interface FileFormatProvider<F extends FileFormat> {
    Class<F> fileFormatClass();

    F fileFormatInstance();

    <C extends ConfigFile> void write(@NonNull Path path, @NonNull C configFile) throws IOException, ParseException;

    <C extends ConfigFile> @Nullable C load(@NonNull Path path) throws IOException, ParseException;

    default Path pathWithExtension(@NonNull Path path) {
        return path.resolveSibling(path.getFileName() + "." + this.fileFormatInstance().fileExtension());
    }
}