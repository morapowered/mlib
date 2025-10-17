package io.github.morapowered.mlib.inventory.item.serializer;

import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.InventoryItemType;
import io.github.morapowered.mlib.inventory.item.types.PlayerHeadItem;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.List;

public class InventoryItemSerializer implements TypeSerializer<InventoryItem> {

    public static InventoryItemSerializer INSTANCE = new InventoryItemSerializer();
    public static final String TYPE_KEY = "type";
    public static String ITEM_KEY = "item";
    public static String SLOTS_KEY = "slots";
    public static String DISPLAY_NAME_KEY = "display-name";
    public static String LORE_KEY = "lore";
    public static String CUSTOM_MODEL_DATA_KEY = "custom-model-data";

    @Override
    public InventoryItem deserialize(@NotNull Type javaType, @NotNull ConfigurationNode node) throws SerializationException {
        if (node.empty() || node.virtual()) {
            return null;
        }
        InventoryItemType type;
        if (!node.hasChild(TYPE_KEY)) {
            type = InventoryItemType.SIMPLE;
        } else {
            type = InventoryItemType.valueOf(node.node(TYPE_KEY).require(String.class));
        }
        return type.getFrom().apply(node);
    }

    @Override
    public void serialize(@NotNull Type type, @Nullable InventoryItem value, @NotNull ConfigurationNode node) throws SerializationException {
        if (value == null) {
            node.raw(null);
            return;
        }
        node.node(TYPE_KEY).set(value.getType().name());
        value.getType().getTo().accept(value, node);
    }

    public static <T extends InventoryItem.Builder<T>> void from(T builder, ConfigurationNode node) throws SerializationException {
        if (!(builder instanceof PlayerHeadItem.Builder)) {
            String itemIdStringed = node.node(ITEM_KEY).getString();
            if (itemIdStringed == null) {
                throw new SerializationException("Item is required");
            }
            builder.item(itemIdStringed);
        }
        if (node.hasChild(SLOTS_KEY)) {
            List<Integer> slots = node.node(SLOTS_KEY).getList(Integer.class);
            if (slots == null) {
                throw new SerializationException("Fail serializing slots");
            }
            builder.slots(slots);
        }
        if (node.hasChild(DISPLAY_NAME_KEY)) {
            builder.displayName(node.node(DISPLAY_NAME_KEY).getString());
        }
        if (node.hasChild(LORE_KEY)) {
            List<String> lore = node.node(LORE_KEY).getList(String.class);
            if (lore == null) {
                throw new SerializationException("Fail serializing slots");
            }
            builder.lore(lore);
        }
        if (node.hasChild(CUSTOM_MODEL_DATA_KEY)) {
            int customModelData = node.node(CUSTOM_MODEL_DATA_KEY).require(Integer.class);
            builder.customModelData(customModelData);
        }
    }

    public static void to(InventoryItem item, ConfigurationNode node) throws SerializationException {
        if (!(item instanceof PlayerHeadItem)) {
            node.node(ITEM_KEY).set(item.getItem().builtInRegistryHolder().key().location().toString());
        }
        if (item.hasSlots()) {
            node.node(SLOTS_KEY).set(item.getSlots());
        }
        if (item.getDisplayName() != null) {
            node.node(DISPLAY_NAME_KEY).set(item.getDisplayName());
        }
        if (item.getLore() != null) {
            node.node(LORE_KEY).set(item.getLore());
        }
        if (item.getCustomModelData() != null) {
            node.node(CUSTOM_MODEL_DATA_KEY).set(item.getCustomModelData());
        }
    }
}
