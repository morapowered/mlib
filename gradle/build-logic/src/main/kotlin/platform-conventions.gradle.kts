import util.CopyFile
import util.VersionType
import util.writeVersion

plugins {
    id("component-conventions")
    id("com.gradleup.shadow")
}

version = writeVersion(VersionType.PLATFORM)


val bundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true

}

configurations {
    create("implementationAndBundle") {
        extendsFrom(getByName("bundle"))
        extendsFrom(getByName("implementation"))
    }

    create("apiAndBundle") {
        extendsFrom(getByName("bundle"))
        extendsFrom(getByName("api"))
    }
}


tasks {
    shadowJar {
        configurations = listOf(bundle)
    }

    jar {
        archiveBaseName.set("mlib-${project.name}")
        archiveClassifier.set("dev-slim")
    }

    shadowJar {
        archiveBaseName.set("mlib-${project.name}")
        archiveClassifier.set("")
    }

    val copyJar by registering(CopyFile::class) {
        val productionJar = tasks.shadowJar.flatMap { it.archiveFile }
        fileToCopy = productionJar
        destination = productionJar.flatMap {
            rootProject.layout.buildDirectory.file("libs/${it.asFile.name}")
        }
    }

    assemble {
        dependsOn(copyJar)
    }

}