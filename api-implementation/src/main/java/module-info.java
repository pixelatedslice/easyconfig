open module com.pixelatedslice.easyconfig.impl {
    uses com.pixelatedslice.easyconfig.api.serialization.builtin.BuiltInSerializer;
    requires com.google.common;
    requires org.jspecify;
    requires com.pixelatedslice.easyconfig.api;
    requires com.google.auto.service;
    requires com.google.errorprone.annotations;
}