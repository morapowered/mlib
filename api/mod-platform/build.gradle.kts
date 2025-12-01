plugins {
    id("modcomponent-conventions")
}

architectury {
    common("fabric", "neoforge")
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.annotations)

    api(project(":api:platform"))


}