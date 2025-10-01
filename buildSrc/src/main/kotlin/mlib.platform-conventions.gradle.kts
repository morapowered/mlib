plugins {
    id("mlib.base-conventions")
    id("com.gradleup.shadow")
}

base {
    archivesName.set("mlib-${project.name}")
}

val bundle: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

tasks {
    shadowJar {
        configurations = listOf(bundle)
    }
}

