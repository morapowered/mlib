plugins {
    id("root-conventions")
}

val fixedBranchName = versioning.info.branch.substringAfterLast("/")
extra["buildSuffix"] = "$fixedBranchName-${versioning.info.build}"
