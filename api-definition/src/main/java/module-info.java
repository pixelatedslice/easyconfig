import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.file.ConfigFileBuilder;
import com.pixelatedslice.easyconfig.api.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.section.builder.ConfigSectionBuilder;

open module com.pixelatedslice.easyconfig.api {
    requires com.google.common;
    requires org.jspecify;

    uses ConfigNodeBuilder;
    uses ConfigFileBuilder;
    uses ConfigSectionBuilder;
    uses EasyConfig;

    exports com.pixelatedslice.easyconfig.api;
    exports com.pixelatedslice.easyconfig.api.config.file;
    exports com.pixelatedslice.easyconfig.api.config.node;
    exports com.pixelatedslice.easyconfig.api.config.section;
    exports com.pixelatedslice.easyconfig.api.exception;
    exports com.pixelatedslice.easyconfig.api.format;
    exports com.pixelatedslice.easyconfig.api.format.builtin;
    exports com.pixelatedslice.easyconfig.api.serialization;
    exports com.pixelatedslice.easyconfig.api.serialization.builtin
            to com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;
    exports com.pixelatedslice.easyconfig.api.comments;
    exports com.pixelatedslice.easyconfig.api.utils.primitive;
    exports com.pixelatedslice.easyconfig.api.utils.type_token;
    exports com.pixelatedslice.easyconfig.api.builder.config;
    exports com.pixelatedslice.easyconfig.api.builder;
    exports com.pixelatedslice.easyconfig.api.mutability.immutable;
    exports com.pixelatedslice.easyconfig.api.mutability.mutable;
    exports com.pixelatedslice.easyconfig.api.validator;
    exports com.pixelatedslice.easyconfig.api.config.node.builder;
    exports com.pixelatedslice.easyconfig.api.config.section.builder;
}