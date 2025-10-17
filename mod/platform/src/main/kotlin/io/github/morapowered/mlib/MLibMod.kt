package io.github.morapowered.mlib

import com.mojang.logging.LogUtils
import io.github.morapowered.mlib.configuration.internal.ConfigurationKotlinSerializers
import io.github.morapowered.mlib.platform.ModPlatform
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger

object MLibMod : PreLaunchEntrypoint, ModPlatform() {

    private val logger: Logger = LogUtils.getLogger()
    private lateinit var server: MinecraftServer

    override fun onPreLaunch() {
        init()
        ServerLifecycleEvents.SERVER_STARTING.register { server -> starting(server) }
        ServerLifecycleEvents.SERVER_STOPPING.register { _ -> stopping() }
    }

    private fun starting(server: MinecraftServer) {
        this.server = server
    }

    private fun stopping() {
        shutdown()
    }

    override fun init() {
        super.init()
        ConfigurationKotlinSerializers.register()
    }

    override fun getServer(): MinecraftServer {
        if (!this::server.isInitialized) {
            throw IllegalStateException("server not initialized yet")
        }
        return server
    }

    override fun getName(): String = "fabric"
    override fun getLogger(): Logger = logger


}