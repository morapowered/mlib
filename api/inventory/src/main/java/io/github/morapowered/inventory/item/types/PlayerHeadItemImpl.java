/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025-2026 Pedro Souza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.morapowered.inventory.item.types;

import io.github.morapowered.inventory.item.ItemType;
import io.github.morapowered.inventory.item.SimpleItemImpl;
import io.github.morapowered.inventory.item.builder.ItemAbstractBuilderImpl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class PlayerHeadItemImpl extends SimpleItemImpl implements PlayerHeadItem {

    private final @Nullable UUID playerUniqueId;

    public PlayerHeadItemImpl(Integer amount, @NotNull List<@NotNull Integer> slots, @Nullable String displayName, @Nullable List<@NotNull String> lore, @Nullable Integer customModelData, @Nullable UUID playerUniqueId) {
        super(Items.PLAYER_HEAD, amount, slots, displayName, lore, customModelData);
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public @NotNull ItemType getItemType() {
        return ItemType.PLAYER_HEAD;
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

    public static class BuilderImpl extends ItemAbstractBuilderImpl<PlayerHeadItem.Builder, PlayerHeadItem> implements PlayerHeadItem.Builder {

        private UUID playerUniqueId;

        @Override
        public PlayerHeadItem.@NotNull Builder playerUniqueId(@Nullable UUID playerUniqueId) {
            this.playerUniqueId = playerUniqueId;
            return this;
        }

        @Override
        public PlayerHeadItem.@NotNull Builder asBuilder() {
            return this;
        }

        @Override
        public @NotNull PlayerHeadItem build() {
            return new PlayerHeadItemImpl(amount, slots, displayName, lore, customModelData, playerUniqueId);
        }

    }
}
