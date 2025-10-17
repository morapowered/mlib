package io.github.morapowered.mlib.inventory.item.types;

import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.InventoryItemType;
import io.github.morapowered.mlib.util.AbstractBuilder;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class CommandItem extends SimpleItem {

    public static Builder command() {
        return new Builder();
    }

    private final @NotNull String command;

    public CommandItem(Item item, @Nullable List<Integer> slots, @Nullable String displayName, @Nullable List<String> lore, @Nullable Integer customModelData, @NotNull String command) {
        super(item, slots, displayName, lore, customModelData);
        this.command = Objects.requireNonNull(command, "command");
    }

    @Override
    public @NotNull InventoryItemType getType() {
        return InventoryItemType.COMMAND;
    }

    public @NotNull String getCommand() {
        return command;
    }

    public static class Builder extends InventoryItem.Builder<Builder> implements AbstractBuilder<CommandItem> {

        private String command;

        @Override
        protected Builder asBuilder() {
            return this;
        }

        public Builder command(final @NotNull String command) {
            this.command = Objects.requireNonNull(command, "command");
            return this;
        }

        @Override
        public CommandItem build() {
            return new CommandItem(item, slots, displayName, lore, customModelData, command);
        }
    }
}
