plugins {
    id("mlib.base-conventions")
    id("fabric-loom")
}



dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft_version")}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.21.1:${rootProject.property("parchment_version")}@zip")
    })
}
