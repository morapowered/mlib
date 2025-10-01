package io.github.eupedroosouza.mlib.platform

import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
object PlatformImplementation {

    lateinit var platform: Platform
        private set

    fun setup(platform: Platform) {
        this.platform = platform
    }

}