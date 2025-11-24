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

package io.github.morapowered.inventory.configurate.item;

import io.github.morapowered.inventory.util.CheckedSupplier;
import io.github.morapowered.inventory.util.ItemKeyConstants;
import io.github.morapowered.inventory.configurate.util.ConfigurateUtil;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.item.builder.ItemAbstractBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Provides a base serializer/deserializer for items.
 */
public interface AbstractItemSerializer {

    static <B extends ItemAbstractBuilder<B, T>, T extends SimpleItem> B deserialize(final @NotNull B builder, final @NotNull ConfigurationNode node) throws SerializationException {
        Objects.requireNonNull(builder, "builder");
        Objects.requireNonNull(node, "node");
        return builder.item(ConfigurateUtil.resourceLocationOrThrow(node.node(ItemKeyConstants.ITEM)))
                .amount(node.node(ItemKeyConstants.AMOUNT).getInt(1))
                .slots(((CheckedSupplier<List<Integer>, SerializationException>) () -> {
                    if (node.hasChild(ItemKeyConstants.SLOTS)) {
                        return node.node(ItemKeyConstants.SLOTS).getList(Integer.class);
                    }
                    return Collections.emptyList();
                }).get())
                .displayName(node.node(ItemKeyConstants.DISPLAY_NAME).getString())
                .lore(node.node(ItemKeyConstants.LORE).getList(String.class))
                // A supplier is needed because it is necessary to return null and not zero!
                .customModelData(((Supplier<Integer>) () -> {
                    if (node.hasChild(ItemKeyConstants.CUSTOM_MODEL_DATA)) {
                        return node.node(ItemKeyConstants.CUSTOM_MODEL_DATA).getInt();
                    }
                    return null;
                }).get());
    }

    static <T extends SimpleItem> void serialize(final @NotNull T item, final @NotNull ConfigurationNode node) throws SerializationException {
        Objects.requireNonNull(node, "node");
        node.node(ItemKeyConstants.ITEM).set(BuiltInRegistries.ITEM.getKey(item.getItem()).toString());
        node.node(ItemKeyConstants.AMOUNT).set(item.getAmount());
        if (!item.getSlots().isEmpty()) {
            node.node(ItemKeyConstants.SLOTS).set(item.getSlots());
        }
        if (item.getDisplayName() != null) {
            node.node(ItemKeyConstants.DISPLAY_NAME).set(item.getDisplayName());
        }
        if (item.getLore() != null) {
            node.node(ItemKeyConstants.LORE).set(item.getLore());
        }
        if (item.getCustomModelData() != null) {
            node.node(ItemKeyConstants.CUSTOM_MODEL_DATA).set(item.getCustomModelData());
        }
    }

}
