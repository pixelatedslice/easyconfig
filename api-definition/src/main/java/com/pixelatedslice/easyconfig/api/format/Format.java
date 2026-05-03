package com.pixelatedslice.easyconfig.api.format;

import com.pixelatedslice.easyconfig.api.config.node.Node;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.Objects;

@SuppressWarnings("unused")
public interface Format {
    @NonNull String fileExtension();

    <N extends Node> void save(@NonNull N node);

    <N extends Node> void writeToString(@NonNull N node, @NonNull StringBuffer buffer);

    <N extends Node> void load(@NonNull N node);

    <N extends Node> void parseFromString(@NonNull N node, @NonNull String content);

    default @NonNull Path pathWithExtension(@NonNull Path path) {
        Objects.requireNonNull(path);
        return path.resolveSibling(path.getFileName() + "." + this.fileExtension()).toAbsolutePath();
    }
}