import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;

open module com.pixelatedslice.easyconfig.impl {
    requires com.google.common;
    requires org.jspecify;
    requires com.pixelatedslice.easyconfig.api;
    requires com.google.auto.service;

    exports com.pixelatedslice.easyconfig.impl;
    exports com.pixelatedslice.easyconfig.impl.config.node;
    exports com.pixelatedslice.easyconfig.impl.config.section;
    exports com.pixelatedslice.easyconfig.impl.config.file;

    provides com.pixelatedslice.easyconfig.api.config.file.ConfigFileBuilder
            with com.pixelatedslice.easyconfig.impl.config.file.ConfigFileBuilderImpl;
    provides com.pixelatedslice.easyconfig.api.config.node.ConfigNodeBuilder
            with com.pixelatedslice.easyconfig.impl.config.node.ConfigNodeBuilderImpl;
    provides ConfigSectionBuilder
            with com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionBuilderImpl;
}