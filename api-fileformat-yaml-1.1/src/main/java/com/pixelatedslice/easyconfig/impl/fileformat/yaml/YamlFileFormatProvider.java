package com.pixelatedslice.easyconfig.impl.fileformat.yaml;

import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.fileformat.FileFormatProvider;
import com.pixelatedslice.easyconfig.api.fileformat.builtin.YamlFileFormat;
import org.jspecify.annotations.NonNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;

public final class YamlFileFormatProvider implements FileFormatProvider<YamlFileFormat> {
    private static final YamlFileFormat fileFormatInstance = YamlFileFormat.instance();
    private static volatile YamlFileFormatProvider INSTANCE;
    private final DumperOptions dumperOptions = dumperOptions();
    private final LoaderOptions loaderOptions = loaderOptions();

    private YamlFileFormatProvider() {
    }

    private static @NonNull DumperOptions dumperOptions() {
        var dumperOptions = new DumperOptions();
        dumperOptions.setIndent(2);
        dumperOptions.setIndicatorIndent(2);
        dumperOptions.setIndentWithIndicator(true);
        dumperOptions.setWidth(120);
        dumperOptions.setSplitLines(false);
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
        dumperOptions.setAllowUnicode(true);
        dumperOptions.setProcessComments(true);
        dumperOptions.setExplicitStart(false);
        dumperOptions.setExplicitEnd(false);
        return dumperOptions;
    }

    private static @NonNull LoaderOptions loaderOptions() {
        var loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false);
        loaderOptions.setMaxAliasesForCollections(50);
        loaderOptions.setCodePointLimit(10_000_000);
        loaderOptions.setProcessComments(true);
        loaderOptions.setEnumCaseSensitive(false);
        loaderOptions.setAllowRecursiveKeys(false);
        return loaderOptions;
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
        return fileFormatInstance;
    }

    @Override
    public <C extends ConfigFile> void write(@NonNull C configFile
    ) throws IOException, ParseException {
        var path = fileFormatInstance.pathWithExtension(configFile.filePathWithoutExtension());

        Files.createDirectories(path.getParent());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        try (var writer = Files.newBufferedWriter(path)) {
            var emitter = new Emitter(writer, this.dumperOptions);

            emitter.emit(new StreamStartEvent(null, null));
            emitter.emit(new DocumentStartEvent(null, null, false, null, null));

            YamlFileFormatProviderEmitter.emitSection(emitter, configFile.rootSection());

            emitter.emit(new DocumentEndEvent(null, null, false));
            emitter.emit(new StreamEndEvent(null, null));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends ConfigFile> void load(@NonNull C configFile)
            throws IOException, ParseException {
        var path = fileFormatInstance.pathWithExtension(configFile.filePathWithoutExtension());
        if (!Files.exists(path)) {
            throw new IOException("The File does not exist!");
        }

        try (var reader = Files.newBufferedReader(path)) {
            var parser = new ParserImpl(new StreamReader(reader), this.loaderOptions);

            // Skip StreamStart and DocumentStart
            parser.getEvent();
            parser.getEvent();

        }
    }

    @Override
    public <C extends ConfigFile> void reload(@NonNull C configFile) throws IOException, ParseException {
        configFile.rootSection().clearNodes();
        configFile.rootSection().clearSections();
    }
}
