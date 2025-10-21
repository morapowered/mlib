package io.github.morapowered.mlib

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import io.github.morapowered.configuration.ConfigurationFactory
import io.github.morapowered.mlib.platform.ProxyPlatform
import io.github.morapowered.mlib.util.BuildParameters
import org.slf4j.Logger
import org.spongepowered.configurate.ScopedConfigurationNode
import org.spongepowered.configurate.kotlin.dataClassFieldDiscoverer
import org.spongepowered.configurate.loader.AbstractConfigurationLoader
import org.spongepowered.configurate.objectmapping.ObjectMapper
import java.nio.file.Path

@Plugin(id = "mlib", name = "mlib", version = BuildParameters.VERSION, authors = ["Pedro Souza", "mlib Contributors"])
class MLibPlugin : ProxyPlatform {

    private val logger: Logger
    private val server: ProxyServer
    private val workDir: Path

    @Inject
    constructor(logger: Logger, server: ProxyServer, @DataDirectory workDir: Path) : super() {
        this.logger = logger
        this.server = server
        this.workDir = workDir
        INSTANCE = this
    }

    @Subscribe
    fun onInitialize(event: ProxyInitializeEvent?) {
        init()
    }

    @Subscribe
    fun onShutdown(event: ProxyShutdownEvent?) {
        shutdown()
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

    override fun getName(): String = "proxy"

    override fun getServer(): ProxyServer = server

    override fun getLogger(): Logger = logger

    companion object {
        @JvmStatic
        private lateinit var INSTANCE: MLibPlugin
    }

}