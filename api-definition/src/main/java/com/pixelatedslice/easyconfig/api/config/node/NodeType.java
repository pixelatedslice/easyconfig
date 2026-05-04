package com.pixelatedslice.easyconfig.api.config.node;

import org.jspecify.annotations.NonNull;

public enum NodeType {
    PLAIN_NODE("PLAIN NODE"),
    CONTAINER_NODE("CONTAINER NODE"),
    VALUE_NODE("VALUE NODE"),
    ENV_NODE("ENV NODE");

    private final String stringified;

    NodeType(String stringified) {
        this.stringified = stringified;
    }

    public static String[] valueNodeBased() {
        return new String[]{
                VALUE_NODE.stringified,
                ENV_NODE.stringified
        };
    }

    @Override
    public @NonNull String toString() {
        return this.stringified;
    }

    public boolean isValueNodeBased() {
        return (this == VALUE_NODE) || (this == ENV_NODE);
    }
}
