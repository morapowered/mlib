plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(project(":api:database:sql-api"))
    compileOnly(libs.sqlite)
    compileOnly(libs.h2)
}