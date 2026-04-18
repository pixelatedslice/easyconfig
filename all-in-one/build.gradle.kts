version = "1.0.0"

dependencies {
    api("com.pixelatedslice.easyconfig:fileformat-toml:1.0.0")
    api("com.pixelatedslice.easyconfig:fileformat-json:1.0.0")
    api("com.pixelatedslice.easyconfig:api:1.0.0")
    api("com.pixelatedslice.easyconfig:fileformat-yaml-1.2:1.0.0")
    api("com.pixelatedslice.easyconfig:core:1.0.0")
}

mavenPublishing {
    coordinates("com.pixelatedslice.easyconfig", "aio", version.toString())

    pom {
        name.set("EasyConfig - All in One")
        description.set("All EasyConfig Modules in one.")
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()
}