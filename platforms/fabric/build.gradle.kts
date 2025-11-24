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

    api(project(":platforms:common"))
    bundle(project(":platforms:common"))

    api(project(":api:configuration"))
    bundle(project(":api:configuration"))
    api(project(":api:database:mongo"))
    bundle(project(":api:database:mongo"))
    api(project(":api:database:redis-bom")) {
        exclude("io.netty")
        exclude("io.projectreactor")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.spongepowered", "configurate-core")
        exclude("com.google.code.gson")
    }
    bundle(project(":api:database:redis-bom")) {
        exclude("io.netty")
        exclude("io.projectreactor")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.spongepowered", "configurate-core")
        exclude("com.google.code.gson")
    }
    api(project(":api:database:sql-bom")) {
        exclude("com.google.code.gson")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.spongepowered", "configurate-core")
    }
    bundle(project(":api:database:sql-bom")) {
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
    modApi(libs.gooeylibs.fabric)
    modApi(libs.adventure.platform.fabric)

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
        exclude("org.jetbrains", "annotations")
    }
    bundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
        exclude("org.jetbrains", "annotations")
    }

    implementation(libs.mariadb.java.client)
    bundle(libs.mariadb.java.client)
    implementation(libs.mysql.connector.j)
    bundle(libs.mysql.connector.j)
    implementation(libs.postgresql)
    bundle(libs.postgresql)
    implementation(libs.sqlite)
    bundle(libs.sqlite)
    implementation(libs.h2)
    bundle(libs.h2)
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
