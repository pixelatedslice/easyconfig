package com.pixelatedslice.easyconfig.api.exception;

import com.google.common.reflect.TypeToken;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class NodeException extends RuntimeException {
    public NodeException(@NonNull String message) {
        Objects.requireNonNull(message);

        super(message);
    }

    public NodeException(@NonNull String message, @NonNull Object @NonNull ... args) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(args);

        super(String.format(message, args));
    }

    public static NodeException ROOT_NODE_HAS_NO_PARENT() {
        return new NodeException("The root node has no parent.");
    }

    public static NodeException EXPECTED_VALUE_NODE_GOT_CONTAINER(@NonNull String key) {
        Objects.requireNonNull(key);

        return new NodeException("The node \"%s\" is a container node and not an value node!");
    }

    public static NodeException EXPECTED_CONTAINER_NODE_GOT_VALUE(@NonNull String key) {
        Objects.requireNonNull(key);

        return new NodeException("The node \"%s\" is a value node and not an container node!");
    }

    public static NodeException CLASS_USED_IN_PLACE_OF_TYPETOKEN(
            @NonNull TypeToken<?> complex,
            @NonNull Class<?> simple
    ) {
        Objects.requireNonNull(complex);
        Objects.requireNonNull(simple);

        return new NodeException(
                "Cannot use a simple type (%s) to check a complex type (%s)!",
                simple.toString(),
                complex.toString()
        );
    }

    public static NodeException CLASS_USED_IN_PLACE_OF_TYPETOKEN(
            @NonNull Class<?> simple
    ) {
        Objects.requireNonNull(simple);

        return new NodeException(
                "Cannot use a simple type (%s) to check a complex type!",
                simple.toString()
        );
    }

    public static NodeException NODE_HAS_WRONG_VALUE_TYPE(
            @NonNull String key,
            @NonNull TypeToken<?> nodeType,
            @NonNull String expectedType
    ) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(nodeType);
        Objects.requireNonNull(expectedType);

        return new NodeException("""
                The value node %s has the wrong type!
                Expected: %s
                Received: %s
                """.trim(), key, nodeType.toString(), expectedType);
    }
}
