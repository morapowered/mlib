import util.VersionType
import util.writeVersion

plugins {
    id("component-conventions")
    id("net.kyori.blossom")
    id("net.nemerosa.versioning")
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.annotations)

    compileOnly("org.slf4j:slf4j-api:2.0.17")
    compileOnly(project(":api:platform"))
    compileOnly(project(":api:dependency-manager"))
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", writeVersion(VersionType.PLATFORM))
                property("mod_version", writeVersion(VersionType.MOD_PLATFORM))
                property("branch", versioning.info.branch)
                property("build", versioning.info.build)
            }
        }
    }
}