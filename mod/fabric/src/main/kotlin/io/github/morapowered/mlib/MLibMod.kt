package io.github.morapowered.mlib

import com.mojang.logging.LogUtils
import io.github.eupedroosouza.mlib.platform.Platform
import io.github.eupedroosouza.mlib.platform.PlatformImplementation
import io.github.morapowered.mlib.platform.ModPlatform
import io.github.morapowered.mlib.util.BuildParameters
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting
import net.kyori.adventure.platform.modcommon.MinecraftAudiences
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger

object MLibMod : ModInitializer, ModPlatform {

    override val name: String = "Fabric"
    override val logger: Logger = LogUtils.getLogger()

    private lateinit var server: MinecraftServer

    override fun onInitialize() {
        PlatformImplementation.setup(this)
        ServerLifecycleEvents.SERVER_STARTING.register { server -> starting(server) }
        ServerLifecycleEvents.SERVER_STOPPING.register { server -> stopping(server) }
    }

    private fun starting(server: MinecraftServer) {
        this.server = server
        logger.info(
            "mlib (fabric) (version: {}, branch: {}}, build: {})",
            BuildParameters.VERSION,
            BuildParameters.BRANCH,
            BuildParameters.BUILD
        )
    }

    private fun stopping(server: MinecraftServer) {

    }

    override val audiences: MinecraftServerAudiences = MinecraftServerAudiences.of(server)
}