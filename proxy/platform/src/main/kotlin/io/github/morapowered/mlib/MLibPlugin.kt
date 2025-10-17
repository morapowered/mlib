package io.github.morapowered.mlib

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import io.github.morapowered.mlib.configuration.internal.ConfigurationKotlinSerializers
import io.github.morapowered.mlib.platform.ProxyPlatform
import io.github.morapowered.mlib.util.BuildParameters
import org.slf4j.Logger
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
        ConfigurationKotlinSerializers.register()
    }

    @Subscribe
    fun onShutdown(event: ProxyShutdownEvent?) {
        shutdown()
    }

    override fun getName(): String = "proxy"

    override fun getServer(): ProxyServer = server

    override fun getLogger(): Logger = logger

    companion object {
        @JvmStatic
        private lateinit var INSTANCE: MLibPlugin
    }

}