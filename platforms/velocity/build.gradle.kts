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

    apiAndBundle(project(":api:platform"))
    apiAndBundle(project(":platforms:common"))
    apiAndBundle(project(":api:dependency-manager")) {
        exclude("org.slf4j", "slf4j-api")
    }

    api(project(":api:configuration"))
    bundle(project(":api:configuration")) { isTransitive = false }
    api(project(":api:database:mongo"))
    bundle(project(":api:database:mongo")) { isTransitive = false }
    api(project(":api:database:redis"))
    bundle(project(":api:database:redis")) { isTransitive = false }
    api(project(":api:database:sql"))
    bundle(project(":api:database:sql")) { isTransitive = false }
    api(project(":api:util"))
    bundle(project(":api:util")) { isTransitive = false }

    api(libs.reactor.core)

    apiAndBundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
        exclude("org.jetbrains", "annotations")
    }

}

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

