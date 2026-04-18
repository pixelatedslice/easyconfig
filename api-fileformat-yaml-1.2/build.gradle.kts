version = "1.0.0"

dependencies {
    compileOnly(project(":api-definition"))
    implementation(project(":api-fileformat-jackson-common"))
    implementation(libs.jackson.yaml)
}

mavenPublishing {
    coordinates("com.pixelatedslice.easyconfig", "fileformat-yaml-1.2", version.toString())

    pom {
        name.set("EasyConfig - Core")
        description.set("EasyConfig's official YAML 1.2 File Format Provider using Jackson")
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()
}