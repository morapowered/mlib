plugins {
    id("base-conventions")
    id("publish-conventions")
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(project(":api:database:redis"))
    api(libs.gson)
}