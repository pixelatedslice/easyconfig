plugins {
    `java-library`
    id("com.vanniktech.maven.publish") version "0.36.0" apply false
}

subprojects {
    if (name == "api-bom") {
        pluginManager.apply("java-platform")
    } else {
        pluginManager.apply("java-library")
        applyDeps()
    }

    pluginManager.apply("com.vanniktech.maven.publish")
    pluginManager.apply("signing")

    group = "com.pixelatedslice.easyconfig"
    version = "2.0.0"

    repositories {
        mavenCentral()
    }

    configure<com.vanniktech.maven.publish.MavenPublishBaseExtension> {
        pom {
            inceptionYear.set("2026")
            url.set("https://github.com/pixelatedslice/easyconfig/")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("pixelatedslice")
                    name.set("pixelatedslice")
                    url.set("https://github.com/pixelatedslice/")
                }
            }
            scm {
                url.set("https://github.com/pixelatedslice/easyconfig/")
                connection.set("scm:git:git://github.com/pixelatedslice/easyconfig.git")
                developerConnection.set("scm:git:ssh://git@github.com/pixelatedslice/easyconfig.git")
            }
        }

        publishToMavenCentral(true)
        signAllPublications()
    }

    configure<SigningExtension> {
        useGpgCmd()
    }
}

fun Project.applyDeps() {
    dependencies {
        compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
        annotationProcessor("com.google.auto.service:auto-service:1.1.1")
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.test {
        useJUnitPlatform()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}