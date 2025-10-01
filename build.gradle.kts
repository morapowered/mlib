plugins {
    id("mlib.root-conventions")
}

version = "${project.property("project_version")}+${project.property("minecraft_version")}"
val isSnapshot = project.findProperty("snapshot")?.toString().toBoolean()
if (isSnapshot) {
    val fixedBranchName = versioning.info.branch.substringAfter("/")
    version = "$version-${fixedBranchName}-${versioning.info.build}"
}