plugins {
    id("java")
    id("java-library")
    id("org.cadixdev.licenser")
}

group = rootProject.group

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}



license {
    header(rootProject.file("HEADER"))
}

tasks {

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 21
    }

    test {
        useJUnitPlatform()
    }
}