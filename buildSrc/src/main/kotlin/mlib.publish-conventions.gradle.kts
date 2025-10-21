import java.util.*

plugins {
    id("mlib.base-conventions")
    `maven-publish`
}

val localProperties: Properties by project.extra

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/morapowered/packages")
            credentials {
                username = localProperties.getProperty("gpr.username") ?: System.getenv("GH_USERNAME")
                password = localProperties.getProperty("gpr.token") ?: System.getenv("GH_TOKEN")
            }
        }
        mavenLocal()
    }

    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = project.findProperty("maven.artifactId")?.toString() ?: project.name
            version = "${rootProject.property("project_version")}+${rootProject.property("minecraft_version")}"
            val isSnapshot = rootProject.findProperty("snapshot")?.toString().toBoolean()
            if (isSnapshot) {
                version = "$version-SNAPSHOT"
            }
        }
    }

}