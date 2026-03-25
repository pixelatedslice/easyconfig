package com.pixelatedslice.easyconfig.api.fileformat.builtin;

public final class TomlFileFormat implements BuiltInFileFormat {
    private static volatile TomlFileFormat INSTANCE;

    private TomlFileFormat() {
    }

    public static TomlFileFormat instance() {
        if (INSTANCE == null) {
            synchronized (TomlFileFormat.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TomlFileFormat();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public String fileExtension() {
        return "toml";
    }
}