package io.github.morapowered.mlib.inventory.item.types;

import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.InventoryItemType;
import io.github.morapowered.mlib.util.AbstractBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class PlayerHeadItem extends SimpleItem {

    public static Builder playerHead() {
        return new Builder();
    }

    private final @Nullable UUID playerUniqueId;

    public PlayerHeadItem(@NotNull List<@NotNull Integer> slots, @Nullable String displayName, @Nullable List<@NotNull String> lore, @Nullable Integer customModelData, @Nullable UUID playerUniqueId) {
        super(Items.PLAYER_HEAD, slots, displayName, lore, customModelData);
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public @NotNull InventoryItemType getType() {
        return InventoryItemType.PLAYER_HEAD;
    }

    public @Nullable UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    @Override
    public ItemStack createStack(Function<String, String> displayNameFormatter, Function<String, String> loreLineFormatter) {
        ItemStack stack = super.createStack(displayNameFormatter, loreLineFormatter);
        if (playerUniqueId != null) {
            // todo: set player to head
        }
        return stack;
    }

    public static class Builder extends InventoryItem.Builder<Builder> implements AbstractBuilder<PlayerHeadItem> {

        private UUID playerUniqueId;

        @Override
        protected Builder asBuilder() {
            return this;
        }

        public Builder playerUniqueId(final @Nullable UUID playerUniqueId) {
            this.playerUniqueId = playerUniqueId;
            return this;
        }

        @Override
        public PlayerHeadItem build() {
            return new PlayerHeadItem(slots, displayName, lore, customModelData, playerUniqueId);
        }
    }
}
