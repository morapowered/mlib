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

package io.github.morapowered.inventory.configurate.inventory;

import io.github.morapowered.inventory.util.InventoryKeyConstants;
import io.github.morapowered.inventory.configurate.util.ConfigurateUtil;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.pages.multi.config.MultiPageInventoryConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public final class MultiPageInventoryConfigurationSerializer implements TypeSerializer<MultiPageInventoryConfiguration> {
    @Override
    public MultiPageInventoryConfiguration deserialize(Type type, ConfigurationNode node) throws SerializationException {
        MultiPageInventoryConfiguration.Builder builder = MultiPageInventoryConfiguration.builder();
        AbstractPageConfigurationSerializer.deserialize(MultiPageInventoryConfiguration.builder(), node);
        if (node.hasChild(InventoryKeyConstants.START) && node.hasChild(InventoryKeyConstants.END)) {
            builder.startAndEnd(ConfigurateUtil.intOrThrow(node.node(InventoryKeyConstants.START)),
                    ConfigurateUtil.intOrThrow(node.node(InventoryKeyConstants.END)),
                    ConfigurateUtil.<Integer>listOrEmpty(node.node(InventoryKeyConstants.NO_SLOTS)).toArray(Integer[]::new));
        } else {
            builder.slots(ConfigurateUtil.listOrEmpty(node.node(InventoryKeyConstants.SLOTS)));
        }
        builder.previousItem(node.node(InventoryKeyConstants.PREVIOUS_ITEM).require(SimpleItem.class));
        builder.nextItem(node.node(InventoryKeyConstants.NEXT_ITEM).require(SimpleItem.class));
        return builder.build();
    }

    @Override
    public void serialize(Type type, @Nullable MultiPageInventoryConfiguration value, ConfigurationNode node) throws SerializationException {
        if (value == null) {
            node.set(null);
            return;
        }
        AbstractPageConfigurationSerializer.serialize(value, node);
        node.node(InventoryKeyConstants.SLOTS).set(value.getSlots());
        node.node(InventoryKeyConstants.PREVIOUS_ITEM).set(value.getPreviousItem());
        node.node(InventoryKeyConstants.NEXT_ITEM).set(value.getNextItem());
    }
}
