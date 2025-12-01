plugins {
    id("modplatform-conventions")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

repositories {
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.impactdev.net/repository/development/")
    mavenLocal()
}


dependencies {
    neoForge(libs.neoforge)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    apiAndBundle(project(":api:platform"))
    api(project(":api:mod-platform", configuration = "namedElements")) { isTransitive = false }
    bundle(project(":api:mod-platform", configuration = "transformProductionNeoForge")) { isTransitive = false }

    apiAndBundle(project(":platforms:common"))

    apiAndBundle(project(":api:configuration"))
    apiAndBundle(project(":api:database:mongo")) {
        exclude("io.projectreactor")
        exclude("org.spongepowered", "configurate-core")
        exclude("com.google.code.gson")
    }
    apiAndBundle(project(":api:database:redis")) {
        exclude("io.netty")
        exclude("io.projectreactor")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.spongepowered", "configurate-core")
        exclude("com.google.code.gson")
    }
    apiAndBundle(project(":api:database:sql")) {
        exclude("com.google.code.gson")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.spongepowered", "configurate-core")
    }
    apiAndBundle(project(":api:util"))

    apiAndBundle(libs.reactor.core)

    api(project(":api:inventory", configuration = "namedElements")) { isTransitive = false }
    include(project(":api:inventory")) { isTransitive = false }
    modApi(libs.gooeylibs.neoforge)
    modApi(libs.adventure.platform.neoforge)

    apiAndBundle(libs.configurate.core)
    apiAndBundle(libs.configurate.yaml)
    apiAndBundle(libs.configurate.gson) {
        exclude("com.google.code.gson")
    }
    apiAndBundle(libs.configurate.hocon)

    apiAndBundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
        exclude("org.jetbrains", "annotations")
    }
}

tasks {
    shadowJar {
        relocate("com.mysql", "io.github.morapowered.mlib.lib.mysql")
        relocate("org.mariadb", "io.github.morapowered.mlib.lib.mariadb")
        relocate("org.postgresql", "io.github.morapowered.mlib.lib.postgresql")
        relocate("org.sqlite", "io.github.morapowered.mlib.lib.sqlite")
        relocate("org.h2", "io.github.morapowered.mlib.lib.h2")
        relocate("org.h2", "io.github.morapowered.mlib.lib.h2")
        relocate("com.google.protobuf", "io.github.morapowered.mlib.lib.protobuf")
    }
}
