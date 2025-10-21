plugins {
    id("mlib.kotlin-conventions")
    id("mlib.mod-conventions")
    id("mlib.publish-conventions")
}

dependencies {
    modImplementation(libs.fabric.api)
    modApi(libs.fabric.language.kotlin)

    modImplementation(libs.gooeylibs.fabric)
    modImplementation(libs.adventure.platform.fabric)

    implementation(project(":mod:api", configuration = "namedElements")) {
        isTransitive = false
    }

    api(project(":common:kotlin"))  {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
    }

}