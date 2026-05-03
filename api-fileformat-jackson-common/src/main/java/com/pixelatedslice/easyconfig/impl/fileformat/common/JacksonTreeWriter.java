package com.pixelatedslice.easyconfig.impl.fileformat.common;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.old.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.old.config.node.EnvConfigNode;
import com.pixelatedslice.easyconfig.api.old.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.old.serialization.Serializer;
import org.jspecify.annotations.NonNull;
import tools.jackson.core.JsonGenerator;

import java.util.Map;

public class JacksonTreeWriter {
    private final @NonNull JsonGenerator generator;
    private final @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers;

    public JacksonTreeWriter(@NonNull JsonGenerator generator,
            @NonNull Map<@NonNull TypeToken<?>, @NonNull Serializer<?>> serializers) {
        this.generator = generator;
        this.serializers = serializers;
    }

    public void write(@NonNull ConfigSection root) {
        this.generator.writeStartObject();
        this.writeSectionContent(root);
        this.generator.writeEndObject();
    }

    private void writeSectionContent(@NonNull ConfigSection section) {
        for (ConfigSection nested : section.sections()) {
            this.writeSection(nested);
        }

        for (ConfigNode<?> node : section.nodes()) {
            this.writeNode(node);
        }
    }

    public void writeSection(@NonNull ConfigSection section) {
        this.generator.writeName(section.key());
        this.generator.writeStartObject();
        this.writeSectionContent(section);
        this.generator.writeEndObject();
    }

    public void writeNode(@NonNull ConfigNode<?> node) {
        this.generator.writeName(node.key());

        if (node instanceof EnvConfigNode<?> envNode) {
            JacksonWriteUtils.write(this.generator, "env(" + envNode.envKey() + ")");
            return;
        }

        Object value = node.valueOrDefault().orElse(null);

        if (value == null) {
            this.generator.writeNull();
            return;
        }

        @SuppressWarnings("unchecked")
        Serializer<Object> serializer = (Serializer<Object>) this.serializers.get(node.typeToken());
        if (serializer != null) {
            var tempBuilder = node.parent().builderForNested(node.key());
            serializer.serialize(value, tempBuilder);

            this.generator.writeStartObject();
            this.writeSectionContent(tempBuilder.build());
            this.generator.writeEndObject();
        } else {
            JacksonWriteUtils.write(this.generator, value);
        }
    }
}
