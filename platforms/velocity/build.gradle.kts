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

    api(libs.reactor.core)
    apiAndBundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
        exclude("org.jetbrains", "annotations")
    }

}

