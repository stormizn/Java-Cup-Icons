plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.20.1"
}

group = "com.stormizn"
version = "1.0.2"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

val platformLocalPath = providers.gradleProperty("platformLocalPath")
    .orElse("C:/Users/Ayush/AppData/Local/Programs/IntelliJ IDEA Ultimate")

dependencies {
    intellijPlatform {
        local(platformLocalPath.get())
        bundledPlugin("com.intellij.java")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = false

    pluginConfiguration {
        ideaVersion {
            sinceBuild = "263"
            untilBuild = "263.*"
        }
    }

    publishing {
        // Set PUBLISH_TOKEN in the environment before running :publishPlugin.
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }
}

tasks {
    buildPlugin {
        archiveBaseName = "java-cup-icons"
        archiveVersion = project.version.toString()
    }
}
