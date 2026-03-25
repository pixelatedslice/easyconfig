package com.pixelatedslice.easyconfig.api.fileformat.builtin;

public final class HoconFileFormat implements BuiltInFileFormat {
    private static volatile HoconFileFormat INSTANCE;

    private HoconFileFormat() {
    }

    public static HoconFileFormat instance() {
        if (INSTANCE == null) {
            synchronized (HoconFileFormat.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HoconFileFormat();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public String fileExtension() {
        return "conf";
    }
}