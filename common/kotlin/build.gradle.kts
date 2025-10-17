plugins {
    id("mlib.kotlin-conventions")
    id("mlib.publish-conventions")
}

dependencies {
    api(project(":common")) {
        isTransitive = false
    }
    api(project(":api:kotlin"))
}