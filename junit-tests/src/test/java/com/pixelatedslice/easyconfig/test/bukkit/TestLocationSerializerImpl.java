package com.pixelatedslice.easyconfig.test.bukkit;

import com.pixelatedslice.easyconfig.api.CopiedEasyConfig;
import com.pixelatedslice.easyconfig.api.EasyConfig;
import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.impl.fileformat.yaml.YamlFileFormatProvider;
import com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit.LocationSerializerImpl;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

public class TestLocationSerializerImpl {
    private final ConfigFile file = ConfigFile.builder().filePath(Path.of("location-test"))
            .node("location",
                    Location.class,
                    (ConfigNodeBuilder<Location> nb) -> nb.value(new Location(null, 1.0, 2.0, 3.0, 4f, 5f))
            ).build();
    private final CopiedEasyConfig ec;

    public TestLocationSerializerImpl() {
        this.ec = EasyConfig.instance().copy();
        this.ec.registerSerializers(LocationSerializerImpl.instance());
    }

    @Test
    void shouldAddNodesToSection() throws IOException {
        System.out.println("shouldAddNodesToSection Test");
        var sectionBuilder = this.file.rootSection().builderForNested("location");

        System.out.println(this.file);

        LocationSerializerImpl
                .instance()
                .serialize(this.file.rootSection().node(Location.class, "location").orElseThrow().value().orElseThrow(),
                        sectionBuilder);

        try (var mut = this.file.mutable()) {
            mut.addSections(sectionBuilder.build());
            mut.removeNodes("location");
        }

        System.out.println(this.file);

        YamlFileFormatProvider.instance().save(this.ec, this.file);
    }

    @Test
    void shouldDeserializeCorrectly() throws IOException {
        System.out.println("shouldDeserializeCorrectly Test");

        YamlFileFormatProvider.instance().load(this.ec, this.file);

        System.out.println(this.file);
    }
}
