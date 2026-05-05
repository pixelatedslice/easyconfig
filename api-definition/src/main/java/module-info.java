import com.pixelatedslice.easyconfig.api.config.node.collection.builder.CollectionNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.container.builder.ContainerNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.env.builder.EnvNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.value.builder.ValueNodeBuilder;
import com.pixelatedslice.easyconfig.api.serialization.SerializerRegistry;

open module com.pixelatedslice.easyconfig.api {
    uses SerializerRegistry;
    uses EnvNodeBuilder;
    uses ValueNodeBuilder;
    uses ContainerNodeBuilder;
    uses CollectionNodeBuilder;
    requires com.google.common;
    requires org.jspecify;

    exports com.pixelatedslice.easyconfig.api.builder;
    exports com.pixelatedslice.easyconfig.api.config.config;
    exports com.pixelatedslice.easyconfig.api.config.node;
    exports com.pixelatedslice.easyconfig.api.config.node.collection;
    exports com.pixelatedslice.easyconfig.api.config.node.collection.builder;
    exports com.pixelatedslice.easyconfig.api.config.node.container;
    exports com.pixelatedslice.easyconfig.api.config.node.container.builder;
    exports com.pixelatedslice.easyconfig.api.config.node.value;
    exports com.pixelatedslice.easyconfig.api.config.node.value.builder;
    exports com.pixelatedslice.easyconfig.api.config.node.env;
    exports com.pixelatedslice.easyconfig.api.config.node.env.builder;
    exports com.pixelatedslice.easyconfig.api.editable;
    exports com.pixelatedslice.easyconfig.api.exception;
    exports com.pixelatedslice.easyconfig.api.format;
    exports com.pixelatedslice.easyconfig.api.serialization;
    exports com.pixelatedslice.easyconfig.api.serialization.format;
    exports com.pixelatedslice.easyconfig.api.serialization.node;
    exports com.pixelatedslice.easyconfig.api.utils.typetoken;
    exports com.pixelatedslice.easyconfig.api.utils.primitive;
    exports com.pixelatedslice.easyconfig.api.validator;

    exports com.pixelatedslice.easyconfig.api.serialization.node.builtin
            to com.pixelatedslice.easyconfig.impl.serialization, com.pixelatedslice.easyconfig.impl;
    exports com.pixelatedslice.easyconfig.api.serialization.format.builtin
            to com.pixelatedslice.easyconfig.impl.serialization, com.pixelatedslice.easyconfig.impl;
    exports com.pixelatedslice.easyconfig.api.config.node.builder;
}