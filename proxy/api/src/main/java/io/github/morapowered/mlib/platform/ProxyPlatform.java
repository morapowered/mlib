package io.github.morapowered.mlib.platform;

import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;

public abstract class ProxyPlatform extends AbstractPlatform {

    public static ProxyPlatform get() {
        if (!(Platform.get() instanceof ProxyPlatform proxyPlatform)) {
            throw new IllegalStateException("unsupported platform");
        }
        return proxyPlatform;
    }

    public abstract @NotNull ProxyServer getServer();

}
