dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "EasyConfig"
include("config-api", "provider-api", "shared-api")