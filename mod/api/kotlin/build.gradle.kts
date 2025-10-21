plugins {
    id("mlib.kotlin-conventions")
    id("mlib.mod-conventions")
    id("mlib.publish-conventions")
}

dependencies {
    modApi(libs.fabric.api)
    modApi(libs.fabric.language.kotlin)

    modImplementation(libs.fabric.loader)

    modApi(libs.gooeylibs.fabric)
    modApi(libs.adventure.platform.fabric)

    api(project(":mod:api", configuration = "namedElements"))

    api(project(":common:kotlin"))  {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
    }
}