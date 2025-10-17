plugins {
    java
    `java-library`
}

//group = rootProject.group
version = rootProject.version

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
    mavenLocal()
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://maven.parchmentmc.org")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 21
    }
}