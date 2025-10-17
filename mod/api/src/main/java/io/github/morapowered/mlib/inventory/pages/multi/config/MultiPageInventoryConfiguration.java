package io.github.morapowered.mlib.inventory.pages.multi.config;

import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.types.SimpleItem;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigSerializable
public class MultiPageInventoryConfiguration {

    public String title = "{green}Title here!";
    public int rows = 6;
    public int start = 10;
    public int end = 43;
    public List<Integer> noItens = List.of(17, 18, 26, 27, 35, 36);
    public ItemsConfigurations items = new ItemsConfigurations();
    public Map<String, InventoryItem> staticItems = new HashMap<>();

    @ConfigSerializable
    public static class ItemsConfigurations {

        public InventoryItem previousPage = SimpleItem.simple()
                .slot(46)
                .displayName("{green}Previous Page")
                .lore("{gray}Click to go to the previous page.")
                .build();
        public InventoryItem nextPage = SimpleItem.simple()
                .slot(52)
                .displayName("{green}Next Page")
                .lore("{gray}Click to go to the next page.")
                .build();

    }

}
