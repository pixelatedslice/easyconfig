package com.pixelatedslice.easyconfig.api.fileformat;

import com.pixelatedslice.easyconfig.api.fileformat.builtin.BuiltInFileFormat;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

@FunctionalInterface
public interface FileFormat {
    static boolean isBuiltIn(@NonNull FileFormat fileFormat) {
        return fileFormat instanceof BuiltInFileFormat;
    }

    static Path toMetaFilePath(@NonNull Path filePathWithExtension) {
        return filePathWithExtension.resolveSibling("." + filePathWithExtension.getFileName() + ".meta");
    }

    String fileExtension();


    default Path pathWithExtension(@NonNull Path path) {
        return path.resolveSibling(path.getFileName() + "." + this.fileExtension());
    }
}