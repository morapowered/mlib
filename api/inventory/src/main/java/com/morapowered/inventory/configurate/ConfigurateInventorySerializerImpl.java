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

package com.morapowered.inventory.configurate;

import com.morapowered.inventory.configurate.inventory.MultiPageInventoryConfigurationSerializer;
import com.morapowered.inventory.configurate.inventory.SimplePageInventoryConfigurationSerializer;
import com.morapowered.inventory.configurate.item.CommandItemSerializer;
import com.morapowered.inventory.configurate.item.PlayerHeadItemSerializer;
import com.morapowered.inventory.configurate.item.SimpleItemSerializer;
import com.morapowered.inventory.configurate.item.configurate.ItemTypeSerializer;
import com.morapowered.inventory.configurate.serializer.ConfigurateItemSerializer;
import com.morapowered.inventory.item.ItemType;
import com.morapowered.inventory.item.SimpleItem;
import com.morapowered.inventory.pages.multi.config.MultiPageInventoryConfiguration;
import com.morapowered.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigurateInventorySerializerImpl implements ConfigurateInventorySerializer {

    public static final Map<ResourceLocation, ConfigurateItemSerializer<?>> DEFAULT_ITEM_SERIALIZERS =
            Map.of(ItemType.SIMPLE.getKey(), SimpleItemSerializer.INSTANCE,
                    ItemType.COMMAND.getKey(), new CommandItemSerializer(),
                    ItemType.PLAYER_HEAD.getKey(), new PlayerHeadItemSerializer());
    public static final ConfigurateInventorySerializer INSTANCE = new BuilderImpl().build();

    private final TypeSerializerCollection serializers;

    ConfigurateInventorySerializerImpl(TypeSerializerCollection.Builder serializerBuilder, HashMap<ResourceLocation, ConfigurateItemSerializer<?>> itemSerializers) {
        this.serializers = Objects.requireNonNull(serializerBuilder, "serializerBuilder")
                .register(SimpleItem.class, new ItemTypeSerializer(itemSerializers))
                .registerExact(SimplePageInventoryConfiguration.class, new SimplePageInventoryConfigurationSerializer())
                .registerExact(MultiPageInventoryConfiguration.class, new MultiPageInventoryConfigurationSerializer())
                .build();
    }

    @Override
    public @NotNull TypeSerializerCollection serializers() {
        return serializers;
    }

    static final class BuilderImpl implements Builder {

        private final TypeSerializerCollection.Builder serializerBuilder = TypeSerializerCollection.builder();
        private final HashMap<ResourceLocation, ConfigurateItemSerializer<? extends SimpleItem>> itemSerializers = new HashMap<>(DEFAULT_ITEM_SERIALIZERS);

        BuilderImpl() {
        }

        @Override
        public @NotNull <T> Builder register(@NotNull Class<T> clazz, @NotNull TypeSerializer<T> serializer) {
            serializerBuilder.register(Objects.requireNonNull(clazz, "clazz"), Objects.requireNonNull(serializer, "serializer"));
            return this;
        }

        @Override
        public @NotNull <T extends SimpleItem> Builder register(@NotNull ItemType type, @NotNull ConfigurateItemSerializer<T> serializer) {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(serializer, "serializer");
            if (itemSerializers.containsKey(type.getKey())) {
                throw new IllegalArgumentException("Already set serializer to " + type.getKey().toString());
            }
            itemSerializers.put(type.getKey(), serializer);
            return this;
        }


        @Override
        public @NotNull ConfigurateInventorySerializer build() {
            return new ConfigurateInventorySerializerImpl(serializerBuilder, itemSerializers);
        }
    }
}
