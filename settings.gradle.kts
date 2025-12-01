rootProject.name = "mlib"

pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.architectury.dev/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

// Misc
include(":api:platform")
include(":api:mod-platform")
include(":api:loader-utils")
include(":api:dependency-manager")
// Configuration
include(":api:configuration")
// Database
include(":api:database:mongo")
include(":api:database:redis")
include(":api:database:sql")
// Inventory
include(":api:inventory")
// Util
include(":api:util")

include(":platforms:common")
include(":platforms:fabric")
//include(":platforms:neoforge")  // disabled because dependency manager (needs maintenance)
include(":platforms:paper")
include(":platforms:velocity")