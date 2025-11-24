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

package io.github.morapowered.inventory.gson.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.morapowered.inventory.gson.util.GsonUtil;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.item.builder.ItemAbstractBuilder;
import io.github.morapowered.inventory.util.ItemKeyConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

public interface GsonAbstractItemSerializer {

    static <B extends ItemAbstractBuilder<B, T>, T extends SimpleItem> B deserialize(final @NotNull B builder, final @NotNull JsonObject object) throws JsonParseException {
        Objects.requireNonNull(builder, "builder");
        Objects.requireNonNull(object, "object");
        return builder.item(GsonUtil.resourceLocationOrThrow(object.get(ItemKeyConstants.ITEM)))
                .amount(GsonUtil.checkGetOrDefault(
                        () -> object.has(ItemKeyConstants.AMOUNT),
                        () -> object.get(ItemKeyConstants.AMOUNT).getAsInt(),
                        1))
                .slots(GsonUtil.checkGetOrDefault(
                        () -> object.has(ItemKeyConstants.SLOTS) && object.get(ItemKeyConstants.SLOTS).isJsonArray(),
                        () -> object.get(ItemKeyConstants.SLOTS).getAsJsonArray().asList().stream().map(JsonElement::getAsInt).toList(),
                        Collections.emptyList()))
                .displayName(GsonUtil.checkGetOrDefault(
                        () -> object.has(ItemKeyConstants.DISPLAY_NAME),
                        () -> object.get(ItemKeyConstants.DISPLAY_NAME).getAsString(),
                        null))
                .lore(GsonUtil.checkGetOrDefault(
                        () -> object.has(ItemKeyConstants.LORE) && object.get(ItemKeyConstants.LORE).isJsonArray(),
                        () -> object.get(ItemKeyConstants.DISPLAY_NAME).getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList(),
                        Collections.emptyList()))
                .customModelData(GsonUtil.checkGetOrDefault(
                        () -> object.has(ItemKeyConstants.CUSTOM_MODEL_DATA),
                        () -> object.get(ItemKeyConstants.CUSTOM_MODEL_DATA).getAsInt(),
                        null));
    }

    static <T extends SimpleItem> void serialize(final @NotNull T item, final @NotNull JsonObject object) throws JsonParseException {
        Objects.requireNonNull(object, "object");
        object.addProperty(ItemKeyConstants.ITEM, BuiltInRegistries.ITEM.getKey(item.getItem()).toString());
        object.addProperty(ItemKeyConstants.AMOUNT, item.getAmount());
        if (!item.getSlots().isEmpty()) {
            JsonArray slotsArray = new JsonArray();
            item.getSlots().forEach(slotsArray::add);
            object.add(ItemKeyConstants.SLOTS, slotsArray);
        }
        if (item.getDisplayName() != null) {
            object.addProperty(ItemKeyConstants.DISPLAY_NAME, item.getDisplayName());
        }
        if (item.getLore() != null) {
            JsonArray loreArray = new JsonArray();
            item.getLore().forEach(loreArray::add);
            object.add(ItemKeyConstants.LORE, loreArray);
        }
        if (item.getCustomModelData() != null) {
            object.addProperty(ItemKeyConstants.CUSTOM_MODEL_DATA, item.getCustomModelData());
        }
    }


}
