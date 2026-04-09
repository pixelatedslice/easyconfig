plugins {
    `java-library`
}

subprojects {
    apply(plugin = "java-library")

    group = "com.pixelatedslice.easyconfig"

    repositories {
        mavenCentral()
    }


    dependencies {
        compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
        annotationProcessor("com.google.auto.service:auto-service:1.1.1")
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