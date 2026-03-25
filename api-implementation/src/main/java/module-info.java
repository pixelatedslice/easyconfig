module com.pixelatedslice.easyconfig.impl {
    requires com.pixelatedslice.easyconfig.api;
    requires com.google.common;
    requires org.jspecify;

    exports com.pixelatedslice.easyconfig.impl;
    exports com.pixelatedslice.easyconfig.impl.config.node;
    exports com.pixelatedslice.easyconfig.impl.config.section;
    exports com.pixelatedslice.easyconfig.impl.config.file;
}