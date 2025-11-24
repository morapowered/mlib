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

    api(project(":platforms:common"))
    bundle(project(":platforms:common"))

    api(project(":api:configuration"))
    bundle(project(":api:configuration"))
    api(project(":api:database:mongo"))
    bundle(project(":api:database:mongo"))
    api(project(":api:database:redis"))  {
        exclude("io.netty")
        exclude("io.projectreactor")
        exclude("org.slf4j", "slf4j-api")
    }
    bundle(project(":api:database:redis")) {
        exclude("io.netty")
        exclude("io.projectreactor")
        exclude("org.slf4j", "slf4j-api")
    }
    api(project(":api:database:sql:bom")) {
        exclude("com.google.code.gson")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.spongepowered", "configurate-core")

    }
    bundle(project(":api:database:sql:bom")) {
        exclude("com.google.code.gson")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.spongepowered", "configurate-core")

    }
    api(project(":api:util"))
    bundle(project(":api:util"))

    api(libs.reactor.core)
    bundle(libs.reactor.core)

    api(project(":api:inventory", configuration = "namedElements")) { isTransitive = false }
    include(project(":api:inventory")) { isTransitive = false }
    modApi(libs.gooeylibs.api)
    modApi(libs.adventure.platform.mod.shared)

    api(libs.configurate.core)
    bundle(libs.configurate.core)
    api(libs.configurate.yaml)
    bundle(libs.configurate.core)
    api(libs.configurate.gson) {
        exclude("com.google.code.gson")
    }
    bundle(libs.configurate.core) {
        exclude("com.google.code.gson")
    }
    api(libs.configurate.hocon)
    bundle(libs.configurate.core)

    api(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
    }
    bundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
    }
}