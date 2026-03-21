module com.pixelatedslice.easyconfig.impl {
    requires com.pixelatedslice.easyconfig.api;
    requires org.jetbrains.annotations;

    exports com.pixelatedslice.easyconfig.impl;
    exports com.pixelatedslice.easyconfig.impl.config;
    exports com.pixelatedslice.easyconfig.impl.serialization;
}