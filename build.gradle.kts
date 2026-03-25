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
    }

    tasks.test {
        useJUnitPlatform()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}