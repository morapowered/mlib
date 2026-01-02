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

    listOf(
        ":api:configuration",
        ":api:platform",
        ":api:dependency-manager",
        ":api:loader-utils",
        ":api:database:mongo",
        ":api:database:redis",
        ":api:database:sql",
        ":api:util",
        ":platforms:common"
    ).forEach { coord ->
        api(project(coord)) {
            exclude("org.slf4j", "slf4j-api")
            exclude("com.google.code.gson")
            exclude("io.netty")
            exclude("org.jetbrains", "annotations")
        }
        bundle(project(coord)) {
            isTransitive = false
        }
    }

    listOf(
        ":api:mod-platform",
        ":api:inventory"
    ).forEach { coord ->
        api(project(coord, configuration = "namedElements")) { isTransitive = false }
        bundle(project(coord, configuration = "transformProductionFabric")) { isTransitive = false }
    }

    api(libs.configurate.yaml)
    api(libs.reactor.core)
    modApi(libs.gooeylibs.fabric)
    modApi(libs.adventure.platform.fabric)

    apiAndBundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
        exclude("org.jetbrains", "annotations")
    }
}