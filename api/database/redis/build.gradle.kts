plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(libs.lettuce.core)
}