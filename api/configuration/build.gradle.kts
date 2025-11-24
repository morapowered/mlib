plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.annotations)

    api(libs.configurate.core)
    api(project(":api:util"))

    testImplementation(libs.configurate.yaml)
}