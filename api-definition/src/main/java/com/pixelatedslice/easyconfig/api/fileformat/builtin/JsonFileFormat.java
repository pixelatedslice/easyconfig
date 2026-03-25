package com.pixelatedslice.easyconfig.api.fileformat.builtin;

public final class JsonFileFormat implements BuiltInFileFormat {
    private static volatile JsonFileFormat INSTANCE;

    private JsonFileFormat() {
    }

    public static JsonFileFormat instance() {
        if (INSTANCE == null) {
            synchronized (JsonFileFormat.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JsonFileFormat();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public String fileExtension() {
        return "json";
    }
}