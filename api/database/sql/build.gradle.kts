plugins {
    id("component-conventions")
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(libs.hikaricp)
    api(libs.configurate.core)
    api(libs.gson)

    implementation(libs.mariadb.java.client)
    implementation(libs.mysql.connector.j)
    implementation(libs.postgresql)
    implementation(libs.sqlite)
    implementation(libs.h2)
}