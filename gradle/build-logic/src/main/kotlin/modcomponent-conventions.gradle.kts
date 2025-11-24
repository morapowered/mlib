import util.VersionType
import util.writeVersion

plugins {
    id("mod-conventions")
    id("modpublish-conventions")
}

version = writeVersion(VersionType.MOD_PUBLISHING)