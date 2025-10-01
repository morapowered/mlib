plugins {
    id("mlib.modplatform-conventions")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

dependencies {
    modImplementation(libs.fabricLoader)
    modApi(libs.fabricApi)

    modImplementation(libs.fabricLanguageKotlin)

    api(project(":mod:common", configuration = "namedElements")) {
        isTransitive = false
    }
    bundle(project(":mod:common", configuration = "namedElements")) {
        isTransitive = false
    }

    api(project(":shared")) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
    }
    bundle(project(":shared")) {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
    }
    modApi(libs.gooeylibs.fabric)
    modApi(libs.adventurePlatformModShared)
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to inputs.properties["version"])
        }
    }
}
