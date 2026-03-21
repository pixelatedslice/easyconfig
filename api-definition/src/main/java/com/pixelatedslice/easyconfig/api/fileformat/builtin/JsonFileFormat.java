package com.pixelatedslice.easyconfig.api.fileformat.builtin;

import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;

/**
 * Represents a JSON file format by providing the associated file extension for JSON files.
 * This interface is intended to standardize the handling of JSON files across the system
 * by extending the {@link FileFormat} interface. It defines the specific file extension
 * used for JSON file types.
 */
public non-sealed interface JsonFileFormat extends BuiltInFileFormat {
    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the file extension for JSON files.
     */
    @Override
    default String fileExtension() {
        return "json";
    }
}
