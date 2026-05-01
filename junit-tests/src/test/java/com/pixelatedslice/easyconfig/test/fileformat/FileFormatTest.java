package com.pixelatedslice.easyconfig.test.fileformat;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.CopiedEasyConfig;
import com.pixelatedslice.easyconfig.api.builder.config.ConfigNodeChildrenStep;
import com.pixelatedslice.easyconfig.api.config.file.ConfigFile;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.node.builder.ConfigNodeBuilder;
import com.pixelatedslice.easyconfig.api.format.FileFormatProvider;
import com.pixelatedslice.easyconfig.impl.EasyConfigImpl;
import com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit.LocationSerializerImpl;

import java.nio.file.Path;
import java.util.List;

public abstract class FileFormatTest {
    protected final FileFormatProvider<?> fileFormatProvider;
    protected final CopiedEasyConfig easyConfig = EasyConfigImpl.instance().copy();

    protected final ConfigFile file = ConfigFile.builder().filePath(Path.of("test"))
            .node("names",
                    new TypeToken<>() {
                    },
                    (ConfigNodeBuilder<List<String>> b) -> b.defaultValue(List.of(
                            "Josh Robertson",
                            "Stefan Mark",
                            "Kevin James"
                    )))
            .section("database", (ConfigNodeChildrenStep b) -> {
                b.node("username", String.class, (ConfigNodeBuilder<String> nb) -> nb.defaultValue("admin"));
                b.node("password", String.class, (ConfigNodeBuilder<String> nb) -> nb.defaultValue("admin"));
                b.node("port", Integer.class, (ConfigNodeBuilder<Integer> nb) -> nb.defaultValue(3636));
                b.node("url", String.class, (ConfigNodeBuilder<String> nb) -> nb.defaultValue("host"));
            })
            .build();

    protected final Path pathWithExtension;

    protected FileFormatTest(FileFormatProvider<?> fileFormatProvider) {
        this.fileFormatProvider = fileFormatProvider;
        this.pathWithExtension = this.fileFormatProvider
                .formatInstance().pathWithExtension(this.file.filePathWithoutExtension());
        this.easyConfig.registerSerializers(LocationSerializerImpl.instance());
    }

    protected void outputAllFields() {
        var root = this.file.rootSection();

        root.<List<String>>node(new TypeToken<>() {
        }, "names").ifPresent((ConfigNode<List<String>> n) -> System.out.println(n.valueOrDefault()));
        root.node(String.class, "database", "username")
                .ifPresent((ConfigNode<String> n) -> System.out.println(n.valueOrDefault()));
        root.node(String.class, "database", "password")
                .ifPresent((ConfigNode<String> n) -> System.out.println(n.valueOrDefault()));
        root.node(Integer.class, "database", "port")
                .ifPresent((ConfigNode<Integer> n) -> System.out.println(n.valueOrDefault()));
        root.node(String.class, "database", "url")
                .ifPresent((ConfigNode<String> n) -> System.out.println(n.valueOrDefault()));
    }
}
