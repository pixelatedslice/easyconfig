dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "EasyConfig"
include("api-serialization-bukkit", "api-fileformat-yaml-1.1", "api-definition", "api-implementation")