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

    api(project(":platforms:common"))
    bundle(project(":platforms:common"))

    api(project(":api:configuration")) {
        exclude("org.spongepowered", "configurate-core")
    }
    bundle(project(":api:configuration")) {
        exclude("org.spongepowered", "configurate-core")
    }
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

    api(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
    }
    bundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
    }
}
