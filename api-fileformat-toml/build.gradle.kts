version = "1.0.0"

dependencies {
    compileOnly(project(":api-definition"))
    implementation(project(":api-fileformat-jackson-common"))
    implementation(libs.jackson.toml)
}

mavenPublishing {
    coordinates("com.pixelatedslice.easyconfig", "fileformat-toml", version.toString())

    pom {
        name.set("EasyConfig - Core")
        description.set("EasyConfig's official TOML File Format Provider using Jackson")
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()
}