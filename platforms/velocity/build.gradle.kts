plugins {
    id("platform-conventions")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenLocal()
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    apiAndBundle(project(":platforms:common"))

    apiAndBundle(project(":api:configuration")) {
        exclude("org.spongepowered", "configurate-core")
    }
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

