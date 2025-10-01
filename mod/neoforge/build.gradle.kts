plugins {
    id("mlib.modplatform-conventions")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}


loom {
    neoForge {

    }
}

dependencies {
    neoForge(libs.neoforge)
    implementation(libs.neo.kotlin.forge)

    implementation(project(":mod:common", configuration = "namedElements"))  {
        isTransitive = false
    }
    bundle(project(":mod:common", configuration = "namedElements"))  {
        isTransitive = false
    }

    modApi(libs.gooeylibs.neoforge)
    modApi(libs.adventurePlatformModShared)
}

tasks {
    processResources {
        inputs.property("version", rootProject.version)
        inputs.property("minecraft_version", rootProject.property("minecraft_version").toString())
        inputs.property("java_version", rootProject.property("java_version").toString())

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(
                "version" to rootProject.version,
                "minecraft_version" to rootProject.property("minecraft_version").toString(),
                "java_version" to rootProject.property("java_version").toString()
            )
        }
    }

}