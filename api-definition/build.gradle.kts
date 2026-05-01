dependencies {
    api(libs.guava)
}

mavenPublishing {
    coordinates("com.pixelatedslice.easyconfig", "api", version.toString())

    pom {
        name.set("EasyConfig - Core")
        description.set("EasyConfig's API Interfaces only")
    }
}