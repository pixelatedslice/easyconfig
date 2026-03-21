dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "EasyConfig"
include("api-definition", "api-implementation", "api-serialization-bukkit:definition")
include("api-serialization-bukkit:implementation")