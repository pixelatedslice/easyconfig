package com.pixelatedslice.easyconfig.impl.fileformat;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormat;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.YamlFileFormat;
import com.pixelatedslice.easyconfig.impl.config.file.ConfigFileBuilderImpl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Map;

public final class YamlFileFormatProvider implements FileFormatProvider<YamlFileFormat> {
    private static volatile YamlFileFormatProvider INSTANCE;
    private final Yaml yaml;

    private YamlFileFormatProvider() {
        this.yaml = new Yaml();
    }

    public static YamlFileFormatProvider instance() {
        if (INSTANCE == null) {
            synchronized (YamlFileFormatProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new YamlFileFormatProvider();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public Class<YamlFileFormat> fileFormatClass() {
        return YamlFileFormat.class;
    }

    @Override
    public YamlFileFormat fileFormatInstance() {
        return YamlFileFormat.instance();
    }

    @Override
    public <C extends ConfigFile> void write(@NonNull Path path, @NonNull C configFile
    ) throws IOException, ParseException {

    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <C extends ConfigFile> C load(@NonNull Path path)
            throws IOException, ParseException {
        if (!Files.exists(path)) {
            throw new IOException("The File does not exist!");
        }
        var metaFilePath = FileFormat.toMetaFilePath(path);
        if (!Files.exists(metaFilePath)) {
            throw new IOException("The Meta File does not exist! Without it, the Config File cannot be loaded!");
        }

        Map<String, Object> metaMap = this.yaml.load(Files.newInputStream(metaFilePath));
        Map<String, Object> fileMap = this.yaml.load(Files.newInputStream(path));

        if (metaMap == null) {
            throw new ParseException("The Meta File content is invalid YAML", 0);
        }
        if (fileMap == null) {
            throw new ParseException("The File content is invalid YAML", 0);
        }

        var builder = new ConfigFileBuilderImpl().filePath(path).fileFormat(this.fileFormatClass());
        return (C) builder.build();
    }
}
