package com.pixelatedslice.easyconfig.api.config.file;

import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.format.Format;
import org.jspecify.annotations.NonNull;

public interface ConfigFile {
    @NonNull Format formatInstance();

    ContainerNode.@NonNull Root root();

    default void save() {
        this.formatInstance().save(this.root());
    }

    default void writeToString(@NonNull StringBuffer buffer) {
        this.formatInstance().writeToString(this.root(), buffer);
    }

    default void load() {
        this.formatInstance().load(this.root());
    }

    default void parseFromString(@NonNull String content) {
        this.formatInstance().parseFromString(this.root(), content);
    }
}
