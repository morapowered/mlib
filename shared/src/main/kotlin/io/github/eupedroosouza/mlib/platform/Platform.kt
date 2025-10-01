package io.github.eupedroosouza.mlib.platform

import org.slf4j.Logger

interface Platform {

    companion object {
        @JvmStatic
        fun platform() = PlatformImplementation.platform
    }

    val name: String
    val logger: Logger

}