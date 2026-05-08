# Easy Config

> [!IMPORTANT]
> There currently is no Javadoc for any methods or classes.

<details>
<summary>AI Usage</summary>

AI is used when I am unfamiliar with a topic so that I can get basic knowledge and AI is used for Javadoc generation.
</details>

Easy Config is a Java library designed to bridge the gap between your code and configuration files. By utilizing Guava's
TypeToken, it ensures that every piece of data you load is exactly the type you expect, even for complex generic
structures like List<Map<String, Integer>>.

**Note**: Comments are implemented on Nodes and Sections, but Jackson doesn't allow writing comments to files.

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
- Out-of-the-box support for YAML, JSON and TOML.
- Fluent builder-based API that lets you define your configuration structure directly in code.
- Extensible-first design to allow custom serializers, new file formats, or even a completely new backend.

## Quick Start

> [!IMPORTANT]
> The [AIO Package](https://central.sonatype.com/artifact/com.pixelatedslice.easyconfig/aio) is now deprecated! <br />
> Use the [BOM](https://central.sonatype.com/artifact/com.pixelatedslice.easyconfig/bom) instead!
 
Add EasyConfig to your
project, [see Maven Central](https://central.sonatype.com/namespace/com.pixelatedslice.easyconfig).

# Usage & Examples

## Config File

[Open File](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/config/ConfigFile.java) <br />
[Open Implementation File](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/file/ConfigFileImpl.java)

### Usage Examples

<details>
<summary>Creating a Config File</summary>

[Also see ConfigFileBuilder.java](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/config/ConfigFileBuilder.java) <br />
[Also see ConfigFileBuilderImpl.java](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/file/ConfigFileBuilderImpl.java) <br />
[Also see ConfigSectionBuilderImpl.java](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/section/ConfigSectionBuilderImpl.java) <br />
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
        .env("node_and_env_key", int.class)
        .env("node_key", "separate_env_key", String.class)
        .section("identity", builder -> {
            builder.node("name", String.class);
            builder.node("age", Integer.class);
        })
        .build();
```

</details>

## Config Sections

[Open File](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/section/ConfigSection.java) <br />
[Open Implementation File](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/section/ConfigSectionImpl.java)

<details>
<summary>Access an section</summary>

```java
ConfigSection section = myConfig.section("my", "cool", "section");
```

</details>

<details>
<summary>Modify an section</summary>

[Also see MutableConfigSection](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/section/MutableConfigSection.java) <br />
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

[Open File](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/node/ConfigNode.java) <br />
[Open Implementation File](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/node/ConfigNodeImpl.java)

<details>
<summary>Access an node</summary>

```java
ConfigNode node = myConfig.section("my", "cool", "section").<String>node("node");
```

</details>

<details>
<summary>Modify an node</summary>

[Also see MutableConfigNode](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/config/node/MutableConfigNode.java) <br />
[Also see MutableConfigNodeImpl](./api-implementation/src/main/java/com/pixelatedslice/easyconfig/impl/config/node/MutableConfigNodeImpl.java)

```java
try(MutableConfigNode mutable = node.mutable()){ // Implements AutoClosable, recommended usage
        mutable.setValue("Good day");
}

// Alternate way
var mutable = node.mutable();
mutable.setValue("Good day");
mutable.close();
```

</details>

## Formats & Format Providers
[Also see Format](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/format/Format.java)
[Also see FormatProvider](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/format/FormatProvider.java)
[Also see FileFormatProvider](./api-definition/src/main/java/com/pixelatedslice/easyconfig/api/format/FileFormatProvider.java)

<details>
<summary>Write a file to disk</summary>

```java
CopiedEasyConfig easyConfig = EasyConfig.instance().copy();
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

easyConfig.provider(YamlFileFormat.class).save(easyConfig, myConfig);
```

</details>

<details>
<summary>Read a file from disk</summary>

```java
CopiedEasyConfig easyConfig = EasyConfig.instance().copy();
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

easyConfig.provider(YamlFileFormat.class).load(easyConfig, myConfig); // This populates the myConfig variable.
```

</details>

## Serializers

You can create your own serializers for custom types. The concrete implementations for built-in serializers are located
in the `com.pixelatedslice.easyconfig.impl.serialization.builtin` package and are singletons.

### Example

[Open File](./api-serialization-bukkit/src/main/java/com/pixelatedslice/easyconfig/impl/serialization/builtin/bukkit/LocationSerializerImpl.java)

```java
public final class LocationSerializerImpl implements BuiltInBukkitSerializer<Location> {
  private static final TypeToken<Location> typeToken = new TypeToken<Location>() {
  };

  private LocationSerializerImpl() {
  }

  public static LocationSerializerImpl instance() {
    return LocationSerializerImplHolder.INSTANCE;
  }

  @Override
  @NonNull
  public TypeToken<Location> forType() {
    return typeToken;
  }

  @Override
  public void serialize(@Nullable Location value, @NonNull ConfigSectionBuilder sectionBuilder) {
    Objects.requireNonNull(sectionBuilder);

    sectionBuilder.node(
            "world",
            ((value != null) && (value.getWorld() != null)) ? value.getWorld().getName() : null,
            String.class
    );
    sectionBuilder.node("x", (value != null) ? value.getX() : null, Double.class);
    sectionBuilder.node("y", (value != null) ? value.getY() : null, Double.class);
    sectionBuilder.node("z", (value != null) ? value.getZ() : null, Double.class);
    sectionBuilder.node("yaw", (value != null) ? value.getYaw() : null, Float.class);
    sectionBuilder.node("pitch", (value != null) ? value.getPitch() : null, Float.class);
  }

  @Override
  @NonNull
  public Location deserialize(@NonNull ConfigSection section) {
    Objects.requireNonNull(section);

    var world = section
            .node(String.class, "world")
            .flatMap(ConfigNode::value)
            .map(Bukkit::getWorld)
            .orElse(null);
    var x = section.node(Double.class, "x").flatMap(ConfigNode::value).orElseThrow();
    var y = section.node(Double.class, "y").flatMap(ConfigNode::value).orElseThrow();
    var z = section.node(Double.class, "z").flatMap(ConfigNode::value).orElseThrow();
    var yaw = section.node(Float.class, "yaw")
            .flatMap(ConfigNode::value)
            .orElseThrow();
    var pitch = section.node(Float.class, "pitch")
            .flatMap(ConfigNode::value)
            .orElseThrow();

    return new Location(
            world,
            x, y, z,
            yaw, pitch
    );
  }

  private static final class LocationSerializerImplHolder {
    private static final LocationSerializerImpl INSTANCE = new LocationSerializerImpl();

    private LocationSerializerImplHolder() {
    }
  }
}
```