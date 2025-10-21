package io.github.morapowered.mlib.platform;

import io.github.morapowered.configuration.ConfigurationFactory;
import io.github.morapowered.mlib.platform.internal.InternalPlatform;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

public interface Platform {

    static @NotNull Platform get() {
        return InternalPlatform.getPlatform();
    }

    @NotNull String getName();

    @NotNull Logger getLogger();

    default <L extends AbstractConfigurationLoader<@NotNull N>,
            N extends ScopedConfigurationNode<@NotNull N>,
            B extends AbstractConfigurationLoader.Builder<@NotNull B, @NotNull L>> ConfigurationFactory.Builder<@NotNull L, @NotNull N, @NotNull B> createConfigurationFactory(final @NotNull Class<B> builder) {
        return ConfigurationFactory.builder(builder);
    }
}
