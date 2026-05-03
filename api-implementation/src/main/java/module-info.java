import com.pixelatedslice.easyconfig.api.old.EasyConfig;
import com.pixelatedslice.easyconfig.api.old.config.file.ConfigFileBuilder;
import com.pixelatedslice.easyconfig.api.old.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.old.config.section.builder.ConfigSectionBuilder;

open module com.pixelatedslice.easyconfig.impl {
    requires com.google.common;
    requires org.jspecify;
    requires com.pixelatedslice.easyconfig.api;
    requires com.google.auto.service;

    exports com.pixelatedslice.easyconfig.impl;
    exports com.pixelatedslice.easyconfig.impl.config.node;
    exports com.pixelatedslice.easyconfig.impl.config.section;
    exports com.pixelatedslice.easyconfig.impl.config.file;
    exports com.pixelatedslice.easyconfig.impl.config.node.builder;

    provides EasyConfig
            with com.pixelatedslice.easyconfig.impl.EasyConfigImpl;
    provides ConfigFileBuilder
            with com.pixelatedslice.easyconfig.impl.config.file.ConfigFileBuilderImpl;
    provides ConfigNodeBuilder
            with com.pixelatedslice.easyconfig.impl.config.node.builder.ConfigNodeBuilderImpl;
    provides ConfigSectionBuilder
            with com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionBuilderImpl;
}