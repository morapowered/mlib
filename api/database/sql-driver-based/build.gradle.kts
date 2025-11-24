plugins {
    id("component-conventions")
}
dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(project(":api:database:sql-api"))
    api(libs.hikaricp)

    compileOnly(libs.mariadb.java.client)
    compileOnly(libs.mysql.connector.j)
    compileOnly(libs.postgresql)
}