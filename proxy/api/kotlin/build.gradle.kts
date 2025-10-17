plugins {
    id("mlib.kotlin-conventions")
    id("mlib.publish-conventions")
}

dependencies {
    api(project(":common:kotlin")) {
        exclude("org.spongepowered", "configurate-hocon")
        exclude("org.spongepowered", "configurate-core")
        exclude("org.spongepowered", "configurate-yaml")
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
        exclude("com.google.code.gson")
    }
    api(project(":proxy:api")) {
        isTransitive = false
    }
    api(libs.velocity.language.kotlin)
}