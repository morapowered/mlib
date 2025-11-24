import util.VersionType
import util.writeVersion

plugins {
    id("base-conventions")
    id("maven-publish")
}

java {
    withJavadocJar()
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])

            groupId = rootProject.group.toString()
            val builder = StringBuilder()
            val path = project.path
            val split = path.split(":")
            for (i in 1 until split.size) {
                if (i != 1) {
                    builder.append("-")
                }
                builder.append(split[i])
            }
            artifactId = builder.toString()
            version = writeVersion(VersionType.MOD_PUBLISHING)
        }
    }
}

