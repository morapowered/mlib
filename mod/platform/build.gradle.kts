plugins {
    id("mlib.modplatform-conventions")
    id("mlib.kotlin-conventions")
}

base {
    archivesName.set("mlib-fabric")
}

dependencies {
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.language.kotlin)

    modImplementation(libs.adventure.platform.fabric)
    include(libs.adventure.platform.fabric)

    implementation(project(":mod:api", configuration = "namedElements")) {
        isTransitive = false
    }
    bundle(project(":mod:api", configuration = "namedElements")) {
        isTransitive = false
    }

    implementation(project(":mod:api:kotlin", configuration = "namedElements")) {
        isTransitive = false
    }
    bundle(project(":mod:api:kotlin", configuration = "namedElements")) {
        isTransitive = false
    }

    implementation(project(":common:kotlin"))  {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
    }

    bundle(project(":common:kotlin"))  {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to inputs.properties["version"])
        }
    }
}