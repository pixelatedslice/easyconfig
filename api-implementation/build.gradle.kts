version = "1.0.0"

dependencies {
    compileOnly(project(":api-definition"))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platformLauncher)
}