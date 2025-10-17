plugins {
    id("mlib.mod-conventions")
    id("mlib.publish-conventions")
}

subprojects {
    group = "io.github.morapowered.mlib.mod.api"
}

dependencies {
    modApi(libs.fabric.api)

    modApi(libs.gooeylibs.fabric)
    modApi(libs.adventure.platform.fabric)

    api(project(":common"))  {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
    }
}