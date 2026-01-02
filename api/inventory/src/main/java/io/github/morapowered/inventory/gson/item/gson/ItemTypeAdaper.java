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

package io.github.morapowered.inventory.gson.item.gson;

import com.google.gson.*;
import io.github.morapowered.inventory.gson.item.SimpleItemSerializer;
import io.github.morapowered.inventory.gson.serializer.GsonItemSerializer;
import io.github.morapowered.inventory.gson.util.GsonUtil;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.util.ItemKeyConstants;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;
import java.util.HashMap;

@RequiredArgsConstructor
public class ItemTypeAdaper implements JsonSerializer<SimpleItem>, JsonDeserializer<SimpleItem> {

    private final HashMap<ResourceLocation, GsonItemSerializer<?>> serializers;

    @Override
    public SimpleItem deserialize(JsonElement element, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!element.isJsonObject()) {
            throw new JsonParseException("Invalid item");
        }
        JsonObject object = element.getAsJsonObject();
        ResourceLocation typeKey = GsonUtil.resourceLocationOrThrow(object.get(ItemKeyConstants.TYPE));
        GsonItemSerializer<?> serializer = serializers.getOrDefault(typeKey, SimpleItemSerializer.INSTANCE);
        return serializer.deserialize(object);
    }

    @Override
    public JsonElement serialize(SimpleItem item, Type type, JsonSerializationContext jsonSerializationContext) {
        if (item == null) {
            return null;
        }
        JsonObject object = new JsonObject();
        writeValue(item, object);
        object.addProperty(ItemKeyConstants.TYPE, item.getItemType().getKey().toString());
        return object;
    }

    <V extends SimpleItem> void writeValue(V value, JsonObject object) throws JsonParseException {
        GsonItemSerializer<V> serializer = (GsonItemSerializer<V>) serializers.getOrDefault(value.getItemType().getKey(), SimpleItemSerializer.INSTANCE);
        serializer.serialize(value, object);
    }
}
