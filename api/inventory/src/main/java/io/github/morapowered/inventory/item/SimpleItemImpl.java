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

package io.github.morapowered.inventory.item;

import io.github.morapowered.inventory.item.builder.ItemAbstractBuilderImpl;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SimpleItemImpl implements SimpleItem {

    private final Item item;
    private final int amount;
    private final List<Integer> slots;
    private final @Nullable String displayName;
    private final @Nullable List<String> lore;
    private final @Nullable Integer customModelData;

    public SimpleItemImpl(Item item, Integer amount, @Nullable List<Integer> slots, @Nullable String displayName, @Nullable List<String> lore, @Nullable Integer customModelData) {
        this.item = Objects.requireNonNull(item, "item");
        this.amount = Optional.ofNullable(amount).orElse(1);
        this.slots = Optional.ofNullable(slots).orElse(Collections.emptyList());
        this.displayName = displayName;
        this.lore = lore;
        this.customModelData = customModelData;
    }

    @Override
    public @NotNull ItemType getItemType() {
        return ItemType.SIMPLE;
    }

    @Override
    public int getAmount() {
        return amount;
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

    public static class BuilderImpl extends ItemAbstractBuilderImpl<SimpleItem.Builder, SimpleItem> implements SimpleItem.Builder {

        BuilderImpl() {
        }

        @Override
        public @NotNull SimpleItem.Builder asBuilder() {
            return this;
        }

        @Override
        public @NotNull SimpleItem build() {
            return new SimpleItemImpl(item, amount, slots, displayName, lore, customModelData);
        }
    }

}
