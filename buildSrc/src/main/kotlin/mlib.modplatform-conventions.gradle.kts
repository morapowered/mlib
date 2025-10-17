import gradle.kotlin.dsl.accessors._1169d4f1d0026c4f82c35d8cb5959f57.loom

plugins {
    id("mlib.mod-conventions")
    id("mlib.platform-conventions")
}

loom {
    runs {
        remove(runs["client"])
    }

    runConfigs.configureEach {
        ideConfigGenerated(true)
    }
}

tasks {
    jar {
        archiveClassifier.set("dev-slim")
    }

    shadowJar {
        archiveClassifier.set("dev-shadow")
        mergeServiceFiles()
    }

    remapJar {
        dependsOn(shadowJar)
        archiveClassifier.set("")
        inputFile.set(shadowJar.flatMap { it.archiveFile })
    }
}