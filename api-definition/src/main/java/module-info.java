import com.pixelatedslice.easyconfig.api.config.file.ConfigFileBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNodeIterator;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionIterator;
import com.pixelatedslice.easyconfig.api.descriptor.DescriptorFactory;
import com.pixelatedslice.easyconfig.api.descriptor.config.node.ConfigNodeDescriptorBuilder;

open module com.pixelatedslice.easyconfig.api {
    requires com.google.common;
    requires org.jspecify;

    uses ConfigNodeBuilder;
    uses ConfigFileBuilder;
    uses ConfigSectionBuilder;
    uses DescriptorFactory;
    uses ConfigNodeDescriptorBuilder;
    uses ConfigNodeIterator;
    uses ConfigSectionIterator;

    exports com.pixelatedslice.easyconfig.api;
    exports com.pixelatedslice.easyconfig.api.config.file;
    exports com.pixelatedslice.easyconfig.api.config.node;
    exports com.pixelatedslice.easyconfig.api.config.section;
    exports com.pixelatedslice.easyconfig.api.descriptor;
    exports com.pixelatedslice.easyconfig.api.descriptor.config.node;
    exports com.pixelatedslice.easyconfig.api.descriptor.config.section;
    exports com.pixelatedslice.easyconfig.api.exception;
    exports com.pixelatedslice.easyconfig.api.fileformat;
    exports com.pixelatedslice.easyconfig.api.fileformat.builtin;
    exports com.pixelatedslice.easyconfig.api.serialization;

    exports com.pixelatedslice.easyconfig.api.serialization.builtin
            to com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;
    exports com.pixelatedslice.easyconfig.api.comments;
    exports com.pixelatedslice.easyconfig.api.utils.type_token;
}