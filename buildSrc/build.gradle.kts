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
    implementation(libs.kotlin)
    implementation(libs.loom)
    implementation(libs.architectury)
    implementation(libs.shadow)
    implementation(libs.blossom)
    implementation(libs.licenser)
    implementation(libs.versioning)
}