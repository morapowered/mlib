package io.github.morapowered.mlib.inventory.item.types;

import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.InventoryItemType;
import io.github.morapowered.mlib.util.AbstractBuilder;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SimpleItem implements InventoryItem {

    public static Builder simple() {
        return new Builder();
    }

    private final Item item;
    private final List<Integer> slots;
    private final @Nullable String displayName;
    private final @Nullable List<String> lore;
    private final @Nullable Integer customModelData;

    public SimpleItem(Item item, @Nullable List<Integer> slots, @Nullable String displayName, @Nullable List<String> lore, @Nullable Integer customModelData) {
        this.item = Objects.requireNonNull(item, "item");
        this.slots = Optional.ofNullable(slots).orElse(Collections.emptyList());
        this.displayName = displayName;
        this.lore = lore;
        this.customModelData = customModelData;
    }

    @Override
    public @NotNull InventoryItemType getType() {
        return InventoryItemType.SIMPLE;
    }

    @Override
    public @NotNull Item getItem() {
        return item;
    }

    @Override
    public @NotNull List<Integer> getSlots() {
        return slots;
    }

    @Override
    public @Nullable String getDisplayName() {
        return displayName;
    }

    @Override
    public @Nullable List<String> getLore() {
        return lore;
    }

    @Override
    public @Nullable Integer getCustomModelData() {
        return customModelData;
    }

    public static class Builder extends InventoryItem.Builder<Builder> implements AbstractBuilder<SimpleItem> {

        @Override
        protected Builder asBuilder() {
            return this;
        }

        @Override
        public SimpleItem build() {
            return new SimpleItem(item, slots, displayName, lore, customModelData);
        }
    }
}
