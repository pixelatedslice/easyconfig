# Easy Config**Note:

** JavaDoc is AI Generated.## Config
File[Open File](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/ConfigFile.java)### Builder
Example (Recommended)```javaimport java.util.List;ConfigFile myConfig = new ConfigFileBuilderImpl()        .node("
location", builder -> { builder.type(Location.class); builder.serializer(LocationSerializer.instance()); // Built-in
serializers are singletons builder.defaultValue(new Location(x, y, z));

        })
        .node("names", builder -> {
            // Example: Map<String, Integer> becomes .type(Map.class, String.class, Integer.class)
            builder.type(new TypeToken<List<String>>() {
            });
        })
        .section("identity", builder -> {
            builder.node("name", String.class);
            builder.node("age", Integer.class);
        })
        .build();

```

### Class Example

```java
import java.nio.file.Path;

class MyConfigFile extends ConfigFileImpl {
    public MyConfigFile() throws IOException, ParseException {
        super(Path.of("my", "config"));
    }

    public MyConfigFile(@NonNull Collection<ConfigNode<?>> childNodes,
            @NonNull Collection<ConfigSection> nestedSections, @NonNull Collection<String> comments) {
        super(Path.of("my", "config"), childNodes, nestedSections, comments);
    }

    Optional<ConfigNode<String>> password() {
        return this.childNode(String.class, "password");
    }
}

void test() {
    var myConfig = EasyConfigImpl
            .instance()
            .currentProvider()
            .orElseThrow()
            .load(Path.of("my", "config"), MyConfigFile.class);

    myConfig.password().ifPresent(System.out::println);
}
```

## Serializers

You can create your own serializers for custom types. The concrete implementations for built-in serializers are located
in the `com.pixelatedslice.easyconfig.impl.serialization.builtin` package and are singletons.

Example Serializer:

```java
package com.pixelatedslice.easyconfig.impl.serialization.builtin.bukkit;

import com.google.common.reflect.TypeToken;
import com.pixelatedslice.easyconfig.api.config.node.ConfigNode;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSection;
import com.pixelatedslice.easyconfig.api.config.section.ConfigSectionBuilder;
import com.pixelatedslice.easyconfig.api.serialization.builtin.bukkit.LocationSerializer;
import com.pixelatedslice.easyconfig.impl.config.section.ConfigSectionBuilderImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jspecify.annotations.NonNull;

public final class LocationSerializerImpl implements LocationSerializer {
    private static volatile LocationSerializerImpl INSTANCE;

    private LocationSerializerImpl() {
    }

    public static LocationSerializerImpl instance() {
        if (INSTANCE == null) {
            synchronized (LocationSerializerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocationSerializerImpl();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public @NonNull ConfigSection serialize(@NonNull Location value) {
        ConfigSectionBuilder sectionBuilder = new ConfigSectionBuilderImpl();

        if (value.getWorld() != null) {
            sectionBuilder.node("world", value.getWorld().getName(), TypeToken.of(String.class));
        }

        sectionBuilder.node("x", value.getX());
        sectionBuilder.node("y", value.getY());
        sectionBuilder.node("z", value.getZ());
        sectionBuilder.node("yaw", value.getYaw());
        sectionBuilder.node("pitch", value.getPitch());

        return sectionBuilder.build();
    }

    @Override
    public @NonNull Location deserialize(@NonNull ConfigSection section) {
        var world = section
                .childNode(String.class, "world")
                .flatMap(ConfigNode::value)
                .map(Bukkit::getWorld)
                .orElse(null);
        var x = section.childNode(Double.class, "x")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var y = section.childNode(Double.class, "y")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var z = section.childNode(Double.class, "z")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var yaw = section.childNode(Float.class, "yaw")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var pitch = section.childNode(Float.class, "pitch")
                .flatMap(ConfigNode::value)
                .orElseThrow();

        return new Location(
                world,
                x, y, z,
                yaw, pitch
        );
    }
}

```

## Config Sections

Example YAML File:

```yaml
tln: "top level" # This is a ConfigNode<String>
location: # This is a ConfigSection
  x: 0.0 # ConfigNode<Double>
  y: 0.0 # ConfigNode<Double>
  z: 14.2 # ConfigNode<Double>
names: # ConfigNode<List<String>>
  - "Jeremy"
  - "John"
  - "Angela"
```