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

package io.github.morapowered.inventory.configurate.inventory;

import io.github.morapowered.inventory.util.InventoryKeyConstants;
import io.github.morapowered.inventory.configurate.util.ConfigurateUtil;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.pages.config.PageConfigurationAbstractBuilder;
import io.github.morapowered.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Map;


/**
 * Provides a base serializer/deserializer for inventory configuration.
 */
public interface AbstractPageConfigurationSerializer {

    static <B extends PageConfigurationAbstractBuilder<B, T>, T> B deserialize(final @NotNull B builder, final @NotNull ConfigurationNode node) throws SerializationException {
        ConfigurationNode staticItemNode = node.node(InventoryKeyConstants.STATIC_ITEMS);
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : staticItemNode.childrenMap().entrySet()) {
            String id = entry.getKey().toString();
            SimpleItem simpleItem = entry.getValue().get(SimpleItem.class);
            if (simpleItem == null) {
                throw new SerializationException("Node " + ConfigurateUtil.stringedPath(entry.getValue()) + " cannot be read as SimpleItem");
            }
            builder.staticItem(id, simpleItem);
        }
        return builder.title(ConfigurateUtil.stringOrThrow(node.node(InventoryKeyConstants.TITLE)))
                .rows(node.node(InventoryKeyConstants.ROWS).getInt());
    }

    static <T extends SimplePageInventoryConfiguration> void serialize(final @NotNull T configuration, final @NotNull ConfigurationNode node) throws SerializationException {
        node.node(InventoryKeyConstants.TITLE).set(configuration.getTitle());
        node.node(InventoryKeyConstants.ROWS).set(configuration.getRows());
        node.node(InventoryKeyConstants.STATIC_ITEMS).set(configuration.getStaticItems());
    }

}
