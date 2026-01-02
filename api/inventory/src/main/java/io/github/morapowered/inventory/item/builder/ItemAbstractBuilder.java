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

package io.github.morapowered.inventory.item.builder;

import io.github.morapowered.inventory.item.SimpleItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface ItemAbstractBuilder<B extends ItemAbstractBuilder<B, T>, T extends SimpleItem> {

    @NotNull B asBuilder();

    @Contract("_ -> this")
    default @NotNull B item(final @NotNull String name) {
        return this.item(ResourceLocation.parse(Objects.requireNonNull(name, "name")));
    }

    @Contract("_ -> this")
    default @NotNull B item(final @NotNull ResourceLocation id) {
        return this.item(ResourceKey.create(Registries.ITEM, id));
    }

    @Contract("_ -> this")
    default @NotNull B item(final @NotNull ResourceKey<Item> key) {
        return item(BuiltInRegistries.ITEM.getOrThrow(Objects.requireNonNull(key, "key")));
    }

    @Contract("_ -> this")
    @NotNull B item(final @NotNull Item item);

    @Contract("_ -> this")
    @NotNull B amount(final int amount);

    @Contract("_ -> this")
    @NotNull B slot(final int slot);

    @Contract("_ -> this")
    @NotNull B slots(List<Integer> slots);

    @Contract("_ -> this")
    @NotNull
    default B slots(final Integer... slots) {
        return slots(Arrays.asList(slots));
    }

    @Contract("_ -> this")
    @NotNull B displayName(final @Nullable String displayName);

    @Contract("_ -> this")
    @NotNull B lore(final @Nullable List<String> lore);

    @Contract("_ -> this")
    @NotNull
    default B lore(final String... lore) {
        return lore(Arrays.asList(lore));
    }

    @Contract("_ -> this")
    @NotNull B customModelData(final @Nullable Integer customModelData);

    @NotNull T build();

}