import com.pixelatedslice.easyconfig.api.config.file.ConfigFileBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.node.descriptor.ConfigNodeDescriptorBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;

open module com.pixelatedslice.easyconfig.api {
    requires com.google.common;
    requires org.jspecify;

    uses ConfigNodeBuilder;
    uses ConfigFileBuilder;
    uses ConfigSectionBuilder;
    uses ConfigNodeDescriptorBuilder;
    uses ConfigNodeIterator;
    uses ConfigSectionIterator;

    exports com.pixelatedslice.easyconfig.api;
    exports com.pixelatedslice.easyconfig.api.config.file;
    exports com.pixelatedslice.easyconfig.api.config.node;
    exports com.pixelatedslice.easyconfig.api.config.section;
    exports com.pixelatedslice.easyconfig.api.descriptor;
    exports com.pixelatedslice.easyconfig.api.exception;
    exports com.pixelatedslice.easyconfig.api.fileformat;
    exports com.pixelatedslice.easyconfig.api.fileformat.builtin;
    exports com.pixelatedslice.easyconfig.api.serialization;
    exports com.pixelatedslice.easyconfig.api.serialization.builtin
            to com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;
    exports com.pixelatedslice.easyconfig.api.comments;
    exports com.pixelatedslice.easyconfig.api.utils.type_token;
    exports com.pixelatedslice.easyconfig.api.config.section.descriptor;
    exports com.pixelatedslice.easyconfig.api.config.node.descriptor;
    exports com.pixelatedslice.easyconfig.api.builder.config;
    exports com.pixelatedslice.easyconfig.api.builder.descriptor;
    exports com.pixelatedslice.easyconfig.api.config.section.immutable;
    exports com.pixelatedslice.easyconfig.api.config.section.mutable;
    exports com.pixelatedslice.easyconfig.api.config.node.mutable;
    exports com.pixelatedslice.easyconfig.api.config.node.immutable;
}