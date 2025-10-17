package io.github.morapowered.mlib;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.morapowered.mlib.platform.ProxyPlatform;
import io.github.morapowered.mlib.util.BuildParameters;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "mlib", name = "mlib", version = BuildParameters.VERSION, authors = {"Pedro Souza", "mlib Contributors"})
public class MLibPlugin extends ProxyPlatform {

    private final Logger logger;
    private final ProxyServer server;
    private final Path workDir;

    @Inject
    public MLibPlugin(Logger logger, ProxyServer server, @DataDirectory Path workDir) {
        this.logger = logger;
        this.server = server;
        this.workDir = workDir;
    }

    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        init();
    }

    @Subscribe
    public void onShutdown(ProxyShutdownEvent event) {
        shutdown();
    }

    @Override
    public @NotNull String getName() {
        return "proxy";
    }

    @Override
    public @NotNull ProxyServer getServer() {
        return server;
    }

    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }
}
