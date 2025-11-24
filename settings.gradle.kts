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
include(":api:database:redis-bom")
include(":api:database:redis-serializer-gson")
include(":api:database:redis-serializer-configurate")
include(":api:database:sql-api")
include(":api:database:sql-bom")
include(":api:database:sql-driver-based")
include(":api:database:sql-driver-based-serializer-configurate")
include(":api:database:sql-driver-based-serializer-gson")
include(":api:database:sql-file-based")

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