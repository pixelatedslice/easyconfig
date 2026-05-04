package com.pixelatedslice.easyconfig.impl.fileformat.common;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.container.ContainerNode;
import com.pixelatedslice.easyconfig.api.config.node.env.EnvNode;
import com.pixelatedslice.easyconfig.api.config.node.value.ValueNode;
import com.pixelatedslice.easyconfig.api.exception.NodeException;
import com.pixelatedslice.easyconfig.api.serialization.Serializer;
import org.jspecify.annotations.NonNull;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;

import java.io.IOException;
import java.util.Map;

public class JacksonTreeReader {
    private final @NonNull JsonParser parser;
    private final @NonNull Map<@NonNull TypeToken<?>,
            @NonNull Serializer<?>> serializers;

    public JacksonTreeReader(@NonNull JsonParser parser,
            @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers) {
        this.parser = parser;
        this.serializers = serializers;
    }

    @SuppressWarnings("unchecked")
    private static <T> void setNodeValue(ValueNode<T> node, Object value) {
        try (var editable = node.editable()) {
            editable.setValue((T) value);
        }
    }

    public void read(@NonNull ContainerNode root) throws IOException, IllegalStateException {
        if (this.parser.currentToken() == null) {
            this.parser.nextToken();
        }
        if (this.parser.currentToken() == JsonToken.START_OBJECT) {
            this.readSectionContent(root);
        }
    }

    private void readSectionContent(@NonNull ContainerNode containerNode) throws IOException, IllegalStateException {
        while (this.parser.nextToken() != JsonToken.END_OBJECT) {
            if (this.parser.nextToken() == JsonToken.START_OBJECT) {
                this.readSection(containerNode);
            } else {
                this.readNode(containerNode);
            }
        }
    }

    public void readNode(@NonNull ContainerNode containerNode) throws IOException, IllegalStateException {
        var key = this.parser.currentName();

        var plainNodeOpt = containerNode.node(key).plainNode();
        if (plainNodeOpt.isPresent()) {
            var node = plainNodeOpt.get();

            if (!(node instanceof ValueNode<?> valueNode)) {
                throw NodeException.DID_NOT_EXPECT_NODE_TYPE_EXPECTED_VALUE_NODE_BASED(node.key(), node.nodeType());
            }

            setNodeValue(valueNode, (valueNode instanceof EnvNode<?>)
                    ? JacksonReadUtils.readEnv(this.parser, valueNode.typeToken())
                    : JacksonReadUtils.read(this.parser, valueNode.typeToken()));
        }
    }

    public void readSection(@NonNull ContainerNode containerNode) throws IOException {
        var key = this.parser.currentName();

        var plainNodeOpt = containerNode.node(key).plainNode();

        if (plainNodeOpt.isEmpty()) {
            this.parser.skipChildren();
            return;
        }

        var plainNode = plainNodeOpt.get();
        if (plainNode instanceof ContainerNode nestedContainerNode) {
            this.readSectionContent(nestedContainerNode);
            return;
        }

        ValueNode<?> valueNode = (ValueNode<?>) plainNode;
        var typeToken = ((ValueNode<?>) plainNode).typeToken();
        var serializer = this.serializers.get(typeToken);

        if (serializer == null) {
            this.parser.skipChildren();
            return;
        }

        var sectionBuilder = containerNode.childContainerBuilder(key);
        serializer.serialize(null, sectionBuilder);
        var tempSection = sectionBuilder.build();
        this.readSectionContent(tempSection);

        var deserializedValue = serializer.deserialize(tempSection);
        setNodeValue(valueNode, deserializedValue);
    }
}
