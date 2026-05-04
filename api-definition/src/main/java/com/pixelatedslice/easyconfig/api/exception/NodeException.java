package com.pixelatedslice.easyconfig.api.exception;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.NodeType;
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
        return new NodeException("Operation failed: The root node does not have a parent.");
    }

    public static NodeException DID_NOT_EXPECT_NODE_TYPE(
            @NonNull String key,
            @NonNull NodeType expected,
            @NonNull NodeType received
    ) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(expected);
        Objects.requireNonNull(received);

        var expectedMessage = expected.toString();
        if (expected.isValueNodeBased()) {
            expectedMessage = String.join(" / ", NodeType.valueNodeBased());
        }

        return new NodeException(
                "Type mismatch for node '%s': Expected %s but encountered %s.",
                key, expectedMessage, received
        );
    }

    public static NodeException DID_NOT_EXPECT_NODE_TYPE_EXPECTED_VALUE_NODE_BASED(
            @NonNull String key,
            @NonNull NodeType received
    ) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(received);

        return new NodeException(
                "Type mismatch for node '%s': Expected %s but encountered %s.",
                key, String.join(" / ", NodeType.valueNodeBased()), received
        );
    }

    public static NodeException NODE_HAS_WRONG_VALUE_TYPE(@NonNull String key, @NonNull TypeToken<?> nodeType,
            @NonNull String expectedType) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(nodeType);
        Objects.requireNonNull(expectedType);

        return new NodeException("Value type mismatch for node '%s': Expected %s but found %s.",
                key, expectedType, nodeType.toString()
        );
    }
}
