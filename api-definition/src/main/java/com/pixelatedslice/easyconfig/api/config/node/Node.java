package com.pixelatedslice.easyconfig.api.config.node;

import com.google.errorprone.annotations.CheckReturnValue;
import org.jspecify.annotations.NonNull;

import java.util.stream.Stream;

public interface Node {
    default @NonNull NodeType nodeType() {
        return NodeType.PLAIN_NODE;
    }

    @NonNull String key();

    @NonNull ReturnedNode parent();

    @NonNull
    @CheckReturnValue
    NodeBuilder toBuilder();

    default @NonNull String[] fullPath() {
        Stream<String> stream = Stream.empty();
        Node current = this;
        while (true) {
            stream = Stream.concat(stream, Stream.of(current.key()));
            if (current.parent().plainNode().isEmpty()) {
                break;
            }
            current = current.parent().plainNode().get();
        }

        return stream.toArray(String[]::new);
    }
}
