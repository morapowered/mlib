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

package com.morapowered.inventory.gson;

import com.google.gson.GsonBuilder;
import com.morapowered.inventory.gson.inventory.MultiPageInventoryConfigurationSerializer;
import com.morapowered.inventory.gson.inventory.SimplePageInventoryConfigurationSerializer;
import com.morapowered.inventory.gson.item.CommandItemSerializer;
import com.morapowered.inventory.gson.item.PlayerHeadItemSerializer;
import com.morapowered.inventory.gson.item.SimpleItemSerializer;
import com.morapowered.inventory.gson.item.gson.ItemTypeAdaper;
import com.morapowered.inventory.gson.serializer.GsonItemSerializer;
import com.morapowered.inventory.item.ItemType;
import com.morapowered.inventory.item.SimpleItem;
import com.morapowered.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GsonInventorySerializerImpl implements GsonInventorySerializer {

    public static final GsonInventorySerializer INSTANCE = new BuilderImpl().build();

    public static final Map<ResourceLocation, GsonItemSerializer<?>> DEFAULT_ITEM_SERIALIZERS =
            Map.of(ItemType.SIMPLE.getKey(), SimpleItemSerializer.INSTANCE,
                    ItemType.COMMAND.getKey(), new CommandItemSerializer(),
                    ItemType.PLAYER_HEAD.getKey(), new PlayerHeadItemSerializer());

    private final HashMap<ResourceLocation, GsonItemSerializer<?>> itemSerializers;

    GsonInventorySerializerImpl(HashMap<ResourceLocation, GsonItemSerializer<?>> itemSerializers) {
        this.itemSerializers = Objects.requireNonNull(itemSerializers, "itemSerializers");
    }

    @Override
    public @NotNull GsonBuilder applyOnBuilder(@NotNull GsonBuilder builder) {
        return builder.registerTypeAdapter(SimpleItem.class, new ItemTypeAdaper(itemSerializers))
                .registerTypeAdapter(SimplePageInventoryConfiguration.class, new SimplePageInventoryConfigurationSerializer())
                .registerTypeAdapter(MultiPageInventoryConfigurationSerializer.class, new MultiPageInventoryConfigurationSerializer());
    }

    static final class BuilderImpl implements GsonInventorySerializer.Builder {

        private final HashMap<ResourceLocation, GsonItemSerializer<?>> itemSerializers = new HashMap<>(DEFAULT_ITEM_SERIALIZERS);

        BuilderImpl() {
        }

        @Override
        public @NotNull <T extends SimpleItem> Builder register(@NotNull ItemType type, @NotNull GsonItemSerializer<T> serializer) {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(serializer, "serializer");
            if (itemSerializers.containsKey(type.getKey())) {
                throw new IllegalArgumentException("Already set serializer to " + type.getKey().toString());
            }
            itemSerializers.put(type.getKey(), serializer);
            return this;
        }

        @Override
        public @NotNull GsonInventorySerializer build() {
            return new GsonInventorySerializerImpl(itemSerializers);
        }
    }
}
