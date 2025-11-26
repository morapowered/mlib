import util.CopyFile
import util.VersionType
import util.writeVersion

plugins {
    id("modcomponent-conventions")
    id("com.gradleup.shadow")
}

version = writeVersion(VersionType.MOD_PLATFORM)

val apiAndBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val bundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
    extendsFrom(apiAndBundle)
}


configurations {
    getByName("api") {
        extendsFrom(apiAndBundle)
    }
}


tasks {

    jar {
        archiveBaseName.set("mlib-${project.name}")
        archiveClassifier.set("dev-slim")
    }

    shadowJar {
        configurations = listOf(bundle)
        archiveBaseName.set("mlib-${project.name}")
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        archiveBaseName.set("mlib-${project.name}")
        archiveClassifier.set("")
    }

    val copyJar by registering(CopyFile::class) {
        val productionJar = tasks.remapJar.flatMap { it.archiveFile }
        fileToCopy = productionJar
        destination = productionJar.flatMap {
            rootProject.layout.buildDirectory.file("libs/${it.asFile.name}")
        }
    }

    assemble {
        dependsOn(copyJar)
    }


}