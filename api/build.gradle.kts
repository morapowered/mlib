plugins {
    id("mlib.base-conventions")
    id("mlib.publish-conventions")
}

group = "io.github.morapowered.mlib"
subprojects {
    group = "io.github.morapowered.mlib.api"
}

dependencies {
    api(libs.configuration)
    api(libs.bundles.configurate)
    api(libs.bundles.adventure)
    api(libs.bundles.sql)
    api(libs.channels)
}