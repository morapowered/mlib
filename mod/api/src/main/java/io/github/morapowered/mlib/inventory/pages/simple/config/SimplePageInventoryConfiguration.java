package io.github.morapowered.mlib.inventory.pages.simple.config;

import io.github.morapowered.mlib.inventory.item.InventoryItem;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.HashMap;

@ConfigSerializable
public class SimplePageInventoryConfiguration {

    public String title = "{green}Title here";
    public int rows = 3;
    public HashMap<String, InventoryItem> staticItems = new HashMap<>();

}
