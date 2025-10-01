package io.github.morapowered.mlib.platform

import io.github.eupedroosouza.mlib.platform.Platform
import io.github.eupedroosouza.mlib.platform.PlatformImplementation
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences

interface ModPlatform : Platform {

    companion object {
        @JvmStatic
        fun platform() = (PlatformImplementation.platform as ModPlatform)
    }

    val audiences: MinecraftServerAudiences

}