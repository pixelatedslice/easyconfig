package com.pixelatedslice.easyconfig.impl.fileformat.json;


import com.pixelatedslice.easyconfig.api.old.CopiedEasyConfig;
import com.pixelatedslice.easyconfig.api.old.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.old.format.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.old.format.builtin.JsonFormat;
import com.pixelatedslice.easyconfig.impl.fileformat.common.JacksonTreeReader;
import com.pixelatedslice.easyconfig.impl.fileformat.common.JacksonTreeWriter;
import org.jspecify.annotations.NonNull;
import tools.jackson.core.JsonEncoding;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.core.util.DefaultPrettyPrinter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Objects;

public final class JsonFileFormatProvider implements FileFormatProvider<JsonFormat> {
    private static final JsonFormat formatInstance = JsonFormat.instance();
    private final JsonFactory factory = new JsonFactory();
    private final ObjectWriteContext objectWriteContext = new ObjectWriteContext.Base() {
        @Override
        public tools.jackson.core.PrettyPrinter getPrettyPrinter() {
            return new DefaultPrettyPrinter();
        }
    };

    private JsonFileFormatProvider() {
    }

    public static JsonFileFormatProvider instance() {
        return JsonFileFormatProviderHolder.INSTANCE;
    }

    @Override
    public Class<JsonFormat> formatClass() {
        return JsonFormat.class;
    }

    @Override
    public JsonFormat formatInstance() {
        return formatInstance;
    }

    @Override
    public <C extends ConfigFile> String writeToString(@NonNull CopiedEasyConfig easyConfig, @NonNull C configFile) {
        Objects.requireNonNull(easyConfig);
        Objects.requireNonNull(configFile);

        var stringWriter = new StringWriter();

        try (var generator = this.factory.createGenerator(this.objectWriteContext, stringWriter)) {
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
                    this.objectWriteContext,
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

        try (var parser = this.factory.createParser(ObjectReadContext.empty(), new StringReader(content))) {
            new JacksonTreeReader(parser, easyConfig.serializers()).read(configFile.rootSection());
        }
    }

    private static final class JsonFileFormatProviderHolder {
        private static final JsonFileFormatProvider INSTANCE = new JsonFileFormatProvider();
    }
}
