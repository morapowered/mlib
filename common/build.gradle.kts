plugins {
    id("mlib.base-conventions")
    id("mlib.publish-conventions")
    id("net.kyori.blossom")
    id("net.nemerosa.versioning")
}

subprojects {
    group = "io.github.morapowered.mlib.common"
}

dependencies {
    api(project(":api"))
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
                property("branch", versioning.info.branch)
                property("build", versioning.info.build)
            }
        }
    }
}