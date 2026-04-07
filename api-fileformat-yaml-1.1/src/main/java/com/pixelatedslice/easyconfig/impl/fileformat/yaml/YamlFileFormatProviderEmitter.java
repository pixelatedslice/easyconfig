package com.pixelatedslice.easyconfig.impl.fileformat.yaml;

import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.events.*;

import java.io.IOException;
import java.lang.reflect.Array;

final class YamlFileFormatProviderEmitter {
    private YamlFileFormatProviderEmitter() {
    }

    static void emitSection(Emitable emitter, ConfigSection section) throws IOException {
        emitter.emit(new MappingStartEvent(null, null, true, null, null, DumperOptions.FlowStyle.BLOCK));

        for (ConfigNode<?> node : section.nodes()) {
            emitNode(emitter, node);
        }

        for (ConfigSection nested : section.sections()) {
            emitComments(emitter, nested.descriptor().comments());

            emitter.emit(new ScalarEvent(
                    null, null,
                    new ImplicitTuple(true, false),
                    nested.descriptor().key(), null, null,
                    DumperOptions.ScalarStyle.DOUBLE_QUOTED
            ));
            emitSection(emitter, nested);
        }

        emitter.emit(new MappingEndEvent(null, null));
    }

    static void emitNode(Emitable emitter, ConfigNode<?> node) throws IOException {
        Object value = node.value().isPresent()
                ? node.value().get()
                : node.defaultValue().orElse(null);

        emitComments(emitter, node.descriptor().comments());
        emitter.emit(new ScalarEvent(
                null, null,
                new ImplicitTuple(true, false),
                node.descriptor().key(),
                null, null,
                DumperOptions.ScalarStyle.DOUBLE_QUOTED
        ));

        emitValue(emitter, value);
    }

    @SuppressWarnings("IfCanBeSwitch")
    static void emitValue(Emitable emitter, Object value) throws IOException {
        if (value == null) {
            emitter.emit(new ScalarEvent(
                    null, null,
                    new ImplicitTuple(true, false),
                    "null",
                    null, null,
                    DumperOptions.ScalarStyle.DOUBLE_QUOTED
            ));
            return;
        }

        if (value instanceof ConfigSection) {
            throw new IllegalStateException("A node cannot contain a config section");
        }

        if (value instanceof Iterable<?> iterable) {
            emitter.emit(new SequenceStartEvent(
                    null, null,
                    true,
                    null, null,
                    DumperOptions.FlowStyle.BLOCK
            ));

            for (Object item : iterable) {
                emitValue(emitter, item);
            }

            emitter.emit(new SequenceEndEvent(null, null));
            return;
        }

        if (value.getClass().isArray()) {
            emitter.emit(new SequenceStartEvent(
                    null, null,
                    true,
                    null, null,
                    DumperOptions.FlowStyle.BLOCK
            ));

            var length = Array.getLength(value);
            for (var i = 0; i < length; i++) {
                var item = Array.get(value, i);
                emitValue(emitter, item);
            }

            emitter.emit(new SequenceEndEvent(null, null));
        }

        emitter.emit(new ScalarEvent(
                null, null,
                new ImplicitTuple(true, true),
                String.valueOf(value),
                null, null,
                DumperOptions.ScalarStyle.DOUBLE_QUOTED
        ));
    }

    static void emitComments(Emitable emitter, Iterable<String> comments) throws IOException {
        for (var comment : comments) {
            emitter.emit(new CommentEvent(CommentType.BLOCK, comment, null, null));
        }
    }

}
