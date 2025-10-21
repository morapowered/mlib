import org.gradle.internal.extensions.core.extra
import java.util.Properties
import kotlin.io.path.inputStream

plugins {
    java
    `java-library`
}

version = rootProject.version

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.toPath().toAbsolutePath().inputStream())
}
project.extra.set("localProperties", localProperties)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://maven.parchmentmc.org")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.pkg.github.com/morapowered/packages") {
        credentials {
            username = localProperties.getProperty("gpr.username") ?: System.getenv("GH_USERNAME")
            password = localProperties.getProperty("gpr.token") ?: System.getenv("GH_TOKEN")
        }
        content {
            includeGroupByRegex("io.github.morapowered")
            includeGroupByRegex("io.github.morapowered.*")
        }
    }
    mavenLocal()
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 21
    }
}