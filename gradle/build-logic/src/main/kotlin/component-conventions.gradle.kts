import util.VersionType
import util.writeVersion

plugins {
    id("base-conventions")
    id("publish-conventions")
}

version = writeVersion(VersionType.PUBLISHING)