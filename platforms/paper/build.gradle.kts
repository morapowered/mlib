plugins {
    id("platform-conventions")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenLocal()
}


dependencies {
    compileOnly(libs.paper.api)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    listOf(
        ":api:platform",
        ":platforms:common",
        ":api:configuration",
        ":api:database:mongo",
        ":api:database:redis",
        ":api:database:sql",
        ":api:util",
    ).forEach {
        api(project(it))
        bundle(project(it)) { isTransitive = false }
    }

    api(libs.reactor.core)
    apiAndBundle(libs.channels.bom) {
        exclude("io.lettuce")
        exclude("com.google.code.gson")
        exclude("org.jetbrains", "annotations")
    }
}

tasks {
    processResources {
        inputs.property("version", project.version.toString())

        filesMatching("plugin.yml") {
            expand("version" to inputs.properties["version"])
        }

    }
}