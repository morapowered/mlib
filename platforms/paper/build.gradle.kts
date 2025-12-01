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

    apiAndBundle(project(":api:platform")) { isTransitive = false }
    apiAndBundle(project(":platforms:common")) { isTransitive = false }
    apiAndBundle(project(":api:configuration")) { isTransitive = false }
    apiAndBundle(project(":api:database:mongo")) { isTransitive = false }
    apiAndBundle(project(":api:database:redis")) { isTransitive = false }
    apiAndBundle(project(":api:database:sql")) { isTransitive = false }
    apiAndBundle(project(":api:util")) { isTransitive = false }

    api(libs.reactor.core)
    apiAndBundle(libs.channels.bom)  {
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