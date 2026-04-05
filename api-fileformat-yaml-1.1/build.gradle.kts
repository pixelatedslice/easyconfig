version = "1.0.0"

dependencies {
    compileOnly(project(":api-definition"))
    implementation(project(":api-implementation"))
    implementation(libs.snakeyaml)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platformLauncher)
}