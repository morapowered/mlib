plugins {
    id("mlib.base-conventions")
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    minecraft = rootProject.property("minecraft_version").toString()
    compileOnly()
}

loom {
    silentMojangMappingsLicense()
}


dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft_version")}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.21.1:${rootProject.property("parchment_version")}@zip")
    })
}
