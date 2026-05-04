dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "EasyConfig"
include(
    "api-serialization-bukkit",
    "api-fileformat-yaml-1.2",
    "api-fileformat-json",
    "api-fileformat-toml",
    "api-definition",
    "api-implementation",
    "api-fileformat-jackson-common",
)
include("api-bom")