plugins {
    id("component-conventions")
}

dependencies {
    api(project(":api:database:redis"))
    api(project(":api:database:redis-serializer-configurate"))
    api(project(":api:database:redis-serializer-gson"))
}