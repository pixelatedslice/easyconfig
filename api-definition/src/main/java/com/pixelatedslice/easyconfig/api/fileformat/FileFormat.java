package com.pixelatedslice.easyconfig.api.fileformat;

import com.pixelatedslice.easyconfig.api.fileformat.builtin.BuiltInFileFormat;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a file format by defining a method to retrieve its associated file extension.
 * <br>
 * This interface is intended to be implemented by classes or enums that describe specific
 * file format types, allowing consistent access to their extensions. The implementations should
 * represent concrete formats commonly used for configuration or data files, such as "json", "xml", or "yaml".
 * <p>
 * Implementations of this interface are often used as a type parameter in providers or utilities
 * that facilitate handling files of a specific format.
 */
@FunctionalInterface
public interface FileFormat {
    /**
     * Determines if the provided {@link FileFormat} instance is a built-in file format.
     *
     * @param fileFormat the {@link FileFormat} instance to check; must not be null
     * @return {@code true} if the provided {@link FileFormat} is an instance of {@link BuiltInFileFormat},
     * {@code false} otherwise
     * @throws NullPointerException if {@code fileFormat} is null
     */
    static boolean isBuiltIn(@NotNull FileFormat fileFormat) {
        return fileFormat instanceof BuiltInFileFormat;
    }

    /**
     * Retrieves the file extension associated with the file format.
     * The returned string typically represents the suffix used for files of this format
     * (e.g., "json", "xml", "yaml").
     *
     * @return the file extension as a non-null {@code String}
     */
    String fileExtension();
}