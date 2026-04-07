open module com.pixelatedslice.easyconfig.impl.fileformaty.yaml {
    requires com.google.common;
    requires org.jspecify;
    requires org.yaml.snakeyaml;
    requires com.pixelatedslice.easyconfig.api;
    requires com.pixelatedslice.easyconfig.impl;
    requires jdk.compiler;

    exports com.pixelatedslice.easyconfig.impl.fileformat.yaml;
}