# Easy Config

<details>
<summary>AI Usage</summary>

AI is used when I am unfamiliar with a topic so that I can get basic knowledge and AI is used for Javadoc generation.
</details>

Easy Config is a Java library designed to bridge the gap between your code and configuration files. By utilizing Guava's
TypeToken, it ensures that every piece of data you load is exactly the type you expect, even for complex generic
structures like List<Map<String, Integer>>.

<details>
<summary>Licenses used</summary>
- Apache-2.0:
    - api-definition
    - api-implementation
    - api-fileformat-jackson-common
    - api-fileformat-json
    - api-fileformat-toml
    - api-fileformat-yaml-1.2

- GPLv3:
    - api-serialization-bukkit (*The Bukkit/Spigot API is GPLv3 licensed.*)
</details>

## Why use it?

- Leveraging Guava's TypeToken for True Type-Safety.
- Out-of-the-box support for YAML, JSON, TOML, and HOCON.
- Fluent builder-based API that lets you define your configuration structure directly in code.
- Extensible-first design to allow custom serializers, new file formats, or even a completely new backend.

## Quick Start

Add EasyConfig to your
project, [see Maven Central](https://central.sonatype.com/namespace/com.pixelatedslice.easyconfig).

# Usage & Examples

## Config File

[Open File](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/file/ConfigFile.java)
[Open Implementation File](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/file/ConfigFileImpl.java)

### Usage Examples

<details>
<summary>Creating a Config File</summary>

[Also see ConfigFileBuilder.java](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/file/ConfigFileBuilder.java)
[Also see ConfigFileBuilderImpl.java](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/file/ConfigFileBuilderImpl.java)
[Also see ConfigSectionBuilderImpl.java](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/section/ConfigSectionBuilderImpl.java)
[Also see ConfigNodeBuilderImpl.java](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/node/ConfigNodeBuilderImpl.java)

```java
import java.util.List;

ConfigFile myConfig = new ConfigFile.builder()
        .node(" location", builder -> {
            builder.type(Location.class);
            builder.serializer(LocationSerializer.instance()); // Built-in serializers are singletons
            builder.defaultValue(new Location(x, y, z));
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

</details>

## Config Sections

[Open File](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/section/ConfigSection.java)
[Open Implementation File](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/section/ConfigSectionImpl.java)

<details>
<summary>Access an section</summary>

```java
ConfigSection section = myConfig.section("my", "cool", "section");
```

</details>

<details>
<summary>Modify an section</summary>

[Also see MutableConfigSection](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/section/MutableConfigSection.java)
[Also see MutableConfigSectionImpl](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/section/MutableConfigSectionImpl.java)

```java
try(MutableConfigSection mutable = section.mutable()){ // Implements AutoClosable, recommended usage
        mutable.addNode(myNode);
}

// Alternate way
var mutable = section.mutable();
mutable.addNode(myNode);
mutable.close();
```

</details>

## Config Nodes

[Open File](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/node/ConfigNode.java)
[Open Implementation File](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/node/ConfigNodeImpl.java)

<details>
<summary>Access an node</summary>

```java
ConfigNode node = myConfig.section("my", "cool", "section").<String>node("node");
```

</details>

<details>
<summary>Modify an node</summary>

[Also see MutableConfigNode](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/node/MutableConfigNode.java)
[Also see MutableConfigNodeImpl](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/node/MutableConfigNodeImpl.java)

```java
try(MutableConfigNode mutable = node.mutable()){ // Implements AutoClosable, recommended usage
        mutable.

setValue("Good day");
}

// Alternate way
var mutable = node.mutable();
mutable.

setValue("Good day");
mutable.

close(); // recommended since otherwise the IDE will warn you, could also call .apply
```

</details>

## Serializers

You can create your own serializers for custom types. The concrete implementations for built-in serializers are located
in the `com.pixelatedslice.easyconfig.impl.serialization.builtin` package and are singletons.

### Example

[Open File](./api-serialization-bukkit/src/main/java/com/pixelatedslice/easyconfig/impl/serialization/builtin/bukkit/LocationSerializerImpl.java)

```java
public final class LocationSerializerImpl implements BuiltInBukkitSerializer<Location> {
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
    public void serialize(@Nullable Location value, @NonNull ConfigSectionBuilder sectionBuilder) {
        if (value == null) {
            return;
        }

        if (value.getWorld() != null) {
            sectionBuilder.node("world", value.getWorld().getName(), TypeToken.of(String.class));
        }
        sectionBuilder.node("x", value.getX());
        sectionBuilder.node("y", value.getY());
        sectionBuilder.node("z", value.getZ());
        sectionBuilder.node("yaw", value.getYaw());
        sectionBuilder.node("pitch", value.getPitch());
    }

    @Override
    @NonNull
    public Location deserialize(@NonNull ConfigSection section) {
        var world = section
                .node(TypeToken.of(String.class), "world")
                .flatMap(ConfigNode::value)
                .map(Bukkit::getWorld)
                .orElse(null);
        var x = section.node(TypeToken.of(Double.class), "x").flatMap(ConfigNode::value).orElseThrow();
        var y = section.node(TypeToken.of(Double.class), "y").flatMap(ConfigNode::value).orElseThrow();
        var z = section.node(TypeToken.of(Double.class), "z").flatMap(ConfigNode::value).orElseThrow();
        var yaw = section.node(TypeToken.of(Float.class), "yaw")
                .flatMap(ConfigNode::value)
                .orElseThrow();
        var pitch = section.node(TypeToken.of(Float.class), "pitch")
                .flatMap(ConfigNode::value)
                .orElseThrow();

        return new Location(
                world,
                x, y, z,
                yaw, pitch
        );
    }

    @Override
    @NonNull
    public Class<Location> forClass() {
        return Location.class;
    }
}
```