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

package io.github.morapowered.inventory.gson.inventory;

import com.google.gson.*;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.pages.config.PageConfigurationAbstractBuilder;
import io.github.morapowered.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import io.github.morapowered.inventory.util.InventoryKeyConstants;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * Provides a base serializer/deserializer for inventory configuration.
 */
public interface AbstractPageConfigurationSerializer {

    static <B extends PageConfigurationAbstractBuilder<B>> B deserialize(final @NotNull B builder, final @NotNull JsonObject object, JsonDeserializationContext context) throws JsonParseException {

        if (object.has(InventoryKeyConstants.STATIC_ITEMS)) {
            JsonObject staticItemsObject = object.getAsJsonObject(InventoryKeyConstants.STATIC_ITEMS);
            for (Map.Entry<String, JsonElement> entry : staticItemsObject.asMap().entrySet()) {
                String id = entry.toString();
                SimpleItem simpleItem = context.deserialize(entry.getValue(), SimpleItem.class);
                if (simpleItem == null) {
                    throw new JsonParseException("Object cannot be read as SimpleItem");
                }
                builder.staticItem(id, simpleItem);
            }
        }
        return builder.title(object.get(InventoryKeyConstants.TITLE).getAsString())
                .rows(object.get(InventoryKeyConstants.ROWS).getAsInt());
    }

    static <T extends SimplePageInventoryConfiguration> void serialize(final @NotNull T configuration, final @NotNull JsonObject object, JsonSerializationContext context) throws JsonParseException {
        object.addProperty(InventoryKeyConstants.TITLE, configuration.getTitle());
        object.addProperty(InventoryKeyConstants.ROWS, configuration.getRows());
        JsonObject staticItemsObject = new JsonObject();
        for (Map.Entry<String, SimpleItem> entry : configuration.getStaticItems().entrySet()) {
            String id = entry.getKey();
            JsonElement simpleItemElement = context.serialize(entry.getValue());
            staticItemsObject.add(id, simpleItemElement);
        }
        object.add(InventoryKeyConstants.STATIC_ITEMS, staticItemsObject);
    }

}
