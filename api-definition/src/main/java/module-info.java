import com.pixelatedslice.easyconfig.api.serialization.SerializerRegistry;

open module com.pixelatedslice.easyconfig.api {
    uses SerializerRegistry;
    requires com.google.common;
    requires org.jspecify;
    requires com.google.errorprone.annotations;

    exports com.pixelatedslice.easyconfig.api.builder;
    exports com.pixelatedslice.easyconfig.api.config.config;
    exports com.pixelatedslice.easyconfig.api.config.node;
    exports com.pixelatedslice.easyconfig.api.config.node.collection;
    exports com.pixelatedslice.easyconfig.api.config.node.container;
    exports com.pixelatedslice.easyconfig.api.config.node.value;
    exports com.pixelatedslice.easyconfig.api.config.node.env;
    exports com.pixelatedslice.easyconfig.api.editable;
    exports com.pixelatedslice.easyconfig.api.exception;
    exports com.pixelatedslice.easyconfig.api.format;
    exports com.pixelatedslice.easyconfig.api.serialization;
    exports com.pixelatedslice.easyconfig.api.utils.typetoken;
    exports com.pixelatedslice.easyconfig.api.utils.primitive;
    exports com.pixelatedslice.easyconfig.api.validator;

    exports com.pixelatedslice.easyconfig.api.serialization.builtin
            to com.pixelatedslice.easyconfig.impl.serialization, com.pixelatedslice.easyconfig.impl;
}