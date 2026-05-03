import com.pixelatedslice.easyconfig.api.old.config.section.builder.ConfigSectionBuilder;

module com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit {
    uses ConfigSectionBuilder;
    requires org.bukkit;
    requires com.pixelatedslice.easyconfig.api;
    requires org.jspecify;
    requires com.google.common;

    exports com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;
}