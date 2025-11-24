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

package io.github.morapowered.inventory.gson.inventory;

import com.google.gson.*;
import io.github.morapowered.inventory.pages.multi.config.MultiPageInventoryConfiguration;
import io.github.morapowered.inventory.util.InventoryKeyConstants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class MultiPageInventoryConfigurationSerializer implements JsonSerializer<MultiPageInventoryConfiguration>, JsonDeserializer<MultiPageInventoryConfiguration> {

    @Override
    public MultiPageInventoryConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!element.isJsonObject()) {
            throw new JsonParseException("Invalid configuration");
        }
        JsonObject object = element.getAsJsonObject();
        MultiPageInventoryConfiguration.Builder builder = MultiPageInventoryConfiguration.builder();
        AbstractPageConfigurationSerializer.deserialize(builder, object, context);
        if (object.has(InventoryKeyConstants.START) && object.has(InventoryKeyConstants.END)) {
            List<Integer> noSlots = new ArrayList<>();
            if (object.has(InventoryKeyConstants.NO_SLOTS)) {
                noSlots.addAll(object.get(InventoryKeyConstants.NO_SLOTS).getAsJsonArray().asList().stream().map(JsonElement::getAsInt).toList());
            }
            builder.startAndEnd(object.get(InventoryKeyConstants.START).getAsInt(),
                    object.get(InventoryKeyConstants.END).getAsInt(),
                    noSlots.toArray(Integer[]::new));
        } else {
            List<Integer> slots = new ArrayList<>();
            if (object.has(InventoryKeyConstants.SLOTS)) {
                slots.addAll(object.get(InventoryKeyConstants.SLOTS).getAsJsonArray().asList().stream().map(JsonElement::getAsInt).toList());
            }
            builder.slots(slots);
        }
        return builder.build();
    }

    @Override
    public JsonElement serialize(MultiPageInventoryConfiguration configuration, Type type, JsonSerializationContext context) {
        if (configuration == null) {
            return null;
        }
        JsonObject object = new JsonObject();
        AbstractPageConfigurationSerializer.serialize(configuration, object, context);
        JsonArray slotsArray = new JsonArray();
        configuration.getSlots().forEach(slotsArray::add);
        object.add(InventoryKeyConstants.SLOTS, slotsArray);
        object.add(InventoryKeyConstants.PREVIOUS_ITEM, context.serialize(configuration.getPreviousItem()));
        object.add(InventoryKeyConstants.NEXT_ITEM, context.serialize(configuration.getNextItem()));
        return object;
    }
}
