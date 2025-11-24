plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(project(":api:database:sql-api"))
    api(project(":api:database:sql-driver-based"))
    api(project(":api:database:sql-driver-based-serializer-configurate"))
    api(project(":api:database:sql-driver-based-serializer-gson"))
    api(project(":api:database:sql-file-based"))
}