package io.github.morapowered.mlib.inventory.pages.confirm.config;

import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.types.SimpleItem;
import io.github.morapowered.mlib.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class ConfirmInventoryConfiguration extends SimplePageInventoryConfiguration {

    public ItemsConfigurations items = new ItemsConfigurations();

    public ConfirmInventoryConfiguration() {
        this.title = "{green}Confirm";
    }

    @ConfigSerializable
    public static class ItemsConfigurations {

        public InventoryItem no = SimpleItem.simple()
                .slot(12)
                .displayName("<red>No")
                .lore("<gray>Click to cancel!")
                .build();

        public InventoryItem yes = SimpleItem.simple()
                .slot(14)
                .displayName("<green>Yes")
                .lore("<gray>Click to confirm!")
                .build();

    }


}
