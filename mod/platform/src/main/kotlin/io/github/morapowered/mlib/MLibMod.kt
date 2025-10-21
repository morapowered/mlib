package io.github.morapowered.mlib

import com.mojang.logging.LogUtils
import io.github.morapowered.configuration.ConfigurationFactory
import io.github.morapowered.mlib.platform.ModPlatform
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.spongepowered.configurate.ScopedConfigurationNode
import org.spongepowered.configurate.kotlin.dataClassFieldDiscoverer
import org.spongepowered.configurate.loader.AbstractConfigurationLoader
import org.spongepowered.configurate.objectmapping.ObjectMapper

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
    }

    override fun <L : AbstractConfigurationLoader<N>, N : ScopedConfigurationNode<N>, B : AbstractConfigurationLoader.Builder<B, L>> createConfigurationFactory(
        builder: Class<B>
    ): ConfigurationFactory.Builder<L, N, B>? {
        return super.createConfigurationFactory(builder)
            .configure { builder ->
                builder.defaultOptions { options ->
                    options.serializers { serializerBuilder ->
                        serializerBuilder.registerAnnotatedObjects(
                            ObjectMapper.factoryBuilder().addDiscoverer(dataClassFieldDiscoverer()).build()
                        )
                    }
                }
            }
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