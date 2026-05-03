package com.pixelatedslice.easyconfig.impl.fileformat.toml;

import com.pixelatedslice.easyconfig.api.old.CopiedEasyConfig;
import com.pixelatedslice.easyconfig.api.old.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.old.format.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.old.format.builtin.TomlFormat;
import com.pixelatedslice.easyconfig.impl.fileformat.common.JacksonTreeReader;
import com.pixelatedslice.easyconfig.impl.fileformat.common.JacksonTreeWriter;
import org.jspecify.annotations.NonNull;
import tools.jackson.core.JsonEncoding;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.dataformat.toml.TomlFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Objects;

public final class TomlFileFormatProvider implements FileFormatProvider<TomlFormat> {
    private static final TomlFormat formatInstance = TomlFormat.instance();
    private final TomlFactory factory = new TomlFactory();

    private TomlFileFormatProvider() {
    }

    public static TomlFileFormatProvider instance() {
        return TomlFileFormatProviderHolder.INSTANCE;
    }

    @Override
    public Class<TomlFormat> formatClass() {
        return TomlFormat.class;
    }

    @Override
    public TomlFormat formatInstance() {
        return formatInstance;
    }

    @Override
    public <C extends ConfigFile> String writeToString(@NonNull CopiedEasyConfig easyConfig, @NonNull C configFile) {
        Objects.requireNonNull(easyConfig);
        Objects.requireNonNull(configFile);

        var stringWriter = new StringWriter();

        try (var generator = this.factory.createGenerator(ObjectWriteContext.empty(), stringWriter)) {
            new JacksonTreeWriter(generator, easyConfig.serializers()).write(configFile.rootSection());
        }

        return stringWriter.toString();
    }

    @Override
    public <C extends ConfigFile> void save(@NonNull CopiedEasyConfig easyConfig, @NonNull C configFile
    ) throws IOException {
        Objects.requireNonNull(easyConfig);
        Objects.requireNonNull(configFile);

        var path = formatInstance.pathWithExtension(configFile.filePathWithoutExtension());

        Files.createDirectories(path.getParent());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        try (var outStream = Files.newOutputStream(path)) {
            try (var generator = this.factory.createGenerator(
                    ObjectWriteContext.empty(),
                    outStream,
                    JsonEncoding.UTF8
            )) {
                new JacksonTreeWriter(generator, easyConfig.serializers()).write(configFile.rootSection());
            }
        }
    }

    @Override
    public <C extends ConfigFile> void load(@NonNull CopiedEasyConfig easyConfig, @NonNull C configFile)
            throws IOException {
        Objects.requireNonNull(easyConfig);
        Objects.requireNonNull(configFile);

        var path = formatInstance.pathWithExtension(configFile.filePathWithoutExtension());
        if (!Files.exists(path)) {
            throw new IOException("The File does not exist!");
        }

        try (var inStream = Files.newInputStream(path)) {
            try (var parser = this.factory.createParser(ObjectReadContext.empty(), inStream)) {
                new JacksonTreeReader(parser, easyConfig.serializers()).read(configFile.rootSection());
            }
        }
    }

    @Override
    public <C extends ConfigFile> void parseFromString(@NonNull CopiedEasyConfig easyConfig, @NonNull C configFile,
            @NonNull String content) throws IOException {
        Objects.requireNonNull(easyConfig);
        Objects.requireNonNull(configFile);
        Objects.requireNonNull(content);

        try (var parser = this.factory.createParser(ObjectReadContext.empty(), new StringReader(content))) {
            new JacksonTreeReader(parser, easyConfig.serializers()).read(configFile.rootSection());
        }
    }

    private static final class TomlFileFormatProviderHolder {
        private static final TomlFileFormatProvider INSTANCE = new TomlFileFormatProvider();
    }
}
