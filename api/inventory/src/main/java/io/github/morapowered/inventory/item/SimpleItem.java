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

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import io.github.morapowered.inventory.item.builder.ItemAbstractBuilder;
import io.github.morapowered.inventory.util.AdventureUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface SimpleItem {

    static @NotNull Builder builder() {
        return new SimpleItemImpl.BuilderImpl();
    }

    @NotNull ItemType getItemType();

    @NotNull Item getItem();

    int getAmount();

    @NotNull List<Integer> getSlots();

    @Nullable String getDisplayName();

    @Nullable List<String> getLore();

    @Nullable Integer getCustomModelData();

    default boolean hasSlots() {
        return !getSlots().isEmpty();
    }

    default ItemStack createStack() {
        return createStack(string -> string, string -> string);
    }

    default ItemStack createStack(Function<String, String> displayNameFormatter, Function<String, String> loreLineFormatter) {
        ItemStack stack = new ItemStack(getItem(), getAmount());
        if (getDisplayName() != null) {
            stack.set(DataComponents.ITEM_NAME, AdventureUtil.text(displayNameFormatter.apply(getDisplayName())));
        }
        if (getLore() != null) {
            stack.set(DataComponents.LORE, new ItemLore(getLore().stream()
                    .map(line -> AdventureUtil.text(loreLineFormatter.apply(line)))
                    .toList()));
        }
        if (getCustomModelData() != null) {
            stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(getCustomModelData()));
        }
        return stack;
    }

    default Button createButton(Consumer<ButtonAction> actionConsumer,
                                Consumer<ItemStack> stackConsumer,
                                Function<String, String> displayNameFormatter,
                                Function<String, String> loreLineFormatter) {
        ItemStack stack = createStack(displayNameFormatter, loreLineFormatter);
        stackConsumer.accept(stack);
        return GooeyButton.builder()
                .display(stack)
                .onClick(actionConsumer)
                .build();
    }

    default void createSimpleButton(ChestTemplate.Builder builder,
                                    Consumer<ButtonAction> actionConsumer,
                                    Consumer<ItemStack> stackConsumer,
                                    Function<String, String> displayNameFormatter,
                                    Function<String, String> loreLineFormatter) {

        getSlots().forEach(slot -> builder.set(slot, createButton(actionConsumer, stackConsumer, displayNameFormatter, loreLineFormatter)));
    }

    default void createSimpleButton(ChestTemplate template,
                                    Consumer<ButtonAction> actionConsumer,
                                    Consumer<ItemStack> stackConsumer,
                                    Function<String, String> displayNameFormatter,
                                    Function<String, String> loreLineFormatter) {
        getSlots().forEach(slot -> template.set(slot, createButton(actionConsumer, stackConsumer, displayNameFormatter, loreLineFormatter)));

    }

    interface Builder extends ItemAbstractBuilder<Builder, SimpleItem> {

    }

}
