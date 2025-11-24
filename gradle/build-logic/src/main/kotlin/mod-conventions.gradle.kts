plugins {
    id("base-conventions")
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    minecraft = project.property("minecraft_version").toString()
    compileOnly()
}

loom {
    silentMojangMappingsLicense()
}

repositories {
    maven("https://maven.parchmentmc.org")
}

dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft_version")}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${rootProject.property("parchment_version")}@zip")
    })
}

tasks {
    processResources {
        inputs.property("version", project.version.toString())

        filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml")) {
            expand("version" to inputs.properties["version"])
        }
    }
    jar {
        manifest {
            attributes("Fabric-Loom-Remap" to true)
        }
    }
}