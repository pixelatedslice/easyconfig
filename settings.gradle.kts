dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "EasyConfig"
include(
    "api-definition",
    "api-implementation",
    "api-serialization-bukkit:definition",
    "api-serialization-bukkit:implementation",
    "api-fileformat:yaml:1.1:implementation"
)