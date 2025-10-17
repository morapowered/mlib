plugins {
    id("fabric-loom")
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            artifact(tasks.remapJar)
            artifact(tasks.remapSourcesJar)

            @Suppress("UnstableApiUsage")
            loom.disableDeprecatedPomGeneration(this)

            groupId = project.group.toString()
            artifactId = project.findProperty("maven.artifactId")?.toString() ?: project.name
            version = "${rootProject.property("project_version")}+${rootProject.property("minecraft_version")}"
            val isSnapshot = rootProject.findProperty("snapshot")?.toString().toBoolean()
            if (isSnapshot) {
                version = "$version-SNAPSHOT"
            }
        }
    }

    repositories {
        mavenLocal()
    }
}