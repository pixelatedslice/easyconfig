package com.pixelatedslice.easyconfig.api.fileformat.builtin;

import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;

/**
 * Represents a YAML file format by providing the associated file extension for YAML files.
 * <br>
 * This interface extends {@link FileFormat} and supplies the specific implementation
 * details for YAML file handling, such as providing the ".yml" extension. It is intended
 * to standardize the usage of YAML files across the system.
 */
public non-sealed interface YamlFileFormat extends BuiltInFileFormat {
    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the file extension for YAML files.
     */
    @Override
    default String fileExtension() {
        return "yml";
    }
}
