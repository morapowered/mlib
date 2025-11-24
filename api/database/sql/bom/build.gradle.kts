plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(project(":api:database:sql:api"))
    api(project(":api:database:sql:driver-based"))
    api(project(":api:database:sql:driver-based-serializer-configurate"))
    api(project(":api:database:sql:driver-based-serializer-gson"))
    api(project(":api:database:sql:file-based"))
    api(project(":api:database:sql:h2"))
    api(project(":api:database:sql:mariadb"))
    api(project(":api:database:sql:mysql"))
    api(project(":api:database:sql:postgresql"))
    api(project(":api:database:sql:sqlite"))
}