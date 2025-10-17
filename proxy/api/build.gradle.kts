plugins {
    id("mlib.base-conventions")
    id("mlib.publish-conventions")
}

subprojects {
    group = "io.github.morapowered.mlib.proxy"
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)

    api(project(":common")) {
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
}