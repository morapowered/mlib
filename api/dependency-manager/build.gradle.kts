plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.annotations)

    implementation(project(":api:util"))
    api(project(":api:loader-utils"))
}