package com.pixelatedslice.easyconfig.api.fileformat.builtin;

import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;

public sealed interface BuiltInFileFormat extends FileFormat
        permits HoconFileFormat, JsonFileFormat, TomlFileFormat, YamlFileFormat {
}