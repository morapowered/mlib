plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://maven.architectury.dev/")
    maven("https://maven.fabricmc.net/")
    maven("https://maven.minecraftforge.net/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(libs.loom)
    implementation(libs.architectury)
    implementation(libs.shadow)
    implementation(libs.blossom)
    implementation(libs.versioning)
    implementation(libs.licenser)
}