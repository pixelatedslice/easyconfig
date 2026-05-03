package com.pixelatedslice.easyconfig.impl.fileformat.common;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.old.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.old.config.node.EnvConfigNode;
import com.pixelatedslice.easyconfig.api.old.config.node.WithConfigNodeChildren;
import com.pixelatedslice.easyconfig.api.old.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.old.serialization.Serializer;
import org.jspecify.annotations.NonNull;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;

import java.io.IOException;
import java.util.Map;

public class JacksonTreeReader {
    private final @NonNull JsonParser parser;
    private final @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers;

    public JacksonTreeReader(@NonNull JsonParser parser,
            @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers) {
        this.parser = parser;
        this.serializers = serializers;
    }

    @SuppressWarnings("unchecked")
    private static <T> void setNodeValue(ConfigNode<T> node, Object value) {
        try (var mutable = node.mutable()) {
            mutable.setValue((T) value);
        }
    }

    public void read(@NonNull ConfigSection root) throws IOException, IllegalStateException {
        if (this.parser.currentToken() == null) {
            this.parser.nextToken();
        }
        if (this.parser.currentToken() == JsonToken.START_OBJECT) {
            this.readSectionContent(root);
        }
    }

    private void readSectionContent(@NonNull ConfigSection section) throws IOException, IllegalStateException {
        while (this.parser.nextToken() != JsonToken.END_OBJECT) {
            if (this.parser.nextToken() == JsonToken.START_OBJECT) {
                this.readSection(section);
            } else {
                this.readNode(section);
            }
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void readNode(@NonNull WithConfigNodeChildren section) throws IOException, IllegalStateException {
        var key = this.parser.currentName();

        var typeTokenOpt = section.nodeTypeToken(key);
        if (typeTokenOpt.isPresent()) {
            var typeToken = typeTokenOpt.get();
            var node = section.node(typeToken, key).get();

            setNodeValue(node, (node instanceof EnvConfigNode<?>)
                    ? JacksonReadUtils.readEnv(this.parser, typeToken)
                    : JacksonReadUtils.read(this.parser, typeToken));
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void readSection(@NonNull ConfigSection section) throws IOException, IllegalStateException {
        var key = this.parser.currentName();

        var nestedSection = section.section(key);
        if (nestedSection.isPresent()) {
            this.readSectionContent(nestedSection.get());
            return;
        }

        var typeTokenOpt = section.nodeTypeToken(key);
        if (typeTokenOpt.isPresent()) {
            var typeToken = typeTokenOpt.get();
            var serializer = this.serializers.get(typeToken);

            if (serializer != null) {
                var sectionBuilder = section.builderForNested(key);
                serializer.serialize(null, sectionBuilder);
                var tempSection = sectionBuilder.build();
                this.readSectionContent(tempSection);

                var deserializedValue = serializer.deserialize(tempSection);
                setNodeValue(section.node(typeToken, key).get(), deserializedValue);
                return;
            }
        }
        this.parser.skipChildren();
    }
}
