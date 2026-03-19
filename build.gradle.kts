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
        compileOnly("org.jetbrains:annotations:26.1.0")
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