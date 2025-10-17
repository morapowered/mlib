pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "mlib"

include(":api", ":api:kotlin")
include(":common", ":common:kotlin")
include(":mod:api", ":mod:api:kotlin", ":mod:platform")
include(":proxy:api", ":proxy:api:kotlin", ":proxy:platform")