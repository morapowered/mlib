plugins {
    id("component-conventions")
}
dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(project(":api:database:sql:file-based"))
    api(libs.h2)
}