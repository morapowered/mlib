package io.github.morapowered.mlib;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.morapowered.mlib.util.BuildParameters;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "mlib", name = "mlib", version = BuildParameters.VERSION, authors = {"Pedro Souza", "mlib Contributors"})
public class MLibPlugin {

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
        logger.info("mlib (proxy) (version: {}, branch: {}}, build: {})", BuildParameters.VERSION, BuildParameters.BRANCH, BuildParameters.BUILD);

    }

    @Subscribe
    public void onShutdown(ProxyShutdownEvent event) {
        logger.info("mlib shutdown");
    }

}
