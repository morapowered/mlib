package io.github.morapowered.mlib.inventory.item;

import io.github.morapowered.mlib.inventory.item.serializer.InventoryItemSerializer;
import io.github.morapowered.mlib.inventory.item.types.CommandItem;
import io.github.morapowered.mlib.inventory.item.types.PlayerHeadItem;
import io.github.morapowered.mlib.inventory.item.types.SimpleItem;
import io.github.morapowered.mlib.util.function.CheckedBiConsumer;
import io.github.morapowered.mlib.util.function.CheckedFunction;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Optional;
import java.util.UUID;

public enum InventoryItemType {
    SIMPLE(node -> {
        SimpleItem.Builder builder = SimpleItem.simple();
        InventoryItemSerializer.from(builder, node);
        return builder.build();
    }, (inventoryItem, node) -> {
        InventoryItemSerializer.to(inventoryItem, node);
        node.removeChild(InventoryItemSerializer.TYPE_KEY);
    }),
    COMMAND(node -> {
        CommandItem.Builder builder = CommandItem.command();
        InventoryItemSerializer.from(builder, node);
        builder.command(node.node("command").require(String.class));
        return builder.build();
    }, (inventoryItem, node) -> {
        if (inventoryItem instanceof CommandItem commandItem) {
            node.node("command").raw(commandItem.getCommand());
        }
        InventoryItemSerializer.to(inventoryItem, node);
    }),
    PLAYER_HEAD(node -> {
        PlayerHeadItem.Builder builder = PlayerHeadItem.playerHead();
        InventoryItemSerializer.from(builder, node);
        builder.playerUniqueId(Optional.ofNullable(node.node("player").getString())
                .map(UUID::fromString)
                .orElse(null));
        return builder.build();
    }, (inventoryItem, node) -> {
        if (inventoryItem instanceof PlayerHeadItem playerHeadItem) {
            if (playerHeadItem.getPlayerUniqueId() != null) {
                node.node("player").raw(playerHeadItem.getPlayerUniqueId().toString());
            }
        }
        InventoryItemSerializer.to(inventoryItem, node);
    });
    private final CheckedFunction<ConfigurationNode, InventoryItem, SerializationException> from;
    private final CheckedBiConsumer<InventoryItem, ConfigurationNode, SerializationException> to;

    InventoryItemType(CheckedFunction<ConfigurationNode, InventoryItem, SerializationException> from, CheckedBiConsumer<InventoryItem, ConfigurationNode, SerializationException> to) {
        this.from = from;
        this.to = to;
    }

    public CheckedFunction<ConfigurationNode, InventoryItem, SerializationException> getFrom() {
        return from;
    }

    public CheckedBiConsumer<InventoryItem, ConfigurationNode, SerializationException> getTo() {
        return to;
    }

}
