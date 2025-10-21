package io.github.morapowered.mlib.platform;

import io.github.morapowered.configuration.ConfigurationFactory;
import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.serializer.InventoryItemSerializer;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

public abstract class ModPlatform extends AbstractPlatform {

    public static ModPlatform get() {
        if (!(Platform.get() instanceof ModPlatform modPlatform)) {
            throw new IllegalStateException("unsupported platform");
        }
        return modPlatform;
    }

    /**
     * Be careful when using, the server is only ready in {@link net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting}.
     *
     * @return MinecraftServer instance
     */
    public abstract @NotNull MinecraftServer getServer();

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public <L extends AbstractConfigurationLoader<@NotNull N>, N extends ScopedConfigurationNode<@NotNull N>, B extends AbstractConfigurationLoader.Builder<@NotNull B, @NotNull L>> ConfigurationFactory.Builder<@NotNull L, @NotNull N, @NotNull B> createConfigurationFactory(@NotNull Class<B> builder) {
        return super.createConfigurationFactory(builder).configure(b -> b.defaultOptions(options -> options.serializers(serializerBuilder ->
                serializerBuilder.register(InventoryItem.class, InventoryItemSerializer.INSTANCE))));
    }

}
