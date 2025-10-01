plugins {
    id("mlib.mod-conventions")
}

architectury {
    common("neoforge", "fabric")
}

dependencies {
    api(project(":shared"))
    modApi(libs.adventurePlatformModShared)
    modApi(libs.gooeylibs.api)
}
