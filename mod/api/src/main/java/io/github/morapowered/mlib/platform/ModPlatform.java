package io.github.morapowered.mlib.platform;

import io.github.morapowered.configuration.serializer.GlobalSerializers;
import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.serializer.InventoryItemSerializer;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public abstract class ModPlatform extends AbstractPlatform {

    public static ModPlatform get() {
        if (!(Platform.get() instanceof ModPlatform modPlatform)) {
            throw new IllegalStateException("unsupported platform");
        }
        return modPlatform;
    }

    /**
     * Be careful when using, the server is only ready in {@link net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting}.
     * @return MinecraftServer instance
     */
    public abstract @NotNull MinecraftServer getServer();

    @Override
    protected void init() {
        super.init();
        GlobalSerializers.BUILDER.register(InventoryItem.class, InventoryItemSerializer.INSTANCE);
    }
}
