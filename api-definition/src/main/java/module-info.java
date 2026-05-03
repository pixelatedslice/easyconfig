open module com.pixelatedslice.easyconfig.api {
    requires com.google.common;
    requires org.jspecify;
    requires com.google.errorprone.annotations;

    uses ConfigNodeBuilder;
    uses ConfigFileBuilder;
    uses ConfigSectionBuilder;
    uses EasyConfig;

    exports com.pixelatedslice.easyconfig.api.old;
    exports com.pixelatedslice.easyconfig.api.old.config.file;
    exports com.pixelatedslice.easyconfig.api.old.config.node;
    exports com.pixelatedslice.easyconfig.api.old.config.section;
    exports com.pixelatedslice.easyconfig.api.old.exception;
    exports com.pixelatedslice.easyconfig.api.old.format;
    exports com.pixelatedslice.easyconfig.api.old.format.builtin;
    exports com.pixelatedslice.easyconfig.api.old.serialization;
    exports com.pixelatedslice.easyconfig.api.old.serialization.builtin
            to com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;
    exports com.pixelatedslice.easyconfig.api.old.comments;
    exports com.pixelatedslice.easyconfig.api.old.utils.primitive;
    exports com.pixelatedslice.easyconfig.api.old.utils.type_token;
    exports com.pixelatedslice.easyconfig.api.old.builder.config;
    exports com.pixelatedslice.easyconfig.api.old.builder;
    exports com.pixelatedslice.easyconfig.api.old.mutability.immutable;
    exports com.pixelatedslice.easyconfig.api.old.mutability.mutable;
    exports com.pixelatedslice.easyconfig.api.old.validator;
    exports com.pixelatedslice.easyconfig.api.old.config.node.builder;
    exports com.pixelatedslice.easyconfig.api.old.config.section.builder;
}