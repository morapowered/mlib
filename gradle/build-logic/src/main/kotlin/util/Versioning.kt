package util

import org.gradle.api.Project
import org.gradle.internal.extensions.core.extra

fun Project.isSnapshot() = rootProject.property("snapshot") == "true"

fun Project.writeVersion(type: VersionType): String {
    val buildSuffix = rootProject.extra.get("buildSuffix")!!.toString()
    return when (type) {
        VersionType.PUBLISHING -> "${rootProject.property("project_version")}${if (isSnapshot()) "-SNAPSHOT" else ""}"
        VersionType.MOD_PUBLISHING -> "${rootProject.property("project_version")}+${rootProject.property("minecraft_version")}${if (isSnapshot()) "-SNAPSHOT" else ""}"
        VersionType.PLATFORM -> "${rootProject.property("project_version")}${if (isSnapshot()) "-${buildSuffix}" else ""} "
        VersionType.MOD_PLATFORM -> "${rootProject.property("project_version")}+${rootProject.property("minecraft_version")}${if (isSnapshot()) "-${buildSuffix}" else ""}"
    }
}

enum class VersionType {
    PUBLISHING,
    MOD_PUBLISHING,
    PLATFORM,
    MOD_PLATFORM
}