plugins {
    id("modcomponent-conventions")
}

architectury {
    common("fabric", "neoforge")
}

repositories {
    maven("https://maven.impactdev.net/repository/development/")
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // For Mixin/Mixin Extra!
    modCompileOnly(libs.fabric.loader)

    api(libs.gooeylibs.api)
    api(libs.adventure.platform.mod.shared)
    api(libs.configurate.core)
}