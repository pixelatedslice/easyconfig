package com.pixelatedslice.easyconfig.api.fileformat.builtin;

import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;

/**
 * Represents a TOML file format by providing the associated file extension for TOML files.
 * This interface is intended to standardize the handling of TOML files across the system
 * by extending the {@link FileFormat} interface. It defines the specific file extension
 * used for TOML file types.
 */
public non-sealed interface TomlFileFormat extends BuiltInFileFormat {
    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the file extension for Toml files.
     */
    @Override
    default String fileExtension() {
        return "toml";
    }
}
