version = "1.0.0"

dependencies {
    compileOnly(project(":api-definition"))
    implementation(project(":api-fileformat-jackson-common"))
}

mavenPublishing {
    coordinates("com.pixelatedslice.easyconfig", "fileformat-json", version.toString())

    pom {
        name.set("EasyConfig - Core")
        description.set("EasyConfig's official JSON File Format Provider using Jackson")
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()
}