pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "mlib"

include(":api")
include(":shared")
include(":mod:common")
include(":mod:fabric")
include(":mod:neoforge")
include(":proxy")