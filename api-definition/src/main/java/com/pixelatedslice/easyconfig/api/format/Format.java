package com.pixelatedslice.easyconfig.api.format;

import com.pixelatedslice.easyconfig.api.config.node.Node;
import org.jspecify.annotations.NonNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Objects;

@SuppressWarnings("unused")
public interface Format {
    @NonNull String fileExtension();

    <N extends Node> void write(@NonNull N node, @NonNull Writer writer);

    <N extends Node> void parse(@NonNull N node, @NonNull Reader reader);

    default @NonNull Path pathWithExtension(@NonNull Path path) {
        Objects.requireNonNull(path);
        return path.resolveSibling(path.getFileName() + "." + this.fileExtension()).toAbsolutePath();
    }
}