/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025 Pedro Souza
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

package io.github.morapowered.inventory.item.builder;

import io.github.morapowered.inventory.item.SimpleItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ItemAbstractBuilderImpl<B extends ItemAbstractBuilder<B, T>, T extends SimpleItem> implements ItemAbstractBuilder<B, T> {

    protected Item item;
    protected int amount = 1;
    protected List<Integer> slots = new ArrayList<>();
    protected String displayName;
    protected List<String> lore;
    protected Integer customModelData;


    @Override
    public @NotNull B item(@NotNull Item item) {
        this.item = Objects.requireNonNull(item, "item");
        return asBuilder();
    }

    @Override
    public @NotNull B slot(int slot) {
        if (slot < 0 || slot > 53) {
            throw new IllegalArgumentException("Invalid slot range [0, 53]: " + slot);
        }
        slots.add(slot);
        return asBuilder();
    }

    @Override
    public @NotNull B amount(int amount) {
        this.amount = amount;
        return asBuilder();
    }

    @Override
    public @NotNull B slots(List<Integer> slots) {
        for (Integer slot : Objects.requireNonNull(slots, "slots")) {
            if (slot < 0 || slot > 53) {
                throw new IllegalArgumentException("Invalid slot range [0, 53]: " + slot);
            }
            this.slots.add(slot);
        }
        return asBuilder();
    }

    @Override
    public @NotNull B displayName(@Nullable String displayName) {
        this.displayName = displayName;
        return asBuilder();
    }

    @Override
    public @NotNull B lore(@Nullable List<String> lore) {
        this.lore = lore;
        return asBuilder();
    }

    @Override
    public @NotNull B customModelData(@Nullable Integer customModelData) {
        this.customModelData = customModelData;
        return asBuilder();
    }

}
