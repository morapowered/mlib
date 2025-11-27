plugins {
    id("base-conventions")
    id("publish-conventions")
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    api(libs.mongodb.driver.sync)
    api(libs.mongodb.driver.reactivestreams)
    api(libs.reactor.core)
    api(libs.configurate.core)
    api(libs.gson)
}