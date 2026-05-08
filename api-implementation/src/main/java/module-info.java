import com.pixelatedslice.easyconfig.api.serialization.BuiltInSerializer;

open module com.pixelatedslice.easyconfig.impl {
    uses BuiltInSerializer;
    requires com.google.common;
    requires org.jspecify;
    requires com.pixelatedslice.easyconfig.api;
    requires com.google.auto.service;

    exports com.pixelatedslice.easyconfig.impl.serialization;

    provides com.pixelatedslice.easyconfig.api.serialization.SerializerRegistry
            with com.pixelatedslice.easyconfig.impl.serialization.SerializerRegistryImpl;
}