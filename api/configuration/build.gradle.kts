plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.annotations)

    api(libs.configurate.core)

    testImplementation(libs.configurate.yaml)
}