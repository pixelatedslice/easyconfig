package com.pixelatedslice.easyconfig.api.config.node;

import java.util.stream.Stream;

public enum NodeType {
    PLAIN_NODE(false),
    CONTAINER_NODE(false),
    VALUE_NODE(true),
    ENV_NODE(true);

    private final boolean isValueBased;

    NodeType(boolean isValueBased) {
        this.isValueBased = isValueBased;
    }

    public static String[] valueNodeBased() {
        return Stream.of(values())
                .filter(NodeType::isValueBased)
                .map(Enum::toString)
                .toArray(String[]::new);
    }

    public boolean isValueBased() {
        return this.isValueBased;
    }
}
