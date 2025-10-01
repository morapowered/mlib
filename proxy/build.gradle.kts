plugins {
    id("mlib.platform-conventions")
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)

    implementation(libs.velocity.language.kotlin)

    api(project(":shared")) {
        exclude("org.spongepowered", "configurate-hocon")
        exclude("org.spongepowered", "configurate-core")
        exclude("org.spongepowered", "configurate-yaml")
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
    }
    bundle(project(":shared")) {
        exclude("org.spongepowered", "configurate-hocon")
        exclude("org.spongepowered", "configurate-core")
        exclude("org.spongepowered", "configurate-yaml")
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("net.kyori", "adventure-api")
        exclude("net.kyori", "adventure-text-minimessage")
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}