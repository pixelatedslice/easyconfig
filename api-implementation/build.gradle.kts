version = "1.0.0"

dependencies {
    api(project(":api-definition"))
}

mavenPublishing {
    coordinates("com.pixelatedslice.easyconfig", "core", version.toString())

    pom {
        name.set("EasyConfig - Core")
        description.set("EasyConfig's API and concrete implementation.")
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()
}