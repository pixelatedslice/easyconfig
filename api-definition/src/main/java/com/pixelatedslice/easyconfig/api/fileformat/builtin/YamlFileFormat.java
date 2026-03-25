package com.pixelatedslice.easyconfig.api.fileformat.builtin;

public final class YamlFileFormat implements BuiltInFileFormat {
    private static volatile YamlFileFormat INSTANCE;

    private YamlFileFormat() {
    }

    public static YamlFileFormat instance() {
        if (INSTANCE == null) {
            synchronized (YamlFileFormat.class) {
                if (INSTANCE == null) {
                    INSTANCE = new YamlFileFormat();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public String fileExtension() {
        return "yml";
    }
}