dependencies {
    compileOnly(project(":api-definition"))
    api(libs.jackson.core)
}

mavenPublishing {
    coordinates("com.pixelatedslice.easyconfig", "fileformat-common", version.toString())

    pom {
        name.set("EasyConfig - Shared API for all File Formats")
        description.set("EasyConfig's shared API for all File Formats.")
    }
}