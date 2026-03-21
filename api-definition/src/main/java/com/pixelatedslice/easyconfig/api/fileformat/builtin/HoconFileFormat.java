package com.pixelatedslice.easyconfig.api.fileformat.builtin;

import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;

/**
 * Represents a HOCON file format by providing the associated file extension for HOCON files.
 * This interface is intended to standardize the handling of HOCON files across the system
 * by extending the {@link FileFormat} interface. It defines the specific file extension
 * used for HOCON file types.
 */
public non-sealed interface HoconFileFormat extends BuiltInFileFormat {
    /**
     * {@inheritDoc}
     * <p>
     * <b>Implementation Details:</b> This method returns the file extension for HOCON files.
     */
    @Override
    default String fileExtension() {
        return "conf";
    }
}
