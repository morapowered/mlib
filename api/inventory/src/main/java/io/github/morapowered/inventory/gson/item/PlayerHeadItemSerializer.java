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

package io.github.morapowered.inventory.gson.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.morapowered.inventory.gson.serializer.GsonItemSerializer;
import io.github.morapowered.inventory.gson.util.GsonUtil;
import io.github.morapowered.inventory.item.types.PlayerHeadItem;
import io.github.morapowered.inventory.util.ItemKeyConstants;
import org.jetbrains.annotations.NotNull;

public class PlayerHeadItemSerializer implements GsonItemSerializer<PlayerHeadItem> {

    @Override
    public PlayerHeadItem deserialize(@NotNull JsonObject object) throws JsonParseException {
        return GsonAbstractItemSerializer.deserialize(PlayerHeadItem.builder(), object)
                .playerUniqueId(GsonUtil.checkGetOrDefault(
                        () -> object.has(ItemKeyConstants.PLAYER_UUID),
                        () -> GsonUtil.uuidOrNull(object.get(ItemKeyConstants.PLAYER_UUID)),
                        null))
                .build();
    }

    @Override
    public void serialize(@NotNull PlayerHeadItem item, @NotNull JsonObject object) throws JsonParseException {
        GsonAbstractItemSerializer.serialize(item, object);
        if (item.getPlayerUniqueId() != null) {
            object.addProperty(ItemKeyConstants.PLAYER_UUID, item.getPlayerUniqueId().toString());
        }
    }

}
