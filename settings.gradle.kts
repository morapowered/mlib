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

// Configuration
include(":api:configuration")
// Database
include(":api:database:mongo")
include(":api:database:redis")
include(":api:database:sql")

// Inventory
include(":api:inventory")
//include(":api:inventory:extra:configurate")
//include(":api:inventory:extra:gson")

// Util
include(":api:util")

include(":platforms:common")
include(":platforms:fabric")
include(":platforms:neoforge")
include(":platforms:velocity")