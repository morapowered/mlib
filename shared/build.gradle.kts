plugins {
    id("mlib.base-conventions")
    id("net.kyori.blossom")
    id("net.nemerosa.versioning")
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