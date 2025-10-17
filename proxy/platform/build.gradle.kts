plugins {
    id("mlib.kotlin-conventions")
    id("mlib.platform-conventions")
    kotlin("kapt")
}

base {
    archivesName.set("mlib-proxy")
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
    kapt(libs.velocity.api)

    implementation(libs.velocity.language.kotlin)

    implementation(project(":proxy:api:kotlin")) {
        isTransitive = false
    }
    bundle(project(":proxy:api:kotlin")) {
        isTransitive = false
    }
    implementation(project(":proxy:api")) {
        isTransitive = false
    }
    bundle(project(":proxy:api")) {
        isTransitive = false
    }
    implementation(project(":common:kotlin")) {
        exclude("org.spongepowered", "configurate-hocon")
        exclude("org.spongepowered", "configurate-core")
        exclude("org.spongepowered", "configurate-yaml")
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
        exclude("com.google.code.gson")
    }
    bundle(project(":common:kotlin")) {
        exclude("org.spongepowered", "configurate-hocon")
        exclude("org.spongepowered", "configurate-core")
        exclude("org.spongepowered", "configurate-yaml")
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("org.slf4j", "slf4j-api")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
        exclude("io.netty")
        exclude("com.google.code.gson")
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }

    assemble {
        dependsOn(shadowJar)
    }
}