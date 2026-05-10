import java.nio.file.Files
import java.nio.file.StandardCopyOption

plugins {
    `java-library`
    jacoco
    id("com.vanniktech.maven.publish") version "0.36.0" apply false
}

subprojects {
    apply(plugin = "jacoco")
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

    tasks.register("mose-jacoco-style") {
        description = "Gives jacoco a darker theme"
        doLast {
            val to = project.file("build/reports/jacoco/test/html/jacoco-resources/report.css")
            val from = project.rootProject.file("gradleBuild/report.css")

            if (!to.exists()) {
                return@doLast;
            }
            Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING)
            System.out.println("Copied " + from.absolutePath + " to " + to.absolutePath)
        }
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
        finalizedBy(tasks.jacocoTestReport)

    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}