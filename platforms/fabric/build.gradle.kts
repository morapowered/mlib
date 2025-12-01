plugins {
    id("modplatform-conventions")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

repositories {
    maven("https://maven.impactdev.net/repository/development/")
    mavenLocal()
}


dependencies {
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    apiAndBundle(project(":api:dependency-manager")) {
        exclude("org.slf4j", "slf4j-api")
    }
    api(project(":api:platform"))
    bundle(project(":api:platform")) { isTransitive = false }
    api(project(":platforms:common"))
    bundle(project(":platforms:common")) { isTransitive = false }
    api(project(":api:mod-platform", configuration = "namedElements")) { isTransitive = false }
    bundle(project(":api:mod-platform", configuration = "transformProductionFabric")) { isTransitive = false }



    api(project(":api:configuration"))
    api(project(":api:configuration")) { isTransitive = false }
    api(project(":api:database:mongo"))
    api(project(":api:database:mongo")) { isTransitive = false }
    api(project(":api:database:redis"))
    api(project(":api:database:redis")) { isTransitive = false }
    api(project(":api:database:sql"))
    api(project(":api:database:sql")) { isTransitive = false }
    api(project(":api:util"))
    bundle(project(":api:util")) { isTransitive = false }

    api(libs.reactor.core)

    api(project(":api:inventory", configuration = "namedElements")) { isTransitive = false }
    include(project(":api:inventory")) { isTransitive = false }
    modApi(libs.gooeylibs.fabric)
    modApi(libs.adventure.platform.fabric)

    api(libs.configurate.core)
    api(libs.configurate.yaml)
    api(libs.configurate.gson)
    api(libs.configurate.hocon)

    apiAndBundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
        exclude("org.jetbrains", "annotations")
    }
}
//
//tasks {
//    shadowJar {
//        relocate("com.mysql", "io.github.morapowered.mlib.lib.mysql")
//        relocate("org.mariadb", "io.github.morapowered.mlib.lib.mariadb")
//        relocate("org.postgresql", "io.github.morapowered.mlib.lib.postgresql")
//        relocate("org.sqlite", "io.github.morapowered.mlib.lib.sqlite")
//        relocate("org.h2", "io.github.morapowered.mlib.lib.h2")
//        relocate("org.h2", "io.github.morapowered.mlib.lib.h2")
//        relocate("com.google.protobuf", "io.github.morapowered.mlib.lib.protobuf")
//    }
//}
